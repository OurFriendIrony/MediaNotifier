package uk.co.ourfriendirony.medianotifier.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;

import static uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition.*;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;

public class TVShowDatabase {

    public static final String SELECT_TVSHOWS = "SELECT " + TT_RAWJSON + " FROM " + TABLE_TVSHOWS + " ORDER BY " + TT_TITLE + " ASC;";
    private final SQLiteDatabase dbWritable;
    private final SQLiteDatabase dbReadable;

    public TVShowDatabase(TVShowDatabaseDefinition databaseHelper) {
        dbWritable = databaseHelper.getWritableDatabase();
        dbReadable = databaseHelper.getReadableDatabase();
    }

    public void saveTVShow(int tvShowId) {
        MovieDatabaseClient client = new MovieDatabaseClient();
        TVShow tvShow;
        try {
            tvShow = client.getTVShow(tvShowId);
        } catch (IOException e) {
            tvShow = new TVShow();
        }

        for (TVSeason season : tvShow.getSeasons()) {
            for (TVEpisode episode : season.getEpisodes()) {
                episode.setId(tvShow.getId());
                insertTVShowEpisode(episode);
            }
            season.setId(tvShow.getId());
            insertTVShowSeason(season);
        }
        insertTVShow(tvShow);
    }

    private void insertTVShow(TVShow tvShow) {
        ObjectMapper mapper = new ObjectMapper();
        String rawJson = null;
        try {
            rawJson = new ObjectMapper().writeValueAsString(tvShow);
        } catch (JsonProcessingException e) {
            rawJson = "";
        }
        ContentValues tvShowRow = new ContentValues();
        tvShowRow.put(TT_ID, tvShow.getId());
        tvShowRow.put(TT_TITLE, cleanTitle(tvShow.getName()));
        tvShowRow.put(TT_IMDB, tvShow.getExternalIds().getImdbId());
        tvShowRow.put(TT_DATE, tvShow.getFirstAirDate().toString());
        tvShowRow.put(TT_OVERVIEW, tvShow.getOverview());
        tvShowRow.put(TT_RAWJSON, rawJson);
        dbWritable.insert(TABLE_TVSHOWS, null, tvShowRow);
    }

    private void insertTVShowSeason(TVSeason season) {
        ContentValues seasonRow = new ContentValues();
        seasonRow.put(TTS_ID, season.getId());
        seasonRow.put(TTS_SEASON_NO, season.getSeasonNumber());
        seasonRow.put(TTS_DATE, season.getAirDate().toString());
        dbWritable.insert(TABLE_TVSHOWS_SEASONS, null, seasonRow);
    }

    private void insertTVShowEpisode(TVEpisode episode) {
        ContentValues episodeRow = new ContentValues();
        episodeRow.put(TTSE_ID, episode.getId());
        episodeRow.put(TTSE_SEASON_NO, episode.getSeasonNumber());
        episodeRow.put(TTSE_EPISODE_NO, episode.getEpisodeNumber());
        episodeRow.put(TTSE_TITLE, episode.getName());
        episodeRow.put(TTSE_DATE, episode.getAirDate().toString());
        episodeRow.put(TTSE_OVERVIEW, episode.getOverview());
        dbWritable.insert(TABLE_TVSHOWS_EPISODES, null, episodeRow);
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

    public void deleteAllTVShows() {
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_SEASONS + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_EPISODES + ";");
    }

    public List<TVShow> getTVShows() {
        List<TVShow> tvShows = new ArrayList<>();

        Cursor cursor = dbReadable.rawQuery(SELECT_TVSHOWS, null);
        try {
            while (cursor.moveToNext()) {
                ObjectMapper mapper = new ObjectMapper();
                TVShow tvShow = new TVShow();
                try {
                    tvShow = mapper.readValue(getColumnValue(cursor, TT_RAWJSON), TVShow.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tvShows.add(tvShow);
            }
        } finally {
            cursor.close();
        }
        return tvShows;
    }
}
