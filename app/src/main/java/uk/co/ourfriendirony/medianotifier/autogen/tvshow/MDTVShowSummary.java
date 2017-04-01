package uk.co.ourfriendirony.medianotifier.autogen.tvshow;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "poster_path",
        "popularity",
        "id",
        "backdrop_path",
        "vote_average",
        "overview",
        "first_air_date",
        "origin_country",
        "genre_ids",
        "original_language",
        "vote_count",
        "name",
        "original_name"
})
public class MDTVShowSummary {

    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("popularity")
    private Double popularity;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("vote_average")
    private Double voteAverage;
    @JsonProperty("overview")
    private String overview;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("first_air_date")
    private Date firstAirDate;
    @JsonProperty("origin_country")
    private List<String> originCountry = new ArrayList<>();
    @JsonProperty("genre_ids")
    private List<Integer> genreIds = new ArrayList<>();
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("vote_count")
    private Integer voteCount;
    @JsonProperty("name")
    private String name;
    @JsonProperty("original_name")
    private String originalName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("poster_path")
    public String getPosterPath() {
        return posterPath;
    }

    @JsonProperty("poster_path")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @JsonProperty("popularity")
    public Double getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("backdrop_path")
    public String getBackdropPath() {
        return backdropPath;
    }

    @JsonProperty("backdrop_path")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @JsonProperty("vote_average")
    public Double getVoteAverage() {
        return voteAverage;
    }

    @JsonProperty("vote_average")
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @JsonProperty("overview")
    public String getOverview() {
        return overview;
    }

    @JsonProperty("overview")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    @JsonProperty("first_air_date")
    public Date getFirstAirDate() {
        return firstAirDate;
    }

    @JsonProperty("first_air_date")
    public void setFirstAirDate(Date firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    @JsonProperty("origin_country")
    public List<String> getOriginCountry() {
        return originCountry;
    }

    @JsonProperty("origin_country")
    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    @JsonProperty("genre_ids")
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    @JsonProperty("genre_ids")
    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    @JsonProperty("original_language")
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    @JsonProperty("original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @JsonProperty("vote_count")
    public Integer getVoteCount() {
        return voteCount;
    }

    @JsonProperty("vote_count")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("original_name")
    public String getOriginalName() {
        return originalName;
    }

    @JsonProperty("original_name")
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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