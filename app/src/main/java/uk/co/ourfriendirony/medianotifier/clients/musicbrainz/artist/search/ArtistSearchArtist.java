package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search;

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
        "id",
        "type",
        "type-id",
        "score",
        "name",
        "sort-name",
        "country",
        "area",
        "begin-area",
        "disambiguation",
        "isnis",
        "life-span",
        "aliases",
        "tags",
        "gender"
})
public class ArtistSearchArtist {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("type-id")
    private String typeId;
    @JsonProperty("score")
    private Integer score;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sort-name")
    private String sortName;
    @JsonProperty("country")
    private String country;
    @JsonProperty("area")
    private ArtistSearchArea area;
    @JsonProperty("begin-area")
    private ArtistSearchBeginArea beginArea;
    @JsonProperty("disambiguation")
    private String disambiguation;
    @JsonProperty("isnis")
    private List<String> isnis = null;
    @JsonProperty("life-span")
    private ArtistSearchLifeSpan lifeSpan;
    @JsonProperty("aliases")
    private List<ArtistSearchAlias> aliases = null;
    @JsonProperty("tags")
    private List<ArtistSearchTag> tags = null;
    @JsonProperty("gender")
    private String gender;
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

    @JsonProperty("type-id")
    public String getTypeId() {
        return typeId;
    }

    @JsonProperty("type-id")
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @JsonProperty("score")
    public Integer getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(Integer score) {
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
    public ArtistSearchArea getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(ArtistSearchArea area) {
        this.area = area;
    }

    @JsonProperty("begin-area")
    public ArtistSearchBeginArea getBeginArea() {
        return beginArea;
    }

    @JsonProperty("begin-area")
    public void setBeginArea(ArtistSearchBeginArea beginArea) {
        this.beginArea = beginArea;
    }

    @JsonProperty("disambiguation")
    public String getDisambiguation() {
        return disambiguation;
    }

    @JsonProperty("disambiguation")
    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    @JsonProperty("isnis")
    public List<String> getIsnis() {
        return isnis;
    }

    @JsonProperty("isnis")
    public void setIsnis(List<String> isnis) {
        this.isnis = isnis;
    }

    @JsonProperty("life-span")
    public ArtistSearchLifeSpan getLifeSpan() {
        return lifeSpan;
    }

    @JsonProperty("life-span")
    public void setLifeSpan(ArtistSearchLifeSpan lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    @JsonProperty("aliases")
    public List<ArtistSearchAlias> getAliases() {
        return aliases;
    }

    @JsonProperty("aliases")
    public void setAliases(List<ArtistSearchAlias> aliases) {
        this.aliases = aliases;
    }

    @JsonProperty("tags")
    public List<ArtistSearchTag> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<ArtistSearchTag> tags) {
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}