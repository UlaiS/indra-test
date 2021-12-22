package mx.ulai.indra.model

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "movie", primaryKeys = ["id"])
data class MovieResponse(
    val adult : Boolean,
    val backdrop_path : String,
    val genre_ids : IntArray,
    val id : Int,
    val original_language : String,
    val original_title : String,
    val overview : String,
    val popularity : String,
    val poster_path : String,
    val release_date : String,
    val title : String,
    val video : Boolean,
    val vote_average : Double,
    val vote_count : Int
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieResponse

        if (adult != other.adult) return false
        if (backdrop_path != other.backdrop_path) return false
        if (!genre_ids.contentEquals(other.genre_ids)) return false
        if (id != other.id) return false
        if (original_language != other.original_language) return false
        if (original_title != other.original_title) return false
        if (overview != other.overview) return false
        if (popularity != other.popularity) return false
        if (poster_path != other.poster_path) return false
        if (release_date != other.release_date) return false
        if (title != other.title) return false
        if (video != other.video) return false
        if (vote_average != other.vote_average) return false

        return true
    }

    override fun hashCode(): Int {
        var result = adult.hashCode()
        result = 31 * result + backdrop_path.hashCode()
        result = 31 * result + genre_ids.contentHashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + original_language.hashCode()
        result = 31 * result + original_title.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + popularity.hashCode()
        result = 31 * result + poster_path.hashCode()
        result = 31 * result + release_date.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + video.hashCode()
        result = 31 * result + vote_average.hashCode()
        return result
    }
}