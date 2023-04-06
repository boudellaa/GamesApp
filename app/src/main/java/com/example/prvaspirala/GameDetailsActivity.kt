package com.example.prvaspirala

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GameDetailsActivity : AppCompatActivity() {

    private lateinit var gameTitle : TextView
    private lateinit var coverImage: ImageView
    private lateinit var platform : TextView
    private lateinit var releaseDate : TextView
    private lateinit var esrbRating : TextView
    private lateinit var developer : TextView
    private lateinit var publisher : TextView
    private lateinit var genre : TextView
    private lateinit var description : TextView
    private lateinit var homeButton : Button
    private lateinit var detailsButton : Button
    private lateinit var game : Game

    private lateinit var reviewAdapter: GameReviewAdapter
    private lateinit var reviewList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val extras = intent.extras
        if(extras != null){
            game = getGameByTitle(extras.getString("game_title", ""))
        }else{
            finish()
        }

        gameTitle = findViewById<TextView>(R.id.game_title_textview)
        coverImage = findViewById(R.id.cover_imageview)
        platform = findViewById<TextView>(R.id.platform_textview)
        releaseDate = findViewById<TextView>(R.id.release_date_textview)
        esrbRating = findViewById<TextView>(R.id.esrb_rating_textview)
        developer = findViewById<TextView>(R.id.developer_textview)
        publisher = findViewById<TextView>(R.id.publisher_textview)
        genre = findViewById<TextView>(R.id.genre_textview)
        description = findViewById<TextView>(R.id.description_textview)
        homeButton = findViewById<Button>(R.id.home_button)
        detailsButton = findViewById<Button>(R.id.details_button)

        // Postavi tekst, slike i druge vrijednosti na View-ove iz XML-a
        gameTitle.text = game.title
        coverImage.setImageResource(platform.context.resources.getIdentifier(game.coverImage, "drawable", platform.context.packageName))
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esrbRating.text = game.esrbRating
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description

        // Inicijalizacija RecyclerView i ReviewAdapter
        reviewList = findViewById(R.id.review_list)
        reviewList.layoutManager = LinearLayoutManager(this)
        reviewAdapter = GameReviewAdapter(GameData.Games.getDetails(gameTitle.text as String)!!.userImpressions.sortedByDescending { it.timestamp })
        reviewList.adapter = reviewAdapter

        homeButton.isEnabled = true
        detailsButton.isEnabled = false
        homeButton.setOnClickListener { home() }
    }
    private fun home(){
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("game_title", game.title)
        }
        try{
            startActivity(intent)
        }
        catch(e: ActivityNotFoundException){

        }
    }

    private fun getGameByTitle(name:String):Game{
        val games: ArrayList<Game> = arrayListOf()
        games.addAll(GameData.Games.getAll())
        val game= games.find { game -> name == game.title }
        return game?:Game("Test","Test","Test",2.0,"Test","Test","Test", "Test"
        , "Test", "Test", mutableListOf()
        )
    }
}