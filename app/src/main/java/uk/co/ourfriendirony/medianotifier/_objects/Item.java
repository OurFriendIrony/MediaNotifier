package uk.co.ourfriendirony.medianotifier._objects;

import android.net.Uri;

import java.util.Date;
import java.util.List;

public interface Item {
    String getId();

    String getTitle();

    String getSubtitle();

    String getDescription();

    Date getReleaseDate();

    String getReleaseDateFull();

    String getReleaseDateYear();

    List<Item> getChildren();

    int countChildren();

    String getExternalLink();

    Uri getExternalUrl();
}
