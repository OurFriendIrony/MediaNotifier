package uk.co.ourfriendirony.medianotifier.autogen.music;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "created",
        "count",
        "offset",
        "artists"
})
public class QueryArtist {

    @JsonProperty("created")
    private String created;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("artists")
    private List<MBArtist> artists = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("offset")
    public Integer getOffset() {
        return offset;
    }

    @JsonProperty("offset")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @JsonProperty("artists")
    public List<MBArtist> getArtists() {
        return artists;
    }

    @JsonProperty("artists")
    public void setArtists(List<MBArtist> artists) {
        this.artists = artists;
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