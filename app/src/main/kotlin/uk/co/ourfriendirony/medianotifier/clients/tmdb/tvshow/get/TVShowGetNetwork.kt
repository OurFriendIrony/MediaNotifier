package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("name", "id", "logo_path", "origin_country")
class TVShowGetNetwork {
    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("logo_path")
    @set:JsonProperty("logo_path")
    @JsonProperty("logo_path")
    var logoPath: String? = null

    @get:JsonProperty("origin_country")
    @set:JsonProperty("origin_country")
    @JsonProperty("origin_country")
    var originCountry: String? = null
}