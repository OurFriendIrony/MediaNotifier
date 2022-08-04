package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
}