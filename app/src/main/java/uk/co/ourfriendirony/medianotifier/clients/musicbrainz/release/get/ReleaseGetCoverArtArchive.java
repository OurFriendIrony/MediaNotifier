package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get;

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
        "darkened",
        "front",
        "count",
        "artwork",
        "back"
})
public class ReleaseGetCoverArtArchive {

    @JsonProperty("darkened")
    private Boolean darkened;
    @JsonProperty("front")
    private Boolean front;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("artwork")
    private Boolean artwork;
    @JsonProperty("back")
    private Boolean back;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("darkened")
    public Boolean getDarkened() {
        return darkened;
    }

    @JsonProperty("darkened")
    public void setDarkened(Boolean darkened) {
        this.darkened = darkened;
    }

    @JsonProperty("front")
    public Boolean getFront() {
        return front;
    }

    @JsonProperty("front")
    public void setFront(Boolean front) {
        this.front = front;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("artwork")
    public Boolean getArtwork() {
        return artwork;
    }

    @JsonProperty("artwork")
    public void setArtwork(Boolean artwork) {
        this.artwork = artwork;
    }

    @JsonProperty("back")
    public Boolean getBack() {
        return back;
    }

    @JsonProperty("back")
    public void setBack(Boolean back) {
        this.back = back;
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
