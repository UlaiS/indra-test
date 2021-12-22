package mx.ulai.indra.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.ulai.indra.model.MoviesTwoResponse

@Dao
interface MoviesTwoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: MoviesTwoResponse)

    @Query("SELECT * FROM movies_two WHERE page = :page AND categorized = :categorized")
    fun selectFromMoviesPage(page: Int, categorized: Int): MoviesTwoResponse

    @Query("SELECT * FROM movies_two WHERE page = :page AND categorized = :categorized")
    fun selectFromMoviesLiveData(page: Int, categorized: Int): LiveData<MoviesTwoResponse>

}