package uk.co.ourfriendirony.medianotifier.db.movie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDatabaseDefinition extends SQLiteOpenHelper {

    public static final String WATCHED_FALSE = "0";
    public static final String WATCHED_TRUE = "1";
    static final String TABLE_MOVIES = "movies";
    public static final String TM_ID = "movie_id";
    public static final String TM_TITLE = "movie_title";
    public static final String TM_IMDB = "movie_imdb_id";
    public static final String TM_DATE = "movie_released_date";
    public static final String TM_OVERVIEW = "movie_overview";
    static final String TM_WATCHED = "movie_watched";
    static final String TM_TAGLINE = "movie_tagline";
    public static final String TM_COLLECTION = "movie_collection";
    private static final String DATABASE_NAME = "movies";
    private static final int DATABASE_VERSION = 4;

    MovieDatabaseDefinition(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("[DB CREATE]", String.valueOf(this.getClass()));
        db.execSQL("CREATE TABLE " + TABLE_MOVIES + " (" +
                TM_ID + " INTEGER, " +
                TM_TITLE + " TEXT, " +
                TM_IMDB + " TEXT, " +
                TM_DATE + " TEXT, " +
                TM_OVERVIEW + " TEXT, " +
                TM_TAGLINE + " TEXT, " +
                TM_COLLECTION + " TEXT, " +
                TM_WATCHED + " INTEGER DEFAULT " + WATCHED_FALSE + ", " +
                "PRIMARY KEY (" + TM_ID + ")" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("[DB UPGRADE]", String.valueOf(this.getClass()) + " version: " + oldVersion + " -> " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES + ";");
        onCreate(db);
    }
}
