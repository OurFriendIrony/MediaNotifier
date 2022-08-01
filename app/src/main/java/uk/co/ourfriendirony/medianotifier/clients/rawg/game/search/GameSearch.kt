package uk.co.ourfriendirony.medianotifier.clients.rawg.game.search

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("count", "next", "previous", "results", "user_platforms")
class GameSearch {
    @get:JsonProperty("count")
    @set:JsonProperty("count")
    @JsonProperty("count")
    var count: Int? = null

    @get:JsonProperty("next")
    @set:JsonProperty("next")
    @JsonProperty("next")
    var next: String? = null

    @get:JsonProperty("previous")
    @set:JsonProperty("previous")
    @JsonProperty("previous")
    var previous: Any? = null

    @get:JsonProperty("results")
    @set:JsonProperty("results")
    @JsonProperty("results")
    var results: List<GameSearchResult>? = null

    @get:JsonProperty("user_platforms")
    @set:JsonProperty("user_platforms")
    @JsonProperty("user_platforms")
    var userPlatforms: Boolean? = null
}