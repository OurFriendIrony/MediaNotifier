package uk.co.ourfriendirony.medianotifier.mediaitem.tv;

import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.clients.objects.tv.get.TVShowGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.tv.search.TVShowSearchResult;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue;
import static uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate;

public class TVShow implements MediaItem {
    private String id;
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private String externalUrl;
    private boolean watched = false;
    private List<MediaItem> children = new ArrayList<>();

    private static final String IMDB_URL = "http://www.imdb.com/title/";


    public TVShow(TVShowGet tvShow, List<MediaItem> children) {
        this.id = String.valueOf(tvShow.getId());
        this.title = tvShow.getName();
        this.description = tvShow.getOverview();
        this.releaseDate = tvShow.getFirstAirDate();
        if (tvShow.getExternalIds() != null && tvShow.getExternalIds().getImdbId() != null) {
            this.externalUrl = IMDB_URL + tvShow.getExternalIds().getImdbId();
        }
        this.children = children;
        Log.d("[FROM GET]", this.toString());
    }

    public TVShow(TVShowSearchResult item) {
        this.id = String.valueOf(item.getId());
        this.title = item.getName();
        this.description = item.getOverview();
        this.releaseDate = item.getFirstAirDate();
        Log.d("[FROM SEARCH]", this.toString());
    }

    public TVShow(Cursor cursor, List<MediaItem> episodes) {
        this.id = getColumnValue(cursor, TVShowDatabaseDefinition.ID);
        this.title = getColumnValue(cursor, TVShowDatabaseDefinition.TITLE);
        this.subtitle = getColumnValue(cursor, TVShowDatabaseDefinition.SUBTITLE);
        this.description = getColumnValue(cursor, TVShowDatabaseDefinition.DESCRIPTION);
        this.releaseDate = stringToDate(getColumnValue(cursor, TVShowDatabaseDefinition.RELEASE_DATE));
        this.externalUrl = getColumnValue(cursor, TVShowDatabaseDefinition.EXTERNAL_URL);
        this.children = episodes;
        Log.d("[DB READ]", this.toString());
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
    public List<MediaItem> getChildren() {
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
    public Boolean getWatched() {
        return watched;
    }

    public String toString() {
        return "TVShow: " + getTitle() + " > " + getReleaseDateFull() + " > Episodes " + countChildren();
    }
}
