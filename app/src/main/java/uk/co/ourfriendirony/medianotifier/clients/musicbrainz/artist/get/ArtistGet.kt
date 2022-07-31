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
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("disambiguation", "id", "area", "gender", "begin-area", "sort-name", "gender-id", "name", "type", "release-groups", "end-area", "ipis", "type-id", "life-span", "isnis", "country")
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