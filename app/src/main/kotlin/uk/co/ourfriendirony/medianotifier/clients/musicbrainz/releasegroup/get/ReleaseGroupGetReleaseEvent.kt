package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.releasegroup.get

import com.fasterxml.jackson.annotation.*
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "area",
    "date"
)
class ReleaseGroupGetReleaseEvent {
    @JsonProperty("area")
    var area: ReleaseGroupGetArea? = null

    @JsonProperty("date")
    var date: String? = null
}