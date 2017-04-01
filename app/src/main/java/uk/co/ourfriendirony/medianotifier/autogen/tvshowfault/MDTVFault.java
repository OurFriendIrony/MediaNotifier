package uk.co.ourfriendirony.medianotifier.autogen.tvshowfault;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status_code",
        "status_message"
})
public class MDTVFault {

    @JsonProperty("status_code")
    private Integer statusCode;
    @JsonProperty("status_message")
    private String statusMessage;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("status_code")
    public Integer getPosterPath() {
        return statusCode;
    }

    @JsonProperty("status_code")
    public void setPosterPath(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @JsonProperty("status_message")
    public String getPopularity() {
        return statusMessage;
    }

    @JsonProperty("status_message")
    public void setPopularity(String popularity) {
        this.statusMessage = popularity;
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