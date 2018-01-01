package uk.co.ourfriendirony.medianotifier._objects.tv;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "air_date",
        "episode_number",
        "name",
        "overview",
        "id",
        "season_number"
})
public class TVEpisode {
    private Date defaultDate = new GregorianCalendar(9999, 0, 1).getTime();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("air_date")
    private Date airDate;
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
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("air_date")
    public Date getAirDate() {
        return airDate;
    }

    @JsonProperty("air_date")
    public void setAirDate(Date airDate) {
        if (airDate == null)
            airDate = defaultDate;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getIdAsString() {
        return String.valueOf(id);
    }

    public String getSeasonNumberAsString() {
        return String.valueOf(seasonNumber);
    }

    public String getEpisodeNumberAsString() {
        return String.valueOf(episodeNumber);
    }
}
