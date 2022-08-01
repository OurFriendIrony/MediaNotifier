package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("begin", "end", "ended")
class ArtistSearchLifeSpan {
    @get:JsonProperty("begin")
    @set:JsonProperty("begin")
    @JsonProperty("begin")
    @JsonDeserialize(using = MultiDateDeserializer::class)
    var begin: Date? = null

    @get:JsonProperty("end")
    @set:JsonProperty("end")
    @JsonProperty("end")
    var end: String? = null

    @get:JsonProperty("ended")
    @set:JsonProperty("ended")
    @JsonProperty("ended")
    var ended: Any? = null
}