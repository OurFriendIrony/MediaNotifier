package uk.co.ourfriendirony.medianotifier._objects.tv;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier.clients.objects.tv.get.TVSeasonGetEpisode;

public class ItemTVEpisode implements Item {
    private String id;
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private String externalUrl;
    private List<Item> children = new ArrayList<>();

    public ItemTVEpisode(TVSeasonGetEpisode episode, String showId) {
        this.id = String.valueOf(showId);
        this.title = episode.getName();
        this.subtitle = String.format("S%02d", episode.getSeasonNumber()) + String.format("E%02d", episode.getEpisodeNumber());
        this.description = episode.getOverview();
        this.releaseDate = episode.getAirDate();
    }

    public ItemTVEpisode(String id, String title, String subtitle, String description, Date releaseDate, String externalUrl, List<Item> children) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.releaseDate = releaseDate;
        if (externalUrl != null)
            this.externalUrl = externalUrl;
        this.children = children;
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
    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Date getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String getReleaseDateFull() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(releaseDate);
    }

    @Override
    public String getReleaseDateYear() {
        return new SimpleDateFormat("yyyy", Locale.UK).format(releaseDate);
    }

    @Override
    public List<Item> getChildren() {
        return children;
    }

    @Override
    public int countChildren() {
        return children.size();
    }

    @Override
    public String getExternalLink() {
        return externalUrl;
    }

    @Override
    public Uri getExternalUrl() {
        if (externalUrl != null)
            return Uri.parse(externalUrl);
        return null;
    }

    public String toString() {
        return "TVEpisode: " + getSubtitle() + " > " + getTitle() + " > " + getReleaseDateFull();
    }
}
