package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get;

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
        "freebase_mid",
        "freebase_id",
        "tvdb_id",
        "tvrage_id"
})
public class TVSeasonGetExternalIds {

    @JsonProperty("freebase_mid")
    private String freebaseMid;
    @JsonProperty("freebase_id")
    private String freebaseId;
    @JsonProperty("tvdb_id")
    private Integer tvdbId;
    @JsonProperty("tvrage_id")
    private Object tvrageId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("freebase_mid")
    public String getFreebaseMid() {
        return freebaseMid;
    }

    @JsonProperty("freebase_mid")
    public void setFreebaseMid(String freebaseMid) {
        this.freebaseMid = freebaseMid;
    }

    @JsonProperty("freebase_id")
    public String getFreebaseId() {
        return freebaseId;
    }

    @JsonProperty("freebase_id")
    public void setFreebaseId(String freebaseId) {
        this.freebaseId = freebaseId;
    }

    @JsonProperty("tvdb_id")
    public Integer getTvdbId() {
        return tvdbId;
    }

    @JsonProperty("tvdb_id")
    public void setTvdbId(Integer tvdbId) {
        this.tvdbId = tvdbId;
    }

    @JsonProperty("tvrage_id")
    public Object getTvrageId() {
        return tvrageId;
    }

    @JsonProperty("tvrage_id")
    public void setTvrageId(Object tvrageId) {
        this.tvrageId = tvrageId;
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
