package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "begin",
        "end",
        "ended"
})
public class ArtistSearchLifeSpan {
    @JsonProperty("begin") @JsonDeserialize(using = MultiDateDeserializer.class)
    private Date begin;
    @JsonProperty("end")
    private String end;
    @JsonProperty("ended")
    private Object ended;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("ended")
    public Object getEnded() {
        return ended;
    }

    @JsonProperty("ended")
    public void setEnded(Object ended) {
        this.ended = ended;
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