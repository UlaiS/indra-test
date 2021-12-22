package mx.ulai.indra.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.ulai.indra.model.MoviesResponse

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: MoviesResponse)

    @Query("SELECT * FROM movies WHERE page = :page AND categorized = :categorized")
    fun selectFromMoviesPage(page: Int, categorized: Int): MoviesResponse

    @Query("SELECT * FROM movies WHERE page = :page AND categorized = :categorized")
    fun selectFromMoviesLiveData(page: Int, categorized: Int): LiveData<MoviesResponse>

}