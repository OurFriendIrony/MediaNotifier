package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.releasegroup.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "language",
    "script"
)
class ReleaseGroupGetTextRepresentation {
    @JsonProperty("language")
    var language: String? = null

    @JsonProperty("script")
    var script: String? = null
}