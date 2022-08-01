package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.search

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("page", "total_results", "total_pages", "results")
class TVShowSearch {
    @get:JsonProperty("page")
    @set:JsonProperty("page")
    @JsonProperty("page")
    var page: Int? = null

    @get:JsonProperty("total_results")
    @set:JsonProperty("total_results")
    @JsonProperty("total_results")
    var totalResults: Int? = null

    @get:JsonProperty("total_pages")
    @set:JsonProperty("total_pages")
    @JsonProperty("total_pages")
    var totalPages: Int? = null

    @get:JsonProperty("results")
    @set:JsonProperty("results")
    @JsonProperty("results")
    var results: List<TVShowSearchResult>? = null
}