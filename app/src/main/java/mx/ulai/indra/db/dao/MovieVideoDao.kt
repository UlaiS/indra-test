package mx.ulai.indra.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.ulai.indra.model.MovieVideosResponse

@Dao
interface MovieVideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieVideos: MovieVideosResponse)


    @Query("SELECT * FROM movie_info_video WHERE id_movie_info_video = :id")
    fun selectFromVideoMovie(id: Int): LiveData<MovieVideosResponse>
}