package uk.co.ourfriendirony.medianotifier.autogen.tvshow;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "air_date",
        "episode_number",
        "name",
        "overview",
        "id",
        "season_number",
        "still_path",
        "vote_average",
        "vote_count"
})
public class TVEpisode {

    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("episode_number")
    private Integer episodeNumber;
    @JsonProperty("name")
    private String name;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("season_number")
    private Integer seasonNumber;
    @JsonProperty("still_path")
    private String stillPath;
    @JsonProperty("vote_average")
    private Double voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;
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

    @JsonProperty("episode_number")
    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    @JsonProperty("episode_number")
    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("overview")
    public String getOverview() {
        return overview;
    }

    @JsonProperty("overview")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("season_number")
    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    @JsonProperty("season_number")
    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    @JsonProperty("still_path")
    public String getStillPath() {
        return stillPath;
    }

    @JsonProperty("still_path")
    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    @JsonProperty("vote_average")
    public Double getVoteAverage() {
        return voteAverage;
    }

    @JsonProperty("vote_average")
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @JsonProperty("vote_count")
    public Integer getVoteCount() {
        return voteCount;
    }

    @JsonProperty("vote_count")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
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