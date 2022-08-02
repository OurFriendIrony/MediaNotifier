package uk.co.ourfriendirony.medianotifier.clients.rawg.game.search

import com.fasterxml.jackson.annotation.*
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "slug",
    "name",
    "playtime",
    "platforms",
    "stores",
    "released",
    "tba",
    "background_image",
    "rating",
    "rating_top",
    "ratings",
    "ratings_count",
    "reviews_text_count",
    "added",
    "added_by_status",
    "metacritic",
    "suggestions_count",
    "id",
    "score",
    "clip",
    "tags",
    "user_game",
    "reviews_count",
    "saturated_color",
    "dominant_color",
    "short_screenshots",
    "parent_platforms",
    "genres"
)
class GameSearchResult {
    @get:JsonProperty("slug")
    @set:JsonProperty("slug")
    @JsonProperty("slug")
    var slug: String? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("playtime")
    @set:JsonProperty("playtime")
    @JsonProperty("playtime")
    var playtime: Int? = null

    @get:JsonProperty("platforms")
    @set:JsonProperty("platforms")
    @JsonProperty("platforms")
    var platforms: List<GameSearchPlatformGroup>? = null

    @get:JsonProperty("stores")
    @set:JsonProperty("stores")
    @JsonProperty("stores")
    var stores: List<GameSearchStore>? = null

    @get:JsonProperty("released")
    @set:JsonProperty("released")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("released")
    var released: Date? = null

    @get:JsonProperty("tba")
    @set:JsonProperty("tba")
    @JsonProperty("tba")
    var tba: Boolean? = null

    @get:JsonProperty("background_image")
    @set:JsonProperty("background_image")
    @JsonProperty("background_image")
    var backgroundImage: String? = null

    @get:JsonProperty("rating")
    @set:JsonProperty("rating")
    @JsonProperty("rating")
    var rating: Double? = null

    @get:JsonProperty("rating_top")
    @set:JsonProperty("rating_top")
    @JsonProperty("rating_top")
    var ratingTop: Int? = null

    @get:JsonProperty("ratings")
    @set:JsonProperty("ratings")
    @JsonProperty("ratings")
    var ratings: List<GameSearchRating>? = null

    @get:JsonProperty("ratings_count")
    @set:JsonProperty("ratings_count")
    @JsonProperty("ratings_count")
    var ratingsCount: Int? = null

    @get:JsonProperty("reviews_text_count")
    @set:JsonProperty("reviews_text_count")
    @JsonProperty("reviews_text_count")
    var reviewsTextCount: Int? = null

    @get:JsonProperty("added")
    @set:JsonProperty("added")
    @JsonProperty("added")
    var added: Int? = null

    @get:JsonProperty("added_by_status")
    @set:JsonProperty("added_by_status")
    @JsonProperty("added_by_status")
    var addedByStatus: GameSearchAddedByStatus? = null

    @get:JsonProperty("metacritic")
    @set:JsonProperty("metacritic")
    @JsonProperty("metacritic")
    var metacritic: Int? = null

    @get:JsonProperty("suggestions_count")
    @set:JsonProperty("suggestions_count")
    @JsonProperty("suggestions_count")
    var suggestionsCount: Int? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("score")
    @set:JsonProperty("score")
    @JsonProperty("score")
    var score: String? = null

    @get:JsonProperty("clip")
    @set:JsonProperty("clip")
    @JsonProperty("clip")
    var clip: Any? = null

    @get:JsonProperty("tags")
    @set:JsonProperty("tags")
    @JsonProperty("tags")
    var tags: List<GameSearchTag>? = null

    @get:JsonProperty("user_game")
    @set:JsonProperty("user_game")
    @JsonProperty("user_game")
    var userGame: Any? = null

    @get:JsonProperty("reviews_count")
    @set:JsonProperty("reviews_count")
    @JsonProperty("reviews_count")
    var reviewsCount: Int? = null

    @get:JsonProperty("saturated_color")
    @set:JsonProperty("saturated_color")
    @JsonProperty("saturated_color")
    var saturatedColor: String? = null

    @get:JsonProperty("dominant_color")
    @set:JsonProperty("dominant_color")
    @JsonProperty("dominant_color")
    var dominantColor: String? = null

    @get:JsonProperty("short_screenshots")
    @set:JsonProperty("short_screenshots")
    @JsonProperty("short_screenshots")
    var shortScreenshots: List<GameSearchShortScreenshot>? = null

    @get:JsonProperty("parent_platforms")
    @set:JsonProperty("parent_platforms")
    @JsonProperty("parent_platforms")
    var parentPlatforms: List<GameSearchParentPlatformGroup>? = null

    @get:JsonProperty("genres")
    @set:JsonProperty("genres")
    @JsonProperty("genres")
    var genres: List<GameSearchGenre>? = null
}