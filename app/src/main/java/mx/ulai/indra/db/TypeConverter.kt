package mx.ulai.indra.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mx.ulai.indra.model.MovieDetailsResponse.*
import mx.ulai.indra.model.MovieResponse
import mx.ulai.indra.model.MovieVideoResponse
import mx.ulai.indra.model.MoviesTwoResponse.Dates
import java.util.*

object TypeConverter {

    @TypeConverter
    @JvmStatic
    fun stringToIntArray(listInt: String): IntArray {
        return Gson().fromJson(listInt, object : TypeToken<IntArray>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun genreIdsToString(listOfString: IntArray): String {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    @JvmStatic
    fun stringToBelongsToCollection(belongsToCollection: String?): BelongsToCollection? =
        belongsToCollection?.let { Gson().fromJson(belongsToCollection, object : TypeToken<BelongsToCollection>() {}.type)}

    @TypeConverter
    @JvmStatic
    fun belongsToCollectionToString(objectOfString: BelongsToCollection?): String? =
        objectOfString?.let { Gson().toJson(objectOfString) }

    @TypeConverter
    @JvmStatic
    fun stringToGenres(listGenres: String): List<Genres> {
        return Gson().fromJson(listGenres, object : TypeToken<List<Genres>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun genresToString(listOfString: List<Genres>): String {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    @JvmStatic
    fun stringToProductionCompanies(listProductionCompanies: String): List<ProductionCompanies> {
        return Gson().fromJson(listProductionCompanies, object : TypeToken<List<ProductionCompanies>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun productionCompaniesToString(listOfString: List<ProductionCompanies>): String {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    @JvmStatic
    fun stringToProductionCountries(listProductionCountries: String): List<ProductionCountries> {
        return Gson().fromJson(listProductionCountries, object : TypeToken<List<ProductionCountries>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun productionCountriesToString(listOfString: List<ProductionCountries>): String {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    @JvmStatic
    fun stringToResultsMovieResponse(listMovieResponse: String): List<MovieResponse> {
        return Gson().fromJson(listMovieResponse, object : TypeToken<List<MovieResponse>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun resultsMovieResponseToString(listOfString: List<MovieResponse>): String {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    @JvmStatic
    fun stringToResultsMovieVideoResponse(listMovieVideoResponse: String): List<MovieVideoResponse> {
        return Gson().fromJson(listMovieVideoResponse, object : TypeToken<List<MovieVideoResponse>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun resultsMovieVideoResponseToString(listOfString: List<MovieVideoResponse>): String {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    @JvmStatic
    fun stringToSpokenLanguages(listSpokenLanguages: String): List<SpokenLanguages> {
        return Gson().fromJson(listSpokenLanguages, object : TypeToken<List<SpokenLanguages>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun spokenLanguagesToString(listOfString: List<SpokenLanguages>): String {
        return Gson().toJson(listOfString)
    }


    @TypeConverter
    @JvmStatic
    fun stringToDates(dates: String?): Dates? =
        dates?.let { Gson().fromJson(dates, object : TypeToken<Dates>() {}.type) }


    @TypeConverter
    @JvmStatic
    fun datesToString(objectOfString: Dates?): String? =
        objectOfString?.let { Gson().toJson(objectOfString) }

    @TypeConverter
    @JvmStatic
    fun stringToDate(value: Long): Date {
        return value.let { Date(it) }
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(date: Date): Long {
        return date.time.toLong()
    }

}