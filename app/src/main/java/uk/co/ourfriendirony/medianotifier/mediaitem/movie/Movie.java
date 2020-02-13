package uk.co.ourfriendirony.medianotifier.mediaitem.movie;

import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.clients.objects.movie.get.MovieGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.movie.search.MovieSearchResult;
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate;

public class Movie implements MediaItem {
    private String id;
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private String externalUrl;
    private boolean watched = false;
    private List<MediaItem> children = new ArrayList<>();

    private static final String IMDB_URL = "http://www.imdb.com/title/";


    public Movie(MovieGet movie) {
        this.id = String.valueOf(movie.getId());
        this.title = movie.getTitle();
        if (movie.getBelongsToCollection() != null) {
            this.subtitle = movie.getBelongsToCollection().getName();
        }
        this.description = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
        if (movie.getImdbId() != null) {
            this.externalUrl = IMDB_URL + movie.getImdbId();
        }
        Log.d("[FROM GET]", this.toString());
    }

    public Movie(MovieSearchResult movie) {
        this.id = String.valueOf(movie.getId());
        this.title = movie.getTitle();
        this.description = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
        Log.d("[FROM SEARCH]", this.toString());
    }

    public Movie(Cursor cursor) {
        this.id = getColumnValue(cursor, MovieDatabaseDefinition.ID);
        this.title = getColumnValue(cursor, MovieDatabaseDefinition.TITLE);
        this.subtitle = getColumnValue(cursor, MovieDatabaseDefinition.SUBTITLE);
        this.description = getColumnValue(cursor, MovieDatabaseDefinition.DESCRIPTION);
        this.releaseDate = stringToDate(getColumnValue(cursor, MovieDatabaseDefinition.RELEASE_DATE));
        this.externalUrl = getColumnValue(cursor, MovieDatabaseDefinition.EXTERNAL_URL);
        this.children = new ArrayList<>();
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
