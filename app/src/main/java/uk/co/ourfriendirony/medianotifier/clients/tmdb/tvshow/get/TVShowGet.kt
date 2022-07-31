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
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("backdrop_path", "created_by", "episode_run_time", "first_air_date", "genres", "homepage", "id", "in_production", "languages", "last_air_date", "last_episode_to_air", "name", "next_episode_to_air", "networks", "number_of_episodes", "number_of_seasons", "origin_country", "original_language", "original_name", "overview", "popularity", "poster_path", "production_companies", "seasons", "status", "type", "vote_average", "vote_count", "external_ids")
class TVShowGet {
    @get:JsonProperty("backdrop_path")
    @set:JsonProperty("backdrop_path")
    @JsonProperty("backdrop_path")
    var backdropPath: String? = null

    @get:JsonProperty("created_by")
    @set:JsonProperty("created_by")
    @JsonProperty("created_by")
    var tVShowGetCreatedBy: List<TVShowGetCreatedBy>? = null

    @get:JsonProperty("episode_run_time")
    @set:JsonProperty("episode_run_time")
    @JsonProperty("episode_run_time")
    var episodeRunTime: List<Int>? = null

    @get:JsonProperty("first_air_date")
    @set:JsonProperty("first_air_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("first_air_date")
    var firstAirDate: Date? = null

    @get:JsonProperty("genres")
    @set:JsonProperty("genres")
    @JsonProperty("genres")
    var genres: List<MovieGetGenre>? = null

    @get:JsonProperty("homepage")
    @set:JsonProperty("homepage")
    @JsonProperty("homepage")
    var homepage: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("in_production")
    @set:JsonProperty("in_production")
    @JsonProperty("in_production")
    var inProduction: Boolean? = null

    @get:JsonProperty("languages")
    @set:JsonProperty("languages")
    @JsonProperty("languages")
    var languages: List<String>? = null

    @get:JsonProperty("last_air_date")
    @set:JsonProperty("last_air_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("last_air_date")
    var lastAirDate: Date? = null

    @get:JsonProperty("last_episode_to_air")
    @set:JsonProperty("last_episode_to_air")
    @JsonProperty("last_episode_to_air")
    var tVShowGetLastEpisodeToAir: TVShowGetLastEpisodeToAir? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("next_episode_to_air")
    @set:JsonProperty("next_episode_to_air")
    @JsonProperty("next_episode_to_air")
    var nextEpisodeToAir: Any? = null

    @get:JsonProperty("networks")
    @set:JsonProperty("networks")
    @JsonProperty("networks")
    var tVShowGetNetworks: List<TVShowGetNetwork>? = null

    @get:JsonProperty("number_of_episodes")
    @set:JsonProperty("number_of_episodes")
    @JsonProperty("number_of_episodes")
    var numberOfEpisodes: Int? = null

    @get:JsonProperty("number_of_seasons")
    @set:JsonProperty("number_of_seasons")
    @JsonProperty("number_of_seasons")
    var numberOfSeasons: Int? = null

    @get:JsonProperty("origin_country")
    @set:JsonProperty("origin_country")
    @JsonProperty("origin_country")
    var originCountry: List<String>? = null

    @get:JsonProperty("original_language")
    @set:JsonProperty("original_language")
    @JsonProperty("original_language")
    var originalLanguage: String? = null

    @get:JsonProperty("original_name")
    @set:JsonProperty("original_name")
    @JsonProperty("original_name")
    var originalName: String? = null

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

    @get:JsonProperty("seasons")
    @set:JsonProperty("seasons")
    @JsonProperty("seasons")
    var tVShowGetSeasons: List<TVShowGetSeason>? = null

    @get:JsonProperty("status")
    @set:JsonProperty("status")
    @JsonProperty("status")
    var status: String? = null

    @get:JsonProperty("type")
    @set:JsonProperty("type")
    @JsonProperty("type")
    var type: String? = null

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
    var externalIds: MovieGetExternalIds? = null

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