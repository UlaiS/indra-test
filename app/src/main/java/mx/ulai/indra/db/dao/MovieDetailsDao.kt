package mx.ulai.indra.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.ulai.indra.model.MovieDetailsResponse

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieDetails: MovieDetailsResponse)

    @Query("SELECT * FROM movie_details WHERE id = :id")
    fun selectFromMovieDetails(id: Int): LiveData<MovieDetailsResponse>
}