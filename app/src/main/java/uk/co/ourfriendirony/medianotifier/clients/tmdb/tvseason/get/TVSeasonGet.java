package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get;

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
        "_id",
        "air_date",
        "episodes",
        "name",
        "overview",
        "id",
        "poster_path",
        "season_number",
        "external_ids"
})
public class TVSeasonGet {

    @JsonProperty("_id")
    private String idCore;
    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("episodes")
    private List<TVSeasonGetEpisode> TVSeasonGetEpisodes = null;
    @JsonProperty("name")
    private String name;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("season_number")
    private Integer seasonNumber;
    @JsonProperty("external_ids")
    private TVSeasonGetExternalIds TVSeasonGetExternalIds;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("_id")
    public String getIdCore() {
        return idCore;
    }

    @JsonProperty("_id")
    public void setIdCore(String idCore) {
        this.idCore = idCore;
    }

    @JsonProperty("air_date")
    public String getAirDate() {
        return airDate;
    }

    @JsonProperty("air_date")
    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    @JsonProperty("episodes")
    public List<TVSeasonGetEpisode> getTVSeasonGetEpisodes() {
        return TVSeasonGetEpisodes;
    }

    @JsonProperty("episodes")
    public void setTVSeasonGetEpisodes(List<TVSeasonGetEpisode> TVSeasonGetEpisodes) {
        this.TVSeasonGetEpisodes = TVSeasonGetEpisodes;
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

    @JsonProperty("poster_path")
    public String getPosterPath() {
        return posterPath;
    }

    @JsonProperty("poster_path")
    public void setPosterPath(String posterPath) {
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

    @JsonProperty("external_ids")
    public TVSeasonGetExternalIds getTVSeasonGetExternalIds() {
        return TVSeasonGetExternalIds;
    }

    @JsonProperty("external_ids")
    public void setTVSeasonGetExternalIds(TVSeasonGetExternalIds TVSeasonGetExternalIds) {
        this.TVSeasonGetExternalIds = TVSeasonGetExternalIds;
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


