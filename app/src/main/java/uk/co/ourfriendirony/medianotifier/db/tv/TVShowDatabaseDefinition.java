package uk.co.ourfriendirony.medianotifier.db.tv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TVShowDatabaseDefinition extends SQLiteOpenHelper {
    public static final String WATCHED_FALSE = "0";
    public static final String WATCHED_TRUE = "1";
    static final String TABLE_TVSHOWS = "tv_shows";
    static final String TT_ID = "show_id";
    static final String TT_TITLE = "show_title";
    static final String TT_IMDB = "show_imdb_id";
    static final String TT_DATE = "show_air_date";
    static final String TT_OVERVIEW = "show_overview";
    static final String TT_RAWJSON = "show_rawjson";
    static final String TABLE_TVSHOWS_SEASONS = "tv_shows_seasons";
    static final String TTS_ID = "show_id";
    static final String TTS_SEASON_NO = "season_no";
    static final String TTS_DATE = "season_air_date";
    static final String TABLE_TVSHOWS_EPISODES = "tv_shows_episodes";
    static final String TTSE_ID = "show_id";
    static final String TTSE_SEASON_NO = "episode_season_no";
    static final String TTSE_EPISODE_NO = "episode_no";
    static final String TTSE_TITLE = "episode_title";
    static final String TTSE_DATE = "episode_air_date";
    static final String TTSE_OVERVIEW = "episode_overview";
    static final String TTSE_WATCHED = "episode_watched";
    private static final String DATABASE_NAME = "tv_shows";
    private static final int DATABASE_VERSION = 1;

    public TVShowDatabaseDefinition(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(String.valueOf(this.getClass()), "onCreate");

        db.execSQL("CREATE TABLE " + TABLE_TVSHOWS + " (" +
                TT_ID + " INTEGER, " +
                TT_TITLE + " TEXT, " +
                TT_IMDB + " TEXT, " +
                TT_DATE + " TEXT, " +
                TT_OVERVIEW + " TEXT, " +
                TT_RAWJSON + " TEXT, " +
                "PRIMARY KEY (" + TT_ID + ")" +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_TVSHOWS_SEASONS + " (" +
                TTS_ID + " INTEGER, " +
                TTS_SEASON_NO + " INTEGER, " +
                TTS_DATE + " TEXT, " +
                "PRIMARY KEY (" + TTS_ID + "," + TTS_SEASON_NO + ")" +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_TVSHOWS_EPISODES + " (" +
                TTSE_ID + " INTEGER, " +
                TTSE_SEASON_NO + " INTEGER, " +
                TTSE_EPISODE_NO + " INTEGER, " +
                TTSE_TITLE + " TEXT, " +
                TTSE_DATE + " TEXT, " +
                TTSE_OVERVIEW + " TEXT, " +
                TTSE_WATCHED + " INTEGER DEFAULT " + WATCHED_FALSE + ", " +
                "PRIMARY KEY (" + TTSE_ID + "," + TTSE_SEASON_NO + "," + TTSE_EPISODE_NO + ") " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(String.valueOf(this.getClass()), "onUpdate: old=" + oldVersion + " new=" + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOWS_SEASONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOWS_EPISODES);
        onCreate(db);
    }
}