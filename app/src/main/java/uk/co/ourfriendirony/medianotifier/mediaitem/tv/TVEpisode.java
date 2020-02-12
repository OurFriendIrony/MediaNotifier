package uk.co.ourfriendirony.medianotifier.mediaitem.tv;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.clients.objects.tv.get.TVSeasonGetEpisode;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.StringHandler.stringToDate;

public class TVEpisode implements MediaItem {
    private String id;
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private String externalUrl;
    private List<MediaItem> children = new ArrayList<>();

    public TVEpisode(TVSeasonGetEpisode episode, String showId) {
        this.id = String.valueOf(showId);
        this.title = episode.getName();
        this.subtitle = String.format("S%02d", episode.getSeasonNumber()) + String.format("E%02d", episode.getEpisodeNumber());
        this.description = episode.getOverview();
        this.releaseDate = episode.getAirDate();
        Log.d("[FROM GET]", this.toString());
    }

    public TVEpisode(Cursor cursor) {
        this.id = getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_ID);
        this.title = getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_TITLE);
        this.subtitle = getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_SUBTITLE);
        this.description = getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_OVERVIEW);
        this.releaseDate = stringToDate(getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_DATE));
        this.externalUrl = getColumnValue(cursor, TVShowDatabaseDefinition.TTSE_IMDB);
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
    public Uri getExternalUrl() {
        if (externalUrl != null)
            return Uri.parse(externalUrl);
        return null;
    }

    public String toString() {
        return "TVEpisode: " + getSubtitle() + " > " + getTitle() + " > " + getReleaseDateFull();
    }
}
