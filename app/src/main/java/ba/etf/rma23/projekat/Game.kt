package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class Game(
    val id: Int,
    @SerializedName("name") val title: String,
    @SerializedName("platforms") val platform: String,
    @SerializedName("release_dates") val releaseDate: String,
    @SerializedName("age_ratings") val rating: Double,
    @SerializedName("cover") val coverImage: String,
    @SerializedName("age_ratings.rating") val esrbRating: String,
    @SerializedName("involved_companies") val developer: String,
    @SerializedName("involved_companies.company") val publisher: String,
    @SerializedName("genres") val genre: String,
    @SerializedName("summary") val description: String,
    val userImpressions: List<UserImpression>
)
