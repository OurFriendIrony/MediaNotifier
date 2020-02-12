package uk.co.ourfriendirony.medianotifier.db.tv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TVShowDatabaseDefinition extends SQLiteOpenHelper {
    public static final String WATCHED_FALSE = "0";
    public static final String WATCHED_TRUE = "1";
    static final String TABLE_TVSHOWS = "tv_shows";
    public static final String TT_ID = "show_id";
    public static final String TT_TITLE = "show_title";
    public static final String TT_SUBTITLE = "show_subtitle";
    public static final String TT_IMDB = "show_imdb_id";
    public static final String TT_DATE = "show_air_date";
    public static final String TT_OVERVIEW = "show_overview";
    static final String TT_WATCHED = "show_watched";
    static final String TABLE_TVSHOWS_EPISODES = "tv_shows_episodes";
    public static final String TTSE_ID = "show_id";
    public static final String TTSE_TITLE = "episode_title";
    public static final String TTSE_SUBTITLE = "episode_subtitle";
    public static final String TTSE_IMDB = "episode_imdb_id";
    public static final String TTSE_DATE = "episode_air_date";
    public static final String TTSE_OVERVIEW = "episode_overview";
    static final String TTSE_WATCHED = "episode_watched";
    private static final String DATABASE_NAME = "tv_shows";
    private static final int DATABASE_VERSION = 2;

    public TVShowDatabaseDefinition(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("[DB CREATE]", String.valueOf(this.getClass()));
        db.execSQL("CREATE TABLE " + TABLE_TVSHOWS + " (" +
                TT_ID + " INTEGER, " +
                TT_TITLE + " TEXT, " +
                TT_SUBTITLE + " TEXT, " +
                TT_IMDB + " TEXT, " +
                TT_DATE + " TEXT, " +
                TT_OVERVIEW + " TEXT, " +
                TT_WATCHED + " INTEGER DEFAULT " + WATCHED_FALSE + ", " +
                "PRIMARY KEY (" + TT_ID + ")" +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_TVSHOWS_EPISODES + " (" +
                TTSE_ID + " INTEGER, " +
                TTSE_TITLE + " TEXT, " +
                TTSE_SUBTITLE + " TEXT, " +
                TTSE_IMDB + " TEXT, " +
                TTSE_DATE + " TEXT, " +
                TTSE_OVERVIEW + " TEXT, " +
                TTSE_WATCHED + " INTEGER DEFAULT " + WATCHED_FALSE + ", " +
                "PRIMARY KEY (" + TTSE_ID + "," + TTSE_SUBTITLE + ") " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("[DB UPGRADE]", String.valueOf(this.getClass()) + " version: " + oldVersion + " -> " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOWS_EPISODES);
        onCreate(db);
    }
}
