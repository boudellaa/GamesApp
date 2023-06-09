package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.IGDBApiConfig.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody.Companion.toRequestBody

object GamesRepository {
    val igdbS = IGDBApiConfig.retrofit
    private var displayedGames: MutableList<Game> = mutableListOf()

    suspend fun getGamesByName(name: String): List<Game>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = igdbS.getGamesByName(name)
                displayedGames = response.body() as MutableList<Game>
                response.body()
            } catch (e: Exception) {
                null
            }
        }
    }


    suspend fun getGamesSafe(name: String): List<Game> {
        val userAge = AccountGamesRepository.getAge()
        return withContext(Dispatchers.IO) {
            // Ako userAge nije postavljeno, vrati praznu listu
            if (userAge == null) {
                return@withContext emptyList<Game>()
            }

            val response = igdbS.getGamesByName(name)

            if (response.isSuccessful) {
                val games = response.body() ?: emptyList()
                // Filtrira listu igara prema age rating-u
                games.filter { it.rating >= userAge }
            } else {
                emptyList()
            }
        }
    }

    suspend fun getGameById(id: Int): List<Game> = withContext(Dispatchers.IO) {
        try {
            val stringId = id.toString()
            val string =
                "fields id,name,platforms.name,release_dates.human,age_ratings.rating,genres.name,cover.url,platforms.name,involved_companies.company.name; where id = $stringId;"
            val response = IGDBApiConfig.retrofit.getGameById(string.toRequestBody())
            print(response.body())
            return@withContext response.body()!!
        } catch (e: Exception) {
            print(e.message)
            return@withContext emptyList()
        }

    }


    suspend fun sortGames(): List<Game> {
        val favoriteGames = AccountGamesRepository.getSavedGames()
        val sortedFavorites = favoriteGames.sortedBy { it.title }
        val nonFavorites = displayedGames.filterNot { game -> favoriteGames.contains(game) }.sortedBy { it.title }
        val sortedGames = sortedFavorites + nonFavorites
        displayedGames = sortedGames.toMutableList()
        return sortedGames
    }


}


