package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get;

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
        "disambiguation",
        "id",
        "area",
        "gender",
        "begin-area",
        "sort-name",
        "gender-id",
        "name",
        "type",
        "release-groups",
        "end-area",
        "ipis",
        "type-id",
        "life-span",
        "isnis",
        "country"
})
public class ArtistGet {
    @JsonProperty("disambiguation")
    private String disambiguation;
    @JsonProperty("id")
    private String id;
    @JsonProperty("area")
    private ArtistGetArea area;
    @JsonProperty("gender")
    private Object gender;
    @JsonProperty("begin-area")
    private ArtistGetBeginArea beginArea;
    @JsonProperty("sort-name")
    private String sortName;
    @JsonProperty("gender-id")
    private Object genderId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("release-groups")
    private List<ArtistGetReleaseGroup> releaseGroups = null;
    @JsonProperty("end-area")
    private Object endArea;
    @JsonProperty("ipis")
    private List<Object> ipis = null;
    @JsonProperty("type-id")
    private String typeId;
    @JsonProperty("life-span")
    private ArtistGetLifeSpan lifeSpan;
    @JsonProperty("isnis")
    private List<String> isnis = null;
    @JsonProperty("country")
    private String country;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("disambiguation")
    public String getDisambiguation() {
        return disambiguation;
    }

    @JsonProperty("disambiguation")
    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("area")
    public ArtistGetArea getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(ArtistGetArea area) {
        this.area = area;
    }

    @JsonProperty("gender")
    public Object getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(Object gender) {
        this.gender = gender;
    }

    @JsonProperty("begin-area")
    public ArtistGetBeginArea getBeginArea() {
        return beginArea;
    }

    @JsonProperty("begin-area")
    public void setBeginArea(ArtistGetBeginArea beginArea) {
        this.beginArea = beginArea;
    }

    @JsonProperty("sort-name")
    public String getSortName() {
        return sortName;
    }

    @JsonProperty("sort-name")
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    @JsonProperty("gender-id")
    public Object getGenderId() {
        return genderId;
    }

    @JsonProperty("gender-id")
    public void setGenderId(Object genderId) {
        this.genderId = genderId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("release-groups")
    public List<ArtistGetReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }

    @JsonProperty("release-groups")
    public void setReleaseGroups(List<ArtistGetReleaseGroup> releaseGroups) {
        this.releaseGroups = releaseGroups;
    }

    @JsonProperty("end-area")
    public Object getEndArea() {
        return endArea;
    }

    @JsonProperty("end-area")
    public void setEndArea(Object endArea) {
        this.endArea = endArea;
    }

    @JsonProperty("ipis")
    public List<Object> getIpis() {
        return ipis;
    }

    @JsonProperty("ipis")
    public void setIpis(List<Object> ipis) {
        this.ipis = ipis;
    }

    @JsonProperty("type-id")
    public String getTypeId() {
        return typeId;
    }

    @JsonProperty("type-id")
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @JsonProperty("life-span")
    public ArtistGetLifeSpan getLifeSpan() {
        return lifeSpan;
    }

    @JsonProperty("life-span")
    public void setLifeSpan(ArtistGetLifeSpan lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    @JsonProperty("isnis")
    public List<String> getIsnis() {
        return isnis;
    }

    @JsonProperty("isnis")
    public void setIsnis(List<String> isnis) {
        this.isnis = isnis;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
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