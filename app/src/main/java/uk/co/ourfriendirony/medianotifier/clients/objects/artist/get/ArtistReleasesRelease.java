package uk.co.ourfriendirony.medianotifier.clients.objects.artist.get;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "stats",
        "thumb",
        "artist",
        "main_release",
        "title",
        "role",
        "year",
        "resource_url",
        "type",
        "id",
        "status",
        "format",
        "label"
})
public class ArtistReleasesRelease {
    @JsonProperty("stats")
    private ArtistReleasesStats stats;
    @JsonProperty("thumb")
    private String thumb;
    @JsonProperty("artist")
    private String artist;
    @JsonProperty("main_release")
    private Integer mainRelease;
    @JsonProperty("title")
    private String title;
    @JsonProperty("role")
    private String role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    @JsonProperty("year")
    private Date year;
    @JsonProperty("resource_url")
    private String resourceUrl;
    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("format")
    private String format;
    @JsonProperty("label")
    private String label;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("stats")
    public ArtistReleasesStats getStats() {
        return stats;
    }

    @JsonProperty("stats")
    public void setStats(ArtistReleasesStats stats) {
        this.stats = stats;
    }

    @JsonProperty("thumb")
    public String getThumb() {
        return thumb;
    }

    @JsonProperty("thumb")
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @JsonProperty("artist")
    public String getArtist() {
        return artist;
    }

    @JsonProperty("artist")
    public void setArtist(String artist) {
        this.artist = artist;
    }

    @JsonProperty("main_release")
    public Integer getMainRelease() {
        return mainRelease;
    }

    @JsonProperty("main_release")
    public void setMainRelease(Integer mainRelease) {
        this.mainRelease = mainRelease;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("year")
    public Date getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(Date year) {
        this.year = year;
    }

    @JsonProperty("resource_url")
    public String getResourceUrl() {
        return resourceUrl;
    }

    @JsonProperty("resource_url")
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
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
