package uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.search

import com.fasterxml.jackson.annotation.*
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "popularity",
    "vote_count",
    "video",
    "poster_path",
    "id",
    "adult",
    "backdrop_path",
    "original_language",
    "original_title",
    "genre_ids",
    "title",
    "vote_average",
    "overview",
    "release_date"
)
class MovieSearchResult {
    @get:JsonProperty("popularity")
    @set:JsonProperty("popularity")
    @JsonProperty("popularity")
    var popularity: Double? = null

    @get:JsonProperty("vote_count")
    @set:JsonProperty("vote_count")
    @JsonProperty("vote_count")
    var voteCount: Int? = null

    @get:JsonProperty("video")
    @set:JsonProperty("video")
    @JsonProperty("video")
    var video: Boolean? = null

    @get:JsonProperty("poster_path")
    @set:JsonProperty("poster_path")
    @JsonProperty("poster_path")
    var posterPath: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("adult")
    @set:JsonProperty("adult")
    @JsonProperty("adult")
    var adult: Boolean? = null

    @get:JsonProperty("backdrop_path")
    @set:JsonProperty("backdrop_path")
    @JsonProperty("backdrop_path")
    var backdropPath: String? = null

    @get:JsonProperty("original_language")
    @set:JsonProperty("original_language")
    @JsonProperty("original_language")
    var originalLanguage: String? = null

    @get:JsonProperty("original_title")
    @set:JsonProperty("original_title")
    @JsonProperty("original_title")
    var originalTitle: String? = null

    @get:JsonProperty("genre_ids")
    @set:JsonProperty("genre_ids")
    @JsonProperty("genre_ids")
    var genreIds: List<Int>? = null

    @get:JsonProperty("title")
    @set:JsonProperty("title")
    @JsonProperty("title")
    var title: String? = null

    @get:JsonProperty("vote_average")
    @set:JsonProperty("vote_average")
    @JsonProperty("vote_average")
    var voteAverage: Double? = null

    @get:JsonProperty("overview")
    @set:JsonProperty("overview")
    @JsonProperty("overview")
    var overview: String? = null

    @get:JsonProperty("release_date")
    @set:JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("release_date")
    var releaseDate: Date? = null
}