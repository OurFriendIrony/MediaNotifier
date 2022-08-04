package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "sort-name",
    "type-id",
    "name",
    "locale",
    "type",
    "primary",
    "begin-date",
    "end-date"
)
class ArtistSearchAlias {
    @get:JsonProperty("sort-name")
    @set:JsonProperty("sort-name")
    @JsonProperty("sort-name")
    var sortName: String? = null

    @get:JsonProperty("type-id")
    @set:JsonProperty("type-id")
    @JsonProperty("type-id")
    var typeId: String? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("locale")
    @set:JsonProperty("locale")
    @JsonProperty("locale")
    var locale: Any? = null

    @get:JsonProperty("type")
    @set:JsonProperty("type")
    @JsonProperty("type")
    var type: Any? = null

    @get:JsonProperty("primary")
    @set:JsonProperty("primary")
    @JsonProperty("primary")
    var primary: Any? = null

    @get:JsonProperty("begin-date")
    @set:JsonProperty("begin-date")
    @JsonProperty("begin-date")
    var beginDate: Any? = null

    @get:JsonProperty("end-date")
    @set:JsonProperty("end-date")
    @JsonProperty("end-date")
    var endDate: Any? = null
}