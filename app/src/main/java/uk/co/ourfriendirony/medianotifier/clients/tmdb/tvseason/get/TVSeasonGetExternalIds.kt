package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("freebase_mid", "freebase_id", "tvdb_id", "tvrage_id")
class TVSeasonGetExternalIds {
    @get:JsonProperty("freebase_mid")
    @set:JsonProperty("freebase_mid")
    @JsonProperty("freebase_mid")
    var freebaseMid: String? = null

    @get:JsonProperty("freebase_id")
    @set:JsonProperty("freebase_id")
    @JsonProperty("freebase_id")
    var freebaseId: String? = null

    @get:JsonProperty("tvdb_id")
    @set:JsonProperty("tvdb_id")
    @JsonProperty("tvdb_id")
    var tvdbId: Int? = null

    @get:JsonProperty("tvrage_id")
    @set:JsonProperty("tvrage_id")
    @JsonProperty("tvrage_id")
    var tvrageId: Any? = null
}