package uk.co.ourfriendirony.medianotifier.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ArtistDatabaseDefinition extends SQLiteOpenHelper {

    public static final String WATCHED_FALSE = "0";
    public static final String WATCHED_TRUE = "1";
    static final String TABLE_ARTISTS = "artists";
    static final String TA_ID = "artist_id";
    static final String TA_TITLE = "artist_title";
    static final String TA_OVERVIEW = "artist_overview";
    static final String TA_WATCHED = "artist_watched";
    private static final String DATABASE_NAME = "artists";
    private static final int DATABASE_VERSION = 4;

    ArtistDatabaseDefinition(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(String.valueOf(this.getClass()), "onCreate");

        db.execSQL("CREATE TABLE " + TABLE_ARTISTS + " (" +
                TA_ID + " INTEGER, " +
                TA_TITLE + " TEXT, " +
                TA_OVERVIEW + " TEXT, " +
                TA_WATCHED + " INTEGER DEFAULT " + WATCHED_FALSE + ", " +
                "PRIMARY KEY (" + TA_ID + ")" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(String.valueOf(this.getClass()), "onUpdate: old=" + oldVersion + " new=" + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTISTS + ";");
        onCreate(db);
    }
}
