package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.releasegroup.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

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