package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface IGDBApi {
    @GET("games")
    suspend fun getGamesByName(
        @Query("search") name: String,
        @Query("fields") fields: String = "id,name,platforms.name,release_dates.human,age_ratings.rating,genres.name,cover.url,platforms.name,involved_companies.company.name",
        @Header("Client-ID") client_id : String = "v20et8avgw4altypcocw4rjiraajl2",
        @Header("Authorization") authorization : String = "Bearer fef18e3xl933zi13w14u0573oba8wn"
    ): Response<List<Game>>

    @Headers(
        "Client-ID: v20et8avgw4altypcocw4rjiraajl2",
        "Authorization: Bearer fef18e3xl933zi13w14u0573oba8wn",
        "Content-Type: application/json"
    )
    @POST("games")
    suspend fun getGameById(
        @Body body: RequestBody
    ): Response<List<Game>>

    @GET("/games")
    suspend fun getGamesContainingString(@Query("search") query: String): Response<List<Game>>


}