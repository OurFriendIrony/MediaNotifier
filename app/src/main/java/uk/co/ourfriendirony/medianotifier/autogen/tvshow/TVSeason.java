package uk.co.ourfriendirony.medianotifier.autogen.tvshow;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "air_date",
        "id",
        "poster_path",
        "season_number",
        "episodes"
})
public class TVSeason {
    private Date defaultDate = new GregorianCalendar(9999, 0, 1).getTime();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("air_date")
    private Date airDate;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("poster_path")
    private Object posterPath;
    @JsonProperty("season_number")
    private Integer seasonNumber;
    @JsonProperty("episodes")
    private List<TVEpisode> episodes = new ArrayList<>();
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
    public List<TVEpisode> getEpisodes() {
        return episodes;
    }

    @JsonProperty("episodes")
    public void setEpisodes(List<TVEpisode> episodes) {
        this.episodes = episodes;
    }

    public Integer getNumberOfEpisodes() {
        return this.getEpisodes().size();
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
