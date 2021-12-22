package mx.ulai.indra.api

import androidx.lifecycle.LiveData
import mx.ulai.indra.model.MovieDetailsResponse
import mx.ulai.indra.model.MovieVideosResponse
import mx.ulai.indra.model.MoviesResponse
import mx.ulai.indra.model.MoviesTwoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovieV1 {


    @GET("movie/popular")
    fun getMovieMostPopular(): LiveData<ApiResponse<MoviesResponse>>

    @GET("movie/popular")
    fun getMovieMostPopularNextPage(@Query("page") page: Int): Call<MoviesResponse>

    @GET("movie/now_playing")
    fun getMovieNowPlaying(): LiveData<ApiResponse<MoviesTwoResponse>>

    @GET("movie/now_playing")
    fun getMovieNowPlayingNextPage(@Query("page") page: Int): Call<MoviesTwoResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): LiveData<ApiResponse<MovieDetailsResponse>>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(@Path("movie_id") id: Int): LiveData<ApiResponse<MovieVideosResponse>>

}