package uk.co.ourfriendirony.medianotifier.autogen.music;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "format-id",
        "position",
        "track-offset",
        "format",
        "tracks",
        "title",
        "track-count"
})
public class MBMedium {

    @JsonProperty("format-id")
    private String formatId;
    @JsonProperty("position")
    private Integer position;
    @JsonProperty("track-offset")
    private Integer trackOffset;
    @JsonProperty("format")
    private String format;
    @JsonProperty("tracks")
    private List<MBTrack> tracks = null;
    @JsonProperty("title")
    private String title;
    @JsonProperty("track-count")
    private Integer trackCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("format-id")
    public String getFormatId() {
        return formatId;
    }

    @JsonProperty("format-id")
    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    @JsonProperty("position")
    public Integer getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Integer position) {
        this.position = position;
    }

    @JsonProperty("track-offset")
    public Integer getTrackOffset() {
        return trackOffset;
    }

    @JsonProperty("track-offset")
    public void setTrackOffset(Integer trackOffset) {
        this.trackOffset = trackOffset;
    }

    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    @JsonProperty("tracks")
    public List<MBTrack> getTracks() {
        return tracks;
    }

    @JsonProperty("tracks")
    public void setTracks(List<MBTrack> tracks) {
        this.tracks = tracks;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("track-count")
    public Integer getTrackCount() {
        return trackCount;
    }

    @JsonProperty("track-count")
    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
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