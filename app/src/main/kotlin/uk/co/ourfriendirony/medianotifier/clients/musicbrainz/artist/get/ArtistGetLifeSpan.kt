package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer
import java.util.Date

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