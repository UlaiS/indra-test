package mx.ulai.indra.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.ulai.indra.model.MovieResponse

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieResponse)

    @Query("SELECT * FROM movie WHERE release_date between :start_date and :end_date /*WHERE id IN (:id)*/ ORDER BY release_date ASC")
    fun selectFromMovieNowPlaying(start_date: String, end_date: String/*id: IntArray*/): LiveData<List<MovieResponse>>

    @Query("SELECT * FROM movie /*WHERE id IN (:id)*/ ORDER BY popularity ASC")
    fun selectFromMovieMostPopular(/*id: IntArray*/): LiveData<List<MovieResponse>>

    @Query("SELECT * FROM movie WHERE  id IN (:id) AND release_date between :start_date and :end_date ORDER BY release_date ASC")
    fun selectFromFavoriteMovieNowPlaying(start_date: String, end_date: String, id: IntArray): LiveData<List<MovieResponse>>

    @Query("SELECT * FROM movie WHERE id IN (:id) ORDER BY popularity DESC")
    fun selectFromFavoriteMovieMostPopular(id: IntArray): LiveData<List<MovieResponse>>
}