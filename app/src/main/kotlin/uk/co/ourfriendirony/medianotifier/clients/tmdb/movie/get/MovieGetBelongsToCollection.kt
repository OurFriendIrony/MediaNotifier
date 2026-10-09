package uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("id", "name", "poster_path", "backdrop_path")
class MovieGetBelongsToCollection {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("poster_path")
    @set:JsonProperty("poster_path")
    @JsonProperty("poster_path")
    var posterPath: String? = null

    @get:JsonProperty("backdrop_path")
    @set:JsonProperty("backdrop_path")
    @JsonProperty("backdrop_path")
    var backdropPath: String? = null
}