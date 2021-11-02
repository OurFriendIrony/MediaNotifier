package uk.co.ourfriendirony.medianotifier.mediaitem.tv;

import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.get.TVShowGet;
import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvshow.search.TVShowSearchResult;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue;
import static uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate;

public class TVShow implements MediaItem {
    private static final String IMDB_URL = "http://www.imdb.com/title/";
    private String id;
    private String subid = "";
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private String externalUrl;
    // TODO: fully implement played as an item
    private boolean played = false;
    private List<MediaItem> children = new ArrayList<>();

    public TVShow(TVShowGet tvShow) {
        this.id = String.valueOf(tvShow.getId());
        this.title = tvShow.getName();
        this.description = tvShow.getOverview();
        this.releaseDate = tvShow.getFirstAirDate();
        if (tvShow.getExternalIds() != null && tvShow.getExternalIds().getImdbId() != null) {
            this.externalUrl = IMDB_URL + tvShow.getExternalIds().getImdbId();
        }
        Log.d("[API GET]", this.toString());
    }

    public TVShow(TVShowSearchResult item) {
        this.id = String.valueOf(item.getId());
        this.title = item.getName();
        this.description = item.getOverview();
        this.releaseDate = item.getFirstAirDate();
        Log.d("[API SEARCH]", this.toString());
    }

    public TVShow(Cursor cursor, List<MediaItem> episodes) {
        this.id = getColumnValue(cursor, TVShowDatabaseDefinition.ID);
        this.subid = getColumnValue(cursor, TVShowDatabaseDefinition.SUBID);
        this.title = getColumnValue(cursor, TVShowDatabaseDefinition.TITLE);
        this.subtitle = getColumnValue(cursor, TVShowDatabaseDefinition.SUBTITLE);
        this.description = getColumnValue(cursor, TVShowDatabaseDefinition.DESCRIPTION);
        this.releaseDate = stringToDate(getColumnValue(cursor, TVShowDatabaseDefinition.RELEASE_DATE));
        this.externalUrl = getColumnValue(cursor, TVShowDatabaseDefinition.EXTERNAL_URL);
        this.children = episodes;
        Log.d("[DB READ]", this.toString());
    }

    public TVShow(Cursor cursor) {
        this(cursor, new ArrayList<>());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSubId() {
        return subid;
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
        if (releaseDate != null) {
            return new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(releaseDate);
        }
        return MediaItem.NO_DATE;
    }

    @Override
    public String getReleaseDateYear() {
        if (releaseDate != null) {
            return new SimpleDateFormat("yyyy", Locale.UK).format(releaseDate);
        }
        return MediaItem.NO_DATE;
    }

    @Override
    public List<MediaItem> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<MediaItem> children) {
        this.children = children;
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
    public Boolean getPlayed() {
        return played;
    }

    @NonNull
    public String toString() {
        return "TVShow: " + getTitle() + " > " + getReleaseDateFull() + " > Episodes " + countChildren();
    }
}
