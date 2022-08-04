package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("darkened", "front", "count", "artwork", "back")
class ReleaseGetCoverArtArchive {
    @get:JsonProperty("darkened")
    @set:JsonProperty("darkened")
    @JsonProperty("darkened")
    var darkened: Boolean? = null

    @get:JsonProperty("front")
    @set:JsonProperty("front")
    @JsonProperty("front")
    var front: Boolean? = null

    @get:JsonProperty("count")
    @set:JsonProperty("count")
    @JsonProperty("count")
    var count: Int? = null

    @get:JsonProperty("artwork")
    @set:JsonProperty("artwork")
    @JsonProperty("artwork")
    var artwork: Boolean? = null

    @get:JsonProperty("back")
    @set:JsonProperty("back")
    @JsonProperty("back")
    var back: Boolean? = null
}