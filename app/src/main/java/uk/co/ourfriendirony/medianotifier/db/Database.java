package uk.co.ourfriendirony.medianotifier.db;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

public interface Database {
    void add(MediaItem i);

    void update(MediaItem i);

    String getWatchedStatus(MediaItem mediaItem);

    boolean getWatchedStatusAsBoolean(MediaItem mediaItem);

    void deleteAll();

    void delete(String id);

    int countUnwatchedReleased();

    List<MediaItem> getUnwatchedReleased();

    List<MediaItem> getUnwatchedTotal();

    List<MediaItem> getUnwatched(String getQuery, String logTag);

    List<MediaItem> readAllItems();

    List<MediaItem> readAllParentItems();

    List<MediaItem> readChildItems(String id);

    void updateWatchedStatus(MediaItem mediaItem, String watchedStatus);

    boolean markWatchedIfReleased(boolean isNew, MediaItem mediaItem);

    String getCoreType();
}
