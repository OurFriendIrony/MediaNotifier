package uk.co.ourfriendirony.medianotifier._objects.artist;

import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier._objects.Item;

public class A implements Item {

    private final String id;
    private final String title;
    private final String subtitile;
    private final String overview;
    private final Date date;

    public A(Artist artist) {
        this(artist.getIdAsString(), artist.getTitle(), "subtitle", artist.getOverview(), new Date());
    }

    public A(String id, String title, String subtitle, String overview, Date date) {
        this.id = id;
        this.title = title;
        this.subtitile = subtitle;
        this.overview = overview;
        this.date = date;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSubTitle() {
        return subtitile;
    }

    @Override
    public String getOverview() {
        return overview;
    }

    @Override
    public Date getReleaseDate() {
        return date;
    }

    @Override
    public List<Item> getSubItems() {
        return null;
    }
}
