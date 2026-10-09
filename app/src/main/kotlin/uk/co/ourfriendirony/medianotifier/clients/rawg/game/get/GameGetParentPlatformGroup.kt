package uk.co.ourfriendirony.medianotifier.clients.rawg.game.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("platform")
class GameGetParentPlatformGroup {
    @get:JsonProperty("platform")
    @set:JsonProperty("platform")
    @JsonProperty("platform")
    var platform: GameGetParentPlatform? = null
}