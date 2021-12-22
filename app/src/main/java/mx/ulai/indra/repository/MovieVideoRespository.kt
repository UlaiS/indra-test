package mx.ulai.indra.repository

import androidx.lifecycle.LiveData
import mx.ulai.indra.api.ApiMovieV1
import mx.ulai.indra.api.ApiResponse
import mx.ulai.indra.application.IndraApplicationExecutors
import mx.ulai.indra.db.dao.MovieVideoDao
import mx.ulai.indra.model.MovieVideosResponse
import mx.ulai.indra.util.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieVideoRespository @Inject constructor(
    private val appExecutor: IndraApplicationExecutors,
    private val moviesVideoDao: MovieVideoDao,
    private val apiMovieV1: ApiMovieV1
){

    private val moviesListRateLimiter = RateLimiter<Int>(2, TimeUnit.MINUTES)
    fun loadMovieVideo(idMovie: Int): LiveData<Resource<MovieVideosResponse>> {
        return object: NetworkBoundResource<MovieVideosResponse, MovieVideosResponse>(appExecutor){
            override fun saveCallResult(item: MovieVideosResponse) {
                moviesVideoDao.insert(item)
            }

            override fun shouldFetch(data: MovieVideosResponse?): Boolean {
                return data == null || data.movie_video_response.isEmpty() || moviesListRateLimiter.shoulFetch(idMovie)
            }

            override fun loadFromDB(): LiveData<MovieVideosResponse> {
                return moviesVideoDao.selectFromVideoMovie(idMovie)
            }

            override fun createCall(): LiveData<ApiResponse<MovieVideosResponse>> {
                return apiMovieV1.getMovieVideos(idMovie)
            }

            override fun onFetchFailed() {
                super.onFetchFailed()
                moviesListRateLimiter.reset(idMovie)
            }
        }.asLiveData()
    }
}