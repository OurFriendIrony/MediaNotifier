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
@JsonPropertyOrder("position", "recording", "title", "length", "number", "id")
class ReleaseGetTrack {
    @get:JsonProperty("position")
    @set:JsonProperty("position")
    @JsonProperty("position")
    var position: Int? = null

    @get:JsonProperty("recording")
    @set:JsonProperty("recording")
    @JsonProperty("recording")
    var recording: ReleaseGetRecording? = null

    @get:JsonProperty("title")
    @set:JsonProperty("title")
    @JsonProperty("title")
    var title: String? = null

    @get:JsonProperty("length")
    @set:JsonProperty("length")
    @JsonProperty("length")
    var length: Int? = null

    @get:JsonProperty("number")
    @set:JsonProperty("number")
    @JsonProperty("number")
    var number: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: String? = null

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