package uk.co.ourfriendirony.medianotifier.mediaitem.game;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGet;
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.get.GameGetParentPlatformGroup;
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchParentPlatformGroup;
import uk.co.ourfriendirony.medianotifier.clients.rawg.game.search.GameSearchResult;
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabaseDefinition;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;

import static uk.co.ourfriendirony.medianotifier.general.Helper.getColumnValue;
import static uk.co.ourfriendirony.medianotifier.general.Helper.stringToDate;

public class Game implements MediaItem {
    private String id;
    private String subid = "";
    private String title;
    private String subtitle = "";
    private String description = "";
    private Date releaseDate;
    private String externalUrl;
    // TODO: fully implement played as an item
    private boolean played = false;
    private List<MediaItem> children = new ArrayList<>();

    public Game(GameGet game) {
        this.id = String.valueOf(game.getId());
        this.title = game.getName();
        this.subtitle = getPlatformsCompressed(game);
        this.description = game.getDescriptionRaw();
        this.releaseDate = game.getReleased();
        if (!game.getWebsite().equals("") && game.getWebsite() != null) {
            this.externalUrl = game.getWebsite();
        }
        Log.d("[API GET]", this.toString());
    }

    public Game(GameSearchResult game) {
        this.id = String.valueOf(game.getId());
        this.title = game.getName();
        this.subtitle = getPlatformsCompressed(game);
        this.releaseDate = game.getReleased();
        Log.d("[API SEARCH]", this.toString());
    }

    private String getPlatformsCompressed(GameGet game) {
        List<String> platforms = new ArrayList<>();
        if (game.getParentPlatforms() != null) {
            for (GameGetParentPlatformGroup group : game.getParentPlatforms()) {
                platforms.add(group.getPlatform().getName());
            }
        }
        return TextUtils.join(", ", platforms);
    }

    private String getPlatformsCompressed(GameSearchResult game) {
        List<String> platforms = new ArrayList<>();
        if (game.getParentPlatforms() != null) {
            for (GameSearchParentPlatformGroup group : game.getParentPlatforms()) {
                platforms.add(group.getPlatform().getName());
            }
        }
        return TextUtils.join(", ", platforms);
    }

    public Game(Cursor cursor) {
        this.id = getColumnValue(cursor, GameDatabaseDefinition.ID);
        this.subid = getColumnValue(cursor, GameDatabaseDefinition.SUBID);
        this.title = getColumnValue(cursor, GameDatabaseDefinition.TITLE);
        this.subtitle = getColumnValue(cursor, GameDatabaseDefinition.SUBTITLE);
        this.description = getColumnValue(cursor, GameDatabaseDefinition.DESCRIPTION);
        this.releaseDate = stringToDate(getColumnValue(cursor, GameDatabaseDefinition.RELEASE_DATE));
        this.externalUrl = getColumnValue(cursor, GameDatabaseDefinition.EXTERNAL_URL);
        this.children = new ArrayList<>();
        Log.d("[DB READ]", this.toString());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSubId() {
        return subid;
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
        if (releaseDate != null) {
            return new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(releaseDate);
        }
        return MediaItem.NO_DATE;
    }

    @Override
    public String getReleaseDateYear() {
        if (releaseDate != null) {
            return new SimpleDateFormat("yyyy", Locale.UK).format(releaseDate);
        }
        return MediaItem.NO_DATE;
    }

    @Override
    public List<MediaItem> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<MediaItem> children) {
        this.children = children;
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
    public Boolean getPlayed() {
        return played;
    }

    public String toString() {
        return "Game: " + getTitle() + " > " + getReleaseDateFull();
    }
}
