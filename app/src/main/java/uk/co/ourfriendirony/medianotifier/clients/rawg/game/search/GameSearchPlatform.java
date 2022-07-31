package uk.co.ourfriendirony.medianotifier.clients.rawg.game.search;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "slug",
        "image",
        "year_end",
        "year_start",
        "games_count",
        "image_background"
})
public class GameSearchPlatform {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("image")
    private Object image;
    @JsonProperty("year_end")
    private Object yearEnd;
    @JsonProperty("year_start")
    private Object yearStart;
    @JsonProperty("games_count")
    private Integer gamesCount;
    @JsonProperty("image_background")
    private String imageBackground;
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

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @JsonProperty("image")
    public Object getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(Object image) {
        this.image = image;
    }

    @JsonProperty("year_end")
    public Object getYearEnd() {
        return yearEnd;
    }

    @JsonProperty("year_end")
    public void setYearEnd(Object yearEnd) {
        this.yearEnd = yearEnd;
    }

    @JsonProperty("year_start")
    public Object getYearStart() {
        return yearStart;
    }

    @JsonProperty("year_start")
    public void setYearStart(Object yearStart) {
        this.yearStart = yearStart;
    }

    @JsonProperty("games_count")
    public Integer getGamesCount() {
        return gamesCount;
    }

    @JsonProperty("games_count")
    public void setGamesCount(Integer gamesCount) {
        this.gamesCount = gamesCount;
    }

    @JsonProperty("image_background")
    public String getImageBackground() {
        return imageBackground;
    }

    @JsonProperty("image_background")
    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
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