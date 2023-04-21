package com.example.prvaspirala


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prvaspirala.GameData.Games.getDetails
import com.google.android.material.bottomnavigation.BottomNavigationView


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

    private lateinit var reviewAdapter: GameReviewAdapter
    private lateinit var reviewList: RecyclerView

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
        val game1 : Game? = getDetails(arguments!!.getString("game_title", "")!!)
        if(game1 != null){
            game = game1
        }
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
        reviewList = view.findViewById(R.id.review_list)
        reviewList.layoutManager = GridLayoutManager(activity, 1)
        reviewAdapter = GameReviewAdapter(GameData.Games.getDetails(gameTitle.text as String)!!.userImpressions.sortedByDescending { it.timestamp })
        reviewList.adapter = reviewAdapter


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
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottom = activity!!.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom?.menu?.findItem(R.id.gameDetailsItem)?.isEnabled = true

    }

}