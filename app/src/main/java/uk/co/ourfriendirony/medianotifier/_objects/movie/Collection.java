package uk.co.ourfriendirony.medianotifier._objects.movie;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name"
})
public class Collection {
    @JsonProperty("name")
    private String collectionName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getCollectionName() {
        return collectionName;
    }

    @JsonProperty("name")
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
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
