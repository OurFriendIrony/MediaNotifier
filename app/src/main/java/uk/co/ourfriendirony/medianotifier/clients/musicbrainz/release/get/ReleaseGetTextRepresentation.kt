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
@JsonPropertyOrder("language", "script")
class ReleaseGetTextRepresentation {
    @get:JsonProperty("language")
    @set:JsonProperty("language")
    @JsonProperty("language")
    var language: String? = null

    @get:JsonProperty("script")
    @set:JsonProperty("script")
    @JsonProperty("script")
    var script: String? = null

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