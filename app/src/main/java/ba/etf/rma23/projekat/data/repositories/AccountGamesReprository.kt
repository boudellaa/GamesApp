package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGameById
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


object AccountGamesRepository {
    private var age : Int = 0
    private var acHash : String? = null
    private var favoriteGames: MutableList<Game> = mutableListOf()

    fun setHash(hash: String): Boolean {
        return try {
            this.acHash = hash
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getHash(): String? {
        return acHash
    }

    suspend fun getSavedGames(): List<Game> {
        val a = AccountApiConfig.retrofit.getSavedGames().body()
        val games = mutableListOf<Game>()
        if(a==null || a.isEmpty()) return listOf()
        for(game in a){
            games.add(getGameById(game.igdbId)[0])
        }
        favoriteGames = games
        return games
    }


    suspend fun saveGame(game : Game) : Game{
        return withContext(Dispatchers.IO){
            val jsonObject = JSONObject()
            val gameJsonObject = JSONObject()
            gameJsonObject.put("igdb_id",game.id)
            gameJsonObject.put("name",game.title)
            jsonObject.put("game",gameJsonObject)
            val mediaType = "application/json".toMediaTypeOrNull()
            AccountApiConfig.retrofit.saveGame(jsonObject.toString().toRequestBody(mediaType))
            favoriteGames.add(game)
            return@withContext game
        }
    }

    fun setAge(age: Int): Boolean {
        if (age in 3..100) {
            this.age = age
            return true
        }
        return false
    }

    fun getAge() : Int{
        return age
    }

    fun removeNonSafe(): Boolean {
        return try {
            val safeGames = favoriteGames.filter { it.rating >= age }
            favoriteGames = safeGames.toMutableList()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getGamesContainingString(query: String): List<Game> {
        return try {
            val response = IGDBApiConfig.retrofit.getGamesContainingString(query)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun removeGame(id: Int): Boolean {
        return try {
            val gameToRemove = favoriteGames.firstOrNull { it.id == id }
            gameToRemove?.let {
                favoriteGames.remove(it)
                AccountApiConfig.retrofit.removeGame(id, getHash()!!)
                true
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

}


