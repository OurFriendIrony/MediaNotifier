package uk.co.ourfriendirony.medianotifier.db.artist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import uk.co.ourfriendirony.medianotifier.general.Constants

class ArtistDatabaseDefinition internal constructor(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("[DB CREATE]", this.javaClass.toString())
        db.execSQL(
            "CREATE TABLE " + TABLE_ARTISTS + " (" +
                    ID + " TEXT, " +
                    SUBID + " TEXT, " +
                    TITLE + " TEXT, " +
                    SUBTITLE + " TEXT, " +
                    DESCRIPTION + " TEXT, " +
                    RELEASE_DATE + " TEXT, " +
                    EXTERNAL_URL + " TEXT, " +
                    PLAYED + " INTEGER DEFAULT " + Constants.DB_FALSE + ", " +
                    "PRIMARY KEY (" + ID + ")" +
                    ")"
        )
        db.execSQL(
            "CREATE TABLE " + TABLE_RELEASES + " (" +
                    ID + " TEXT, " +
                    SUBID + " TEXT, " +
                    TITLE + " TEXT, " +
                    SUBTITLE + " TEXT, " +
                    DESCRIPTION + " TEXT, " +
                    RELEASE_DATE + " TEXT, " +
                    EXTERNAL_URL + " TEXT, " +
                    PLAYED + " INTEGER DEFAULT " + Constants.DB_FALSE + ", " +
                    "PRIMARY KEY (" + ID + "," + SUBID + ") " +
                    ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(
            "[DB UPGRADE]",
            this.javaClass.toString() + " version: " + oldVersion + " -> " + newVersion
        )
        db.execSQL("DROP TABLE IF EXISTS ${TABLE_ARTISTS};")
        db.execSQL("DROP TABLE IF EXISTS ${TABLE_RELEASES};")
        onCreate(db)
    }

    companion object {
        const val ID = "id"
        const val SUBID = "sub_id"
        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val DESCRIPTION = "description"
        const val RELEASE_DATE = "release_date"
        const val EXTERNAL_URL = "external_url"
        const val PLAYED = "watched"
        const val DATABASE_NAME = "artists"
        const val TABLE_ARTISTS = "artists"
        const val TABLE_RELEASES = "releases"
        private const val DATABASE_VERSION = 7
    }
}