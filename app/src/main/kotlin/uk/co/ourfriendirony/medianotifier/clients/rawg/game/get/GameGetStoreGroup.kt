package uk.co.ourfriendirony.medianotifier.clients.rawg.game.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("id", "url", "store")
class GameGetStoreGroup {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("url")
    @set:JsonProperty("url")
    @JsonProperty("url")
    var url: String? = null

    @get:JsonProperty("store")
    @set:JsonProperty("store")
    @JsonProperty("store")
    var store: GameGetStore? = null
}