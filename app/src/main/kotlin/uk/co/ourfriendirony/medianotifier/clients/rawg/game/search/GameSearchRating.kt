package uk.co.ourfriendirony.medianotifier.clients.rawg.game.search

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("id", "title", "count", "percent")
class GameSearchRating {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("title")
    @set:JsonProperty("title")
    @JsonProperty("title")
    var title: String? = null

    @get:JsonProperty("count")
    @set:JsonProperty("count")
    @JsonProperty("count")
    var count: Int? = null

    @get:JsonProperty("percent")
    @set:JsonProperty("percent")
    @JsonProperty("percent")
    var percent: Double? = null
}