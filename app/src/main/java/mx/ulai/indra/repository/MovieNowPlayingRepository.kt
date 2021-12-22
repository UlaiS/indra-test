package mx.ulai.indra.repository

import androidx.lifecycle.LiveData
import mx.ulai.indra.api.ApiMovieV1
import mx.ulai.indra.api.ApiResponse
import mx.ulai.indra.application.IndraApplicationExecutors
import mx.ulai.indra.db.IndraDB
import mx.ulai.indra.db.dao.MoviesTwoDao
import mx.ulai.indra.model.MoviesTwoResponse
import mx.ulai.indra.util.Constants.CATEGORIZED_NOW_PLAYING
import mx.ulai.indra.util.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieNowPlayingRepository @Inject constructor(
    private val appExecutor: IndraApplicationExecutors,
    private val moviesDao: MoviesTwoDao,
    private val db: IndraDB,
    private val apiMovieV1: ApiMovieV1
){
    private val moviesListRateLimiter = RateLimiter<Int>(2, TimeUnit.MINUTES)
    fun loadNowPlayingMovies(page: Int?): LiveData<Resource<MoviesTwoResponse>> {
        return object: NetworkBoundResource<MoviesTwoResponse, MoviesTwoResponse>(appExecutor){
            override fun saveCallResult(item: MoviesTwoResponse) {
                item.categorized = CATEGORIZED_NOW_PLAYING
                moviesDao.insert(item)
            }

            override fun shouldFetch(data: MoviesTwoResponse?): Boolean {
                return data == null || data.movie_response.isEmpty() || moviesListRateLimiter.shoulFetch(CATEGORIZED_NOW_PLAYING)
            }

            override fun loadFromDB(): LiveData<MoviesTwoResponse> =   moviesDao.selectFromMoviesLiveData(page?: 1, CATEGORIZED_NOW_PLAYING)


            override fun createCall(): LiveData<ApiResponse<MoviesTwoResponse>> = apiMovieV1.getMovieNowPlaying()


            override fun onFetchFailed() {
                super.onFetchFailed()
                moviesListRateLimiter.reset(CATEGORIZED_NOW_PLAYING)
            }
        }.asLiveData()
    }

    fun nextPage(page: Int): LiveData<Resource<MoviesTwoResponse>> {
        val fetchNextPageTask = FetchNextPageTaskTwo(
            page = page,
            categorized = CATEGORIZED_NOW_PLAYING,
            apiMovieV1 = apiMovieV1,
            db = db
        )
        appExecutor.networkIO().execute(fetchNextPageTask)
        return fetchNextPageTask.liveData
    }
}