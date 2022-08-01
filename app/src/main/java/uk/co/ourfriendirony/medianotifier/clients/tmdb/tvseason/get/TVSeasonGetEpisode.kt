package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get

import com.fasterxml.jackson.annotation.*
import java.util.*

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
    "vote_count",
    "TVSeasonGetCrew",
    "guest_stars"
)
class TVSeasonGetEpisode {
    @get:JsonProperty("air_date")
    @set:JsonProperty("air_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("air_date")
    var airDate: Date? = null

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

    @get:JsonProperty("crew")
    @set:JsonProperty("crew")
    @JsonProperty("crew")
    var tVSeasonGetCrew: List<TVSeasonGetCrew>? = null

    @get:JsonProperty("guest_stars")
    @set:JsonProperty("guest_stars")
    @JsonProperty("guest_stars")
    var tVSeasonGetGuestStars: List<TVSeasonGetGuestStar>? = null
}