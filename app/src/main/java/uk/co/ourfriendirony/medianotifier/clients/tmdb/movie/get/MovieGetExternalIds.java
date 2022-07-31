package uk.co.ourfriendirony.medianotifier.clients.tmdb.movie.get;

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
        "facebook_id",
        "instagram_id",
        "twitter_id"
})
public class MovieGetExternalIds {

    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("facebook_id")
    private Object facebookId;
    @JsonProperty("instagram_id")
    private Object instagramId;
    @JsonProperty("twitter_id")
    private Object twitterId;
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

    @JsonProperty("facebook_id")
    public Object getFacebookId() {
        return facebookId;
    }

    @JsonProperty("facebook_id")
    public void setFacebookId(Object facebookId) {
        this.facebookId = facebookId;
    }

    @JsonProperty("instagram_id")
    public Object getInstagramId() {
        return instagramId;
    }

    @JsonProperty("instagram_id")
    public void setInstagramId(Object instagramId) {
        this.instagramId = instagramId;
    }

    @JsonProperty("twitter_id")
    public Object getTwitterId() {
        return twitterId;
    }

    @JsonProperty("twitter_id")
    public void setTwitterId(Object twitterId) {
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
