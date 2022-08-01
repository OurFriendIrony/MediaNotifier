package uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("id", "logo_path", "name", "origin_country")
class MovieGetProductionCompany {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("logo_path")
    @set:JsonProperty("logo_path")
    @JsonProperty("logo_path")
    var logoPath: String? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("origin_country")
    @set:JsonProperty("origin_country")
    @JsonProperty("origin_country")
    var originCountry: String? = null
}