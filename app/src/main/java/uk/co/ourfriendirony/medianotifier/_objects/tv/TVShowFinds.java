package uk.co.ourfriendirony.medianotifier._objects.tv;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "results"
})
public class TVShowFinds {
    @JsonProperty("results")
    private List<TVShow> tvShows = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("results")
    private List<TVShow> getTVShows() {
        return tvShows;
    }

    @JsonProperty("results")
    public void setTVShows(List<TVShow> tvShows) {
        this.tvShows = tvShows;
    }

    public List<TVShow> getTVShowsWithDates() {
        List<TVShow> tvShows = new ArrayList<>();
        for (TVShow tvShow : getTVShows()) {
            if (!("".equals(tvShow.getFirstAirDate())) && tvShow.getFirstAirDate() != null) {
                tvShows.add(tvShow);
            }
        }
        return tvShows;
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
