package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "id",
    "type",
    "type-id",
    "score",
    "name",
    "sort-name",
    "country",
    "area",
    "begin-area",
    "disambiguation",
    "isnis",
    "life-span",
    "aliases",
    "tags",
    "gender"
)
class ArtistSearchArtist {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: String? = null

    @get:JsonProperty("type")
    @set:JsonProperty("type")
    @JsonProperty("type")
    var type: String? = null

    @get:JsonProperty("type-id")
    @set:JsonProperty("type-id")
    @JsonProperty("type-id")
    var typeId: String? = null

    @get:JsonProperty("score")
    @set:JsonProperty("score")
    @JsonProperty("score")
    var score: Int? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("sort-name")
    @set:JsonProperty("sort-name")
    @JsonProperty("sort-name")
    var sortName: String? = null

    @get:JsonProperty("country")
    @set:JsonProperty("country")
    @JsonProperty("country")
    var country: String? = null

    @get:JsonProperty("area")
    @set:JsonProperty("area")
    @JsonProperty("area")
    var area: ArtistSearchArea? = null

    @get:JsonProperty("begin-area")
    @set:JsonProperty("begin-area")
    @JsonProperty("begin-area")
    var beginArea: ArtistSearchBeginArea? = null

    @get:JsonProperty("disambiguation")
    @set:JsonProperty("disambiguation")
    @JsonProperty("disambiguation")
    var disambiguation: String? = null

    @get:JsonProperty("isnis")
    @set:JsonProperty("isnis")
    @JsonProperty("isnis")
    var isnis: List<String>? = null

    @get:JsonProperty("life-span")
    @set:JsonProperty("life-span")
    @JsonProperty("life-span")
    var lifeSpan: ArtistSearchLifeSpan? = null

    @get:JsonProperty("aliases")
    @set:JsonProperty("aliases")
    @JsonProperty("aliases")
    var aliases: List<ArtistSearchAlias>? = null

    @get:JsonProperty("tags")
    @set:JsonProperty("tags")
    @JsonProperty("tags")
    var tags: List<ArtistSearchTag>? = null

    @get:JsonProperty("gender")
    @set:JsonProperty("gender")
    @JsonProperty("gender")
    var gender: String? = null
}