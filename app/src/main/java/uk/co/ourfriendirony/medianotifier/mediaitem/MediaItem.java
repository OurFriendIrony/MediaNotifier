package uk.co.ourfriendirony.medianotifier.mediaitem;

import android.net.Uri;

import java.util.Date;
import java.util.List;

public interface MediaItem {
    String getId();

    String getTitle();

    String getSubtitle();

    String getDescription();

    Date getReleaseDate();

    String getReleaseDateFull();

    String getReleaseDateYear();

    List<MediaItem> getChildren();

    int countChildren();

    String getExternalLink();

    Uri getExternalUrl();
}
