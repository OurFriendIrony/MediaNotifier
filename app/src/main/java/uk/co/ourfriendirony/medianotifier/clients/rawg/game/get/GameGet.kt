package uk.co.ourfriendirony.medianotifier.clients.rawg.game.get

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonFormat
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetRating
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetReactions
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetAddedByStatus
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetParentPlatformGroup
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetPlatformGroup
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetStoreGroup
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetDeveloper
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetGenre
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetTag
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetPublisher
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetEsrbRating
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetStore
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetPlatform
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetParentPlatform
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchResult
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchPlatformGroup
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchStore
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchRating
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchAddedByStatus
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchTag
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchShortScreenshot
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchParentPlatformGroup
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchGenre
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchPlatform
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchParentPlatform
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "slug", "name", "name_original", "description", "metacritic", "metacritic_platforms", "released", "tba", "updated", "background_image", "background_image_additional", "website", "rating", "rating_top", "ratings", "reactions", "added", "added_by_status", "playtime", "screenshots_count", "movies_count", "creators_count", "achievements_count", "parent_achievements_count", "reddit_url", "reddit_name", "reddit_description", "reddit_logo", "reddit_count", "twitch_count", "youtube_count", "reviews_text_count", "ratings_count", "suggestions_count", "alternative_names", "metacritic_url", "parents_count", "additions_count", "game_series_count", "user_game", "reviews_count", "saturated_color", "dominant_color", "parent_platforms", "platforms", "stores", "developers", "genres", "tags", "publishers", "esrb_rating", "clip", "description_raw")
class GameGet {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("slug")
    @set:JsonProperty("slug")
    @JsonProperty("slug")
    var slug: String? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("name_original")
    @set:JsonProperty("name_original")
    @JsonProperty("name_original")
    var nameOriginal: String? = null

    @get:JsonProperty("description")
    @set:JsonProperty("description")
    @JsonProperty("description")
    var description: String? = null

    @get:JsonProperty("metacritic")
    @set:JsonProperty("metacritic")
    @JsonProperty("metacritic")
    var metacritic: Int? = null

    @get:JsonProperty("metacritic_platforms")
    @set:JsonProperty("metacritic_platforms")
    @JsonProperty("metacritic_platforms")
    var metacriticPlatforms: List<Any>? = null

    @get:JsonProperty("released")
    @set:JsonProperty("released")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("released")
    var released: Date? = null

    @get:JsonProperty("tba")
    @set:JsonProperty("tba")
    @JsonProperty("tba")
    var tba: Boolean? = null

