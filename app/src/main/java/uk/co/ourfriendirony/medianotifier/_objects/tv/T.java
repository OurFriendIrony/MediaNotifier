package uk.co.ourfriendirony.medianotifier._objects.tv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier._objects.Item;

public class T implements Item {
    private String id;
    private String title;
    private String subtitle;
    private String overview;
    private Date date;
    private List<Item> children;
    private String specialId;

    public T(TVShow tvShow) {
        this(tvShow.getIdAsString(), tvShow.getName(), "", tvShow.getOverview(), tvShow.getFirstAirDate(), tvShow.getSeasons(), tvShow);
    }

    public T(TVSeason tvSeason, TVShow tvShow) {
        this("", "Season " + tvSeason.getSeasonNumber(), "", "", tvSeason.getAirDate(), tvSeason.getEpisodes(), tvSeason, tvShow);
    }

    public T(TVEpisode tvEpisode, TVSeason tvSeason, TVShow tvShow) {
        this("", tvEpisode.getName(), tvShow.getName(), tvEpisode.getOverview(), tvEpisode.getAirDate());
    }

    public T(String id, String title, String subtitle, String overview, Date date, List<TVSeason> tvSeasons, TVShow tvShow) {
        // Capture a TVShow as an Item
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.overview = overview;
        this.date = date;
        List<Item> items = new ArrayList<>();
        for (TVSeason tvSeason : tvSeasons) {
            items.add(new T(tvSeason, tvShow));
        }
        this.children = items;
        this.specialId = tvShow.getExternalIds().getImdbId();
    }

    public T(String id, String title, String subtitle, String overview, Date date, List<TVEpisode> tvEpisodes, TVSeason tvSeason, TVShow tvShow) {
        // Capture a TVSeason as an Item
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.overview = overview;
        this.date = date;
        List<Item> items = new ArrayList<>();
        for (TVEpisode tvEpisode : tvEpisodes) {
            items.add(new T(tvEpisode, tvSeason, tvShow));
        }
        this.children = items;
        this.specialId = null;
    }

    public T(String id, String title, String subtitle, String overview, Date date) {
        // Capture a TVEpisode as an Item
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.overview = overview;
        this.date = date;
        this.children = null;
        this.specialId = null;
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
        return subtitle;
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
        return children;
    }

    @Override
    public String getSpecialId() {
        return specialId;
    }
}
