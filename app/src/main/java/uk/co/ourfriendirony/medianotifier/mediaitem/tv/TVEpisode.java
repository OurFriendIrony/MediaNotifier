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

    public TVEpisode(TVSeasonGetEpisode episode, String showId) {
        this.id = String.valueOf(showId);
        this.subid = formatSubtitle(episode);
        this.title = episode.getName();
        this.subtitle = formatSubtitle(episode);
        this.description = episode.getOverview();
        this.releaseDate = episode.getAirDate();
        Log.d("[FROM GET]", this.toString());
    }

    @NonNull
    private String formatSubtitle(TVSeasonGetEpisode episode) {
        return String.format(Locale.UK, "S%02d", episode.getSeasonNumber()) + String.format(Locale.UK, "E%02d", episode.getEpisodeNumber());
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

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
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
