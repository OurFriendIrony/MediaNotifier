package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get

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
@JsonPropertyOrder("air_date", "episode_number", "id", "name", "overview", "production_code", "season_number", "show_id", "still_path", "vote_average", "vote_count")
class TVShowGetLastEpisodeToAir {
    @get:JsonProperty("air_date")
    @set:JsonProperty("air_date")
    @JsonProperty("air_date")
    var airDate: String? = null

    @get:JsonProperty("episode_number")
    @set:JsonProperty("episode_number")
    @JsonProperty("episode_number")
    var episodeNumber: Int? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("overview")
    @set:JsonProperty("overview")
    @JsonProperty("overview")
    var overview: String? = null

    @get:JsonProperty("production_code")
    @set:JsonProperty("production_code")
    @JsonProperty("production_code")
    var productionCode: String? = null

    @get:JsonProperty("season_number")
    @set:JsonProperty("season_number")
    @JsonProperty("season_number")
    var seasonNumber: Int? = null

    @get:JsonProperty("show_id")
    @set:JsonProperty("show_id")
    @JsonProperty("show_id")
    var showId: Int? = null

    @get:JsonProperty("still_path")
    @set:JsonProperty("still_path")
    @JsonProperty("still_path")
    var stillPath: String? = null

    @get:JsonProperty("vote_average")
    @set:JsonProperty("vote_average")
    @JsonProperty("vote_average")
    var voteAverage: Double? = null

    @get:JsonProperty("vote_count")
    @set:JsonProperty("vote_count")
    @JsonProperty("vote_count")
    var voteCount: Int? = null

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