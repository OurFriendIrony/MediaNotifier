package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
}