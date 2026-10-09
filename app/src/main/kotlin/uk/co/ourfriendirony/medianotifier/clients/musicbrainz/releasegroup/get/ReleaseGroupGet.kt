package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.releasegroup.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "primary-type",
    "title",
    "first-release-date",
    "id",
    "secondary-type-ids",
    "disambiguation",
    "releases",
    "secondary-types",
    "primary-type-id"
)
class ReleaseGroupGet {
    @JsonProperty("primary-type")
    var primaryType: String? = null

    @JsonProperty("title")
    var title: String? = null

    @JsonProperty("first-release-date")
    var firstReleaseDate: String? = null

    @JsonProperty("id")
    var id: String? = null

    @JsonProperty("disambiguation")
    var disambiguation: String? = null

    @JsonProperty("primary-type-id")
    var primaryTypeId: String? = null

    @JsonProperty("releases")
    var releases: ArrayList<ReleaseGroupGetRelease> = arrayListOf()

    @JsonProperty("secondary-types")
    var secondaryTypes: ArrayList<String> = arrayListOf()

    @JsonProperty("secondary-type-ids")
    var secondaryTypeIds: ArrayList<String> = arrayListOf()
}
