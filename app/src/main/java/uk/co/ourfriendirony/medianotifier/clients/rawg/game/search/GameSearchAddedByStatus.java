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
        "yet",
        "owned",
        "beaten",
        "toplay",
        "dropped",
        "playing"
})
public class GameSearchAddedByStatus {

    @JsonProperty("yet")
    private Integer yet;
    @JsonProperty("owned")
    private Integer owned;
    @JsonProperty("beaten")
    private Integer beaten;
    @JsonProperty("toplay")
    private Integer toplay;
    @JsonProperty("dropped")
    private Integer dropped;
    @JsonProperty("playing")
    private Integer playing;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("yet")
    public Integer getYet() {
        return yet;
    }

    @JsonProperty("yet")
    public void setYet(Integer yet) {
        this.yet = yet;
    }

    @JsonProperty("owned")
    public Integer getOwned() {
        return owned;
    }

    @JsonProperty("owned")
    public void setOwned(Integer owned) {
        this.owned = owned;
    }

    @JsonProperty("beaten")
    public Integer getBeaten() {
        return beaten;
    }

    @JsonProperty("beaten")
    public void setBeaten(Integer beaten) {
        this.beaten = beaten;
    }

    @JsonProperty("toplay")
    public Integer getToplay() {
        return toplay;
    }

    @JsonProperty("toplay")
    public void setToplay(Integer toplay) {
        this.toplay = toplay;
    }

    @JsonProperty("dropped")
    public Integer getDropped() {
        return dropped;
    }

    @JsonProperty("dropped")
    public void setDropped(Integer dropped) {
        this.dropped = dropped;
    }

    @JsonProperty("playing")
    public Integer getPlaying() {
        return playing;
    }

    @JsonProperty("playing")
    public void setPlaying(Integer playing) {
        this.playing = playing;
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