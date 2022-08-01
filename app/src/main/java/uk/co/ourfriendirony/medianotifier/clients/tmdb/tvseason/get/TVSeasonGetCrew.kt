package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("id", "credit_id", "name", "department", "job", "profile_path")
class TVSeasonGetCrew {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("credit_id")
    @set:JsonProperty("credit_id")
    @JsonProperty("credit_id")
    var creditId: String? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("department")
    @set:JsonProperty("department")
    @JsonProperty("department")
    var department: String? = null

    @get:JsonProperty("job")
    @set:JsonProperty("job")
    @JsonProperty("job")
    var job: String? = null

    @get:JsonProperty("profile_path")
    @set:JsonProperty("profile_path")
    @JsonProperty("profile_path")
    var profilePath: Any? = null
}