package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
}