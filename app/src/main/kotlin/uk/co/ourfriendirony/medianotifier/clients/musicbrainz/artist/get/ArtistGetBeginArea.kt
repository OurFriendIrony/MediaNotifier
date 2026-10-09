package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("name", "disambiguation", "id", "sort-name")
class ArtistGetBeginArea {
    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("disambiguation")
    @set:JsonProperty("disambiguation")
    @JsonProperty("disambiguation")
    var disambiguation: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: String? = null

    @get:JsonProperty("sort-name")
    @set:JsonProperty("sort-name")
    @JsonProperty("sort-name")
    var sortName: String? = null
}