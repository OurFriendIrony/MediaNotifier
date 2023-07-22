package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.releasegroup.get

import com.fasterxml.jackson.annotation.*
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "type-id",
    "sort-name",
    "iso-3166-1-codes",
    "disambiguation",
    "type",
    "name",
    "id"
)
class ReleaseGroupGetArea {
    @JsonProperty("type-id")
    var typeId: String? = null

    @JsonProperty("sort-name")
    var sortName: String? = null

    @JsonProperty("iso-3166-1-codes")
    var iso3166codes: ArrayList<String> = arrayListOf()

    @JsonProperty("disambiguation")
    var disambiguation: String? = null

    @JsonProperty("type")
    var type: String? = null

    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("id")
    var id: String? = null
}