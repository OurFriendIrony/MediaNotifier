package uk.co.ourfriendirony.medianotifier.mediaitem.artist;

import static uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue;
import static uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate;

import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGetReleaseGroup;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class Release implements MediaItem {
    private final String id;
    private final String title;
    private final Date releaseDate;
    // TODO: fully implement played as an item
    private final boolean played = false;
    private String subid = "";
    private String subtitle = "";
    private String description = "";
    private String externalUrl;
    private List<MediaItem> children = new ArrayList<>();

    public Release(ArtistGetReleaseGroup release, Artist artist) {
        this.id = artist.getId();
        this.subid = release.getId();
        if (!release.getDisambiguation().isEmpty()) {
            this.title = release.getTitle() + " (" + release.getDisambiguation() + ")";
        } else {
            this.title = release.getTitle();
        }
        this.subtitle = artist.getTitle();
        this.releaseDate = release.getFirstReleaseDate();
        Log.d("[API GET]", this.toString());
    }

    public Release(Cursor cursor) {
        this.id = getColumnValue(cursor, ArtistDatabaseDefinition.ID);
        this.subid = getColumnValue(cursor, ArtistDatabaseDefinition.SUBID);
        this.title = getColumnValue(cursor, ArtistDatabaseDefinition.TITLE);
        this.subtitle = getColumnValue(cursor, ArtistDatabaseDefinition.SUBTITLE);
        this.description = getColumnValue(cursor, ArtistDatabaseDefinition.DESCRIPTION);
        this.releaseDate = stringToDate(getColumnValue(cursor, ArtistDatabaseDefinition.RELEASE_DATE));
        this.externalUrl = getColumnValue(cursor, ArtistDatabaseDefinition.EXTERNAL_URL);
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
        return "Release: " + getTitle() + " > " + getReleaseDateFull();
    }
}
