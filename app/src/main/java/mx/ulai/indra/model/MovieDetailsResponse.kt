package mx.ulai.indra.model

import androidx.annotation.Nullable
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "movie_details", primaryKeys = ["imdb_id"],)
data class MovieDetailsResponse(
    @Nullable
    val adult: Boolean?,
    @Nullable
    val backdrop_path: String?,
    @Nullable
    val belongs_to_collection: BelongsToCollection??,
    @Nullable
    val budget: Long?,
    @Nullable
    val genres: List<Genres>?,
    @Nullable
    val homepage: String?,
    val id: Int,
    @Nullable
    val id_movie: Int?,
    val imdb_id: String,
    @Nullable
    val original_language: String?,
    @Nullable
    val original_title: String?,
    @Nullable
    val overview: String?,
    @Nullable
    val popularity: Double?,
    @Nullable
    val poster_path: String?,
    @Nullable
    val production_companies: List<ProductionCompanies>?,
    @Nullable
    val production_countries: List<ProductionCountries>?,
    @Nullable
    val release_date: String?,
    @Nullable
    val revenue: Long?,
    @Nullable
    val runtime: Int?,
    @Nullable
    val spoken_languages: List<SpokenLanguages>?,
    @Nullable
    val status: String?,
    @Nullable
    val tagline: String?,
    @Nullable
    val title: String?,
    @Nullable
    val video: Boolean?,
    @Nullable
    val vote_average: Double?,
    @Nullable
    val vote_count: Int?
): Serializable{
    data class BelongsToCollection(
        @Nullable
        val id : Long?,
        @Nullable
        val name : String?,
        @Nullable
        val poster_path : String?,
        @Nullable
        val backdrop_path : String?
    ): Serializable

    data class Genres(
        @Nullable
        val id: Int?,
        @Nullable
        val name: String?
    ): Serializable

    data class ProductionCompanies(
        @Nullable
        val id: Int?,
        @Nullable
        val logo_path: String?,
        @Nullable
        val name: String?,
        @Nullable
        val origin_country: String?
    ): Serializable

    data class ProductionCountries(
        @Nullable
        val iso_3166_1: String?,
        @Nullable
        val name: String?,
    ): Serializable

    data class SpokenLanguages(
        @Nullable
        val english_name: String?,
        @Nullable
        val iso_639_1: String?,
        @Nullable
        val name: String?,
    ): Serializable
}