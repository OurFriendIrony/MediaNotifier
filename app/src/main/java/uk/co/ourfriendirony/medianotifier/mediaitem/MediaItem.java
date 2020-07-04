package uk.co.ourfriendirony.medianotifier.mediaitem;

import java.util.Date;
import java.util.List;

public interface MediaItem {
    String NO_DATE = "Date TBD";

    String getId();

    String getSubId();

    String getTitle();

    String getSubtitle();

    String getDescription();

    Date getReleaseDate();

    String getReleaseDateFull();

    String getReleaseDateYear();

    List<MediaItem> getChildren();

    void setChildren(List<MediaItem> children);

    int countChildren();

    String getExternalLink();

    Boolean getPlayed();
}
