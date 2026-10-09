package uk.co.ourfriendirony.medianotifier.clients.rawg.game.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("id", "name", "slug")
class GameGetParentPlatform {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("slug")
    @set:JsonProperty("slug")
    @JsonProperty("slug")
    var slug: String? = null
}