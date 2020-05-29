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

    int countUnplayedReleased();

    List<MediaItem> getUnplayedReleased();

    List<MediaItem> getUnplayedTotal();

    List<MediaItem> getUnplayed(String getQuery, String logTag);

    List<MediaItem> readAllItems();

    List<MediaItem> readAllParentItems();

    List<MediaItem> readChildItems(String id);

    void updatePlayedStatus(MediaItem mediaItem, String playedStatus);

    boolean markPlayedIfReleased(boolean isNew, MediaItem mediaItem);

    String getCoreType();
}
