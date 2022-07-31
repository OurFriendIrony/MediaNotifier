package uk.co.ourfriendirony.medianotifier.clients.rawg.game.search;

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
        "slug",
        "name",
        "playtime",
        "platforms",
        "stores",
        "released",
        "tba",
        "background_image",
        "rating",
        "rating_top",
        "ratings",
        "ratings_count",
        "reviews_text_count",
        "added",
        "added_by_status",
        "metacritic",
        "suggestions_count",
        "id",
        "score",
        "clip",
        "tags",
        "user_game",
        "reviews_count",
        "saturated_color",
        "dominant_color",
        "short_screenshots",
        "parent_platforms",
        "genres"
})
public class GameSearchResult {

    @JsonProperty("slug")
    private String slug;
    @JsonProperty("name")
    private String name;
    @JsonProperty("playtime")
    private Integer playtime;
    @JsonProperty("platforms")
    private List<GameSearchPlatformGroup> platforms = null;
    @JsonProperty("stores")
    private List<GameSearchStore> stores = null;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("released")
    private Date released;
    @JsonProperty("tba")
    private Boolean tba;
    @JsonProperty("background_image")
    private String backgroundImage;
    @JsonProperty("rating")
    private Double rating;
    @JsonProperty("rating_top")
    private Integer ratingTop;
    @JsonProperty("ratings")
    private List<GameSearchRating> ratings = null;
    @JsonProperty("ratings_count")
    private Integer ratingsCount;
    @JsonProperty("reviews_text_count")
    private Integer reviewsTextCount;
    @JsonProperty("added")
    private Integer added;
    @JsonProperty("added_by_status")
    private GameSearchAddedByStatus addedByStatus;
    @JsonProperty("metacritic")
    private Integer metacritic;
    @JsonProperty("suggestions_count")
    private Integer suggestionsCount;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("score")
    private String score;
    @JsonProperty("clip")
    private Object clip;
    @JsonProperty("tags")
    private List<GameSearchTag> tags = null;
    @JsonProperty("user_game")
    private Object userGame;
    @JsonProperty("reviews_count")
    private Integer reviewsCount;
    @JsonProperty("saturated_color")
    private String saturatedColor;
    @JsonProperty("dominant_color")
    private String dominantColor;
    @JsonProperty("short_screenshots")
    private List<GameSearchShortScreenshot> shortScreenshots = null;
    @JsonProperty("parent_platforms")
    private List<GameSearchParentPlatformGroup> parentPlatforms = null;
    @JsonProperty("genres")
    private List<GameSearchGenre> genres = null;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("playtime")
    public Integer getPlaytime() {
        return playtime;
    }

    @JsonProperty("playtime")
    public void setPlaytime(Integer playtime) {
        this.playtime = playtime;
    }

    @JsonProperty("platforms")
    public List<GameSearchPlatformGroup> getPlatforms() {
        return platforms;
    }

    @JsonProperty("platforms")
    public void setPlatforms(List<GameSearchPlatformGroup> platforms) {
        this.platforms = platforms;
    }

    @JsonProperty("stores")
    public List<GameSearchStore> getStores() {
        return stores;
    }

    @JsonProperty("stores")
    public void setStores(List<GameSearchStore> stores) {
        this.stores = stores;
    }

    @JsonProperty("released")
    public Date getReleased() {
        return released;
    }

    @JsonProperty("released")
    public void setReleased(Date released) {
        this.released = released;
    }

    @JsonProperty("tba")
    public Boolean getTba() {
        return tba;
    }

    @JsonProperty("tba")
    public void setTba(Boolean tba) {
        this.tba = tba;
    }

    @JsonProperty("background_image")
    public String getBackgroundImage() {
        return backgroundImage;
    }

    @JsonProperty("background_image")
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @JsonProperty("rating")
    public Double getRating() {
        return rating;
    }

    @JsonProperty("rating")
    public void setRating(Double rating) {
        this.rating = rating;
    }

    @JsonProperty("rating_top")
    public Integer getRatingTop() {
        return ratingTop;
    }

