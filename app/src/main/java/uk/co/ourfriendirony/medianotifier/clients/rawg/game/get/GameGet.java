package uk.co.ourfriendirony.medianotifier.clients.rawg.game.get;

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
        "id",
        "slug",
        "name",
        "name_original",
        "description",
        "metacritic",
        "metacritic_platforms",
        "released",
        "tba",
        "updated",
        "background_image",
        "background_image_additional",
        "website",
        "rating",
        "rating_top",
        "ratings",
        "reactions",
        "added",
        "added_by_status",
        "playtime",
        "screenshots_count",
        "movies_count",
        "creators_count",
        "achievements_count",
        "parent_achievements_count",
        "reddit_url",
        "reddit_name",
        "reddit_description",
        "reddit_logo",
        "reddit_count",
        "twitch_count",
        "youtube_count",
        "reviews_text_count",
        "ratings_count",
        "suggestions_count",
        "alternative_names",
        "metacritic_url",
        "parents_count",
        "additions_count",
        "game_series_count",
        "user_game",
        "reviews_count",
        "saturated_color",
        "dominant_color",
        "parent_platforms",
        "platforms",
        "stores",
        "developers",
        "genres",
        "tags",
        "publishers",
        "esrb_rating",
        "clip",
        "description_raw"
})
public class GameGet {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("name")
    private String name;
    @JsonProperty("name_original")
    private String nameOriginal;
    @JsonProperty("description")
    private String description;
    @JsonProperty("metacritic")
    private Integer metacritic;
    @JsonProperty("metacritic_platforms")
    private List<Object> metacriticPlatforms = null;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("released")
    private Date released;
    @JsonProperty("tba")
    private Boolean tba;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("updated")
    private Date updated;
    @JsonProperty("background_image")
    private String backgroundImage;
    @JsonProperty("background_image_additional")
    private String backgroundImageAdditional;
    @JsonProperty("website")
    private String website;
    @JsonProperty("rating")
    private Double rating;
    @JsonProperty("rating_top")
    private Integer ratingTop;
    @JsonProperty("ratings")
    private List<GameGetRating> ratings = null;
    @JsonProperty("reactions")
    private GameGetReactions reactions;
    @JsonProperty("added")
    private Integer added;
    @JsonProperty("added_by_status")
    private GameGetAddedByStatus addedByStatus;
    @JsonProperty("playtime")
    private Integer playtime;
    @JsonProperty("screenshots_count")
    private Integer screenshotsCount;
    @JsonProperty("movies_count")
    private Integer moviesCount;
    @JsonProperty("creators_count")
    private Integer creatorsCount;
    @JsonProperty("achievements_count")
    private Integer achievementsCount;
    @JsonProperty("parent_achievements_count")
    private Integer parentAchievementsCount;
    @JsonProperty("reddit_url")
    private String redditUrl;
    @JsonProperty("reddit_name")
    private String redditName;
    @JsonProperty("reddit_description")
    private String redditDescription;
    @JsonProperty("reddit_logo")
    private String redditLogo;
    @JsonProperty("reddit_count")
    private Integer redditCount;
    @JsonProperty("twitch_count")
    private Integer twitchCount;
    @JsonProperty("youtube_count")
    private Integer youtubeCount;
    @JsonProperty("reviews_text_count")
    private Integer reviewsTextCount;
    @JsonProperty("ratings_count")
    private Integer ratingsCount;
    @JsonProperty("suggestions_count")
    private Integer suggestionsCount;
    @JsonProperty("alternative_names")
    private List<String> alternativeNames = null;
    @JsonProperty("metacritic_url")
    private String metacriticUrl;
    @JsonProperty("parents_count")
    private Integer parentsCount;
    @JsonProperty("additions_count")
    private Integer additionsCount;
    @JsonProperty("game_series_count")
    private Integer gameSeriesCount;
    @JsonProperty("user_game")
    private Object userGame;
    @JsonProperty("reviews_count")
    private Integer reviewsCount;
    @JsonProperty("saturated_color")
    private String saturatedColor;
    @JsonProperty("dominant_color")
    private String dominantColor;
    @JsonProperty("parent_platforms")
    private List<GameGetParentPlatformGroup> parentPlatforms = null;
    @JsonProperty("platforms")
    private List<GameGetPlatformGroup> platforms = null;
    @JsonProperty("stores")
    private List<GameGetStoreGroup> stores = null;
    @JsonProperty("developers")
    private List<GameGetDeveloper> developers = null;
    @JsonProperty("genres")
    private List<GameGetGenre> genres = null;
    @JsonProperty("tags")
    private List<GameGetTag> tags = null;
    @JsonProperty("publishers")
    private List<GameGetPublisher> publishers = null;
    @JsonProperty("esrb_rating")
    private GameGetEsrbRating esrbRating;
    @JsonProperty("clip")
    private Object clip;
    @JsonProperty("description_raw")
    private String descriptionRaw;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

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

