package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "cover-art-archive",
    "barcode",
    "status-id",
    "title",
    "media",
    "quality",
    "date",
    "packaging-id",
    "packaging",
    "asin",
    "country",
    "release-events",
    "status",
    "disambiguation",
    "id",
    "text-representation"
)
class ReleaseGet {
    @get:JsonProperty("cover-art-archive")
    @set:JsonProperty("cover-art-archive")
    @JsonProperty("cover-art-archive")
    var coverArtArchive: ReleaseGetCoverArtArchive? = null

    @get:JsonProperty("barcode")
    @set:JsonProperty("barcode")
    @JsonProperty("barcode")
    var barcode: String? = null

    @get:JsonProperty("status-id")
    @set:JsonProperty("status-id")
    @JsonProperty("status-id")
    var statusId: String? = null

    @get:JsonProperty("title")
    @set:JsonProperty("title")
    @JsonProperty("title")
    var title: String? = null

    @get:JsonProperty("media")
    @set:JsonProperty("media")
    @JsonProperty("media")
    var media: List<ReleaseGetMedium>? = null

    @get:JsonProperty("quality")
    @set:JsonProperty("quality")
    @JsonProperty("quality")
    var quality: String? = null

    @get:JsonProperty("date")
    @set:JsonProperty("date")
    @JsonProperty("date")
    @JsonDeserialize(using = MultiDateDeserializer::class)
    var date: Date? = null

    @get:JsonProperty("packaging-id")
    @set:JsonProperty("packaging-id")
    @JsonProperty("packaging-id")
    var packagingId: String? = null

    @get:JsonProperty("packaging")
    @set:JsonProperty("packaging")
    @JsonProperty("packaging")
    var packaging: String? = null

    @get:JsonProperty("asin")
    @set:JsonProperty("asin")
    @JsonProperty("asin")
    var asin: Any? = null

    @get:JsonProperty("country")
    @set:JsonProperty("country")
    @JsonProperty("country")
    var country: String? = null

    @get:JsonProperty("release-events")
    @set:JsonProperty("release-events")
    @JsonProperty("release-events")
    var releaseEvents: List<ReleaseGetReleaseEvent>? = null

    @get:JsonProperty("status")
    @set:JsonProperty("status")
    @JsonProperty("status")
    var status: String? = null

    @get:JsonProperty("disambiguation")
    @set:JsonProperty("disambiguation")
    @JsonProperty("disambiguation")
    var disambiguation: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: String? = null

    @get:JsonProperty("text-representation")
    @set:JsonProperty("text-representation")
    @JsonProperty("text-representation")
    var textRepresentation: ReleaseGetTextRepresentation? = null
}