package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.get;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cover-art-archive",
        "barcode",
        "status-id",
        "title",
        "media",
        "quality",
        "date",
        "packaging-id",
        "packaging",
        "asin",
        "country",
        "release-events",
        "status",
        "disambiguation",
        "id",
        "text-representation"
})
public class AlbumGet {

    @JsonProperty("cover-art-archive")
    private AlbumGetCoverArtArchive coverArtArchive;
    @JsonProperty("barcode")
    private String barcode;
    @JsonProperty("status-id")
    private String statusId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("media")
    private List<AlbumGetMedium> media = null;
    @JsonProperty("quality")
    private String quality;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    @JsonProperty("date")
    private Date date;
    @JsonProperty("packaging-id")
    private String packagingId;
    @JsonProperty("packaging")
    private String packaging;
    @JsonProperty("asin")
    private Object asin;
    @JsonProperty("country")
    private String country;
    @JsonProperty("release-events")
    private List<AlbumGetReleaseEvent> releaseEvents = null;
    @JsonProperty("status")
    private String status;
    @JsonProperty("disambiguation")
    private String disambiguation;
    @JsonProperty("id")
    private String id;
    @JsonProperty("text-representation")
    private AlbumGetTextRepresentation textRepresentation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("cover-art-archive")
    public AlbumGetCoverArtArchive getCoverArtArchive() {
        return coverArtArchive;
    }

    @JsonProperty("cover-art-archive")
    public void setCoverArtArchive(AlbumGetCoverArtArchive coverArtArchive) {
        this.coverArtArchive = coverArtArchive;
    }

    @JsonProperty("barcode")
    public String getBarcode() {
        return barcode;
    }

    @JsonProperty("barcode")
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @JsonProperty("status-id")
    public String getStatusId() {
        return statusId;
    }

    @JsonProperty("status-id")
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("media")
    public List<AlbumGetMedium> getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(List<AlbumGetMedium> media) {
        this.media = media;
    }

    @JsonProperty("quality")
    public String getQuality() {
        return quality;
    }

    @JsonProperty("quality")
    public void setQuality(String quality) {
        this.quality = quality;
    }

    @JsonProperty("date")
    public Date getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(Date date) {
        this.date = date;
    }

    @JsonProperty("packaging-id")
    public String getPackagingId() {
        return packagingId;
    }

    @JsonProperty("packaging-id")
    public void setPackagingId(String packagingId) {
        this.packagingId = packagingId;
    }

    @JsonProperty("packaging")
    public String getPackaging() {
        return packaging;
    }

    @JsonProperty("packaging")
    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    @JsonProperty("asin")
    public Object getAsin() {
        return asin;
    }

    @JsonProperty("asin")
    public void setAsin(Object asin) {
        this.asin = asin;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("release-events")
    public List<AlbumGetReleaseEvent> getReleaseEvents() {
        return releaseEvents;
    }

    @JsonProperty("release-events")
    public void setReleaseEvents(List<AlbumGetReleaseEvent> releaseEvents) {
        this.releaseEvents = releaseEvents;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

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

    @JsonProperty("text-representation")
    public AlbumGetTextRepresentation getTextRepresentation() {
        return textRepresentation;
    }

    @JsonProperty("text-representation")
    public void setTextRepresentation(AlbumGetTextRepresentation textRepresentation) {
        this.textRepresentation = textRepresentation;
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
