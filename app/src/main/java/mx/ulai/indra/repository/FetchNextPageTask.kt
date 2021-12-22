package mx.ulai.indra.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.ulai.indra.api.*
import mx.ulai.indra.db.IndraDB
import mx.ulai.indra.model.MoviesResponse
import mx.ulai.indra.util.Constants.CATEGORIZED_MOST_POPULAR
import java.io.IOException

class FetchNextPageTask constructor(
    private val page: Int,
    private val categorized: Int,
    private val apiMovieV1: ApiMovieV1,
    private val db: IndraDB
): Runnable {
    private val _liveData = MutableLiveData<Resource<MoviesResponse>>()
    val liveData: LiveData<Resource<MoviesResponse>> = _liveData


    override fun run() {
        val current: MoviesResponse?  =
            db.moviesDao().selectFromMoviesPage(page, CATEGORIZED_MOST_POPULAR)

        current?.let {
            _liveData.postValue(Resource.success(current))
            return
        }

        val newValue = try {
            val response  =
                apiMovieV1.getMovieMostPopularNextPage(page).execute()
            val apiResponse = ApiResponse.create(response)
            when(apiResponse){
                is SuccessResponse -> {
                    try{
                        apiResponse.body.categorized = categorized
                        db.beginTransaction()
                        db.moviesDao().insert(apiResponse.body)
                        db.setTransactionSuccessful()
                    } finally {
                        db.endTransaction()
                    }
                    Resource.success(apiResponse.body)
                }
                is EmptyResponse ->{
                    Resource.success(null)
                }
                is ErrorResponse -> {
                    Resource.error(null, apiResponse.errorMessage)
                }
            }
        } catch (e: IOException){
            Resource.error(null, e.message!!)
        }
        _liveData.postValue(newValue)
        return
    }
}