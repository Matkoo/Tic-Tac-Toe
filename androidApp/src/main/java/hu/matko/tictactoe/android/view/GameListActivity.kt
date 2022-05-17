package hu.matko.tictactoe.android.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import hu.matko.tictactoe.android.R
import hu.matko.tictactoe.android.adapter.GameRecyclerAdapter
import hu.matko.tictactoe.android.model.Game

/**
 * @author Matkovics Gergely<br></br>
 * E-mail: [gergelymatkovics82@gmail.com](mailto:gergelymatkovics82@gmail.com)
 */

open class GameListActivity : AppCompatActivity() {
    private var gameList: List<Game?>? = null
    private var adapter: GameRecyclerAdapter? = null


    var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_list)

        val backToMain = findViewById(R.id.backToMain) as Button
        backToMain.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
    }



    private fun initDatabase() {
//        val database = GameDatabase.getInstance(applicationContext)
//        database.gameDAO().allGame.subscribeOn(Schedulers.computation())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : SingleObserver<List<Game?>?> {
//                override fun onSubscribe(d: Disposable) {}
//                override fun onSuccess(games: List<Game?>) {
//                    gameList = ArrayList(games)
//                    //The last game will a first on the list
//                    Collections.reverse(gameList)
//                    initList()
//                }
//
//                override fun onError(e: Throwable) {}
//            })
    }

    private fun initList() {
//    adapter = GameRecyclerAdapter(gameList as List<Game>, baseContext)
//    recyclerView!!.layoutManager = LinearLayoutManager(this)
//    recyclerView!!.adapter = adapter
    }
}