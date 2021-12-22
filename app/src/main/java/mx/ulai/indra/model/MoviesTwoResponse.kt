package mx.ulai.indra.model

import androidx.annotation.Nullable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movies_two", primaryKeys = ["page"])
data class MoviesTwoResponse (
    @Nullable
    val dates: Dates?,
    var page: Int,
    var categorized: Int,
    @field:SerializedName("results")
    var movie_response: MutableList<MovieResponse>,
    val total_pages : Int,
    val total_results : Int,
): Serializable {
     data class Dates(
         val maximum: String,
         val minimum: String
     )
}