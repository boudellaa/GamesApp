package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.google.gson.*
import java.lang.reflect.Type

class Deserializer : JsonDeserializer<Game> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Game {
        val jsonObject = json?.asJsonObject
        val id = jsonObject?.get("id")?.asInt ?: 0
        val title = jsonObject?.get("name")?.asString ?: ""
        val platform = jsonObject?.getAsJsonArray("platforms")?.get(0)?.asJsonObject?.get("name")?.asString ?: ""
        val releaseDate = jsonObject?.getAsJsonArray("release_dates")?.get(0)?.asJsonObject?.get("human")?.asString ?: ""
        val rating = jsonObject?.getAsJsonArray("age_ratings")?.get(0)?.asJsonObject?.get("rating")?.asDouble ?: 0.0
        val coverImage = jsonObject?.get("cover")?.asJsonObject?.get("url")?.toString() ?: ""
        val esrbRating = jsonObject?.getAsJsonArray("age_ratings")?.get(0)?.asJsonObject?.get("rating")?.asString ?: ""
        val developer = jsonObject?.getAsJsonArray("involved_companies")?.get(0)?.asJsonObject?.get("company")?.asJsonObject?.get("name")?.asString ?: ""
        val publisher = jsonObject?.getAsJsonArray("involved_companies")?.get(0)?.asJsonObject?.get("company")?.asJsonObject?.get("name")?.asString ?: ""
        val genre = jsonObject?.getAsJsonArray("genres")?.get(0)?.asJsonObject?.get("name")?.asString ?: ""
        val description = jsonObject?.get("summary")?.asString ?: ""

        return Game(id, title, platform, releaseDate, rating, coverImage, esrbRating, developer, publisher, genre, description, emptyList())
    }
}