package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.get;

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
        "ended",
        "begin",
        "end"
})
public class ArtistGetLifeSpan {

    @JsonProperty("ended")
    private Boolean ended;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    @JsonProperty("begin")
    private Date begin;
    @JsonProperty("end")
    private String end;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ended")
    public Boolean getEnded() {
        return ended;
    }

    @JsonProperty("ended")
    public void setEnded(Boolean ended) {
        this.ended = ended;
    }

    @JsonProperty("begin")
    public Date getBegin() {
        return begin;
    }

    @JsonProperty("begin")
    public void setBegin(Date begin) {
        this.begin = begin;
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