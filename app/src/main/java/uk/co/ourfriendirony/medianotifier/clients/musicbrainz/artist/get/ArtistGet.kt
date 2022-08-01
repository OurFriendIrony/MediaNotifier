package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "disambiguation",
    "id",
    "area",
    "gender",
    "begin-area",
    "sort-name",
    "gender-id",
    "name",
    "type",
    "release-groups",
    "end-area",
    "ipis",
    "type-id",
    "life-span",
    "isnis",
    "country"
)
class ArtistGet {
    @get:JsonProperty("disambiguation")
    @set:JsonProperty("disambiguation")
    @JsonProperty("disambiguation")
    var disambiguation: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: String? = null

    @get:JsonProperty("area")
    @set:JsonProperty("area")
    @JsonProperty("area")
    var area: ArtistGetArea? = null

    @get:JsonProperty("gender")
    @set:JsonProperty("gender")
    @JsonProperty("gender")
    var gender: Any? = null

    @get:JsonProperty("begin-area")
    @set:JsonProperty("begin-area")
    @JsonProperty("begin-area")
    var beginArea: ArtistGetBeginArea? = null

    @get:JsonProperty("sort-name")
    @set:JsonProperty("sort-name")
    @JsonProperty("sort-name")
    var sortName: String? = null

    @get:JsonProperty("gender-id")
    @set:JsonProperty("gender-id")
    @JsonProperty("gender-id")
    var genderId: Any? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("type")
    @set:JsonProperty("type")
    @JsonProperty("type")
    var type: String? = null

    @get:JsonProperty("release-groups")
    @set:JsonProperty("release-groups")
    @JsonProperty("release-groups")
    var releaseGroups: List<ArtistGetReleaseGroup>? = null

    @get:JsonProperty("end-area")
    @set:JsonProperty("end-area")
    @JsonProperty("end-area")
    var endArea: Any? = null

    @get:JsonProperty("ipis")
    @set:JsonProperty("ipis")
    @JsonProperty("ipis")
    var ipis: List<Any>? = null

    @get:JsonProperty("type-id")
    @set:JsonProperty("type-id")
    @JsonProperty("type-id")
    var typeId: String? = null

    @get:JsonProperty("life-span")
    @set:JsonProperty("life-span")
    @JsonProperty("life-span")
    var lifeSpan: ArtistGetLifeSpan? = null

    @get:JsonProperty("isnis")
    @set:JsonProperty("isnis")
    @JsonProperty("isnis")
    var isnis: List<String>? = null

    @get:JsonProperty("country")
    @set:JsonProperty("country")
    @JsonProperty("country")
    var country: String? = null
}