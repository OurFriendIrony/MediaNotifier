package uk.co.ourfriendirony.medianotifier.clients.rawg.game.get;

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
        "2",
        "11",
        "12"
})
public class GameGetReactions {

    @JsonProperty("2")
    private Integer _2;
    @JsonProperty("11")
    private Integer _11;
    @JsonProperty("12")
    private Integer _12;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("2")
    public Integer get2() {
        return _2;
    }

    @JsonProperty("2")
    public void set2(Integer _2) {
        this._2 = _2;
    }

    @JsonProperty("11")
    public Integer get11() {
        return _11;
    }

    @JsonProperty("11")
    public void set11(Integer _11) {
        this._11 = _11;
    }

    @JsonProperty("12")
    public Integer get12() {
        return _12;
    }

    @JsonProperty("12")
    public void set12(Integer _12) {
        this._12 = _12;
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