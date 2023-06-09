package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.FavoriteGames
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getHash
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AccountApi {

    @POST("account/{aid}/game")
    suspend fun saveGame( @Body body: RequestBody, @Path("aid") acHash: String = getHash()!!)

    @DELETE("account/{aid}/game/{gid}/")
    suspend fun removeGame(@Path("gid") gid : Int, @Path("aid") acHash: String = getHash()!!)

    @GET("account/{aid}/games")
    suspend fun getSavedGames(@Path("aid") aid: String = getHash()!!) : Response<List<FavoriteGames>>

    @GET("/games")
    suspend fun getGamesContainingString(@Query("search") query: String): Response<List<Game>>


}