    @JsonProperty("rating_top")
    public void setRatingTop(Integer ratingTop) {
        this.ratingTop = ratingTop;
    }

    @JsonProperty("ratings")
    public List<GameSearchRating> getRatings() {
        return ratings;
    }

    @JsonProperty("ratings")
    public void setRatings(List<GameSearchRating> ratings) {
        this.ratings = ratings;
    }

    @JsonProperty("ratings_count")
    public Integer getRatingsCount() {
        return ratingsCount;
    }

    @JsonProperty("ratings_count")
    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    @JsonProperty("reviews_text_count")
    public Integer getReviewsTextCount() {
        return reviewsTextCount;
    }

    @JsonProperty("reviews_text_count")
    public void setReviewsTextCount(Integer reviewsTextCount) {
        this.reviewsTextCount = reviewsTextCount;
    }

    @JsonProperty("added")
    public Integer getAdded() {
        return added;
    }

    @JsonProperty("added")
    public void setAdded(Integer added) {
        this.added = added;
    }

    @JsonProperty("added_by_status")
    public GameSearchAddedByStatus getAddedByStatus() {
        return addedByStatus;
    }

    @JsonProperty("added_by_status")
    public void setAddedByStatus(GameSearchAddedByStatus addedByStatus) {
        this.addedByStatus = addedByStatus;
    }

    @JsonProperty("metacritic")
    public Integer getMetacritic() {
        return metacritic;
    }

    @JsonProperty("metacritic")
    public void setMetacritic(Integer metacritic) {
        this.metacritic = metacritic;
    }

    @JsonProperty("suggestions_count")
    public Integer getSuggestionsCount() {
        return suggestionsCount;
    }

    @JsonProperty("suggestions_count")
    public void setSuggestionsCount(Integer suggestionsCount) {
        this.suggestionsCount = suggestionsCount;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("score")
    public String getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(String score) {
        this.score = score;
    }

    @JsonProperty("clip")
    public Object getClip() {
        return clip;
    }

    @JsonProperty("clip")
    public void setClip(Object clip) {
        this.clip = clip;
    }

    @JsonProperty("tags")
    public List<GameSearchTag> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<GameSearchTag> tags) {
        this.tags = tags;
    }

    @JsonProperty("user_game")
    public Object getUserGame() {
        return userGame;
    }

    @JsonProperty("user_game")
    public void setUserGame(Object userGame) {
        this.userGame = userGame;
    }

    @JsonProperty("reviews_count")
    public Integer getReviewsCount() {
        return reviewsCount;
    }

    @JsonProperty("reviews_count")
    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    @JsonProperty("saturated_color")
    public String getSaturatedColor() {
        return saturatedColor;
    }

    @JsonProperty("saturated_color")
    public void setSaturatedColor(String saturatedColor) {
        this.saturatedColor = saturatedColor;
    }

    @JsonProperty("dominant_color")
    public String getDominantColor() {
        return dominantColor;
    }

    @JsonProperty("dominant_color")
    public void setDominantColor(String dominantColor) {
        this.dominantColor = dominantColor;
    }

    @JsonProperty("short_screenshots")
    public List<GameSearchShortScreenshot> getShortScreenshots() {
        return shortScreenshots;
    }

    @JsonProperty("short_screenshots")
    public void setShortScreenshots(List<GameSearchShortScreenshot> shortScreenshots) {
        this.shortScreenshots = shortScreenshots;
    }

    @JsonProperty("parent_platforms")
    public List<GameSearchParentPlatformGroup> getParentPlatforms() {
        return parentPlatforms;
    }

    @JsonProperty("parent_platforms")
    public void setParentPlatforms(List<GameSearchParentPlatformGroup> parentPlatforms) {
        this.parentPlatforms = parentPlatforms;
    }

    @JsonProperty("genres")
    public List<GameSearchGenre> getGenres() {
        return genres;
    }

    @JsonProperty("genres")
    public void setGenres(List<GameSearchGenre> genres) {
        this.genres = genres;
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