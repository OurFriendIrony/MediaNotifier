package uk.co.ourfriendirony.medianotifier.clients.objects.artist.search;

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
        "results"
})
public class ArtistSearch {

    @JsonProperty("pagination")
    private ArtistSearchPagination pagination;
    @JsonProperty("results")
    private List<ArtistSearchResult> results = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("pagination")
    public ArtistSearchPagination getPagination() {
        return pagination;
    }

    @JsonProperty("pagination")
    public void setPagination(ArtistSearchPagination pagination) {
        this.pagination = pagination;
    }

    @JsonProperty("results")
    public List<ArtistSearchResult> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<ArtistSearchResult> results) {
        this.results = results;
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
