package uk.co.ourfriendirony.medianotifier.clients.rawg.game.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("yet", "owned", "beaten", "toplay", "dropped", "playing")
class GameSearchAddedByStatus {
    @get:JsonProperty("yet")
    @set:JsonProperty("yet")
    @JsonProperty("yet")
    var yet: Int? = null

    @get:JsonProperty("owned")
    @set:JsonProperty("owned")
    @JsonProperty("owned")
    var owned: Int? = null

    @get:JsonProperty("beaten")
    @set:JsonProperty("beaten")
    @JsonProperty("beaten")
    var beaten: Int? = null

    @get:JsonProperty("toplay")
    @set:JsonProperty("toplay")
    @JsonProperty("toplay")
    var toplay: Int? = null

    @get:JsonProperty("dropped")
    @set:JsonProperty("dropped")
    @JsonProperty("dropped")
    var dropped: Int? = null

    @get:JsonProperty("playing")
    @set:JsonProperty("playing")
    @JsonProperty("playing")
    var playing: Int? = null
}