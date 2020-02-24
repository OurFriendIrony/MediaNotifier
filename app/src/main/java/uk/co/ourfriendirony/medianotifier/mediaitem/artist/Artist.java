package uk.co.ourfriendirony.medianotifier.mediaitem.artist;

import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.get.ArtistGet;
import uk.co.ourfriendirony.medianotifier.clients.musicbrainz.artist.search.ArtistSearchArtist;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate;

public class Artist implements MediaItem {
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

    public Artist(ArtistGet artist, List<MediaItem> children) {
        this.id = artist.getId();
        this.title = artist.getName();
        if (artist.getDisambiguation() != null) {
            this.description = artist.getDisambiguation();
        } else if (artist.getArea().getName() != null && artist.getType() != null) {
            this.description = artist.getType() + " from " + artist.getArea().getName();
        }
        if (artist.getLifeSpan() != null && artist.getLifeSpan().getBegin() != null) {
            this.releaseDate = artist.getLifeSpan().getBegin();
        }
        this.children = children;
        Log.d("[FROM GET]", this.toString());
    }

    public Artist(ArtistSearchArtist artist) {
        this.id = artist.getId();
        this.title = artist.getName();
        if (artist.getDisambiguation() != null) {
            this.description = artist.getDisambiguation();
        } else if (artist.getArea().getName() != null && artist.getType() != null) {
            this.description = artist.getType() + " from " + artist.getArea().getName();
        }
        if (artist.getLifeSpan() != null && artist.getLifeSpan().getBegin() != null) {
            this.releaseDate = artist.getLifeSpan().getBegin();
        }
        this.children = children;
        Log.d("[FROM GET]", this.toString());
    }

    public Artist(Cursor cursor, List<MediaItem> releases) {
        this.id = getColumnValue(cursor, ArtistDatabaseDefinition.ID);
        this.subid = getColumnValue(cursor, ArtistDatabaseDefinition.SUBID);
        this.title = getColumnValue(cursor, ArtistDatabaseDefinition.TITLE);
        this.description = getColumnValue(cursor, ArtistDatabaseDefinition.DESCRIPTION);
        this.releaseDate = stringToDate(getColumnValue(cursor, ArtistDatabaseDefinition.RELEASE_DATE));
        this.children = releases;
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
        return "Artist: " + getTitle() + " > " + getReleaseDateFull() + " > Children " + countChildren();
    }
}
