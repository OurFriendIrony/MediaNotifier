package uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("imdb_id", "facebook_id", "instagram_id", "twitter_id")
class MovieGetExternalIds {
    @get:JsonProperty("imdb_id")
    @set:JsonProperty("imdb_id")
    @JsonProperty("imdb_id")
    var imdbId: String? = null

    @get:JsonProperty("facebook_id")
    @set:JsonProperty("facebook_id")
    @JsonProperty("facebook_id")
    var facebookId: Any? = null

    @get:JsonProperty("instagram_id")
    @set:JsonProperty("instagram_id")
    @JsonProperty("instagram_id")
    var instagramId: Any? = null

    @get:JsonProperty("twitter_id")
    @set:JsonProperty("twitter_id")
    @JsonProperty("twitter_id")
    var twitterId: Any? = null
}