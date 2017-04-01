package uk.co.ourfriendirony.medianotifier.autogen.tvshow;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "air_date",
        "episode_count",
        "id",
        "poster_path",
        "season_number",
        "episodes"
})
public class MDSeason {

    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("episode_count")
    private Integer episodeCount;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("poster_path")
    private Object posterPath;
    @JsonProperty("season_number")
    private Integer seasonNumber;
    @JsonProperty("episodes")
    private List<MDEpisode> episodes = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("air_date")
    public String getAirDate() {
        return airDate;
    }

    @JsonProperty("air_date")
    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    @JsonProperty("episode_count")
    public Integer getEpisodeCount() {
        return episodeCount;
    }

    @JsonProperty("episode_count")
    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("poster_path")
    public Object getPosterPath() {
        return posterPath;
    }

    @JsonProperty("poster_path")
    public void setPosterPath(Object posterPath) {
        this.posterPath = posterPath;
    }

    @JsonProperty("season_number")
    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    @JsonProperty("season_number")
    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    @JsonProperty("episodes")
    public List<MDEpisode> getEpisodes() {
        return episodes;
    }

    @JsonProperty("episodes")
    public void setEpisodes(List<MDEpisode> episodes) {
        this.episodes = episodes;
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