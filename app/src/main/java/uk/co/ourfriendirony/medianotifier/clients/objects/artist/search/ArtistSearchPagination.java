package uk.co.ourfriendirony.medianotifier.clients.objects.artist.search;

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
        "per_page",
        "items",
        "page",
        "urls",
        "pages"
})
public class ArtistSearchPagination {
    @JsonProperty("per_page")
    private Integer perPage;
    @JsonProperty("items")
    private Integer items;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("urls")
    private ArtistSearchUrl urls;
    @JsonProperty("pages")
    private Integer pages;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("per_page")
    public Integer getPerPage() {
        return perPage;
    }

    @JsonProperty("per_page")
    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    @JsonProperty("items")
    public Integer getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(Integer items) {
        this.items = items;
    }

    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    @JsonProperty("urls")
    public ArtistSearchUrl getUrls() {
        return urls;
    }

    @JsonProperty("urls")
    public void setUrls(ArtistSearchUrl urls) {
        this.urls = urls;
    }

    @JsonProperty("pages")
    public Integer getPages() {
        return pages;
    }

    @JsonProperty("pages")
    public void setPages(Integer pages) {
        this.pages = pages;
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
