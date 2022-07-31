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
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("date", "area")
class ReleaseGetReleaseEvent {
    @get:JsonProperty("date")
    @set:JsonProperty("date")
    @JsonProperty("date")
    @JsonDeserialize(using = MultiDateDeserializer::class)
    var date: Date? = null

    @get:JsonProperty("area")
    @set:JsonProperty("area")
    @JsonProperty("area")
    var area: ReleaseGetArea? = null

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