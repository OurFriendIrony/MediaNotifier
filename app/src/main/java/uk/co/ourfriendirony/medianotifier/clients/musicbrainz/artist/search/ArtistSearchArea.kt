package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search

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
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "type", "type-id", "name", "sort-name", "life-span")
class ArtistSearchArea {
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

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("sort-name")
    @set:JsonProperty("sort-name")
    @JsonProperty("sort-name")
    var sortName: String? = null

    @get:JsonProperty("life-span")
    @set:JsonProperty("life-span")
    @JsonProperty("life-span")
    var lifeSpan: ArtistSearchLifeSpan? = null

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