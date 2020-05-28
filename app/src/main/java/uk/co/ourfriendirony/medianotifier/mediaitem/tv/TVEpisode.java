package uk.co.ourfriendirony.medianotifier.mediaitem.tv;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.clients.tmdb.tvseason.get.TVSeasonGetEpisode;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue;
import static uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate;

public class TVEpisode implements MediaItem {
    private String id;
    private String subid;
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private String externalUrl;
    // TODO: fully implement watched as an item
    private boolean watched = false;
    private List<MediaItem> children = new ArrayList<>();

    public TVEpisode(TVSeasonGetEpisode episode, TVShow tvShow) {
        this.id = tvShow.getId();
        this.subid = formatEpSe(episode);
        this.title = episode.getName();
        this.subtitle = formatEpSe(episode) + " " + tvShow.getTitle();
        this.description = episode.getOverview();
        this.releaseDate = episode.getAirDate();
        Log.d("[API GET]", this.toString());
    }

    public TVEpisode(Cursor cursor) {
        this.id = getColumnValue(cursor, TVShowDatabaseDefinition.ID);
        this.subid = getColumnValue(cursor, TVShowDatabaseDefinition.SUBID);
        this.title = getColumnValue(cursor, TVShowDatabaseDefinition.TITLE);
        this.subtitle = getColumnValue(cursor, TVShowDatabaseDefinition.SUBTITLE);
        this.description = getColumnValue(cursor, TVShowDatabaseDefinition.DESCRIPTION);
        this.releaseDate = stringToDate(getColumnValue(cursor, TVShowDatabaseDefinition.RELEASE_DATE));
        this.externalUrl = getColumnValue(cursor, TVShowDatabaseDefinition.EXTERNAL_URL);
        Log.d("[DB READ]", this.toString());
    }

    private static String pad(int num, String prefix, int size) {
        return prefix + String.format(Locale.UK, "%0" + size + "d", num);
    }

    @NonNull
    private String formatEpSe(TVSeasonGetEpisode episode) {
        return pad(episode.getSeasonNumber(), "S", 2) + pad(episode.getEpisodeNumber(), "E", 2);
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
        return "?";
    }

    @Override
    public String getReleaseDateYear() {
        if (releaseDate != null) {
            return new SimpleDateFormat("yyyy", Locale.UK).format(releaseDate);
        }
        return "?";
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
    public Boolean getWatched() {
        return watched;
    }

    public String toString() {
        return "TVEpisode: " + getSubtitle() + " > " + getTitle() + " > " + getReleaseDateFull();
    }
}
