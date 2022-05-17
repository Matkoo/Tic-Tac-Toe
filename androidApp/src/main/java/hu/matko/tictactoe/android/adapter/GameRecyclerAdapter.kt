package hu.matko.tictactoe.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.matko.tictactoe.android.R
import hu.matko.tictactoe.android.model.Game

/**
 * @author Matkovics Gergely<br></br>
 * E-mail: [gergelymatkovics82@gmail.com](mailto:gergelymatkovics82@gmail.com)
 */

class GameRecyclerAdapter(gameList: List<Game>, mContext: Context) :
    RecyclerView.Adapter<GameRecyclerAdapter.GameVH>() {
    private var gameList: List<Game> = ArrayList()
    private val mContext: Context
    private val lastPlace = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val holder: RecyclerView.ViewHolder = GameVH(view)
        return holder as GameVH
    }

    override fun onBindViewHolder(holder: GameVH, position: Int) {
        holder.tVDate.text = gameList[position].gameDate.toString()
        holder.tvWinner.text = gameList[position].winner.toString()
        holder.tvGameSteps.text = gameList[position].gamePos.toString()
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    inner class GameVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tVDate: TextView
        var tvWinner: TextView
        var tvGameSteps: TextView

        init {
            tVDate = itemView.findViewById(R.id.tVDate)
            tvWinner = itemView.findViewById(R.id.tvWinner)
            tvGameSteps = itemView.findViewById(R.id.tvGameSteps)
        }
    }

    init {
        this.gameList = gameList
        this.mContext = mContext
    }
}