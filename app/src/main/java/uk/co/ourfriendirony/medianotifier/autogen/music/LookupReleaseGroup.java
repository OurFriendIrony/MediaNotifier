package uk.co.ourfriendirony.medianotifier.autogen.music;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "releases",
        "primary-type-id",
        "disambiguation",
        "primary-type",
        "secondary-types",
        "secondary-type-ids",
        "first-release-date",
        "title"
})
public class LookupReleaseGroup {

    @JsonProperty("id")
    private String id;
    @JsonProperty("releases")
    private List<MBRelease> releases = null;
    @JsonProperty("primary-type-id")
    private String primaryTypeId;
    @JsonProperty("disambiguation")
    private String disambiguation;
    @JsonProperty("primary-type")
    private String primaryType;
    @JsonProperty("secondary-types")
    private List<Object> secondaryTypes = null;
    @JsonProperty("secondary-type-ids")
    private List<Object> secondaryTypeIds = null;
    @JsonProperty("first-release-date")
    private String firstReleaseDate;
    @JsonProperty("title")
    private String title;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("releases")
    public List<MBRelease> getReleases() {
        return releases;
    }

    @JsonProperty("releases")
    public void setReleases(List<MBRelease> releases) {
        this.releases = releases;
    }

    @JsonProperty("primary-type-id")
    public String getPrimaryTypeId() {
        return primaryTypeId;
    }

    @JsonProperty("primary-type-id")
    public void setPrimaryTypeId(String primaryTypeId) {
        this.primaryTypeId = primaryTypeId;
    }

    @JsonProperty("disambiguation")
    public String getDisambiguation() {
        return disambiguation;
    }

    @JsonProperty("disambiguation")
    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    @JsonProperty("primary-type")
    public String getPrimaryType() {
        return primaryType;
    }

    @JsonProperty("primary-type")
    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    @JsonProperty("secondary-types")
    public List<Object> getSecondaryTypes() {
        return secondaryTypes;
    }

    @JsonProperty("secondary-types")
    public void setSecondaryTypes(List<Object> secondaryTypes) {
        this.secondaryTypes = secondaryTypes;
    }

    @JsonProperty("secondary-type-ids")
    public List<Object> getSecondaryTypeIds() {
        return secondaryTypeIds;
    }

    @JsonProperty("secondary-type-ids")
    public void setSecondaryTypeIds(List<Object> secondaryTypeIds) {
        this.secondaryTypeIds = secondaryTypeIds;
    }

    @JsonProperty("first-release-date")
    public String getFirstReleaseDate() {
        return firstReleaseDate;
    }

    @JsonProperty("first-release-date")
    public void setFirstReleaseDate(String firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
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
