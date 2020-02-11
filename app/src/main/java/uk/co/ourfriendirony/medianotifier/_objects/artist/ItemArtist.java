package uk.co.ourfriendirony.medianotifier._objects.artist;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier.clients.objects.artist.get.ArtistGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.artist.search.ArtistSearchResult;

public class ItemArtist implements Item {
    private String id;
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate = getDefaultDate();
    private String externalUrl;
    private List<Item> children = new ArrayList<>();

    public ItemArtist(ArtistGet artist) {
        this.id = String.valueOf(artist.getId());
        this.title = artist.getName();
        this.description = artist.getProfile();
        if (artist.getUrls() != null && artist.getUrls().size() > 0)
            this.externalUrl = artist.getUrls().get(0);
        else
            this.externalUrl = artist.getUri();
    }

    public ItemArtist(ArtistSearchResult artist) {
        this.id = String.valueOf(artist.getId());
        this.title = artist.getTitle();
    }

    public ItemArtist(String id, String title, String subtitle, String description, Date releaseDate, String externalUrl, List<Item> children) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.releaseDate = releaseDate;
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
        return "Movie: " + getTitle() + " > " + getReleaseDateFull() + " > Children " + countChildren();
    }

    private Date getDefaultDate() {
        Calendar x = Calendar.getInstance();
        x.set(9999, 1, 1);
        return x.getTime();
    }
}
