package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "_id",
    "air_date",
    "episodes",
    "name",
    "overview",
    "id",
    "poster_path",
    "season_number",
    "external_ids"
)
class TVSeasonGet {
    @get:JsonProperty("_id")
    @set:JsonProperty("_id")
    @JsonProperty("_id")
    var idCore: String? = null

    @get:JsonProperty("air_date")
    @set:JsonProperty("air_date")
    @JsonProperty("air_date")
    var airDate: String? = null

    @get:JsonProperty("episodes")
    @set:JsonProperty("episodes")
    @JsonProperty("episodes")
    var tvSeasonGetEpisodes: List<TVSeasonGetEpisode>? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("overview")
    @set:JsonProperty("overview")
    @JsonProperty("overview")
    var overview: String? = null

    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("poster_path")
    @set:JsonProperty("poster_path")
    @JsonProperty("poster_path")
    var posterPath: String? = null

    @get:JsonProperty("season_number")
    @set:JsonProperty("season_number")
    @JsonProperty("season_number")
    var seasonNumber: Int? = null

    @get:JsonProperty("external_ids")
    @set:JsonProperty("external_ids")
    @JsonProperty("external_ids")
    var tVSeasonGetExternalIds: TVSeasonGetExternalIds? = null
}