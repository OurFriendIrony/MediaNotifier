package uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("iso_639_1", "name")
class MovieGetSpokenLanguage {
    @get:JsonProperty("iso_639_1")
    @set:JsonProperty("iso_639_1")
    @JsonProperty("iso_639_1")
    var iso6391: String? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null
}