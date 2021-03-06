package uk.co.ourfriendirony.medianotifier.db.game;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static uk.co.ourfriendirony.medianotifier.general.Constants.DB_FALSE;

public class GameDatabaseDefinition extends SQLiteOpenHelper {
    public static final String ID = "id";
    public static final String SUBID = "sub_id";
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    public static final String DESCRIPTION = "description";
    public static final String RELEASE_DATE = "release_date";
    public static final String EXTERNAL_URL = "external_url";
    public static final String PLAYED = "watched";
    public static final String DATABASE_NAME = "games";
    public static final String TABLE_GAMES = "games";
    private static final int DATABASE_VERSION = 6;

    GameDatabaseDefinition(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("[DB CREATE]", String.valueOf(this.getClass()));
        db.execSQL("CREATE TABLE " + TABLE_GAMES + " (" +
                ID + " TEXT, " +
                SUBID + " TEXT, " +
                TITLE + " TEXT, " +
                SUBTITLE + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                RELEASE_DATE + " TEXT, " +
                EXTERNAL_URL + " TEXT, " +
                PLAYED + " INTEGER DEFAULT " + DB_FALSE + ", " +
                "PRIMARY KEY (" + ID + ")" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("[DB UPGRADE]", String.valueOf(this.getClass()) + " version: " + oldVersion + " -> " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES + ";");
        onCreate(db);
    }
}
