package uk.co.ourfriendirony.medianotifier.autogen.music;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "packaging-id",
        "barcode",
        "title",
        "status-id",
        "date",
        "id",
        "disambiguation",
        "cover-art-archive",
        "asin",
        "release-events",
        "media",
        "text-representation",
        "packaging",
        "status",
        "quality",
        "country"
})
public class LookupAlbum {

    @JsonProperty("packaging-id")
    private String packagingId;
    @JsonProperty("barcode")
    private String barcode;
    @JsonProperty("title")
    private String title;
    @JsonProperty("status-id")
    private String statusId;
    @JsonProperty("date")
    private String date;
    @JsonProperty("id")
    private String id;
    @JsonProperty("disambiguation")
    private String disambiguation;
    @JsonProperty("cover-art-archive")
    private MBCoverArtArchive coverArtArchive;
    @JsonProperty("asin")
    private Object asin;
    @JsonProperty("release-events")
    private List<MBReleaseEvent> releaseEvents = null;
    @JsonProperty("media")
    private List<MBMedium> media = null;
    @JsonProperty("text-representation")
    private MBTextRepresentation textRepresentation;
    @JsonProperty("packaging")
    private String packaging;
    @JsonProperty("status")
    private String status;
    @JsonProperty("quality")
    private String quality;
    @JsonProperty("country")
    private String country;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("packaging-id")
    public String getPackagingId() {
        return packagingId;
    }

    @JsonProperty("packaging-id")
    public void setPackagingId(String packagingId) {
        this.packagingId = packagingId;
    }

    @JsonProperty("barcode")
    public String getBarcode() {
        return barcode;
    }

    @JsonProperty("barcode")
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
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

    @JsonProperty("cover-art-archive")
    public MBCoverArtArchive getCoverArtArchive() {
        return coverArtArchive;
    }

    @JsonProperty("cover-art-archive")
    public void setCoverArtArchive(MBCoverArtArchive coverArtArchive) {
        this.coverArtArchive = coverArtArchive;
    }

    @JsonProperty("asin")
    public Object getAsin() {
        return asin;
    }

    @JsonProperty("asin")
    public void setAsin(Object asin) {
        this.asin = asin;
    }

    @JsonProperty("release-events")
    public List<MBReleaseEvent> getReleaseEvents() {
        return releaseEvents;
    }

    @JsonProperty("release-events")
    public void setReleaseEvents(List<MBReleaseEvent> releaseEvents) {
        this.releaseEvents = releaseEvents;
    }

    @JsonProperty("media")
    public List<MBMedium> getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(List<MBMedium> media) {
        this.media = media;
    }

    @JsonProperty("text-representation")
    public MBTextRepresentation getTextRepresentation() {
        return textRepresentation;
    }

    @JsonProperty("text-representation")
    public void setTextRepresentation(MBTextRepresentation textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    @JsonProperty("packaging")
    public String getPackaging() {
        return packaging;
    }

    @JsonProperty("packaging")
    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("quality")
    public String getQuality() {
        return quality;
    }

    @JsonProperty("quality")
    public void setQuality(String quality) {
        this.quality = quality;
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