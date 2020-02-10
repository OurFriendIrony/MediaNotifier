package uk.co.ourfriendirony.medianotifier._objects.movie;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier.clients.objects.movie.get.MovieGet;
import uk.co.ourfriendirony.medianotifier.clients.objects.movie.search.MovieSearchResult;

public class ItemMovie implements Item {
    private String id;
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private Uri externalUrl = null;
    private List<Item> children = new ArrayList<>();

    private static final String IMDB_URL = "http://www.imdb.com/title/";

    public ItemMovie(MovieGet movie) {
        this.id = String.valueOf(movie.getId());
        this.title = movie.getTitle();
        if (movie.getBelongsToCollection() != null)
            this.subtitle = movie.getBelongsToCollection().getName();
        this.description = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
        this.externalUrl = Uri.parse(IMDB_URL + movie.getImdbId());
    }

    public ItemMovie(MovieSearchResult movie) {
        this.id = String.valueOf(movie.getId());
        this.title = movie.getTitle();
        this.description = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
    }

    public ItemMovie(String id, String title, String subtitle, String description, Date releaseDate, String externalUrl, List<Item> children) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.releaseDate = releaseDate;
        this.externalUrl = Uri.parse(externalUrl);
        this.children = children;
        System.out.println(this.toString());
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
        return new SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(releaseDate);
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
    public Uri getExternalLink() {
        return externalUrl;
    }

    public String toString() {
        return getTitle() + " > " + getReleaseDateFull();
    }
}
