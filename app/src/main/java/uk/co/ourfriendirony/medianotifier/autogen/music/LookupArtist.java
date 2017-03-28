package uk.co.ourfriendirony.medianotifier.autogen.music;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "area",
        "begin_area",
        "country",
        "disambiguation",
        "end_area",
        "id",
        "ipis",
        "isnis",
        "gender",
        "gender-id",
        "life-span",
        "name",
        "release-groups",
        "sort-name",
        "type",
        "type-id"
})
public class LookupArtist {

    @JsonProperty("end_area")
    private Object endArea;
    @JsonProperty("type")
    private String type;
    @JsonProperty("country")
    private String country;
    @JsonProperty("isnis")
    private List<String> isnis = null;
    @JsonProperty("release-groups")
    private List<MBReleaseGroup> releaseGroups = null;
    @JsonProperty("disambiguation")
    private String disambiguation;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type-id")
    private String typeId;
    @JsonProperty("id")
    private String id;
    @JsonProperty("gender-id")
    private Object genderId;
    @JsonProperty("life-span")
    private MBLifeSpan lifeSpan;
    @JsonProperty("begin_area")
    private MBBeginArea beginArea;
    @JsonProperty("gender")
    private Object gender;
    @JsonProperty("ipis")
    private List<Object> ipis = null;
    @JsonProperty("sort-name")
    private String sortName;
    @JsonProperty("area")
    private MBArea area;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("end_area")
    public Object getEndArea() {
        return endArea;
    }

    @JsonProperty("end_area")
    public void setEndArea(Object endArea) {
        this.endArea = endArea;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("isnis")
    public List<String> getIsnis() {
        return isnis;
    }

    @JsonProperty("isnis")
    public void setIsnis(List<String> isnis) {
        this.isnis = isnis;
    }

    @JsonProperty("release-groups")
    public List<MBReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }

    @JsonProperty("release-groups")
    public void setReleaseGroups(List<MBReleaseGroup> releaseGroups) {
        this.releaseGroups = releaseGroups;
    }

    @JsonProperty("disambiguation")
    public String getDisambiguation() {
        return disambiguation;
    }

    @JsonProperty("disambiguation")
    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type-id")
    public String getTypeId() {
        return typeId;
    }

    @JsonProperty("type-id")
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("gender-id")
    public Object getGenderId() {
        return genderId;
    }

    @JsonProperty("gender-id")
    public void setGenderId(Object genderId) {
        this.genderId = genderId;
    }

    @JsonProperty("life-span")
    public MBLifeSpan getLifeSpan() {
        return lifeSpan;
    }

    @JsonProperty("life-span")
    public void setLifeSpan(MBLifeSpan lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    @JsonProperty("begin_area")
    public MBBeginArea getBeginArea() {
        return beginArea;
    }

    @JsonProperty("begin_area")
    public void setBeginArea(MBBeginArea beginArea) {
        this.beginArea = beginArea;
    }

    @JsonProperty("gender")
    public Object getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(Object gender) {
        this.gender = gender;
    }

    @JsonProperty("ipis")
    public List<Object> getIpis() {
        return ipis;
    }

    @JsonProperty("ipis")
    public void setIpis(List<Object> ipis) {
        this.ipis = ipis;
    }

    @JsonProperty("sort-name")
    public String getSortName() {
        return sortName;
    }

    @JsonProperty("sort-name")
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    @JsonProperty("area")
    public MBArea getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(MBArea area) {
        this.area = area;
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
