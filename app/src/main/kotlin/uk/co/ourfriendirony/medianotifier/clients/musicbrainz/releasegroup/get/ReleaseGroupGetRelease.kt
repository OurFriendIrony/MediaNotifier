package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.releasegroup.get

import com.fasterxml.jackson.annotation.*
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "barcode",
    "country",
    "status",
    "status-id",
    "packaging-id",
    "disambiguation",
    "packaging",
    "id",
    "title",
    "quality",
    "date",
    "release-events",
    "text-representation"
)
class ReleaseGroupGetRelease {
    @JsonProperty("barcode")
    var barcode: String? = null

    @JsonProperty("country")
    var country: String? = null

    @JsonProperty("status")
    var status: String? = null

    @JsonProperty("status-id")
    var statusId: String? = null

    @JsonProperty("packaging-id")
    var packagingId: String? = null

    @JsonProperty("disambiguation")
    var disambiguation: String? = null

    @JsonProperty("packaging")
    var packaging: String? = null

    @JsonProperty("id")
    var id: String? = null

    @JsonProperty("title")
    var title: String? = null

    @JsonProperty("quality")
    var quality: String? = null

    @JsonProperty("date")
    var date: String? = null

    @JsonProperty("release-events")
    var releaseEvents: ArrayList<ReleaseGroupGetReleaseEvent> = arrayListOf()

    @JsonProperty("text-representation")
    var textRepresentation: ReleaseGroupGetTextRepresentation? = ReleaseGroupGetTextRepresentation()
}
