package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonProperty
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get.ReleaseGetCoverArtArchive
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get.ReleaseGetMedium
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get.ReleaseGetReleaseEvent
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get.ReleaseGetTextRepresentation
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("cover-art-archive", "barcode", "status-id", "title", "media", "quality", "date", "packaging-id", "packaging", "asin", "country", "release-events", "status", "disambiguation", "id", "text-representation")
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

    @JsonIgnore
    private val additionalProperties: MutableMap<String, Any> = HashMap()
    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        additionalProperties[name] = value
    }
}