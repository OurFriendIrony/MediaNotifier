package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "air_date",
    "episode_number",
    "id",
    "name",
    "overview",
    "production_code",
    "season_number",
    "show_id",
    "still_path",
    "vote_average",
    "vote_count"
)
class TVShowGetLastEpisodeToAir {
    @get:JsonProperty("air_date")
    @set:JsonProperty("air_date")
    @JsonProperty("air_date")
    var airDate: String? = null

    @get:JsonProperty("episode_number")
    @set:JsonProperty("episode_number")
    @JsonProperty("episode_number")
    var episodeNumber: Int? = null

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

    @get:JsonProperty("production_code")
    @set:JsonProperty("production_code")
    @JsonProperty("production_code")
    var productionCode: String? = null

    @get:JsonProperty("season_number")
    @set:JsonProperty("season_number")
    @JsonProperty("season_number")
    var seasonNumber: Int? = null

    @get:JsonProperty("show_id")
    @set:JsonProperty("show_id")
    @JsonProperty("show_id")
    var showId: Int? = null

    @get:JsonProperty("still_path")
    @set:JsonProperty("still_path")
    @JsonProperty("still_path")
    var stillPath: String? = null

    @get:JsonProperty("vote_average")
    @set:JsonProperty("vote_average")
    @JsonProperty("vote_average")
    var voteAverage: Double? = null

    @get:JsonProperty("vote_count")
    @set:JsonProperty("vote_count")
    @JsonProperty("vote_count")
    var voteCount: Int? = null
}