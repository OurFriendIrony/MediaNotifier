package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "imdb_id",
    "freebase_mid",
    "freebase_id",
    "tvdb_id",
    "tvrage_id",
    "facebook_id",
    "instagram_id",
    "twitter_id"
)
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
}