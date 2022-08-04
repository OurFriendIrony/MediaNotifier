package uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get

import com.fasterxml.jackson.annotation.*
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "adult",
    "backdrop_path",
    "belongs_to_collection",
    "budget",
    "movieGetGenres",
    "homepage",
    "id",
    "imdb_id",
    "original_language",
    "original_title",
    "overview",
    "popularity",
    "poster_path",
    "production_companies",
    "production_countries",
    "release_date",
    "revenue",
    "runtime",
    "spoken_languages",
    "status",
    "tagline",
    "title",
    "video",
    "vote_average",
    "vote_count",
    "external_ids"
)
class MovieGet {
    @get:JsonProperty("adult")
    @set:JsonProperty("adult")
    @JsonProperty("adult")
    var adult: Boolean? = null

    @get:JsonProperty("backdrop_path")
    @set:JsonProperty("backdrop_path")
    @JsonProperty("backdrop_path")
    var backdropPath: String? = null

    @get:JsonProperty("belongs_to_collection")
    @set:JsonProperty("belongs_to_collection")
    @JsonProperty("belongs_to_collection")
    var belongsToCollection: MovieGetBelongsToCollection? = null

    @get:JsonProperty("budget")
    @set:JsonProperty("budget")
    @JsonProperty("budget")
    var budget: Int? = null

    @get:JsonProperty("movieGetGenres")
    @set:JsonProperty("movieGetGenres")
    @JsonProperty("movieGetGenres")
    var movieGetGenres: List<MovieGetGenre>? = null

    @get:JsonProperty("homepage")
    @set:JsonProperty("homepage")
    @JsonProperty("homepage")
    var homepage: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("imdb_id")
    @set:JsonProperty("imdb_id")
    @JsonProperty("imdb_id")
    var imdbId: String? = null

    @get:JsonProperty("original_language")
    @set:JsonProperty("original_language")
    @JsonProperty("original_language")
    var originalLanguage: String? = null

    @get:JsonProperty("original_title")
    @set:JsonProperty("original_title")
    @JsonProperty("original_title")
    var originalTitle: String? = null

    @get:JsonProperty("overview")
    @set:JsonProperty("overview")
    @JsonProperty("overview")
    var overview: String? = null

    @get:JsonProperty("popularity")
    @set:JsonProperty("popularity")
    @JsonProperty("popularity")
    var popularity: Double? = null

    @get:JsonProperty("poster_path")
    @set:JsonProperty("poster_path")
    @JsonProperty("poster_path")
    var posterPath: String? = null

    @get:JsonProperty("production_companies")
    @set:JsonProperty("production_companies")
    @JsonProperty("production_companies")
    var productionCompanies: List<MovieGetProductionCompany>? = null

    @get:JsonProperty("production_countries")
    @set:JsonProperty("production_countries")
    @JsonProperty("production_countries")
    var productionCountries: List<MovieGetProductionCountry>? = null

    @get:JsonProperty("release_date")
    @set:JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("release_date")
    var releaseDate: Date? = null

    @get:JsonProperty("revenue")
    @set:JsonProperty("revenue")
    @JsonProperty("revenue")
    var revenue: Long? = null

    @get:JsonProperty("runtime")
    @set:JsonProperty("runtime")
    @JsonProperty("runtime")
    var runtime: Int? = null

    @get:JsonProperty("spoken_languages")
    @set:JsonProperty("spoken_languages")
    @JsonProperty("spoken_languages")
    var movieGetSpokenLanguages: List<MovieGetSpokenLanguage>? = null

    @get:JsonProperty("status")
    @set:JsonProperty("status")
    @JsonProperty("status")
    var status: String? = null

    @get:JsonProperty("tagline")
    @set:JsonProperty("tagline")
    @JsonProperty("tagline")
    var tagline: String? = null

    @get:JsonProperty("title")
    @set:JsonProperty("title")
    @JsonProperty("title")
    var title: String? = null

    @get:JsonProperty("video")
    @set:JsonProperty("video")
    @JsonProperty("video")
    var video: Boolean? = null

    @get:JsonProperty("vote_average")
    @set:JsonProperty("vote_average")
    @JsonProperty("vote_average")
    var voteAverage: Double? = null

    @get:JsonProperty("vote_count")
    @set:JsonProperty("vote_count")
    @JsonProperty("vote_count")
    var voteCount: Int? = null

    @get:JsonProperty("external_ids")
    @set:JsonProperty("external_ids")
    @JsonProperty("external_ids")
    var movieGetExternalIds: MovieGetExternalIds? = null
}