package mx.ulai.indra.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.ulai.indra.api.*
import mx.ulai.indra.db.IndraDB
import mx.ulai.indra.model.MoviesTwoResponse
import mx.ulai.indra.util.Constants.CATEGORIZED_NOW_PLAYING
import java.io.IOException

class FetchNextPageTaskTwo constructor(
    private val page: Int,
    private val categorized: Int,
    private val apiMovieV1: ApiMovieV1,
    private val db: IndraDB
): Runnable {
    private val _liveData = MutableLiveData<Resource<MoviesTwoResponse>>()
    val liveData: LiveData<Resource<MoviesTwoResponse>> = _liveData


    override fun run() {
        val current: MoviesTwoResponse?  =
            db.moviesTwoDao().selectFromMoviesPage(page, CATEGORIZED_NOW_PLAYING)

        current?.let {
            _liveData.postValue(Resource.success(current))
            return
        }

        val newValue = try {
            val response  =
                apiMovieV1.getMovieNowPlayingNextPage(page).execute()
            val apiResponse = ApiResponse.create(response)
            when(apiResponse){
                is SuccessResponse -> {
                    try{
                        apiResponse.body.categorized = categorized
                        db.beginTransaction()
                        db.moviesTwoDao().insert(apiResponse.body)
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