package uk.co.ourfriendirony.medianotifier.autogen.tvshow;

import android.util.Log;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "backdrop_path",
        "created_by",
        "episode_run_time",
        "first_air_date",
        "genres",
        "homepage",
        "id",
        "in_production",
        "languages",
        "last_air_date",
        "name",
        "networks",
        "number_of_episodes",
        "number_of_seasons",
        "origin_country",
        "original_language",
        "original_name",
        "overview",
        "popularity",
        "poster_path",
        "production_companies",
        "seasons",
        "status",
        "type",
        "vote_average",
        "vote_count"
})
public class MDLookupTVShow {

    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("created_by")
    private List<MDCreatedBy> createdBy = new ArrayList<>();
    @JsonProperty("episode_run_time")
    private List<Integer> episodeRunTime = new ArrayList<>();
    @JsonProperty("first_air_date")
    private String firstAirDate;
    @JsonProperty("genres")
    private List<MDGenre> genres = new ArrayList<>();
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("in_production")
    private Boolean inProduction;
    @JsonProperty("languages")
    private List<String> languages = new ArrayList<>();
    @JsonProperty("last_air_date")
    private String lastAirDate;
    @JsonProperty("name")
    private String name;
    @JsonProperty("networks")
    private List<MDNetwork> networks = new ArrayList<>();
    @JsonProperty("number_of_episodes")
    private Integer numberOfEpisodes;
    @JsonProperty("number_of_seasons")
    private Integer numberOfSeasons;
    @JsonProperty("origin_country")
    private List<String> originCountry = new ArrayList<>();
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_name")
    private String originalName;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("popularity")
    private Double popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("production_companies")
    private List<MDProductionCompany> productionCompanies = new ArrayList<>();
    @JsonProperty("seasons")
    private List<MDSeason> seasons = new ArrayList<>();
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
    @JsonProperty("vote_average")
    private Integer voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("backdrop_path")
    public String getBackdropPath() {
        return backdropPath;
    }

    @JsonProperty("backdrop_path")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @JsonProperty("created_by")
    public List<MDCreatedBy> getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("created_by")
    public void setCreatedBy(List<MDCreatedBy> createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("episode_run_time")
    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    @JsonProperty("episode_run_time")
    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    @JsonProperty("first_air_date")
    public String getFirstAirDate() {
        return firstAirDate;
    }

    @JsonProperty("first_air_date")
    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    @JsonProperty("genres")
    public List<MDGenre> getGenres() {
        return genres;
    }

    @JsonProperty("genres")
    public void setGenres(List<MDGenre> genres) {
        this.genres = genres;
    }

    @JsonProperty("homepage")
    public String getHomepage() {
        return homepage;
    }

    @JsonProperty("homepage")
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("in_production")
    public Boolean getInProduction() {
        return inProduction;
    }

    @JsonProperty("in_production")
    public void setInProduction(Boolean inProduction) {
        this.inProduction = inProduction;
    }

    @JsonProperty("languages")
    public List<String> getLanguages() {
        return languages;
    }

    @JsonProperty("languages")
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    @JsonProperty("last_air_date")
    public String getLastAirDate() {
        return lastAirDate;
    }

    @JsonProperty("last_air_date")
    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("networks")
    public List<MDNetwork> getNetworks() {
        return networks;
    }

    @JsonProperty("networks")
    public void setNetworks(List<MDNetwork> networks) {
        this.networks = networks;
    }

    @JsonProperty("number_of_episodes")
    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    @JsonProperty("number_of_episodes")
    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    @JsonProperty("number_of_seasons")
    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    @JsonProperty("number_of_seasons")
    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    @JsonProperty("origin_country")
    public List<String> getOriginCountry() {
        return originCountry;
    }

    @JsonProperty("origin_country")
    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    @JsonProperty("original_language")
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    @JsonProperty("original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @JsonProperty("original_name")
    public String getOriginalName() {
        return originalName;
    }

    @JsonProperty("original_name")
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @JsonProperty("overview")
    public String getOverview() {
        return overview;
    }

    @JsonProperty("overview")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    @JsonProperty("popularity")
    public Double getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    @JsonProperty("poster_path")
    public String getPosterPath() {
        return posterPath;
    }

    @JsonProperty("poster_path")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @JsonProperty("production_companies")
    public List<MDProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    @JsonProperty("production_companies")
    public void setProductionCompanies(List<MDProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    @JsonProperty("seasons")
    public List<MDSeason> getSeasons() {
        return seasons;
    }

    @JsonProperty("seasons")
    public void setSeasons(List<MDSeason> seasons) {
        this.seasons = seasons;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("vote_average")
    public Integer getVoteAverage() {
        return voteAverage;
    }

    @JsonProperty("vote_average")
    public void setVoteAverage(Integer voteAverage) {
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