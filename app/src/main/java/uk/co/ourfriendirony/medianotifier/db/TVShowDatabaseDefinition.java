package uk.co.ourfriendirony.medianotifier.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TVShowDatabaseDefinition extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tv_shows";

    public static final String TABLE_TVSHOWS = "tv_shows";
    public static final String TT_ID = "_id";
    public static final String TT_TITLE = "title";
    public static final String TT_IMDB = "imdb_id";
    public static final String TT_DATE = "air_date";
    public static final String TT_OVERVIEW = "overview";
    public static final String TT_RAWJSON = "rawjson";

    public static final String TABLE_TVSHOWS_SEASONS = "tv_shows_seasons";
    public static final String TTS_ID = "_id";
    public static final String TTS_SEASON_NO = "season_no";
    public static final String TTS_DATE = "air_date";

    public static final String TABLE_TVSHOWS_EPISODES = "tv_shows_episodes";
    public static final String TTSE_ID = "_id";
    public static final String TTSE_SEASON_NO = "season_no";
    public static final String TTSE_EPISODE_NO = "episode_no";
    public static final String TTSE_TITLE = "title";
    public static final String TTSE_DATE = "air_date";
    public static final String TTSE_OVERVIEW = "overview";

    private static final int DATABASE_VERSION = 1;
    private final Context context;

    public TVShowDatabaseDefinition(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(String.valueOf(this.getClass()), "TV.onCreate");

        db.execSQL("CREATE TABLE " + TABLE_TVSHOWS + " (" +
                TT_ID + " INTEGER, " +
                TT_TITLE + " TEXT, " +
                TT_IMDB + " TEXT, " +
                TT_DATE + " TEXT, " +
                TT_OVERVIEW + " TEXT, " +
                TT_RAWJSON+ " TEXT, " +
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
                "PRIMARY KEY (" + TTSE_ID + "," + TTSE_SEASON_NO + "," + TTSE_EPISODE_NO + ")" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(String.valueOf(this.getClass()), "TV.onUpdate: old=" + oldVersion + " new=" + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOWS_SEASONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOWS_EPISODES);
        onCreate(db);
    }
}
