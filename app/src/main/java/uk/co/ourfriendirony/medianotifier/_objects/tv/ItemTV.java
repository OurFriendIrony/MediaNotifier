package uk.co.ourfriendirony.medianotifier._objects.tv;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier.clients.objects.tv.get.TVShowGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.tv.search.TVShowSearchResult;

public class ItemTV implements Item {
    private String id;
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private String externalUrl;
    private List<Item> children = new ArrayList<>();

    private static final String IMDB_URL = "http://www.imdb.com/title/";

    public ItemTV(TVShowGet tvShow, List<Item> children) {
        this.id = String.valueOf(tvShow.getId());
        this.title = tvShow.getName();
        this.description = tvShow.getOverview();
        this.releaseDate = tvShow.getFirstAirDate();
        if (tvShow.getExternalIds() != null && tvShow.getExternalIds().getImdbId() != null)
            this.externalUrl = IMDB_URL + tvShow.getExternalIds().getImdbId();
        this.children = children;
    }

    public ItemTV(TVShowSearchResult item) {
        this.id = String.valueOf(item.getId());
        this.title = item.getName();
        this.description = item.getOverview();
        this.releaseDate = item.getFirstAirDate();
    }

    public ItemTV(String id, String title, String subtitle, String description, Date releaseDate, String externalUrl, List<Item> children) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.releaseDate = releaseDate;
        this.externalUrl = externalUrl;
        this.children = children;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Date getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String getReleaseDateFull() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(releaseDate);
    }

    @Override
    public String getReleaseDateYear() {
        return new SimpleDateFormat("yyyy", Locale.UK).format(releaseDate);
    }

    @Override
    public List<Item> getChildren() {
        return children;
    }

    @Override
    public int countChildren() {
        return children.size();
    }

    @Override
    public String getExternalLink() {
        return externalUrl;
    }

    @Override
    public Uri getExternalUrl() {
        if (externalUrl != null)
            return Uri.parse(externalUrl);
        return null;
    }

    public String toString() {
        return "TVShow: " + getTitle() + " > " + getReleaseDateFull() + " > Episodes " + countChildren();
    }
}
