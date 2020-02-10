package uk.co.ourfriendirony.medianotifier.clients.objects.tv.get;

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

import uk.co.ourfriendirony.medianotifier.clients.objects.movie.get.MovieGetExternalIds;
import uk.co.ourfriendirony.medianotifier.clients.objects.movie.get.MovieGetGenre;
import uk.co.ourfriendirony.medianotifier.clients.objects.movie.get.MovieGetProductionCompany;


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
        "last_episode_to_air",
        "name",
        "next_episode_to_air",
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
        "vote_count",
        "external_ids"
})
public class TVShowGet {

    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("created_by")
    private List<TVShowGetCreatedBy> TVShowGetCreatedBy = null;
    @JsonProperty("episode_run_time")
    private List<Integer> episodeRunTime = null;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("first_air_date")
    private Date firstAirDate;
    @JsonProperty("genres")
    private List<MovieGetGenre> genres = null;
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("in_production")
    private Boolean inProduction;
    @JsonProperty("languages")
    private List<String> languages = null;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("last_air_date")
    private Date lastAirDate;
    @JsonProperty("last_episode_to_air")
    private TVShowGetLastEpisodeToAir TVShowGetLastEpisodeToAir;
    @JsonProperty("name")
    private String name;
    @JsonProperty("next_episode_to_air")
    private Object nextEpisodeToAir;
    @JsonProperty("networks")
    private List<TVShowGetNetwork> TVShowGetNetworks = null;
    @JsonProperty("number_of_episodes")
    private Integer numberOfEpisodes;
    @JsonProperty("number_of_seasons")
    private Integer numberOfSeasons;
    @JsonProperty("origin_country")
    private List<String> originCountry = null;
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
    private List<MovieGetProductionCompany> productionCompanies = null;
    @JsonProperty("seasons")
    private List<TVShowGetSeason> TVShowGetSeasons = null;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
    @JsonProperty("vote_average")
    private Double voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;
    @JsonProperty("external_ids")
    private MovieGetExternalIds externalIds;
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
    public List<TVShowGetCreatedBy> getTVShowGetCreatedBy() {
        return TVShowGetCreatedBy;
    }

    @JsonProperty("created_by")
    public void setTVShowGetCreatedBy(List<TVShowGetCreatedBy> TVShowGetCreatedBy) {
        this.TVShowGetCreatedBy = TVShowGetCreatedBy;
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
    public Date getFirstAirDate() {
        return firstAirDate;
    }

    @JsonProperty("first_air_date")
    public void setFirstAirDate(Date firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    @JsonProperty("genres")
    public List<MovieGetGenre> getGenres() {
        return genres;
    }

    @JsonProperty("genres")
    public void setGenres(List<MovieGetGenre> genres) {
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
    public Date getLastAirDate() {
        return lastAirDate;
    }

    @JsonProperty("last_air_date")
    public void setLastAirDate(Date lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    @JsonProperty("last_episode_to_air")
    public TVShowGetLastEpisodeToAir getTVShowGetLastEpisodeToAir() {
        return TVShowGetLastEpisodeToAir;
    }

    @JsonProperty("last_episode_to_air")
    public void setTVShowGetLastEpisodeToAir(TVShowGetLastEpisodeToAir TVShowGetLastEpisodeToAir) {
        this.TVShowGetLastEpisodeToAir = TVShowGetLastEpisodeToAir;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("next_episode_to_air")
    public Object getNextEpisodeToAir() {
        return nextEpisodeToAir;
    }

    @JsonProperty("next_episode_to_air")
    public void setNextEpisodeToAir(Object nextEpisodeToAir) {
        this.nextEpisodeToAir = nextEpisodeToAir;
    }

    @JsonProperty("networks")
    public List<TVShowGetNetwork> getTVShowGetNetworks() {
        return TVShowGetNetworks;
    }

    @JsonProperty("networks")
    public void setTVShowGetNetworks(List<TVShowGetNetwork> TVShowGetNetworks) {
        this.TVShowGetNetworks = TVShowGetNetworks;
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
    public List<MovieGetProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    @JsonProperty("production_companies")
    public void setProductionCompanies(List<MovieGetProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    @JsonProperty("seasons")
    public List<TVShowGetSeason> getTVShowGetSeasons() {
        return TVShowGetSeasons;
    }

    @JsonProperty("seasons")
    public void setTVShowGetSeasons(List<TVShowGetSeason> TVShowGetSeasons) {
        this.TVShowGetSeasons = TVShowGetSeasons;
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

    @JsonProperty("external_ids")
    public MovieGetExternalIds getExternalIds() {
        return externalIds;
    }

    @JsonProperty("external_ids")
    public void setExternalIds(MovieGetExternalIds externalIds) {
        this.externalIds = externalIds;
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

