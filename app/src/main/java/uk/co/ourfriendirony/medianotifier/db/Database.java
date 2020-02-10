package uk.co.ourfriendirony.medianotifier.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import uk.co.ourfriendirony.medianotifier._objects.Item;

public interface Database {
    void add(Item i);

    void update(Item i);

    String getWatchedStatus(SQLiteDatabase dbReadable, Item item);

    boolean getWatchedStatusAsBoolean(Item item);

    void delete(String id);

    int countUnwatchedReleased();

    List<Item> getUnwatchedReleased();

    List<Item> getUnwatchedUnReleased();

    List<Item> getUnwatchedTotal();

    List<Item> getUnwatched(String getQuery, String logTag);

    List<Item> getAll();

    void updateWatchedStatus(Item item, String watchedStatus);
}
