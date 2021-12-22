package mx.ulai.indra.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "movie_info_video", primaryKeys = ["id_movie_info_video"])
data class MovieVideosResponse (
    @field:SerializedName("id")
    val id_movie_info_video: Int,
    @field:SerializedName("results")
    val movie_video_response: List<MovieVideoResponse>
):Serializable