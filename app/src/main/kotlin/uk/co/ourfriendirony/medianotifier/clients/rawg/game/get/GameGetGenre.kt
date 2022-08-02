package uk.co.ourfriendirony.medianotifier.clients.rawg.game.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("id", "name", "slug", "games_count", "image_background")
class GameGetGenre {
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

    @get:JsonProperty("games_count")
    @set:JsonProperty("games_count")
    @JsonProperty("games_count")
    var gamesCount: Int? = null

    @get:JsonProperty("image_background")
    @set:JsonProperty("image_background")
    @JsonProperty("image_background")
    var imageBackground: String? = null
}