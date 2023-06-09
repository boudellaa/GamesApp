package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class FavoriteGames (
    @SerializedName("igdb_id") val igdbId: Int,
    @SerializedName("name") val name: String
)