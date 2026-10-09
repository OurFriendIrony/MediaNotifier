package uk.co.ourfriendirony.medianotifier.clients.rawg.game.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("id", "image")
class GameSearchShortScreenshot {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("image")
    @set:JsonProperty("image")
    @JsonProperty("image")
    var image: String? = null
}