package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "air_date",
        "episode_number",
        "id",
        "name",
        "overview",
        "production_code",
        "season_number",
        "show_id",
        "still_path",
        "vote_average",
        "vote_count",
        "TVSeasonGetCrew",
        "guest_stars"
})
public class TVSeasonGetEpisode {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("air_date")
    private Date airDate;
    @JsonProperty("episode_number")
    private Integer episodeNumber;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("production_code")
    private String productionCode;
    @JsonProperty("season_number")
    private Integer seasonNumber;
    @JsonProperty("show_id")
    private Integer showId;
    @JsonProperty("still_path")
    private String stillPath;
    @JsonProperty("vote_average")
    private Double voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;
    @JsonProperty("crew")
    private List<TVSeasonGetCrew> TVSeasonGetCrew = null;
    @JsonProperty("guest_stars")
    private List<TVSeasonGetGuestStar> TVSeasonGetGuestStars = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("air_date")
    public Date getAirDate() {
        return airDate;
    }

    @JsonProperty("air_date")
    public void setAirDate(Date airDate) {
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

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
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

    @JsonProperty("production_code")
    public String getProductionCode() {
        return productionCode;
    }

    @JsonProperty("production_code")
    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    @JsonProperty("season_number")
    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    @JsonProperty("season_number")
    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    @JsonProperty("show_id")
    public Integer getShowId() {
        return showId;
    }

    @JsonProperty("show_id")
    public void setShowId(Integer showId) {
        this.showId = showId;
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

    @JsonProperty("crew")
    public List<TVSeasonGetCrew> getTVSeasonGetCrew() {
        return TVSeasonGetCrew;
    }

    @JsonProperty("crew")
    public void setTVSeasonGetCrew(List<TVSeasonGetCrew> TVSeasonGetCrew) {
        this.TVSeasonGetCrew = TVSeasonGetCrew;
    }

    @JsonProperty("guest_stars")
    public List<TVSeasonGetGuestStar> getTVSeasonGetGuestStars() {
        return TVSeasonGetGuestStars;
    }

    @JsonProperty("guest_stars")
    public void setTVSeasonGetGuestStars(List<TVSeasonGetGuestStar> TVSeasonGetGuestStars) {
        this.TVSeasonGetGuestStars = TVSeasonGetGuestStars;
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
