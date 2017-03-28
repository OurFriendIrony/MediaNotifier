package uk.co.ourfriendirony.medianotifier.autogen.music;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "secondary-types",
        "id",
        "secondary-type-ids",
        "disambiguation",
        "primary-type-id",
        "first-release-date",
        "primary-type"
})
public class MBReleaseGroup {

    @JsonProperty("title")
    private String title;
    @JsonProperty("secondary-types")
    private List<String> secondaryTypes = null;
    @JsonProperty("id")
    private String id;
    @JsonProperty("secondary-type-ids")
    private List<String> secondaryTypeIds = null;
    @JsonProperty("disambiguation")
    private String disambiguation;
    @JsonProperty("primary-type-id")
    private String primaryTypeId;
    @JsonProperty("first-release-date")
    private String firstReleaseDate;
    @JsonProperty("primary-type")
    private String primaryType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("secondary-types")
    public List<String> getSecondaryTypes() {
        return secondaryTypes;
    }

    @JsonProperty("secondary-types")
    public void setSecondaryTypes(List<String> secondaryTypes) {
        this.secondaryTypes = secondaryTypes;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("secondary-type-ids")
    public List<String> getSecondaryTypeIds() {
        return secondaryTypeIds;
    }

    @JsonProperty("secondary-type-ids")
    public void setSecondaryTypeIds(List<String> secondaryTypeIds) {
        this.secondaryTypeIds = secondaryTypeIds;
    }

    @JsonProperty("disambiguation")
    public String getDisambiguation() {
        return disambiguation;
    }

    @JsonProperty("disambiguation")
    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    @JsonProperty("primary-type-id")
    public String getPrimaryTypeId() {
        return primaryTypeId;
    }

    @JsonProperty("primary-type-id")
    public void setPrimaryTypeId(String primaryTypeId) {
        this.primaryTypeId = primaryTypeId;
    }

    @JsonProperty("first-release-date")
    public String getFirstReleaseDate() {
        return firstReleaseDate;
    }

    @JsonProperty("first-release-date")
    public void setFirstReleaseDate(String firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    @JsonProperty("primary-type")
    public String getPrimaryType() {
        return primaryType;
    }

    @JsonProperty("primary-type")
    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
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
