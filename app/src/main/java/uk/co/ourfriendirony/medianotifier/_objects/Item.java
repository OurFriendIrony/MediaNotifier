package uk.co.ourfriendirony.medianotifier._objects;

import java.util.Date;
import java.util.List;

public interface Item {
    String getId();

    String getTitle();

    String getSubTitle();

    String getOverview();

    Date getReleaseDate();

    List<Item> getSubItems();
}
