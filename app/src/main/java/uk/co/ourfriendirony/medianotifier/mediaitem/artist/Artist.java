package uk.co.ourfriendirony.medianotifier.mediaitem.artist;

import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.clients.objects.artist.get.ArtistGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.artist.search.ArtistSearchResult;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public class Artist implements MediaItem {
    private String id;
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private String externalUrl;
    private boolean watched = false;
    private List<MediaItem> children = new ArrayList<>();

    public Artist(ArtistGet artist) {
        this.id = String.valueOf(artist.getId());
        this.title = artist.getName();
        this.description = artist.getProfile();
        if (artist.getUrls() != null && artist.getUrls().size() > 0) {
            this.externalUrl = artist.getUrls().get(0);
        } else {
            this.externalUrl = artist.getUri();
        }
        Log.d("[FROM GET]", this.toString());
    }

    public Artist(ArtistSearchResult artist) {
        this.id = String.valueOf(artist.getId());
        this.title = artist.getTitle();
        Log.d("[FROM SEARCH]", this.toString());
    }

    public Artist(Cursor cursor) {
        // TODO: Need to standardise the fields being stored.
        this.id = getColumnValue(cursor, ArtistDatabaseDefinition.ID);
        this.title = getColumnValue(cursor, ArtistDatabaseDefinition.TITLE);
        this.description = getColumnValue(cursor, ArtistDatabaseDefinition.DESCRIPTION);
        Log.d("[FROM DB]", this.toString());
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
        return "Movie: " + getTitle() + " > " + getReleaseDateFull() + " > Children " + countChildren();
    }
}
