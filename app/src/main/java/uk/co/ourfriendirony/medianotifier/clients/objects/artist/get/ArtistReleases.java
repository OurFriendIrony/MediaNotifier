package uk.co.ourfriendirony.medianotifier.clients.objects.artist.get;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pagination",
        "releases"
})
public class ArtistReleases {

    @JsonProperty("pagination")
    private ArtistReleasesPagination pagination;
    @JsonProperty("releases")
    private List<ArtistReleasesRelease> releases = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("pagination")
    public ArtistReleasesPagination getPagination() {
        return pagination;
    }

    @JsonProperty("pagination")
    public void setPagination(ArtistReleasesPagination pagination) {
        this.pagination = pagination;
    }

    @JsonProperty("releases")
    public List<ArtistReleasesRelease> getReleases() {
        return releases;
    }

    @JsonProperty("releases")
    public void setReleases(List<ArtistReleasesRelease> releases) {
        this.releases = releases;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
