package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "air_date",
    "episode_count",
    "id",
    "name",
    "overview",
    "poster_path",
    "season_number"
)
class TVShowGetSeason {
    @get:JsonProperty("air_date")
    @set:JsonProperty("air_date")
    @JsonProperty("air_date")
    var airDate: String? = null

    @get:JsonProperty("episode_count")
    @set:JsonProperty("episode_count")
    @JsonProperty("episode_count")
    var episodeCount: Int? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("overview")
    @set:JsonProperty("overview")
    @JsonProperty("overview")
    var overview: String? = null

    @get:JsonProperty("poster_path")
    @set:JsonProperty("poster_path")
    @JsonProperty("poster_path")
    var posterPath: String? = null

    @get:JsonProperty("season_number")
    @set:JsonProperty("season_number")
    @JsonProperty("season_number")
    var seasonNumber: Int? = null
}