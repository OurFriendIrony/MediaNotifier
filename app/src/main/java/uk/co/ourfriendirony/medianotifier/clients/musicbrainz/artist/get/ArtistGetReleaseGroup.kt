package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonProperty
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGetArea
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGetBeginArea
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGetReleaseGroup
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGetLifeSpan
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearchArtist
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearchLifeSpan
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearchArea
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearchBeginArea
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearchAlias
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearchTag
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "disambiguation", "title", "secondary-type-ids", "primary-type", "secondary-types", "first-release-date", "primary-type-id")
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