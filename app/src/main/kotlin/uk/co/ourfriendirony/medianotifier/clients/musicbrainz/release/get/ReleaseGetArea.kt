package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("sort-name", "iso-3166-1-codes", "name", "id", "disambiguation")
class ReleaseGetArea {
    @get:JsonProperty("sort-name")
    @set:JsonProperty("sort-name")
    @JsonProperty("sort-name")
    var sortName: String? = null

    @get:JsonProperty("iso-3166-1-codes")
    @set:JsonProperty("iso-3166-1-codes")
    @JsonProperty("iso-3166-1-codes")
    var iso31661Codes: List<String>? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: String? = null

    @get:JsonProperty("disambiguation")
    @set:JsonProperty("disambiguation")
    @JsonProperty("disambiguation")
    var disambiguation: String? = null
}