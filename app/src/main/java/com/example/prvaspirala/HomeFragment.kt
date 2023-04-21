package com.example.prvaspirala


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var gameList: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var games = GameData.getAll()

    private var isGameOpened = false



    private fun showGameDetails(game: Game) {
        val bundle = Bundle()
        bundle.putString("game_title", game.title)
        isGameOpened = true
        findNavController().navigate(R.id.gameDetailsItem, bundle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?) : View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        gameList = view.findViewById(R.id.game_list)
        gameListAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game)}
        gameList.adapter = gameListAdapter
        gameList.layoutManager = GridLayoutManager(activity, 1)
        gameListAdapter.updateGames(games)

        isGameOpened = savedInstanceState != null

        val navView: BottomNavigationView = activity!!.findViewById(R.id.bottom_nav)
        navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.gameDetailsItem -> {
                    val bundle = bundleOf("game_title" to GameData.getDetails(arguments?.getString("game_title")!!)!!.title)
                    findNavController().navigate(R.id.gameDetailsItem, bundle)
                    true
                }
                else -> false
            }
        }

        val bottom = activity!!.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom?.menu?.findItem(R.id.gameDetailsItem)?.isEnabled = isGameOpened
        bottom?.menu?.findItem(R.id.homeItem)?.isChecked = true

        return view
    }




}