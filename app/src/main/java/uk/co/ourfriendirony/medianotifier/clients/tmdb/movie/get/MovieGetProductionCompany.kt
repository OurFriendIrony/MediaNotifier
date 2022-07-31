package uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get

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
@JsonPropertyOrder("id", "logo_path", "name", "origin_country")
class MovieGetProductionCompany {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("logo_path")
    @set:JsonProperty("logo_path")
    @JsonProperty("logo_path")
    var logoPath: String? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("origin_country")
    @set:JsonProperty("origin_country")
    @JsonProperty("origin_country")
    var originCountry: String? = null

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