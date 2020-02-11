package uk.co.ourfriendirony.medianotifier.clients.objects.artist.search;

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
        "thumb",
        "title",
        "uri",
        "master_url",
        "cover_image",
        "resource_url",
        "master_id",
        "type",
        "id"
})
public class ArtistSearchResult {
    @JsonProperty("thumb")
    private String thumb;
    @JsonProperty("title")
    private String title;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("master_url")
    private Object masterUrl;
    @JsonProperty("cover_image")
    private String coverImage;
    @JsonProperty("resource_url")
    private String resourceUrl;
    @JsonProperty("master_id")
    private Object masterId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private Integer id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("thumb")
    public String getThumb() {
        return thumb;
    }

    @JsonProperty("thumb")
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("master_url")
    public Object getMasterUrl() {
        return masterUrl;
    }

    @JsonProperty("master_url")
    public void setMasterUrl(Object masterUrl) {
        this.masterUrl = masterUrl;
    }

    @JsonProperty("cover_image")
    public String getCoverImage() {
        return coverImage;
    }

    @JsonProperty("cover_image")
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    @JsonProperty("resource_url")
    public String getResourceUrl() {
        return resourceUrl;
    }

    @JsonProperty("resource_url")
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    @JsonProperty("master_id")
    public Object getMasterId() {
        return masterId;
    }

    @JsonProperty("master_id")
    public void setMasterId(Object masterId) {
        this.masterId = masterId;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
