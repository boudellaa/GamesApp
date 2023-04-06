package com.example.prvaspirala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prvaspirala.GameData.Games.getDetails

class HomeActivity : AppCompatActivity() {

    private var lastGameOpened: Game? = null

    private lateinit var logoImage: ImageView
    private lateinit var homeButton: Button
    private lateinit var detailsButton: Button
    private lateinit var searchQuery: EditText
    private lateinit var searchButton: Button
    private lateinit var gameList: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var games = GameData.getAll()
    private lateinit var searchText: EditText

    private fun showGameDetails(game: Game) {
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game_title", game.title)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logoImage = findViewById(R.id.logo_image)
        homeButton = findViewById(R.id.home_button)
        detailsButton = findViewById(R.id.details_button)
        searchQuery = findViewById(R.id.search_query_edittext)
        searchButton = findViewById(R.id.search_button)
        gameList = findViewById(R.id.game_list)

        // postavljanje Adaptera za RecyclerView
        gameListAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game)}
        gameList.adapter = gameListAdapter
        gameList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gameListAdapter.updateGames(games)

        // postavljanje klika na dugmad za navigaciju
        homeButton.setOnClickListener {
            // Onemogući povratak na početnu aktivnost
            homeButton.isEnabled = false
        }


        /* detailsButton.setOnClickListener {
            lastGameOpened?.let { game ->
                // otvori detalje posljednje otvorene igre
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.EXTRA_GAME, game)
                startActivity(intent)
            }
        }
    }*/
        var extras = intent.extras
        if(extras == null){
            detailsButton.isEnabled = false
        }
        else{
            detailsButton.setOnClickListener { showGameDetails(getDetails(extras.getString("game_title",""))!!) }
        }
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            searchText.setText(it)
        }
    }


}
