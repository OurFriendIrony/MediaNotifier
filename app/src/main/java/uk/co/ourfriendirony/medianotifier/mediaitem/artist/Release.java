package uk.co.ourfriendirony.medianotifier.mediaitem.artist;

import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGetReleaseGroup;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue;
import static uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate;

public class Release implements MediaItem {
    private String id;
    private String subid = "";
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private String externalUrl;
    // TODO: fully implement watched as an item
    private boolean watched = false;
    private List<MediaItem> children = new ArrayList<>();

    public Release(ArtistGetReleaseGroup release, String artistId) {
        this.id = artistId;
        this.subid = release.getId();
        if (!release.getDisambiguation().isEmpty()) {
            this.title = release.getTitle() + " (" + release.getDisambiguation() + ")";
        } else {
            this.title = release.getTitle();
        }
        this.releaseDate = release.getFirstReleaseDate();
        Log.d("[FROM GET]", this.toString());
    }

    public Release(Cursor cursor) {
        this.id = getColumnValue(cursor, ArtistDatabaseDefinition.ID);
        this.subid = getColumnValue(cursor, ArtistDatabaseDefinition.SUBID);
        this.title = getColumnValue(cursor, ArtistDatabaseDefinition.TITLE);
        this.subtitle = getColumnValue(cursor, ArtistDatabaseDefinition.SUBTITLE);
        this.description = getColumnValue(cursor, ArtistDatabaseDefinition.DESCRIPTION);
        this.releaseDate = stringToDate(getColumnValue(cursor, ArtistDatabaseDefinition.RELEASE_DATE));
        this.externalUrl = getColumnValue(cursor, ArtistDatabaseDefinition.EXTERNAL_URL);
//        this.watched = Boolean.getBoolean(getColumnValue(cursor,ArtistDatabaseDefinition.WATCHED));
        Log.d("[DB READ]", this.toString());
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
        return "Release: " + getTitle() + " > " + getReleaseDateFull();
    }
}
