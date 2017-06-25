package uk.co.ourfriendirony.medianotifier.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.autogen.movie.Collection;
import uk.co.ourfriendirony.medianotifier.autogen.movie.Movie;
import uk.co.ourfriendirony.medianotifier.general.StringHandler;

import static uk.co.ourfriendirony.medianotifier.db.MovieDatabaseDefinition.*;
import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffset;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.dateToString;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.stringToDate;

public class MovieDatabase {
    private static final String SELECT_MOVIES = "SELECT * FROM " + TABLE_MOVIES + " ORDER BY " + TM_TITLE + " ASC;";

    private static final String GET_MOVIE_TITLE_BY_ID = "SELECT " + TM_TITLE + " FROM " + TABLE_MOVIES + " WHERE " + TM_ID + "=?;";

    private static final String GET_MOVIE_WATCHED_STATUS = "SELECT " + TM_WATCHED + " FROM " + TABLE_MOVIES + " WHERE " + TM_ID + "=?;";

    private static final String COUNT_UNWATCHED_MOVIES_UNRELEASED = "SELECT COUNT(*) FROM " + TABLE_MOVIES + " " +
            "WHERE " + TM_WATCHED + "=" + WATCHED_FALSE + " AND " + TM_DATE + " > @OFFSET@;";
    private static final String GET_UNWATCHED_MOVIES_UNRELEASED = "SELECT * " +
            "FROM " + TABLE_MOVIES + " " +
            "WHERE " + TM_WATCHED + "=" + WATCHED_FALSE + " AND " + TM_DATE + " > @OFFSET@ ORDER BY " + TM_DATE + " ASC;";

    private static final String COUNT_UNWATCHED_MOVIES_RELEASED = "SELECT COUNT(*) FROM " + TABLE_MOVIES + " " +
            "WHERE " + TM_WATCHED + "=" + WATCHED_FALSE + " AND " + TM_DATE + " <= @OFFSET@;";
    private static final String GET_UNWATCHED_MOVIES_RELEASED = "SELECT * " +
            "FROM " + TABLE_MOVIES + " " +
            "WHERE " + TM_WATCHED + "=" + WATCHED_FALSE + " AND " + TM_DATE + " <= @OFFSET@ ORDER BY " + TM_DATE + " ASC;";

    private final MovieDatabaseDefinition databaseHelper;
    private final Context context;

    public MovieDatabase(Context context) {
        this.databaseHelper = new MovieDatabaseDefinition(context);
        this.context = context;
    }

