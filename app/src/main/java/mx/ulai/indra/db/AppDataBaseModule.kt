package mx.ulai.indra.db

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(context: Application): IndraDB{
        return Room.databaseBuilder(context.applicationContext, IndraDB::class.java, "indra_db")
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(@NonNull indraDB: IndraDB) = indraDB.movieDao()

    @Provides
    @Singleton
    fun provideMoviesDao(@NonNull indraDB: IndraDB) = indraDB.moviesDao()

    @Provides
    @Singleton
    fun provideMoviesTwoDao(@NonNull indraDB: IndraDB) = indraDB.moviesTwoDao()

    @Provides
    @Singleton
    fun provideMovieDetailDao(@NonNull indraDB: IndraDB) = indraDB.movieDetailsDao()

    @Provides
    @Singleton
    fun provideMovieVideoDao(@NonNull indraDB: IndraDB) = indraDB.movieVideoDao()

}