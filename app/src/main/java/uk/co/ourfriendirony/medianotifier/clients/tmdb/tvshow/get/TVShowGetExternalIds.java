package uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get;

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
        "imdb_id",
        "freebase_mid",
        "freebase_id",
        "tvdb_id",
        "tvrage_id",
        "facebook_id",
        "instagram_id",
        "twitter_id"
})
public class TVShowGetExternalIds {

    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("freebase_mid")
    private String freebaseMid;
    @JsonProperty("freebase_id")
    private String freebaseId;
    @JsonProperty("tvdb_id")
    private Integer tvdbId;
    @JsonProperty("tvrage_id")
    private Integer tvrageId;
    @JsonProperty("facebook_id")
    private String facebookId;
    @JsonProperty("instagram_id")
    private String instagramId;
    @JsonProperty("twitter_id")
    private String twitterId;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("imdb_id")
    public String getImdbId() {
        return imdbId;
    }

    @JsonProperty("imdb_id")
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

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
    public Integer getTvrageId() {
        return tvrageId;
    }

    @JsonProperty("tvrage_id")
    public void setTvrageId(Integer tvrageId) {
        this.tvrageId = tvrageId;
    }

    @JsonProperty("facebook_id")
    public String getFacebookId() {
        return facebookId;
    }

    @JsonProperty("facebook_id")
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    @JsonProperty("instagram_id")
    public String getInstagramId() {
        return instagramId;
    }

    @JsonProperty("instagram_id")
    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }

    @JsonProperty("twitter_id")
    public String getTwitterId() {
        return twitterId;
    }

    @JsonProperty("twitter_id")
    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
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
