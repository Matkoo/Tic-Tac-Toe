package hu.matko.tictactoe.android.view

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.matko.tictactoe.android.R
import hu.matko.tictactoe.android.model.Game
import hu.matko.tictactoe.android.model.GameEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Matkovics Gergely<br></br>
 * E-mail: [gergelymatkovics82@gmail.com](mailto:gergelymatkovics82@gmail.com)
 */

open class MainActivity : AppCompatActivity() {
    //    private var database: GameDatabase? = null
    var toast: Toast? = null
    var isFinished = false


    // Player representation
    // 0 - X
    // 1 - O
    var activePlayer = 0
    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

    // this function will be called every time a
    // players tap in an empty box of the grid
    fun playerTap(view: View) {
        val img = view as ImageView
        val tappedImage = img.tag.toString().toInt()

        //If the game is not finished
        if (!isFinished) {

            // if the tapped image is empty
            if (gameState[tappedImage] == 2) {
                // increase the counter
                // after every tap
                counter++

                // mark this position
                gameState[tappedImage] = activePlayer

                // this will give a motion
                // effect to the image
                img.translationY = -1000f

                // change the active player
                // from 0 to 1 or 1 to 0
                if (activePlayer == 0) {
                    // set the image of x
                    img.setImageResource(R.drawable.x)
                    activePlayer = 1
                    val status = findViewById<TextView>(R.id.status)

                    // change the status
                    status.text = "O Következik"
                } else {
                    // set the image of o
                    img.setImageResource(R.drawable.o)
                    activePlayer = 0
                    val status = findViewById<TextView>(R.id.status)

                    // change the status
                    status.text = "X Következik"
                }
                img.animate().translationYBy(1000f).duration = 300
            }
            var flag = 0
            // Check if any player has won
            for (winPosition in winPositions) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[1]] == gameState[winPosition[2]] && gameState[winPosition[0]] != 2) {
                    flag = 1

                    // Somebody has won! - Find out who!
                    var winnerStr: String

                    // game reset function be called
                    winnerStr = if (gameState[winPosition[0]] == 0) {
                        "X nyert"
                    } else {
                        "O nyert"
                    }
                    // Update the status bar for winner announcement
                    val status = findViewById<TextView>(R.id.status)
                    status.text = winnerStr
                    saveGame(winnerStr, gameState)
                    isFinished = true
                    toast!!.show()
                }
            }
            // set the status if the match draw
            if (counter == 9 && flag == 0) {
                val status = findViewById<TextView>(R.id.status)
                status.text = "Döntetlen"
                saveGame("Döntetlen", gameState)
                isFinished = true
                toast!!.show()
            }
        }
    }

    fun saveGame(winnerStr: String?, gameState: IntArray) {

        //Set the date pattern
        val pattern = "yyyy.MM.dd HH:mm"
        val simpleDateFormat = SimpleDateFormat(pattern)
        var date = simpleDateFormat.format(Calendar.getInstance().time)
        var gamePosition = String()


        //Save the positions in string
        var counter = 0
        for (i in gameState.indices) {
            if (gameState[i] == 0) {
                gamePosition += "X "
            } else if (gameState[i] == 1) {
                gamePosition += "O "
            } else if (gameState[i] == 2) {
                gamePosition += "_ "
            }
            counter++
            if (counter % 3 == 0) {
                gamePosition += "\n"
            }
        }
//        database!!.gameDAO().saveGame(Game(date, winnerStr, gamePosition))


        runBlocking {
            val db = Database.connect("jdbc:h2:mem:regular", "org.h2.Driver")
//            TransactionManager.manager.defaultIsolationLevel =
//                Connection.TRANSACTION_SERIALIZABLE
            transaction (db) {
                SchemaUtils.create (Game)
                GameEntity.new {
                    gameDate = date
                    winner = winnerStr.toString()
                    gamePos = gamePosition
                }

            }
        }

    }

    // reset the game
    fun gameReset(view: View?) {
        isFinished = false
        counter = 0
        activePlayer = 0
        for (i in gameState.indices) {
            gameState[i] = 2
        }
        // remove all the images from the boxes inside the grid
        (findViewById<View>(R.id.imageView0) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView1) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView2) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView3) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView4) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView5) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView6) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView7) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView8) as ImageView).setImageResource(0)
        val status = findViewById<TextView>(R.id.status)
        status.text = "X Következik"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toast = Toast.makeText(baseContext, "A játék el lett mentve", Toast.LENGTH_LONG)
        toast?.setGravity(Gravity.CENTER, 0, 0)

        val resetGame = findViewById(R.id.resetGame) as Button
        resetGame.setOnClickListener {
            gameReset(view = null)
        }

        val gameList = findViewById(R.id.gameList) as Button
        gameList.setOnClickListener {
            //Reset game before leave the activity
            gameReset(view = null)
            //Start the new activity
            startActivity(Intent(this, GameListActivity::class.java))
        }


    }


    companion object {
        // State meanings:
        // 0 - X
        // 1 - O
        // 2 - Null
        // put all win positions in a 2D array
        var winPositions = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
        )
        var counter = 0
    }
}
