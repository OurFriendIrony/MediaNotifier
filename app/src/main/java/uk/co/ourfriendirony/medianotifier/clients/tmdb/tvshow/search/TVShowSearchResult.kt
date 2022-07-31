package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.search

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonProperty
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get.MovieGetBelongsToCollection
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get.MovieGetGenre
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get.MovieGetProductionCompany
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get.MovieGetProductionCountry
import com.fasterxml.jackson.annotation.JsonFormat
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get.MovieGetSpokenLanguage
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get.MovieGetExternalIds
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.search.MovieSearchResult
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get.TVShowGetCreatedBy
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get.TVShowGetLastEpisodeToAir
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get.TVShowGetNetwork
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get.TVShowGetSeason
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.search.TVShowSearchResult
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get.TVSeasonGetEpisode
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get.TVSeasonGetExternalIds
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get.TVSeasonGetCrew
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get.TVSeasonGetGuestStar
import uk.co.ourfriendirony.medianotifier.clients.AbstractClient
import kotlin.Throws
import uk.co.ourfriendirony.medianotifier.clients.tmdb.TMDBClient
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.search.MovieSearch
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.search.TVShowSearch
import uk.co.ourfriendirony.medianotifier.mediaitem.tv.TVShow
import uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get.MovieGet
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get.TVShowGet
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get.TVSeasonGet
import uk.co.ourfriendirony.medianotifier.mediaitem.tv.TVEpisode
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("original_name", "genre_ids", "name", "popularity", "origin_country", "vote_count", "first_air_date", "backdrop_path", "original_language", "id", "vote_average", "overview", "poster_path")
class TVShowSearchResult {
    @get:JsonProperty("original_name")
    @set:JsonProperty("original_name")
    @JsonProperty("original_name")
    var originalName: String? = null

    @get:JsonProperty("genre_ids")
    @set:JsonProperty("genre_ids")
    @JsonProperty("genre_ids")
    var genreIds: List<Int>? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("popularity")
    @set:JsonProperty("popularity")
    @JsonProperty("popularity")
    var popularity: Double? = null

    @get:JsonProperty("origin_country")
    @set:JsonProperty("origin_country")
    @JsonProperty("origin_country")
    var originCountry: List<String>? = null

    @get:JsonProperty("vote_count")
    @set:JsonProperty("vote_count")
    @JsonProperty("vote_count")
    var voteCount: Int? = null

    @get:JsonProperty("first_air_date")
    @set:JsonProperty("first_air_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("first_air_date")
    var firstAirDate: Date? = null

    @get:JsonProperty("backdrop_path")
    @set:JsonProperty("backdrop_path")
    @JsonProperty("backdrop_path")
    var backdropPath: String? = null

    @get:JsonProperty("original_language")
    @set:JsonProperty("original_language")
    @JsonProperty("original_language")
    var originalLanguage: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("vote_average")
    @set:JsonProperty("vote_average")
    @JsonProperty("vote_average")
    var voteAverage: Double? = null

    @get:JsonProperty("overview")
    @set:JsonProperty("overview")
    @JsonProperty("overview")
    var overview: String? = null

    @get:JsonProperty("poster_path")
    @set:JsonProperty("poster_path")
    @JsonProperty("poster_path")
    var posterPath: String? = null

    @JsonIgnore
    private val additionalProperties: MutableMap<String, Any> = HashMap()
    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        additionalProperties[name] = value
    }
}