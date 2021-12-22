package mx.ulai.indra.repository

import androidx.lifecycle.LiveData
import mx.ulai.indra.api.ApiMovieV1
import mx.ulai.indra.api.ApiResponse
import mx.ulai.indra.application.IndraApplicationExecutors
import mx.ulai.indra.db.IndraDB
import mx.ulai.indra.db.dao.MoviesDao
import mx.ulai.indra.model.MoviesResponse
import mx.ulai.indra.util.Constants.CATEGORIZED_MOST_POPULAR
import mx.ulai.indra.util.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieMostPopularRepository @Inject constructor(
    private val appExecutor: IndraApplicationExecutors,
    private val moviesDao: MoviesDao,
    private val db: IndraDB,
    private val apiMovieV1: ApiMovieV1
){
    private val moviesListRateLimiter = RateLimiter<Int>(2, TimeUnit.MINUTES)
    fun loadMovies(page: Int?): LiveData<Resource<MoviesResponse>> {
        return object: NetworkBoundResource<MoviesResponse, MoviesResponse>(appExecutor){
            override fun saveCallResult(item: MoviesResponse) {
                item.categorized = CATEGORIZED_MOST_POPULAR
                moviesDao.insert(item)
            }

            override fun shouldFetch(data: MoviesResponse?): Boolean {
                return data == null || data.movie_response.isEmpty() || moviesListRateLimiter.shoulFetch(CATEGORIZED_MOST_POPULAR)
            }

            override fun loadFromDB(): LiveData<MoviesResponse> =  moviesDao.selectFromMoviesLiveData(page?: 1, CATEGORIZED_MOST_POPULAR)


            override fun createCall(): LiveData<ApiResponse<MoviesResponse>> = apiMovieV1.getMovieMostPopular()


            override fun onFetchFailed() {
                super.onFetchFailed()
                moviesListRateLimiter.reset(CATEGORIZED_MOST_POPULAR)
            }
        }.asLiveData()
    }

    fun nextPage(page: Int?): LiveData<Resource<MoviesResponse>>{
        val fetchNextPageTask = FetchNextPageTask(
            page = page?: 1,
            categorized = CATEGORIZED_MOST_POPULAR,
            apiMovieV1 = apiMovieV1,
            db = db
        )
        appExecutor.networkIO().execute(fetchNextPageTask)
        return fetchNextPageTask.liveData
    }
}