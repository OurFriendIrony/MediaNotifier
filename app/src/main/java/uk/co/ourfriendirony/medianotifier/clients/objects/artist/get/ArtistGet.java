package uk.co.ourfriendirony.medianotifier.clients.objects.artist.get;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "profile",
        "releases_url",
        "name",
        "namevariations",
        "uri",
        "members",
        "urls",
        "images",
        "resource_url",
        "id",
        "data_quality",
        "aliases"
})
public class ArtistGet {

    @JsonProperty("profile")
    private String profile;
    @JsonProperty("releases_url")
    private String releasesUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("namevariations")
    private List<String> namevariations = null;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("members")
    private List<ArtistGetMember> members = null;
    @JsonProperty("urls")
    private List<String> urls = null;
    @JsonProperty("images")
    private List<ArtistGetImage> images = null;
    @JsonProperty("resource_url")
    private String resourceUrl;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("data_quality")
    private String dataQuality;
    @JsonProperty("aliases")
    private List<ArtistGetAlias> aliases = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("profile")
    public String getProfile() {
        return profile;
    }

    @JsonProperty("profile")
    public void setProfile(String profile) {
        this.profile = profile;
    }

    @JsonProperty("releases_url")
    public String getReleasesUrl() {
        return releasesUrl;
    }

    @JsonProperty("releases_url")
    public void setReleasesUrl(String releasesUrl) {
        this.releasesUrl = releasesUrl;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("namevariations")
    public List<String> getNamevariations() {
        return namevariations;
    }

    @JsonProperty("namevariations")
    public void setNamevariations(List<String> namevariations) {
        this.namevariations = namevariations;
    }

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("members")
    public List<ArtistGetMember> getMembers() {
        return members;
    }

    @JsonProperty("members")
    public void setMembers(List<ArtistGetMember> members) {
        this.members = members;
    }

    @JsonProperty("urls")
    public List<String> getUrls() {
        return urls;
    }

    @JsonProperty("urls")
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @JsonProperty("images")
    public List<ArtistGetImage> getImages() {
        return images;
    }

    @JsonProperty("images")
    public void setImages(List<ArtistGetImage> images) {
        this.images = images;
    }

    @JsonProperty("resource_url")
    public String getResourceUrl() {
        return resourceUrl;
    }

    @JsonProperty("resource_url")
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("data_quality")
    public String getDataQuality() {
        return dataQuality;
    }

    @JsonProperty("data_quality")
    public void setDataQuality(String dataQuality) {
        this.dataQuality = dataQuality;
    }

    @JsonProperty("aliases")
    public List<ArtistGetAlias> getAliases() {
        return aliases;
    }

    @JsonProperty("aliases")
    public void setAliases(List<ArtistGetAlias> aliases) {
        this.aliases = aliases;
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
