package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get.ReleaseGetTrack
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get.ReleaseGetArea
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get.ReleaseGetRecording
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("darkened", "front", "count", "artwork", "back")
class ReleaseGetCoverArtArchive {
    @get:JsonProperty("darkened")
    @set:JsonProperty("darkened")
    @JsonProperty("darkened")
    var darkened: Boolean? = null

    @get:JsonProperty("front")
    @set:JsonProperty("front")
    @JsonProperty("front")
    var front: Boolean? = null

    @get:JsonProperty("count")
    @set:JsonProperty("count")
    @JsonProperty("count")
    var count: Int? = null

    @get:JsonProperty("artwork")
    @set:JsonProperty("artwork")
    @JsonProperty("artwork")
    var artwork: Boolean? = null

    @get:JsonProperty("back")
    @set:JsonProperty("back")
    @JsonProperty("back")
    var back: Boolean? = null

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