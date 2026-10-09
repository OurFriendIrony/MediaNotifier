package uk.co.ourfriendirony.medianotifier.clients.rawg.game.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("platform", "released_at", "requirements")
class GameSearchPlatformGroup {
    @get:JsonProperty("platform")
    @set:JsonProperty("platform")
    @JsonProperty("platform")
    var platform: GameSearchPlatform? = null

    @get:JsonProperty("released_at")
    @set:JsonProperty("released_at")
    @JsonProperty("released_at")
    var releasedAt: String? = null

    @get:JsonProperty("requirements")
    @set:JsonProperty("requirements")
    @JsonProperty("requirements")
    var requirements: Any? = null
}