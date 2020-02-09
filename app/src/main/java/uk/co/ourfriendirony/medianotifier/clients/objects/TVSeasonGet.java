package uk.co.ourfriendirony.medianotifier.clients.objects;

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
        "TVSeasonGetEpisodes",
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
    @JsonProperty("TVSeasonGetEpisodes")
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

    @JsonProperty("TVSeasonGetEpisodes")
    public List<TVSeasonGetEpisode> getTVSeasonGetEpisodes() {
        return TVSeasonGetEpisodes;
    }

    @JsonProperty("TVSeasonGetEpisodes")
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


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "credit_id",
        "name",
        "department",
        "job",
        "profile_path"
})
class TVSeasonGetCrew {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("credit_id")
    private String creditId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("department")
    private String department;
    @JsonProperty("job")
    private String job;
    @JsonProperty("profile_path")
    private Object profilePath;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("credit_id")
    public String getCreditId() {
        return creditId;
    }

    @JsonProperty("credit_id")
    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("department")
    public String getDepartment() {
        return department;
    }

    @JsonProperty("department")
    public void setDepartment(String department) {
        this.department = department;
    }

    @JsonProperty("job")
    public String getJob() {
        return job;
    }

    @JsonProperty("job")
    public void setJob(String job) {
        this.job = job;
    }

    @JsonProperty("profile_path")
    public Object getProfilePath() {
        return profilePath;
    }

    @JsonProperty("profile_path")
    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
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
class TVSeasonGetEpisode {

    @JsonProperty("air_date")
    private String airDate;
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
    @JsonProperty("TVSeasonGetCrew")
    private List<TVSeasonGetCrew> TVSeasonGetCrew = null;
    @JsonProperty("guest_stars")
    private List<TVSeasonGetGuestStar> TVSeasonGetGuestStars = null;
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

    @JsonProperty("TVSeasonGetCrew")
    public List<TVSeasonGetCrew> getTVSeasonGetCrew() {
        return TVSeasonGetCrew;
    }

    @JsonProperty("TVSeasonGetCrew")
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "freebase_mid",
        "freebase_id",
        "tvdb_id",
        "tvrage_id"
})
class TVSeasonGetExternalIds {

    @JsonProperty("freebase_mid")
    private String freebaseMid;
    @JsonProperty("freebase_id")
    private String freebaseId;
    @JsonProperty("tvdb_id")
    private Integer tvdbId;
    @JsonProperty("tvrage_id")
    private Object tvrageId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("freebase_mid")
    public String getFreebaseMid() {
        return freebaseMid;
    }

    @JsonProperty("freebase_mid")
    public void setFreebaseMid(String freebaseMid) {
        this.freebaseMid = freebaseMid;
    }

    @JsonProperty("freebase_id")
    public String getFreebaseId() {
        return freebaseId;
    }

    @JsonProperty("freebase_id")
    public void setFreebaseId(String freebaseId) {
        this.freebaseId = freebaseId;
    }

    @JsonProperty("tvdb_id")
    public Integer getTvdbId() {
        return tvdbId;
    }

    @JsonProperty("tvdb_id")
    public void setTvdbId(Integer tvdbId) {
        this.tvdbId = tvdbId;
    }

    @JsonProperty("tvrage_id")
    public Object getTvrageId() {
        return tvrageId;
    }

    @JsonProperty("tvrage_id")
    public void setTvrageId(Object tvrageId) {
        this.tvrageId = tvrageId;
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "credit_id",
        "character",
        "order",
        "gender",
        "profile_path"
})
class TVSeasonGetGuestStar {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("credit_id")
    private String creditId;
    @JsonProperty("character")
    private String character;
    @JsonProperty("order")
    private Integer order;
    @JsonProperty("gender")
    private Integer gender;
    @JsonProperty("profile_path")
    private Object profilePath;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("credit_id")
    public String getCreditId() {
        return creditId;
    }

    @JsonProperty("credit_id")
    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    @JsonProperty("character")
    public String getCharacter() {
        return character;
    }

    @JsonProperty("character")
    public void setCharacter(String character) {
        this.character = character;
    }

    @JsonProperty("order")
    public Integer getOrder() {
        return order;
    }

    @JsonProperty("order")
    public void setOrder(Integer order) {
        this.order = order;
    }

    @JsonProperty("gender")
    public Integer getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @JsonProperty("profile_path")
    public Object getProfilePath() {
        return profilePath;
    }

    @JsonProperty("profile_path")
    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
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
