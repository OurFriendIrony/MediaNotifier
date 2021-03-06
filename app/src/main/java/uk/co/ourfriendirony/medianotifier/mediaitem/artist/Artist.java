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

import static uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue;
import static uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate;

public class Artist implements MediaItem {
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

    public Artist(ArtistGet artist) {
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
        Log.d("[API SEARCH]", this.toString());
    }

    public Artist(Cursor cursor, List<MediaItem> releases) {
        // Build Artist from DB with children
        this.id = getColumnValue(cursor, ArtistDatabaseDefinition.ID);
        this.subid = getColumnValue(cursor, ArtistDatabaseDefinition.SUBID);
        this.title = getColumnValue(cursor, ArtistDatabaseDefinition.TITLE);
        this.description = getColumnValue(cursor, ArtistDatabaseDefinition.DESCRIPTION);
        this.releaseDate = stringToDate(getColumnValue(cursor, ArtistDatabaseDefinition.RELEASE_DATE));
        this.children = releases;
        Log.d("[DB READ]", this.toString());
    }

    public Artist(Cursor cursor) {
        // Build Artist from DB without children
        this(cursor, new ArrayList<MediaItem>());
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

    public String toString() {
        return "Artist: " + getTitle() + " > " + getReleaseDateFull() + " > Releases " + countChildren();
    }
}
