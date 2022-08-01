package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "id",
    "disambiguation",
    "title",
    "secondary-type-ids",
    "primary-type",
    "secondary-types",
    "first-release-date",
    "primary-type-id"
)
class ArtistGetReleaseGroup {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: String? = null

    @get:JsonProperty("disambiguation")
    @set:JsonProperty("disambiguation")
    @JsonProperty("disambiguation")
    var disambiguation: String? = null

    @get:JsonProperty("title")
    @set:JsonProperty("title")
    @JsonProperty("title")
    var title: String? = null

    @get:JsonProperty("secondary-type-ids")
    @set:JsonProperty("secondary-type-ids")
    @JsonProperty("secondary-type-ids")
    var secondaryTypeIds: List<String>? = null

    @get:JsonProperty("primary-type")
    @set:JsonProperty("primary-type")
    @JsonProperty("primary-type")
    var primaryType: String? = null

    @get:JsonProperty("secondary-types")
    @set:JsonProperty("secondary-types")
    @JsonProperty("secondary-types")
    var secondaryTypes: List<String>? = null

    @get:JsonProperty("first-release-date")
    @set:JsonProperty("first-release-date")
    @JsonProperty("first-release-date")
    @JsonDeserialize(using = MultiDateDeserializer::class)
    var firstReleaseDate: Date? = null

    @get:JsonProperty("primary-type-id")
    @set:JsonProperty("primary-type-id")
    @JsonProperty("primary-type-id")
    var primaryTypeId: String? = null
}