package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
}