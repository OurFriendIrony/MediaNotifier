package uk.co.ourfriendirony.medianotifier.clients.rawg.game.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("platform", "released_at", "requirements")
class GameGetPlatformGroup {
    @get:JsonProperty("platform")
    @set:JsonProperty("platform")
    @JsonProperty("platform")
    var platform: GameGetPlatform? = null

    @get:JsonProperty("released_at")
    @set:JsonProperty("released_at")
    @JsonProperty("released_at")
    var releasedAt: String? = null

    @get:JsonProperty("requirements")
    @set:JsonProperty("requirements")
    @JsonProperty("requirements")
    var requirements: Any? = null
}