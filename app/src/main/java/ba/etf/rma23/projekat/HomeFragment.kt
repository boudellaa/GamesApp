package ba.etf.rma23.projekat


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getSavedGames
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.saveGame
import ba.etf.rma23.projekat.data.repositories.GamesRepository

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    private lateinit var gameList: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var games = GameData.getAll()
    private var isGameOpened = false
    private lateinit var searchButton: Button
    private lateinit var searchText: EditText


    private fun showGameDetails(game: Game) {
        val bundle = bundleOf("id" to game.id)
        isGameOpened = true
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val gameDetailsFragment = GameDetailsFragment()
            gameDetailsFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.game_details_fragment_container, gameDetailsFragment).commit()
        } else {
            findNavController().navigate(R.id.gameDetailsItem, bundle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?) : View? {

        val view = inflater.inflate(R.layout.home_fragment, container, false)
        gameList = view.findViewById(R.id.game_list)
        gameListAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game)}
        gameList.adapter = gameListAdapter
        gameList.layoutManager = GridLayoutManager(activity, 1)
        //gameListAdapter.updateGames(games)
        searchButton = view.findViewById(R.id.search_button)
        searchText = view.findViewById(R.id.search_query_edittext)
        //lifecycleScope.launch { val games1 = getSavedGames()
        //gameListAdapter.updateGames(games1)}

        runBlocking {
            games = GamesRepository.getGamesByName("")!!
            games = GamesRepository.sortGames()
        gameListAdapter.updateGames(games)}
        isGameOpened = savedInstanceState != null

        val config : Configuration = resources.configuration
        if(config.orientation == Configuration.ORIENTATION_PORTRAIT) {

            val navView: BottomNavigationView = activity!!.findViewById(R.id.bottom_nav)
            navView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.gameDetailsItem -> {
                        gameListAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game)}
                        gameList.adapter = gameListAdapter
                        gameList.layoutManager = GridLayoutManager(activity, 1)
                        val bundle =
                            bundleOf("game_title" to GameData.getDetails(arguments?.getString("game_title")!!)!!.title)
                        findNavController().navigate(R.id.gameDetailsItem, bundle)
                        true
                    }
                    else -> false
                }
            }

            val bottom = activity!!.findViewById<BottomNavigationView>(R.id.bottom_nav)
            bottom?.menu?.findItem(R.id.gameDetailsItem)?.isEnabled = isGameOpened
            bottom?.menu?.findItem(R.id.homeItem)?.isChecked = true
        }

        searchButton.setOnClickListener{
            search()
        }
        return view
    }

    private fun search() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            games = GamesRepository.getGamesByName(searchText.text.toString())!!
            gameListAdapter.updateGames(games)
        }
    }


}

