package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("id", "name", "credit_id", "character", "order", "gender", "profile_path")
class TVSeasonGetGuestStar {
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    @JsonProperty("id")
    var id: Int? = null

    @get:JsonProperty("name")
    @set:JsonProperty("name")
    @JsonProperty("name")
    var name: String? = null

    @get:JsonProperty("credit_id")
    @set:JsonProperty("credit_id")
    @JsonProperty("credit_id")
    var creditId: String? = null

    @get:JsonProperty("character")
    @set:JsonProperty("character")
    @JsonProperty("character")
    var character: String? = null

    @get:JsonProperty("order")
    @set:JsonProperty("order")
    @JsonProperty("order")
    var order: Int? = null

    @get:JsonProperty("gender")
    @set:JsonProperty("gender")
    @JsonProperty("gender")
    var gender: Int? = null

    @get:JsonProperty("profile_path")
    @set:JsonProperty("profile_path")
    @JsonProperty("profile_path")
    var profilePath: Any? = null
}