    public void addMovie(Movie movie) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        insertMovie(dbWritable, movie, true);
        dbWritable.close();
    }

    public void updateMovie(Movie movie) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        insertMovie(dbWritable, movie, false);
        dbWritable.close();
    }

    private void insertMovie(SQLiteDatabase dbWritable, Movie movie, boolean newMovie) {
        String currentWatchedStatus = getMovieWatchedStatus(dbWritable, movie);
        ContentValues movieRow = new ContentValues();
        movieRow.put(TM_ID, movie.getId());
        movieRow.put(TM_TITLE, cleanTitle(movie.getTitle()));
        movieRow.put(TM_IMDB, movie.getImdbId());
        movieRow.put(TM_DATE, dateToString(movie.getReleaseDate()));
        movieRow.put(TM_OVERVIEW, movie.getOverview());
        movieRow.put(TM_TAGLINE, movie.getTagline());
        movieRow.put(TM_COLLECTION, movie.getBelongsToCollection().getCollectionName());
        if (newMovie && alreadyReleased(movie)) {
            movieRow.put(TM_WATCHED, WATCHED_TRUE);
        } else {
            movieRow.put(TM_WATCHED, currentWatchedStatus);
        }
        dbWritable.replace(TABLE_MOVIES, null, movieRow);
    }

    public String getMovieWatchedStatus(SQLiteDatabase dbReadable, Movie movie) {
        String[] args = new String[]{movie.getIdAsString()};
        Cursor cursor = dbReadable.rawQuery(GET_MOVIE_WATCHED_STATUS, args);
        String watchedStatus = WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, TM_WATCHED);
            }
        } finally {
            cursor.close();
        }

        return watchedStatus;
    }

    public boolean getMovieWatchedStatusAsBoolean(Movie movie) {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        String[] args = new String[]{movie.getIdAsString()};
        Cursor cursor = dbReadable.rawQuery(GET_MOVIE_WATCHED_STATUS, args);
        String watchedStatus = WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, TM_WATCHED);
            }
        } finally {
            cursor.close();
        }

        dbReadable.close();
        return WATCHED_TRUE.equals(watchedStatus);
    }

    public void deleteAllMovies() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TABLE_MOVIES + ";");
        dbWritable.close();
    }

    public void deleteMovie(Integer movieId) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TABLE_MOVIES + " WHERE " + TM_ID + "=" + movieId + ";");
        dbWritable.close();
    }

    public String getTitleById(int movieId) {
        String title = "";
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(GET_MOVIE_TITLE_BY_ID, new String[]{String.valueOf(movieId)});
        try {
            while (cursor.moveToNext()) {
                title = getColumnValue(cursor, TM_TITLE);
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return title;
    }

    public int countUnwatchedReleasedMovies() {
        String offset = "date('now','-" + getNotificationDayOffset(context) + " days')";
        String query = StringHandler.replaceTokens(COUNT_UNWATCHED_MOVIES_RELEASED, "@OFFSET@", offset);
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        dbReadable.close();
        return count;
    }

    public int countUnwatchedUnreleasedMovies() {
        String offset = "date('now','-" + getNotificationDayOffset(context) + " days')";
        String query = StringHandler.replaceTokens(COUNT_UNWATCHED_MOVIES_UNRELEASED, "@OFFSET@", offset);
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        dbReadable.close();
        return count;
    }

    public List<Movie> getUnwatchedReleasedMovies() {
        String offset = "date('now','-" + getNotificationDayOffset(context) + " days')";
        String query = StringHandler.replaceTokens(GET_UNWATCHED_MOVIES_RELEASED, "@OFFSET@", offset);
        List<Movie> movies = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                Movie movie = buildMovie(cursor);
                movies.add(movie);
                Log.v("*****IMHERE*****", "UNWATCHED AIRED MOVIES: Id=" + movie.getId() + " | Title=" + movie.getTitle() + " | Date=" + movie.getReleaseDate());
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return movies;
    }

    public List<Movie> getUnwatchedUnreleasedMovies() {
        String offset = "date('now','-" + getNotificationDayOffset(context) + " days')";
        String query = StringHandler.replaceTokens(GET_UNWATCHED_MOVIES_UNRELEASED, "@OFFSET@", offset);
        List<Movie> movies = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                Movie movie = buildMovie(cursor);
                movies.add(movie);
                Log.v("*****IMHERE*****", "UNWATCHED UNAIRED MOVIES: Id=" + movie.getId() + " | Title=" + movie.getTitle() + " | Date=" + movie.getReleaseDate());
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return movies;
    }

    @NonNull
    private Movie buildMovie(Cursor cursor) {
        Movie movie = new Movie();

        Collection movieCollection = new Collection();
        movieCollection.setCollectionName(getColumnValue(cursor, TM_COLLECTION));

        movie.setId(Integer.parseInt(getColumnValue(cursor, TM_ID)));
        movie.setTitle(getColumnValue(cursor, TM_TITLE));
        movie.setOverview(getColumnValue(cursor, TM_OVERVIEW));
        movie.setReleaseDate(stringToDate(getColumnValue(cursor, TM_DATE)));
        movie.setImdbId(getColumnValue(cursor, TM_IMDB));
        movie.setTagline(getColumnValue(cursor, TM_TAGLINE));
        movie.setBelongsToCollection(movieCollection);

        Log.d("BUILD_MOVIE", movie.getId() + " " + movie.getTitle());

        return movie;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(SELECT_MOVIES, null);
        try {
            while (cursor.moveToNext()) {
                movies.add(buildMovie(cursor));
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return movies;
    }

    public void updateMovieWatchedStatus(Movie movie, String watchedStatus) {
        SQLiteDatabase dbWriteable = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TM_WATCHED, watchedStatus);
        String where = TM_ID + "=?";
        String[] whereArgs = new String[]{movie.getIdAsString()};
        dbWriteable.update(TABLE_MOVIES, values, where, whereArgs);
        dbWriteable.close();
    }

    private boolean alreadyReleased(Movie movie) {
        return movie.getReleaseDate().compareTo(new Date()) < 0;
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }
}
