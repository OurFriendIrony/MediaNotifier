package uk.co.ourfriendirony.medianotifier.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShow;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVShowExternalIds;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;

import static uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition.*;

public class TVShowDatabase {

    private final SQLiteDatabase dbWritable;
    private final SQLiteDatabase dbReadable;
    DateFormat dateFormat = new SimpleDateFormat("yyyy");

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
        ContentValues tvShowRow = new ContentValues();
        tvShowRow.put(TT_ID, tvShow.getId());
        tvShowRow.put(TT_TITLE, tvShow.getName());
        tvShowRow.put(TT_IMDB, tvShow.getExternalIds().getImdbId());
        tvShowRow.put(TT_DATE, tvShow.getFirstAirDate().toString());
        tvShowRow.put(TT_OVERVIEW, tvShow.getOverview());
        dbWritable.insert(TABLE_TVSHOWS, null, tvShowRow);
    }

    private void insertTVShowSeason(TVSeason season) {
        ContentValues seasonRow = new ContentValues();
        seasonRow.put(TTS_ID, season.getId());
        seasonRow.put(TTS_SEASON_NO, season.getSeasonNumber());
        seasonRow.put(TTS_DATE, season.getAirDate());
        dbWritable.insert(TABLE_TVSHOWS_SEASONS, null, seasonRow);
    }

    private void insertTVShowEpisode(TVEpisode episode) {
        ContentValues episodeRow = new ContentValues();
        episodeRow.put(TTSE_ID, episode.getId());
        episodeRow.put(TTSE_SEASON_NO, episode.getSeasonNumber());
        episodeRow.put(TTSE_EPISODE_NO, episode.getEpisodeNumber());
        episodeRow.put(TTSE_TITLE, episode.getName());
        episodeRow.put(TTSE_DATE, episode.getAirDate());
        episodeRow.put(TTSE_OVERVIEW, episode.getOverview());
        dbWritable.insert(TABLE_TVSHOWS_EPISODES, null, episodeRow);
    }

    public String selectTVShow() {
        StringBuilder result = new StringBuilder();

        String sql = "SELECT * FROM " + TABLE_TVSHOWS + ";";
        Cursor cursor = dbReadable.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                result.append(getColumnValue(cursor, TT_ID))
                        .append(" | ").append(getColumnValue(cursor, TT_DATE))
                        .append(" | ").append(getColumnValue(cursor, TT_TITLE))
                        .append(" | ").append(getColumnValue(cursor, TT_IMDB))
                        .append(" | ").append(getColumnValue(cursor, TT_OVERVIEW))
                        .append("\n*****************\n");
            }
        } finally {
            cursor.close();
        }

        String sqlSeasons = "SELECT * FROM " + TABLE_TVSHOWS_SEASONS + ";";
        Cursor cursorSeasons = dbReadable.rawQuery(sqlSeasons, null);
        try {
            while (cursorSeasons.moveToNext()) {
                result.append(">>> ").append(getColumnValue(cursorSeasons, TTS_ID))
                        .append(" | ").append(getColumnValue(cursorSeasons, TTS_SEASON_NO))
                        .append(" | ").append(getColumnValue(cursorSeasons, TTS_DATE))
                        .append("\n*****************\n");
            }
        } finally {
            cursor.close();
        }

        String sqlEpisodes = "SELECT * FROM " + TABLE_TVSHOWS_EPISODES + ";";
        Cursor cursorEpisodes = dbReadable.rawQuery(sqlEpisodes, null);
        try {
            while (cursorEpisodes.moveToNext()) {
                result.append(">>>>> ").append(getColumnValue(cursorEpisodes, TTSE_ID))
                        .append(" | ").append(getColumnValue(cursorEpisodes, TTSE_SEASON_NO))
                        .append(" | ").append(getColumnValue(cursorEpisodes, TTSE_EPISODE_NO))
                        .append(" | ").append(getColumnValue(cursorEpisodes, TTSE_DATE))
                        .append(" | ").append(getColumnValue(cursorEpisodes, TTSE_TITLE))
                        .append(" | ").append(getColumnValue(cursorEpisodes, TTSE_OVERVIEW))
                        .append("\n*****************\n");
            }
        } finally {
            cursor.close();
        }


        return result.toString();

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

        String sql = "SELECT * FROM " + TABLE_TVSHOWS + ";";
        Cursor cursor = dbReadable.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                TVShow tvShow = new TVShow();
                tvShow.setId(Integer.parseInt(getColumnValue(cursor, TT_ID)));
                try {
                    tvShow.setFirstAirDate(dateFormat.parse(getColumnValue(cursor, TT_ID)));
                } catch (ParseException e) {
                    tvShow.setFirstAirDate(new Date());
                }
                tvShow.setName(getColumnValue(cursor, TT_TITLE));
                tvShow.setOverview(getColumnValue(cursor, TT_OVERVIEW));
                TVShowExternalIds ids = new TVShowExternalIds();
                ids.setImdbId(getColumnValue(cursor, TT_IMDB));
                tvShow.setExternalIds(ids);
                tvShows.add(tvShow);
            }
        } finally {
            cursor.close();
        }
        return tvShows;
    }
}
