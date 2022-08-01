package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("ended", "begin", "end")
class ArtistGetLifeSpan {
    @get:JsonProperty("ended")
    @set:JsonProperty("ended")
    @JsonProperty("ended")
    var ended: Boolean? = null

    @get:JsonProperty("begin")
    @set:JsonProperty("begin")
    @JsonProperty("begin")
    @JsonDeserialize(using = MultiDateDeserializer::class)
    var begin: Date? = null

    @get:JsonProperty("end")
    @set:JsonProperty("end")
    @JsonProperty("end")
    var end: String? = null
}