package uk.co.ourfriendirony.medianotifier.autogen.music;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "aliases",
        "area",
        "begin-area",
        "country",
        "disambiguation",
        "gender",
        "id",
        "life-span",
        "name",
        "score",
        "sort-name",
        "tags",
        "type"
})
public class MBArtist {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("score")
    private String score;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sort-name")
    private String sortName;
    @JsonProperty("country")
    private String country;
    @JsonProperty("area")
    private MBArea area;
    @JsonProperty("begin-area")
    private MBBeginArea beginArea;
    @JsonProperty("life-span")
    private MBLifeSpan lifeSpan;
    @JsonProperty("aliases")
    private List<MBAlias> aliases = null;
    @JsonProperty("tags")
    private List<MBTag> tags = null;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("disambiguation")
    private String disambiguation;
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

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("score")
    public String getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(String score) {
        this.score = score;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("sort-name")
    public String getSortName() {
        return sortName;
    }

    @JsonProperty("sort-name")
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("area")
    public MBArea getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(MBArea area) {
        this.area = area;
    }

    @JsonProperty("begin-area")
    public MBBeginArea getBeginArea() {
        return beginArea;
    }

    @JsonProperty("begin-area")
    public void setBeginArea(MBBeginArea beginArea) {
        this.beginArea = beginArea;
    }

    @JsonProperty("life-span")
    public MBLifeSpan getLifeSpan() {
        return lifeSpan;
    }

    @JsonProperty("life-span")
    public void setLifeSpan(MBLifeSpan lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    @JsonProperty("aliases")
    public List<MBAlias> getAliases() {
        return aliases;
    }

    @JsonProperty("aliases")
    public void setAliases(List<MBAlias> aliases) {
        this.aliases = aliases;
    }

    @JsonProperty("tags")
    public List<MBTag> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<MBTag> tags) {
        this.tags = tags;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("disambiguation")
    public String getDisambiguation() {
        return disambiguation;
    }

    @JsonProperty("disambiguation")
    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
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