package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.ourfriendirony.medianotifier.general.MultiDateDeserializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "disambiguation",
        "title",
        "secondary-type-ids",
        "primary-type",
        "secondary-types",
        "first-release-date",
        "primary-type-id"
})
public class ArtistGetReleaseGroup {

    @JsonProperty("id")
    private String id;
    @JsonProperty("disambiguation")
    private String disambiguation;
    @JsonProperty("title")
    private String title;
    @JsonProperty("secondary-type-ids")
    private List<String> secondaryTypeIds = null;
    @JsonProperty("primary-type")
    private String primaryType;
    @JsonProperty("secondary-types")
    private List<String> secondaryTypes = null;
    @JsonProperty("first-release-date")
    @JsonDeserialize(using = MultiDateDeserializer.class)
    private Date firstReleaseDate;
    @JsonProperty("primary-type-id")
    private String primaryTypeId;
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

    @JsonProperty("disambiguation")
    public String getDisambiguation() {
        return disambiguation;
    }

    @JsonProperty("disambiguation")
    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("secondary-type-ids")
    public List<String> getSecondaryTypeIds() {
        return secondaryTypeIds;
    }

    @JsonProperty("secondary-type-ids")
    public void setSecondaryTypeIds(List<String> secondaryTypeIds) {
        this.secondaryTypeIds = secondaryTypeIds;
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
    public List<String> getSecondaryTypes() {
        return secondaryTypes;
    }

    @JsonProperty("secondary-types")
    public void setSecondaryTypes(List<String> secondaryTypes) {
        this.secondaryTypes = secondaryTypes;
    }

    @JsonProperty("first-release-date")
    public Date getFirstReleaseDate() {
        return firstReleaseDate;
    }

    @JsonProperty("first-release-date")
    public void setFirstReleaseDate(Date firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    @JsonProperty("primary-type-id")
    public String getPrimaryTypeId() {
        return primaryTypeId;
    }

    @JsonProperty("primary-type-id")
    public void setPrimaryTypeId(String primaryTypeId) {
        this.primaryTypeId = primaryTypeId;
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