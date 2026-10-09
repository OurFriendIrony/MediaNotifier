package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("count", "name")
class ArtistSearchTag {
    @get:JsonProperty("count")
    @set:JsonProperty("count")
    @JsonProperty("count")
    var count: Int? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null
}