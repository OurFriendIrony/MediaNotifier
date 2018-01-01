package uk.co.ourfriendirony.medianotifier.db.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier._objects.movie.Collection;
import uk.co.ourfriendirony.medianotifier._objects.movie.Movie;
import uk.co.ourfriendirony.medianotifier.general.StringHandler;

import static uk.co.ourfriendirony.medianotifier.db.PropertyHelper.getNotificationDayOffsetMovie;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.dateToString;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.stringToDate;

public class MovieDatabase {
    private static final String SELECT_MOVIES = "SELECT * FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " ORDER BY " + MovieDatabaseDefinition.TM_TITLE + " ASC;";

    private static final String GET_MOVIE_TITLE_BY_ID = "SELECT " + MovieDatabaseDefinition.TM_TITLE + " FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.TM_ID + "=?;";

    private static final String GET_MOVIE_WATCHED_STATUS = "SELECT " + MovieDatabaseDefinition.TM_WATCHED + " FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.TM_ID + "=?;";

    private static final String COUNT_UNWATCHED_MOVIES_UNRELEASED = "SELECT COUNT(*) FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.TM_WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " AND " + MovieDatabaseDefinition.TM_DATE + " > @OFFSET@;";
    private static final String GET_UNWATCHED_MOVIES_UNRELEASED = "SELECT * " +
            "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.TM_WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " AND " + MovieDatabaseDefinition.TM_DATE + " > @OFFSET@ ORDER BY " + MovieDatabaseDefinition.TM_DATE + " ASC;";

    private static final String COUNT_UNWATCHED_MOVIES_RELEASED = "SELECT COUNT(*) FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.TM_WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " AND " + MovieDatabaseDefinition.TM_DATE + " <= @OFFSET@;";
    private static final String GET_UNWATCHED_MOVIES_RELEASED = "SELECT * " +
            "FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " " +
            "WHERE " + MovieDatabaseDefinition.TM_WATCHED + "=" + MovieDatabaseDefinition.WATCHED_FALSE + " AND " + MovieDatabaseDefinition.TM_DATE + " <= @OFFSET@ ORDER BY " + MovieDatabaseDefinition.TM_DATE + " ASC;";

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
        movieRow.put(MovieDatabaseDefinition.TM_ID, movie.getId());
        movieRow.put(MovieDatabaseDefinition.TM_TITLE, cleanTitle(movie.getTitle()));
        movieRow.put(MovieDatabaseDefinition.TM_IMDB, movie.getImdbId());
        movieRow.put(MovieDatabaseDefinition.TM_DATE, dateToString(movie.getReleaseDate()));
        movieRow.put(MovieDatabaseDefinition.TM_OVERVIEW, movie.getOverview());
        movieRow.put(MovieDatabaseDefinition.TM_TAGLINE, movie.getTagline());
        movieRow.put(MovieDatabaseDefinition.TM_COLLECTION, movie.getBelongsToCollection().getCollectionName());
        if (newMovie && alreadyReleased(movie)) {
            movieRow.put(MovieDatabaseDefinition.TM_WATCHED, MovieDatabaseDefinition.WATCHED_TRUE);
        } else {
            movieRow.put(MovieDatabaseDefinition.TM_WATCHED, currentWatchedStatus);
        }
        dbWritable.replace(MovieDatabaseDefinition.TABLE_MOVIES, null, movieRow);
    }

    public String getMovieWatchedStatus(SQLiteDatabase dbReadable, Movie movie) {
        String[] args = new String[]{movie.getIdAsString()};
        Cursor cursor = dbReadable.rawQuery(GET_MOVIE_WATCHED_STATUS, args);
        String watchedStatus = MovieDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, MovieDatabaseDefinition.TM_WATCHED);
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
        String watchedStatus = MovieDatabaseDefinition.WATCHED_FALSE;

        try {
            while (cursor.moveToNext()) {
                watchedStatus = getColumnValue(cursor, MovieDatabaseDefinition.TM_WATCHED);
            }
        } finally {
            cursor.close();
        }

        dbReadable.close();
        return MovieDatabaseDefinition.WATCHED_TRUE.equals(watchedStatus);
    }

    public void deleteAllMovies() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + MovieDatabaseDefinition.TABLE_MOVIES + ";");
        dbWritable.close();
    }

    public void deleteMovie(Integer movieId) {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + MovieDatabaseDefinition.TABLE_MOVIES + " WHERE " + MovieDatabaseDefinition.TM_ID + "=" + movieId + ";");
        dbWritable.close();
    }

    public String getTitleById(int movieId) {
        String title = "";
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
        Cursor cursor = dbReadable.rawQuery(GET_MOVIE_TITLE_BY_ID, new String[]{String.valueOf(movieId)});
        try {
            while (cursor.moveToNext()) {
                title = getColumnValue(cursor, MovieDatabaseDefinition.TM_TITLE);
            }
        } finally {
            cursor.close();
        }
        dbReadable.close();
        return title;
    }

    public int countUnwatchedReleasedMovies() {
        String offset = "date('now','-" + getNotificationDayOffsetMovie(context) + " days')";
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
        String offset = "date('now','-" + getNotificationDayOffsetMovie(context) + " days')";
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
        String offset = "date('now','-" + getNotificationDayOffsetMovie(context) + " days')";
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
        String offset = "date('now','-" + getNotificationDayOffsetMovie(context) + " days')";
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
        movieCollection.setCollectionName(getColumnValue(cursor, MovieDatabaseDefinition.TM_COLLECTION));

        movie.setId(Integer.parseInt(getColumnValue(cursor, MovieDatabaseDefinition.TM_ID)));
        movie.setTitle(getColumnValue(cursor, MovieDatabaseDefinition.TM_TITLE));
        movie.setOverview(getColumnValue(cursor, MovieDatabaseDefinition.TM_OVERVIEW));
        movie.setReleaseDate(stringToDate(getColumnValue(cursor, MovieDatabaseDefinition.TM_DATE)));
        movie.setImdbId(getColumnValue(cursor, MovieDatabaseDefinition.TM_IMDB));
        movie.setTagline(getColumnValue(cursor, MovieDatabaseDefinition.TM_TAGLINE));
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
        values.put(MovieDatabaseDefinition.TM_WATCHED, watchedStatus);
        String where = MovieDatabaseDefinition.TM_ID + "=?";
        String[] whereArgs = new String[]{movie.getIdAsString()};
        dbWriteable.update(MovieDatabaseDefinition.TABLE_MOVIES, values, where, whereArgs);
        dbWriteable.close();
    }

    private boolean alreadyReleased(Movie movie) {
        return movie.getReleaseDate().compareTo(new Date()) < 0;
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }
}
