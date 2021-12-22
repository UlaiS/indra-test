package mx.ulai.indra.api

import android.app.Application
import dagger.Module
import dagger.Provides
import mx.ulai.indra.util.Constants
import mx.ulai.indra.util.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiMovieModule {
    @Provides
    @Singleton
    fun provideApiMovie(context: Application): ApiMovieV1 {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(OkHttpClient.Builder().addInterceptor {
                val url = it
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", Constants.API_KEY)
                    .addQueryParameter("language", Constants.LANGUAGE)
                    .build()
                it.proceed(it.request().newBuilder().url(url).build())
            }.build()
            ).build()
            .create(ApiMovieV1::class.java)
    }
}