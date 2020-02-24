package uk.co.ourfriendirony.medianotifier.db.movie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDatabaseDefinition extends SQLiteOpenHelper {
    public static final String WATCHED_FALSE = "0";
    public static final String WATCHED_TRUE = "1";

    public static final String ID = "id";
    public static final String SUBID = "sub_id";
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    public static final String DESCRIPTION = "description";
    public static final String RELEASE_DATE = "release_date";
    public static final String EXTERNAL_URL = "external_url";
    public static final String WATCHED = "watched";

    private static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "movies";
    public static final String TABLE_MOVIES = "movies";

    MovieDatabaseDefinition(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("[DB CREATE]", String.valueOf(this.getClass()));
        db.execSQL("CREATE TABLE " + TABLE_MOVIES + " (" +
                ID + " TEXT, " +
                SUBID + " TEXT, " +
                TITLE + " TEXT, " +
                SUBTITLE + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                RELEASE_DATE + " TEXT, " +
                EXTERNAL_URL + " TEXT, " +
                WATCHED + " INTEGER DEFAULT " + WATCHED_FALSE + ", " +
                "PRIMARY KEY (" + ID + ")" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("[DB UPGRADE]", String.valueOf(this.getClass()) + " version: " + oldVersion + " -> " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES + ";");
        onCreate(db);
    }
}
