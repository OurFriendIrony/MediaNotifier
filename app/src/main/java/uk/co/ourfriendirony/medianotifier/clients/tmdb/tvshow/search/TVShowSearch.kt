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
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("page", "total_results", "total_pages", "results")
class TVShowSearch {
    @get:JsonProperty("page")
    @set:JsonProperty("page")
    @JsonProperty("page")
    var page: Int? = null

    @get:JsonProperty("total_results")
    @set:JsonProperty("total_results")
    @JsonProperty("total_results")
    var totalResults: Int? = null

    @get:JsonProperty("total_pages")
    @set:JsonProperty("total_pages")
    @JsonProperty("total_pages")
    var totalPages: Int? = null

    @get:JsonProperty("results")
    @set:JsonProperty("results")
    @JsonProperty("results")
    var results: List<TVShowSearchResult>? = null

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