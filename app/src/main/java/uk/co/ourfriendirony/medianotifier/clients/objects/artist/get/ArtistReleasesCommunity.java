package uk.co.ourfriendirony.medianotifier.clients.objects.artist.get;


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
        "in_collection",
        "in_wantlist"
})
public class ArtistReleasesCommunity {

    @JsonProperty("in_collection")
    private Integer inCollection;
    @JsonProperty("in_wantlist")
    private Integer inWantlist;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("in_collection")
    public Integer getInCollection() {
        return inCollection;
    }

    @JsonProperty("in_collection")
    public void setInCollection(Integer inCollection) {
        this.inCollection = inCollection;
    }

    @JsonProperty("in_wantlist")
    public Integer getInWantlist() {
        return inWantlist;
    }

    @JsonProperty("in_wantlist")
    public void setInWantlist(Integer inWantlist) {
        this.inWantlist = inWantlist;
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
