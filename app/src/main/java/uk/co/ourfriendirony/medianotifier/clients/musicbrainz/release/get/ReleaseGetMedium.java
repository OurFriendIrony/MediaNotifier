package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "tracks",
        "track-count",
        "format-id",
        "title",
        "track-offset",
        "position",
        "format"
})
public class ReleaseGetMedium {

    @JsonProperty("tracks")
    private List<ReleaseGetTrack> tracks = null;
    @JsonProperty("track-count")
    private Integer trackCount;
    @JsonProperty("format-id")
    private String formatId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("track-offset")
    private Integer trackOffset;
    @JsonProperty("position")
    private Integer position;
    @JsonProperty("format")
    private String format;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("tracks")
    public List<ReleaseGetTrack> getTracks() {
        return tracks;
    }

    @JsonProperty("tracks")
    public void setTracks(List<ReleaseGetTrack> tracks) {
        this.tracks = tracks;
    }

    @JsonProperty("track-count")
    public Integer getTrackCount() {
        return trackCount;
    }

    @JsonProperty("track-count")
    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
    }

    @JsonProperty("format-id")
    public String getFormatId() {
        return formatId;
    }

    @JsonProperty("format-id")
    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("track-offset")
    public Integer getTrackOffset() {
        return trackOffset;
    }

    @JsonProperty("track-offset")
    public void setTrackOffset(Integer trackOffset) {
        this.trackOffset = trackOffset;
    }

    @JsonProperty("position")
    public Integer getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Integer position) {
        this.position = position;
    }

    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
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