    @JsonProperty("name_original")
    public String getNameOriginal() {
        return nameOriginal;
    }

    @JsonProperty("name_original")
    public void setNameOriginal(String nameOriginal) {
        this.nameOriginal = nameOriginal;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("metacritic")
    public Integer getMetacritic() {
        return metacritic;
    }

    @JsonProperty("metacritic")
    public void setMetacritic(Integer metacritic) {
        this.metacritic = metacritic;
    }

    @JsonProperty("metacritic_platforms")
    public List<Object> getMetacriticPlatforms() {
        return metacriticPlatforms;
    }

    @JsonProperty("metacritic_platforms")
    public void setMetacriticPlatforms(List<Object> metacriticPlatforms) {
        this.metacriticPlatforms = metacriticPlatforms;
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

    @JsonProperty("updated")
    public Date getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @JsonProperty("background_image")
    public String getBackgroundImage() {
        return backgroundImage;
    }

    @JsonProperty("background_image")
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @JsonProperty("background_image_additional")
    public String getBackgroundImageAdditional() {
        return backgroundImageAdditional;
    }

    @JsonProperty("background_image_additional")
    public void setBackgroundImageAdditional(String backgroundImageAdditional) {
        this.backgroundImageAdditional = backgroundImageAdditional;
    }

    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    @JsonProperty("website")
    public void setWebsite(String website) {
        this.website = website;
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
    public List<GameGetRating> getRatings() {
        return ratings;
    }

    @JsonProperty("ratings")
    public void setRatings(List<GameGetRating> ratings) {
        this.ratings = ratings;
    }

    @JsonProperty("reactions")
    public GameGetReactions getReactions() {
        return reactions;
    }

    @JsonProperty("reactions")
    public void setReactions(GameGetReactions reactions) {
        this.reactions = reactions;
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
    public GameGetAddedByStatus getAddedByStatus() {
        return addedByStatus;
    }

    @JsonProperty("added_by_status")
    public void setAddedByStatus(GameGetAddedByStatus addedByStatus) {
        this.addedByStatus = addedByStatus;
    }

    @JsonProperty("playtime")
    public Integer getPlaytime() {
        return playtime;
    }

    @JsonProperty("playtime")
    public void setPlaytime(Integer playtime) {
        this.playtime = playtime;
    }

    @JsonProperty("screenshots_count")
    public Integer getScreenshotsCount() {
        return screenshotsCount;
    }

    @JsonProperty("screenshots_count")
    public void setScreenshotsCount(Integer screenshotsCount) {
        this.screenshotsCount = screenshotsCount;
    }

    @JsonProperty("movies_count")
    public Integer getMoviesCount() {
        return moviesCount;
    }

    @JsonProperty("movies_count")
    public void setMoviesCount(Integer moviesCount) {
        this.moviesCount = moviesCount;
    }

    @JsonProperty("creators_count")
    public Integer getCreatorsCount() {
        return creatorsCount;
    }

    @JsonProperty("creators_count")
    public void setCreatorsCount(Integer creatorsCount) {
        this.creatorsCount = creatorsCount;
    }

    @JsonProperty("achievements_count")
    public Integer getAchievementsCount() {
        return achievementsCount;
    }

    @JsonProperty("achievements_count")
    public void setAchievementsCount(Integer achievementsCount) {
        this.achievementsCount = achievementsCount;
    }

    @JsonProperty("parent_achievements_count")
    public Integer getParentAchievementsCount() {
        return parentAchievementsCount;
    }

    @JsonProperty("parent_achievements_count")
    public void setParentAchievementsCount(Integer parentAchievementsCount) {
        this.parentAchievementsCount = parentAchievementsCount;
    }

    @JsonProperty("reddit_url")
    public String getRedditUrl() {
        return redditUrl;
    }

    @JsonProperty("reddit_url")
    public void setRedditUrl(String redditUrl) {
        this.redditUrl = redditUrl;
    }

    @JsonProperty("reddit_name")
    public String getRedditName() {
        return redditName;
    }

    @JsonProperty("reddit_name")
    public void setRedditName(String redditName) {
        this.redditName = redditName;
    }

    @JsonProperty("reddit_description")
    public String getRedditDescription() {
        return redditDescription;
    }

    @JsonProperty("reddit_description")
    public void setRedditDescription(String redditDescription) {
        this.redditDescription = redditDescription;
    }

    @JsonProperty("reddit_logo")
    public String getRedditLogo() {
        return redditLogo;
    }

    @JsonProperty("reddit_logo")
    public void setRedditLogo(String redditLogo) {
        this.redditLogo = redditLogo;
    }

    @JsonProperty("reddit_count")
    public Integer getRedditCount() {
        return redditCount;
    }

    @JsonProperty("reddit_count")
    public void setRedditCount(Integer redditCount) {
        this.redditCount = redditCount;
    }

    @JsonProperty("twitch_count")
    public Integer getTwitchCount() {
        return twitchCount;
    }

    @JsonProperty("twitch_count")
    public void setTwitchCount(Integer twitchCount) {
        this.twitchCount = twitchCount;
    }

    @JsonProperty("youtube_count")
    public Integer getYoutubeCount() {
        return youtubeCount;
    }

    @JsonProperty("youtube_count")
    public void setYoutubeCount(Integer youtubeCount) {
        this.youtubeCount = youtubeCount;
    }

    @JsonProperty("reviews_text_count")
    public Integer getReviewsTextCount() {
        return reviewsTextCount;
    }

    @JsonProperty("reviews_text_count")
    public void setReviewsTextCount(Integer reviewsTextCount) {
        this.reviewsTextCount = reviewsTextCount;
    }

    @JsonProperty("ratings_count")
    public Integer getRatingsCount() {
        return ratingsCount;
    }

    @JsonProperty("ratings_count")
    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    @JsonProperty("suggestions_count")
    public Integer getSuggestionsCount() {
        return suggestionsCount;
    }

    @JsonProperty("suggestions_count")
    public void setSuggestionsCount(Integer suggestionsCount) {
        this.suggestionsCount = suggestionsCount;
    }

    @JsonProperty("alternative_names")
    public List<String> getAlternativeNames() {
        return alternativeNames;
    }

    @JsonProperty("alternative_names")
    public void setAlternativeNames(List<String> alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    @JsonProperty("metacritic_url")
    public String getMetacriticUrl() {
        return metacriticUrl;
    }

    @JsonProperty("metacritic_url")
    public void setMetacriticUrl(String metacriticUrl) {
        this.metacriticUrl = metacriticUrl;
    }

    @JsonProperty("parents_count")
    public Integer getParentsCount() {
        return parentsCount;
    }

    @JsonProperty("parents_count")
    public void setParentsCount(Integer parentsCount) {
        this.parentsCount = parentsCount;
    }

    @JsonProperty("additions_count")
    public Integer getAdditionsCount() {
        return additionsCount;
    }

    @JsonProperty("additions_count")
    public void setAdditionsCount(Integer additionsCount) {
        this.additionsCount = additionsCount;
    }

    @JsonProperty("game_series_count")
    public Integer getGameSeriesCount() {
        return gameSeriesCount;
    }

    @JsonProperty("game_series_count")
    public void setGameSeriesCount(Integer gameSeriesCount) {
        this.gameSeriesCount = gameSeriesCount;
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

    @JsonProperty("parent_platforms")
    public List<GameGetParentPlatformGroup> getParentPlatforms() {
        return parentPlatforms;
    }

    @JsonProperty("parent_platforms")
    public void setParentPlatforms(List<GameGetParentPlatformGroup> parentPlatforms) {
        this.parentPlatforms = parentPlatforms;
    }

    @JsonProperty("platforms")
    public List<GameGetPlatformGroup> getPlatforms() {
        return platforms;
    }

    @JsonProperty("platforms")
    public void setPlatforms(List<GameGetPlatformGroup> platforms) {
        this.platforms = platforms;
    }

    @JsonProperty("stores")
    public List<GameGetStoreGroup> getStores() {
        return stores;
    }

    @JsonProperty("stores")
    public void setStores(List<GameGetStoreGroup> stores) {
        this.stores = stores;
    }

    @JsonProperty("developers")
    public List<GameGetDeveloper> getDevelopers() {
        return developers;
    }

    @JsonProperty("developers")
    public void setDevelopers(List<GameGetDeveloper> developers) {
        this.developers = developers;
    }

    @JsonProperty("genres")
    public List<GameGetGenre> getGenres() {
        return genres;
    }

    @JsonProperty("genres")
    public void setGenres(List<GameGetGenre> genres) {
        this.genres = genres;
    }

    @JsonProperty("tags")
    public List<GameGetTag> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<GameGetTag> tags) {
        this.tags = tags;
    }

    @JsonProperty("publishers")
    public List<GameGetPublisher> getPublishers() {
        return publishers;
    }

    @JsonProperty("publishers")
    public void setPublishers(List<GameGetPublisher> publishers) {
        this.publishers = publishers;
    }

    @JsonProperty("esrb_rating")
    public GameGetEsrbRating getEsrbRating() {
        return esrbRating;
    }

    @JsonProperty("esrb_rating")
    public void setEsrbRating(GameGetEsrbRating esrbRating) {
        this.esrbRating = esrbRating;
    }

    @JsonProperty("clip")
    public Object getClip() {
        return clip;
    }

    @JsonProperty("clip")
    public void setClip(Object clip) {
        this.clip = clip;
    }

    @JsonProperty("description_raw")
    public String getDescriptionRaw() {
        return descriptionRaw;
    }

    @JsonProperty("description_raw")
    public void setDescriptionRaw(String descriptionRaw) {
        this.descriptionRaw = descriptionRaw;
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