    @get:JsonProperty("updated")
    @set:JsonProperty("updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("updated")
    var updated: Date? = null

    @get:JsonProperty("background_image")
    @set:JsonProperty("background_image")
    @JsonProperty("background_image")
    var backgroundImage: String? = null

    @get:JsonProperty("background_image_additional")
    @set:JsonProperty("background_image_additional")
    @JsonProperty("background_image_additional")
    var backgroundImageAdditional: String? = null

    @get:JsonProperty("website")
    @set:JsonProperty("website")
    @JsonProperty("website")
    var website: String? = null

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
    var ratings: List<GameGetRating>? = null

    @get:JsonProperty("reactions")
    @set:JsonProperty("reactions")
    @JsonProperty("reactions")
    var reactions: GameGetReactions? = null

    @get:JsonProperty("added")
    @set:JsonProperty("added")
    @JsonProperty("added")
    var added: Int? = null

    @get:JsonProperty("added_by_status")
    @set:JsonProperty("added_by_status")
    @JsonProperty("added_by_status")
    var addedByStatus: GameGetAddedByStatus? = null

    @get:JsonProperty("playtime")
    @set:JsonProperty("playtime")
    @JsonProperty("playtime")
    var playtime: Int? = null

    @get:JsonProperty("screenshots_count")
    @set:JsonProperty("screenshots_count")
    @JsonProperty("screenshots_count")
    var screenshotsCount: Int? = null

    @get:JsonProperty("movies_count")
    @set:JsonProperty("movies_count")
    @JsonProperty("movies_count")
    var moviesCount: Int? = null

    @get:JsonProperty("creators_count")
    @set:JsonProperty("creators_count")
    @JsonProperty("creators_count")
    var creatorsCount: Int? = null

    @get:JsonProperty("achievements_count")
    @set:JsonProperty("achievements_count")
    @JsonProperty("achievements_count")
    var achievementsCount: Int? = null

    @get:JsonProperty("parent_achievements_count")
    @set:JsonProperty("parent_achievements_count")
    @JsonProperty("parent_achievements_count")
    var parentAchievementsCount: Int? = null

    @get:JsonProperty("reddit_url")
    @set:JsonProperty("reddit_url")
    @JsonProperty("reddit_url")
    var redditUrl: String? = null

    @get:JsonProperty("reddit_name")
    @set:JsonProperty("reddit_name")
    @JsonProperty("reddit_name")
    var redditName: String? = null

    @get:JsonProperty("reddit_description")
    @set:JsonProperty("reddit_description")
    @JsonProperty("reddit_description")
    var redditDescription: String? = null

    @get:JsonProperty("reddit_logo")
    @set:JsonProperty("reddit_logo")
    @JsonProperty("reddit_logo")
    var redditLogo: String? = null

    @get:JsonProperty("reddit_count")
    @set:JsonProperty("reddit_count")
    @JsonProperty("reddit_count")
    var redditCount: Int? = null

    @get:JsonProperty("twitch_count")
    @set:JsonProperty("twitch_count")
    @JsonProperty("twitch_count")
    var twitchCount: Int? = null

    @get:JsonProperty("youtube_count")
    @set:JsonProperty("youtube_count")
    @JsonProperty("youtube_count")
    var youtubeCount: Int? = null

    @get:JsonProperty("reviews_text_count")
    @set:JsonProperty("reviews_text_count")
    @JsonProperty("reviews_text_count")
    var reviewsTextCount: Int? = null

    @get:JsonProperty("ratings_count")
    @set:JsonProperty("ratings_count")
    @JsonProperty("ratings_count")
    var ratingsCount: Int? = null

    @get:JsonProperty("suggestions_count")
    @set:JsonProperty("suggestions_count")
    @JsonProperty("suggestions_count")
    var suggestionsCount: Int? = null

    @get:JsonProperty("alternative_names")
    @set:JsonProperty("alternative_names")
    @JsonProperty("alternative_names")
    var alternativeNames: List<String>? = null

    @get:JsonProperty("metacritic_url")
    @set:JsonProperty("metacritic_url")
    @JsonProperty("metacritic_url")
    var metacriticUrl: String? = null

    @get:JsonProperty("parents_count")
    @set:JsonProperty("parents_count")
    @JsonProperty("parents_count")
    var parentsCount: Int? = null

    @get:JsonProperty("additions_count")
    @set:JsonProperty("additions_count")
    @JsonProperty("additions_count")
    var additionsCount: Int? = null

    @get:JsonProperty("game_series_count")
    @set:JsonProperty("game_series_count")
    @JsonProperty("game_series_count")
    var gameSeriesCount: Int? = null

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

    @get:JsonProperty("parent_platforms")
    @set:JsonProperty("parent_platforms")
    @JsonProperty("parent_platforms")
    var parentPlatforms: List<GameGetParentPlatformGroup>? = null

    @get:JsonProperty("platforms")
    @set:JsonProperty("platforms")
    @JsonProperty("platforms")
    var platforms: List<GameGetPlatformGroup>? = null

    @get:JsonProperty("stores")
    @set:JsonProperty("stores")
    @JsonProperty("stores")
    var stores: List<GameGetStoreGroup>? = null

    @get:JsonProperty("developers")
    @set:JsonProperty("developers")
    @JsonProperty("developers")
    var developers: List<GameGetDeveloper>? = null

    @get:JsonProperty("genres")
    @set:JsonProperty("genres")
    @JsonProperty("genres")
    var genres: List<GameGetGenre>? = null

    @get:JsonProperty("tags")
    @set:JsonProperty("tags")
    @JsonProperty("tags")
    var tags: List<GameGetTag>? = null

    @get:JsonProperty("publishers")
    @set:JsonProperty("publishers")
    @JsonProperty("publishers")
    var publishers: List<GameGetPublisher>? = null

    @get:JsonProperty("esrb_rating")
    @set:JsonProperty("esrb_rating")
    @JsonProperty("esrb_rating")
    var esrbRating: GameGetEsrbRating? = null

    @get:JsonProperty("clip")
    @set:JsonProperty("clip")
    @JsonProperty("clip")
    var clip: Any? = null

    @get:JsonProperty("description_raw")
    @set:JsonProperty("description_raw")
    @JsonProperty("description_raw")
    var descriptionRaw: String? = null

    @JsonIgnore
    private val additionalProperties: MutableMap<String, Any> = HashMap()
    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        additionalProperties[name] = value
    }
}