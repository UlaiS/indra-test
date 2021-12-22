package mx.ulai.indra.repository

import androidx.lifecycle.LiveData
import mx.ulai.indra.api.ApiMovieV1
import mx.ulai.indra.api.ApiResponse
import mx.ulai.indra.application.IndraApplicationExecutors
import mx.ulai.indra.db.dao.MovieDetailsDao
import mx.ulai.indra.model.MovieDetailsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRepository @Inject constructor(
    private val appExecutor: IndraApplicationExecutors,
    private val movieDetailsDao: MovieDetailsDao,
    private val apiMovieV1: ApiMovieV1
){
    fun loadMovieDetails(idMovie: Int): LiveData<Resource<MovieDetailsResponse>> {
        return object: NetworkBoundResource<MovieDetailsResponse, MovieDetailsResponse>(appExecutor){
            override fun saveCallResult(item: MovieDetailsResponse) {
                movieDetailsDao.insert(item)
            }

            override fun shouldFetch(data: MovieDetailsResponse?): Boolean {
                return data == null
            }

            override fun loadFromDB(): LiveData<MovieDetailsResponse> {
                return movieDetailsDao.selectFromMovieDetails(idMovie)
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailsResponse>> {
                return apiMovieV1.getMovieDetails(idMovie)
            }
        }.asLiveData()
    }
}