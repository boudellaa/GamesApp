package com.example.prvaspirala

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class GameListAdapter(private var games: List<Game>, private val onItemClicked: (game : Game) -> Unit) :
    RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.gameTitleTextView.text = games[position].title
        holder.gameReleaseDateTextView.text = games[position].releaseDate
        holder.gamePlatformTextView.text = games[position].platform
        holder.gameRatingTextView.text = games[position].rating.toString()
        holder.itemView.setOnClickListener{onItemClicked(games[position])}
    }

    override fun getItemCount(): Int {
        return games.size
    }


    fun updateGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gameTitleTextView: TextView
        var gameReleaseDateTextView: TextView
        var gamePlatformTextView: TextView
        var gameRatingTextView: TextView

        init {
            gameTitleTextView = itemView.findViewById(R.id.game_title_textview)
            gameReleaseDateTextView = itemView.findViewById(R.id.game_release_date_textview)
            gamePlatformTextView = itemView.findViewById(R.id.game_platform_textview)
            gameRatingTextView = itemView.findViewById(R.id.game_rating_textview)
        }
    }


}