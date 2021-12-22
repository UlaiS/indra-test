package mx.ulai.indra.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movies", primaryKeys = ["page"])
data class MoviesResponse (
    var page: Int,
    var categorized: Int,
    @field:SerializedName("results")
    var movie_response: MutableList<MovieResponse>,
    val total_pages : Int,
    val total_results : Int,
): Serializable {
}