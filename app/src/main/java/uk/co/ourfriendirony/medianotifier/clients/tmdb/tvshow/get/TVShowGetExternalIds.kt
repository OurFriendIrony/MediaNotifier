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
@JsonPropertyOrder("imdb_id", "freebase_mid", "freebase_id", "tvdb_id", "tvrage_id", "facebook_id", "instagram_id", "twitter_id")
class TVShowGetExternalIds {
    @get:JsonProperty("imdb_id")
    @set:JsonProperty("imdb_id")
    @JsonProperty("imdb_id")
    var imdbId: String? = null

    @get:JsonProperty("freebase_mid")
    @set:JsonProperty("freebase_mid")
    @JsonProperty("freebase_mid")
    var freebaseMid: String? = null

    @get:JsonProperty("freebase_id")
    @set:JsonProperty("freebase_id")
    @JsonProperty("freebase_id")
    var freebaseId: String? = null

    @get:JsonProperty("tvdb_id")
    @set:JsonProperty("tvdb_id")
    @JsonProperty("tvdb_id")
    var tvdbId: Int? = null

    @get:JsonProperty("tvrage_id")
    @set:JsonProperty("tvrage_id")
    @JsonProperty("tvrage_id")
    var tvrageId: Int? = null

    @get:JsonProperty("facebook_id")
    @set:JsonProperty("facebook_id")
    @JsonProperty("facebook_id")
    var facebookId: String? = null

    @get:JsonProperty("instagram_id")
    @set:JsonProperty("instagram_id")
    @JsonProperty("instagram_id")
    var instagramId: String? = null

    @get:JsonProperty("twitter_id")
    @set:JsonProperty("twitter_id")
    @JsonProperty("twitter_id")
    var twitterId: String? = null

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