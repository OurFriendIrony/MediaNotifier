package uk.co.ourfriendirony.medianotifier.autogen.music;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "text-representation",
        "title",
        "barcode",
        "packaging-id",
        "release-events",
        "country",
        "disambiguation",
        "packaging",
        "quality",
        "status",
        "id",
        "status-id",
        "date"
})
public class MBRelease {

    @JsonProperty("text-representation")
    private MBTextRepresentation textRepresentation;
    @JsonProperty("title")
    private String title;
    @JsonProperty("barcode")
    private String barcode;
    @JsonProperty("packaging-id")
    private String packagingId;
    @JsonProperty("release-events")
    private List<MBReleaseEvent> releaseEvents = null;
    @JsonProperty("country")
    private String country;
    @JsonProperty("disambiguation")
    private String disambiguation;
    @JsonProperty("packaging")
    private String packaging;
    @JsonProperty("quality")
    private String quality;
    @JsonProperty("status")
    private String status;
    @JsonProperty("id")
    private String id;
    @JsonProperty("status-id")
    private String statusId;
    @JsonProperty("date")
    private String date;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("text-representation")
    public MBTextRepresentation getTextRepresentation() {
        return textRepresentation;
    }

    @JsonProperty("text-representation")
    public void setTextRepresentation(MBTextRepresentation textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("barcode")
    public String getBarcode() {
        return barcode;
    }

    @JsonProperty("barcode")
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @JsonProperty("packaging-id")
    public String getPackagingId() {
        return packagingId;
    }

    @JsonProperty("packaging-id")
    public void setPackagingId(String packagingId) {
        this.packagingId = packagingId;
    }

    @JsonProperty("release-events")
    public List<MBReleaseEvent> getReleaseEvents() {
        return releaseEvents;
    }

    @JsonProperty("release-events")
    public void setReleaseEvents(List<MBReleaseEvent> releaseEvents) {
        this.releaseEvents = releaseEvents;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("disambiguation")
    public String getDisambiguation() {
        return disambiguation;
    }

    @JsonProperty("disambiguation")
    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    @JsonProperty("packaging")
    public String getPackaging() {
        return packaging;
    }

    @JsonProperty("packaging")
    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    @JsonProperty("quality")
    public String getQuality() {
        return quality;
    }

    @JsonProperty("quality")
    public void setQuality(String quality) {
        this.quality = quality;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("status-id")
    public String getStatusId() {
        return statusId;
    }

    @JsonProperty("status-id")
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
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