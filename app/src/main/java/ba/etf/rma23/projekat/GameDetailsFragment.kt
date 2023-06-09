package ba.etf.rma23.projekat


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.GameData.Games.getAll
import ba.etf.rma23.projekat.GameData.Games.getDetails
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.bumptech.glide.Glide

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class GameDetailsFragment : Fragment() {

    private lateinit var gameTitle : TextView
    private lateinit var coverImage: ImageView
    private lateinit var platform : TextView
    private lateinit var releaseDate : TextView
    private lateinit var esrbRating : TextView
    private lateinit var developer : TextView
    private lateinit var publisher : TextView
    private lateinit var genre : TextView
    private lateinit var description : TextView
    private lateinit var game : Game
    private lateinit var saveButton: Button
    private lateinit var removeButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.game_details_fragment, container, false)
        gameTitle = view.findViewById(R.id.item_title_textview)
        coverImage = view.findViewById(R.id.cover_imageview)
        platform = view.findViewById(R.id.platform_textview)
        releaseDate = view.findViewById(R.id.release_date_textview)
        esrbRating = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)


        val bundle : Bundle? = arguments
        if(bundle != null){
            val gameId : Int? = arguments!!.getInt("id", -1)
            if (gameId != null && gameId != -1) {
                runBlocking {
                    game = GamesRepository.getGameById(gameId).first()
                }
            }
        }

        gameTitle.text = game.title
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esrbRating.text = game.esrbRating
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description

        var url: String = game.coverImage
       url = url.drop(1).dropLast(1)
        url = "https:"+ url
        print("GAME COVER: " + url + "\n")
        /*Glide.with(context!!).load("https:" + game.coverImage).centerCrop().placeholder(R.drawable.logo)
            .error(id).fallback(id).into(coverImage)*/
        val cci = requireContext().resources.getIdentifier("logo", "drawable", coverImage.context.packageName)
        Glide.with(coverImage.context).load(url).error(cci).fallback(cci).into(coverImage)

        val config : Configuration = resources.configuration
        if(config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navView: BottomNavigationView = activity!!.findViewById(R.id.bottom_nav)
            navView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.homeItem -> {
                        val bundle = Bundle()
                        bundle.putString("game_title", game.title);
                        findNavController().navigate(R.id.homeItem, bundle)
                        true
                    }
                    else -> false
                }
            }
        }

        saveButton = view.findViewById(R.id.button1)
        removeButton = view.findViewById(R.id.button2)

        CoroutineScope(Dispatchers.Main).launch {
            val savedGames = AccountGamesRepository.getSavedGames()
            val isGameSaved = savedGames.find { it.id == game.id } != null

            saveButton.isEnabled = !isGameSaved
            removeButton.isEnabled = isGameSaved
        }

        saveButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                AccountGamesRepository.saveGame(game)
                saveButton.isEnabled = false
                removeButton.isEnabled = true
            }
        }

        removeButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val removed = AccountGamesRepository.removeGame(game.id)
                if (removed) {
                    saveButton.isEnabled = true
                    removeButton.isEnabled = false
                }
            }
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottom = activity!!.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom?.menu?.findItem(R.id.gameDetailsItem)?.isEnabled = true

    }

}
