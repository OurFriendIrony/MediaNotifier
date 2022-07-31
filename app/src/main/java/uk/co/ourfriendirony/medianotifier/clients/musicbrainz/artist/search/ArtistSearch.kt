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
@JsonPropertyOrder("created", "count", "offset", "artists")
class ArtistSearch {
    @get:JsonProperty("created")
    @set:JsonProperty("created")
    @JsonProperty("created")
    var created: String? = null

    @get:JsonProperty("count")
    @set:JsonProperty("count")
    @JsonProperty("count")
    var count: Int? = null

    @get:JsonProperty("offset")
    @set:JsonProperty("offset")
    @JsonProperty("offset")
    var offset: Int? = null

    @get:JsonProperty("artists")
    @set:JsonProperty("artists")
    @JsonProperty("artists")
    var artists: List<ArtistSearchArtist>? = null

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