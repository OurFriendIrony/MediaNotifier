package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search;

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
        "sort-name",
        "type-id",
        "name",
        "locale",
        "type",
        "primary",
        "begin-date",
        "end-date"
})
public class ArtistSearchAlias {

    @JsonProperty("sort-name")
    private String sortName;
    @JsonProperty("type-id")
    private String typeId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("locale")
    private Object locale;
    @JsonProperty("type")
    private Object type;
    @JsonProperty("primary")
    private Object primary;
    @JsonProperty("begin-date")
    private Object beginDate;
    @JsonProperty("end-date")
    private Object endDate;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sort-name")
    public String getSortName() {
        return sortName;
    }

    @JsonProperty("sort-name")
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    @JsonProperty("type-id")
    public String getTypeId() {
        return typeId;
    }

    @JsonProperty("type-id")
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("locale")
    public Object getLocale() {
        return locale;
    }

    @JsonProperty("locale")
    public void setLocale(Object locale) {
        this.locale = locale;
    }

    @JsonProperty("type")
    public Object getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Object type) {
        this.type = type;
    }

    @JsonProperty("primary")
    public Object getPrimary() {
        return primary;
    }

    @JsonProperty("primary")
    public void setPrimary(Object primary) {
        this.primary = primary;
    }

    @JsonProperty("begin-date")
    public Object getBeginDate() {
        return beginDate;
    }

    @JsonProperty("begin-date")
    public void setBeginDate(Object beginDate) {
        this.beginDate = beginDate;
    }

    @JsonProperty("end-date")
    public Object getEndDate() {
        return endDate;
    }

    @JsonProperty("end-date")
    public void setEndDate(Object endDate) {
        this.endDate = endDate;
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