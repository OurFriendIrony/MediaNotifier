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
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanDate;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.cleanTitle;

public class TVShowDatabase {
    private static final String SELECT_TVSHOWS = "SELECT " + TT_RAWJSON + " FROM " + TABLE_TVSHOWS + " ORDER BY " + TT_TITLE + " ASC;";
    private static final String COUNT_UNWATCHED_EPISODES = "SELECT COUNT(*) FROM " + TABLE_TVSHOWS_EPISODES + " WHERE " + TTSE_DATE + " >= date('now');";

    private final TVShowDatabaseDefinition databaseHelper;

    public TVShowDatabase(TVShowDatabaseDefinition databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void saveTVShow(int tvShowId) {
        MovieDatabaseClient client = new MovieDatabaseClient();
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        TVShow tvShow;
        try {
            tvShow = client.getTVShow(tvShowId);
        } catch (IOException e) {
            tvShow = new TVShow();
        }

        for (TVSeason season : tvShow.getSeasons()) {
            for (TVEpisode episode : season.getEpisodes()) {
                episode.setId(tvShow.getId());
                insertTVShowEpisode(dbWritable, episode);
            }
            season.setId(tvShow.getId());
            insertTVShowSeason(dbWritable, season);
        }
        insertTVShow(dbWritable, tvShow);
        dbWritable.close();
    }

    private void insertTVShow(SQLiteDatabase dbWritable, TVShow tvShow) {
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
        tvShowRow.put(TT_DATE, cleanDate(tvShow.getFirstAirDate()));
        tvShowRow.put(TT_OVERVIEW, tvShow.getOverview());
        tvShowRow.put(TT_RAWJSON, rawJson);
        dbWritable.insert(TABLE_TVSHOWS, null, tvShowRow);
    }

    private void insertTVShowSeason(SQLiteDatabase dbWritable, TVSeason season) {
        ContentValues seasonRow = new ContentValues();
        seasonRow.put(TTS_ID, season.getId());
        seasonRow.put(TTS_SEASON_NO, season.getSeasonNumber());
        seasonRow.put(TTS_DATE, cleanDate(season.getAirDate()));
        dbWritable.insert(TABLE_TVSHOWS_SEASONS, null, seasonRow);
    }

    private void insertTVShowEpisode(SQLiteDatabase dbWritable, TVEpisode episode) {
        ContentValues episodeRow = new ContentValues();
        episodeRow.put(TTSE_ID, episode.getId());
        episodeRow.put(TTSE_SEASON_NO, episode.getSeasonNumber());
        episodeRow.put(TTSE_EPISODE_NO, episode.getEpisodeNumber());
        episodeRow.put(TTSE_TITLE, episode.getName());
        episodeRow.put(TTSE_DATE, cleanDate(episode.getAirDate()));
        episodeRow.put(TTSE_OVERVIEW, episode.getOverview());
        dbWritable.insert(TABLE_TVSHOWS_EPISODES, null, episodeRow);
    }

    private String getColumnValue(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

    public void deleteAllTVShows() {
        SQLiteDatabase dbWritable = databaseHelper.getWritableDatabase();
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_SEASONS + ";");
        dbWritable.execSQL("DELETE FROM " + TABLE_TVSHOWS_EPISODES + ";");
        dbWritable.close();
    }

    public int countUnwatchedEpisodes() {
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();

        Cursor cursor = dbReadable.rawQuery(COUNT_UNWATCHED_EPISODES, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        cursor.close();
        dbReadable.close();
        return count;
    }

    public List<TVShow> getTVShows() {
        List<TVShow> tvShows = new ArrayList<>();
        SQLiteDatabase dbReadable = databaseHelper.getReadableDatabase();
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
        dbReadable.close();
        return tvShows;
    }
}
