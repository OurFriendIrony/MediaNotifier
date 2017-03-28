package uk.co.ourfriendirony.medianotifier.autogen.music;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "begin",
        "ended",
        "end"
})
public class MBLifeSpan {

    @JsonProperty("begin")
    private String begin;
    @JsonProperty("ended")
    private Object ended;
    @JsonProperty("end")
    private String end;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("begin")
    public String getBegin() {
        return begin;
    }

    @JsonProperty("begin")
    public void setBegin(String begin) {
        this.begin = begin;
    }

    @JsonProperty("ended")
    public Object getEnded() {
        return ended;
    }

    @JsonProperty("ended")
    public void setEnded(Object ended) {
        this.ended = ended;
    }

    @JsonProperty("end")
    public String getEnd() {
        return end;
    }

    @JsonProperty("end")
    public void setEnd(String end) {
        this.end = end;
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