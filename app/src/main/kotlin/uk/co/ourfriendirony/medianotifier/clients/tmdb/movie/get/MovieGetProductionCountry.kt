package uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("iso_3166_1", "name")
class MovieGetProductionCountry {
    @get:JsonProperty("iso_3166_1")
    @set:JsonProperty("iso_3166_1")
    @JsonProperty("iso_3166_1")
    var iso31661: String? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null
}