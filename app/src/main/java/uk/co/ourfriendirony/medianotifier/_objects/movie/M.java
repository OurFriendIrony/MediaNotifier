package uk.co.ourfriendirony.medianotifier._objects.movie;

import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier._objects.Item;

public class M implements Item {

    private final String id;
    private final String title;
    private final String subtitile;
    private final String overview;
    private final Date date;
    private final String specialId;

    public M(Movie movie) {
        this(movie.getIdAsString(), movie.getTitle(), movie.getBelongsToCollection().getCollectionName(), movie.getOverview(), movie.getReleaseDate(), movie.getImdbId());
    }

    public M(String id, String title, String subtitle, String overview, Date date, String specialId) {
        this.id = id;
        this.title = title;
        this.subtitile = subtitle;
        this.overview = overview;
        this.date = date;
        this.specialId = specialId;
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

    @Override
    public String getSpecialId() {
        return specialId;
    }
}
