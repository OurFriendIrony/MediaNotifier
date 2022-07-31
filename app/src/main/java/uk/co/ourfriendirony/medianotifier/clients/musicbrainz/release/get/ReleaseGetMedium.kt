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
@JsonPropertyOrder("tracks", "track-count", "format-id", "title", "track-offset", "position", "format")
class ReleaseGetMedium {
    @get:JsonProperty("tracks")
    @set:JsonProperty("tracks")
    @JsonProperty("tracks")
    var tracks: List<ReleaseGetTrack>? = null

    @get:JsonProperty("track-count")
    @set:JsonProperty("track-count")
    @JsonProperty("track-count")
    var trackCount: Int? = null

    @get:JsonProperty("format-id")
    @set:JsonProperty("format-id")
    @JsonProperty("format-id")
    var formatId: String? = null

    @get:JsonProperty("title")
    @set:JsonProperty("title")
    @JsonProperty("title")
    var title: String? = null

    @get:JsonProperty("track-offset")
    @set:JsonProperty("track-offset")
    @JsonProperty("track-offset")
    var trackOffset: Int? = null

    @get:JsonProperty("position")
    @set:JsonProperty("position")
    @JsonProperty("position")
    var position: Int? = null

    @get:JsonProperty("format")
    @set:JsonProperty("format")
    @JsonProperty("format")
    var format: String? = null

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