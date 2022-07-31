package uk.co.ourfriendirony.medianotifier.clients.rawg.game.search

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
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("platform", "released_at", "requirements")
class GameSearchPlatformGroup {
    @get:JsonProperty("platform")
    @set:JsonProperty("platform")
    @JsonProperty("platform")
    var platform: GameSearchPlatform? = null

    @get:JsonProperty("released_at")
    @set:JsonProperty("released_at")
    @JsonProperty("released_at")
    var releasedAt: String? = null

    @get:JsonProperty("requirements")
    @set:JsonProperty("requirements")
    @JsonProperty("requirements")
    var requirements: Any? = null

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