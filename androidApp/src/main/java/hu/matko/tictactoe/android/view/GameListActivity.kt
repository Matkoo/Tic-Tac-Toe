package hu.matko.tictactoe.android.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.matko.tictactoe.android.R
import hu.matko.tictactoe.android.adapter.GameRecyclerAdapter
import hu.matko.tictactoe.android.model.GameEntity
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * @author Matkovics Gergely<br></br>
 * E-mail: [gergelymatkovics82@gmail.com](mailto:gergelymatkovics82@gmail.com)
 */

open class GameListActivity : AppCompatActivity() {

    private var gameList: List<GameEntity>? = null

    private var adapter: GameRecyclerAdapter? = null

    val db = Database.connect(
        "jdbc:h2:file:/data/data/hu.matko.tictactoe.android/data/data.db",
        "org.h2.Driver"
    )

    var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_list)

        recyclerView = findViewById<RecyclerView>(R.id.rVList)

        val backToMain = findViewById(R.id.backToMain) as Button
        backToMain.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

        initList()
    }


    private fun initList() {
        runBlocking {

        }
        transaction(db) {
            gameList = GameEntity.all().toList()
        }
        Collections.reverse(gameList)
        adapter = GameRecyclerAdapter(gameList as List<GameEntity>, getBaseContext())
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.setAdapter(adapter)
    }
}