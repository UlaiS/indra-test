package mx.ulai.indra.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mx.ulai.indra.db.dao.*
import mx.ulai.indra.model.*

@Database(
    entities = [
        MovieDetailsResponse::class,
        MovieVideosResponse::class,
        MoviesResponse::class,
        MoviesTwoResponse::class,
        MovieResponse::class
    ], version = 1
)
@TypeConverters(TypeConverter::class)
abstract class IndraDB: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun moviesDao(): MoviesDao
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun movieVideoDao(): MovieVideoDao
    abstract fun moviesTwoDao(): MoviesTwoDao

}