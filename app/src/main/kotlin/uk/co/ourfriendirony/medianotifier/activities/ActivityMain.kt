package uk.co.ourfriendirony.medianotifier.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uk.co.ourfriendirony.medianotifier.R
import uk.co.ourfriendirony.medianotifier.activities.async.UpdateMediaItem
import uk.co.ourfriendirony.medianotifier.clients.*
import uk.co.ourfriendirony.medianotifier.db.Database
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase
import uk.co.ourfriendirony.medianotifier.db.game.GameDatabase
import uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabase
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase
import uk.co.ourfriendirony.medianotifier.general.Constants.ARTIST
import uk.co.ourfriendirony.medianotifier.general.Constants.GAME
import uk.co.ourfriendirony.medianotifier.general.Constants.INTENT_KEY
import uk.co.ourfriendirony.medianotifier.general.Constants.MOVIE
import uk.co.ourfriendirony.medianotifier.general.Constants.TVSHOW
import uk.co.ourfriendirony.medianotifier.general.Helper.getNotificationNumber
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator.contactEmailIntent
import uk.co.ourfriendirony.medianotifier.general.IntentGenerator.getWebPageIntent
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem
import java.util.concurrent.Executors

class ActivityMain : AppCompatActivity() {
    private val tvShowClient: Client = TVClient()
    private val movieClient: Client = MovieClient()
    private val artistClient: Client = ArtistClient()
    private val gameClient: Client = GameClient()

    private var tvShowDatabase: Database? = null
    private var movieDatabase: Database? = null
    private var artistDatabase: Database? = null
    private var gameDatabase: Database? = null

    private var mainButtonTvshowNotification: TextView? = null
    private var mainButtonMovieNotification: TextView? = null
    private var mainButtonArtistNotification: TextView? = null
    private var mainButtonGameNotification: TextView? = null
    private var progressBar: ProgressBar? = null

    private val myHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.main_progress)
        tvShowDatabase = TVShowDatabase(applicationContext)
        movieDatabase = MovieDatabase(applicationContext)
        artistDatabase = ArtistDatabase(applicationContext)
        gameDatabase = GameDatabase(applicationContext)
        val mainButtonTvshowFind = findViewById<FloatingActionButton>(R.id.main_button_tv_find)
        val mainButtonMovieFind = findViewById<FloatingActionButton>(R.id.main_button_movie_find)
        val mainButtonArtistFind = findViewById<FloatingActionButton>(R.id.main_button_artist_find)
        val mainButtonGameFind = findViewById<FloatingActionButton>(R.id.main_button_game_find)
        mainButtonTvshowNotification = findViewById(R.id.main_button_tv_notification)
        mainButtonMovieNotification = findViewById(R.id.main_button_movie_notification)
        mainButtonArtistNotification = findViewById(R.id.main_button_artist_notification)
        mainButtonGameNotification = findViewById(R.id.main_button_game_notification)
        val mainButtonTvshowLibrary = findViewById<Button>(R.id.main_button_tv)
        val mainButtonMovieLibrary = findViewById<Button>(R.id.main_button_movie)
        val mainButtonArtistLibrary = findViewById<Button>(R.id.main_button_artist)
        val mainButtonGameLibrary = findViewById<Button>(R.id.main_button_game)
        val tmdbImage = findViewById<ImageView>(R.id.badge_tmdb)
        val musicbrainzImage = findViewById<ImageView>(R.id.badge_musicbrainz)
        val rawgImage = findViewById<ImageView>(R.id.badge_rawg)
        prepButton(mainButtonTvshowFind, ActivityFind::class.java, TVSHOW)
        prepButton(mainButtonMovieFind, ActivityFind::class.java, MOVIE)
        prepButton(mainButtonArtistFind, ActivityFind::class.java, ARTIST)
        prepButton(mainButtonGameFind, ActivityFind::class.java, GAME)
        prepButton(mainButtonTvshowLibrary, ActivityLibrary::class.java, TVSHOW)
        prepButton(mainButtonMovieLibrary, ActivityLibrary::class.java, MOVIE)
        prepButton(mainButtonArtistLibrary, ActivityLibrary::class.java, ARTIST)
        prepButton(mainButtonGameLibrary, ActivityLibrary::class.java, GAME)
        prepButton(mainButtonTvshowNotification, ActivityUnplayed::class.java, TVSHOW)
        prepButton(mainButtonMovieNotification, ActivityUnplayed::class.java, MOVIE)
        prepButton(mainButtonArtistNotification, ActivityUnplayed::class.java, ARTIST)
        prepButton(mainButtonGameNotification, ActivityUnplayed::class.java, GAME)
        tmdbImage.setOnClickListener { startActivity(getWebPageIntent("https://www.themoviedb.org/")) }
        musicbrainzImage.setOnClickListener { startActivity(getWebPageIntent("https://musicbrainz.org/")) }
        rawgImage.setOnClickListener { startActivity(getWebPageIntent("https://rawg.io/")) }
    }

    private fun prepButton(view: View?, c: Class<*>, type: String) {
        view!!.setOnClickListener { subView: View ->
            val intent = Intent(subView.context, c).putExtra(INTENT_KEY, type)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val notificationOn = resources.getDrawable(R.drawable.button_notification_on)
        val notificationOff = resources.getDrawable(R.drawable.button_notification_off)
        val numEpisodes = tvShowDatabase!!.countUnplayedReleased()
        val numMovies = movieDatabase!!.countUnplayedReleased()
        val numAlbums = artistDatabase!!.countUnplayedReleased()
        val numGames = gameDatabase!!.countUnplayedReleased()
        val tvNotifyBG = if (numEpisodes > 0) notificationOn else notificationOff
        val movieNotifyBG = if (numMovies > 0) notificationOn else notificationOff
        val albumNotifyBG = if (numAlbums > 0) notificationOn else notificationOff
        val gameNotifyBG = if (numGames > 0) notificationOn else notificationOff
        mainButtonTvshowNotification!!.text = getNotificationNumber(numEpisodes)
        mainButtonTvshowNotification!!.background = tvNotifyBG
        mainButtonMovieNotification!!.background = movieNotifyBG
        mainButtonMovieNotification!!.text = getNotificationNumber(numMovies)
        mainButtonArtistNotification!!.background = albumNotifyBG
        mainButtonArtistNotification!!.text = getNotificationNumber(numAlbums)
        mainButtonGameNotification!!.background = gameNotifyBG
        mainButtonGameNotification!!.text = getNotificationNumber(numGames)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_about).isEnabled = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, ActivitySettings::class.java)
                startActivity(intent)
                true
            }
            R.id.action_contact -> {
                startActivity(contactEmailIntent)
                true
            }
            R.id.action_refresh -> {
                Executors.newSingleThreadExecutor().execute(
                    UpdateMediaItem(
                        baseContext,
                        progressBar,
                        tvShowDatabase,
                        tvShowClient,
                        myHandler,
                        *asArray(tvShowDatabase!!.readAllItems())
                    )
                )
                Executors.newSingleThreadExecutor().execute(
                    UpdateMediaItem(
                        baseContext,
                        progressBar,
                        movieDatabase,
                        movieClient,
                        myHandler,
                        *asArray(movieDatabase!!.readAllItems())
                    )
                )
                Executors.newSingleThreadExecutor().execute(
                    UpdateMediaItem(
                        baseContext,
                        progressBar,
                        artistDatabase,
                        artistClient,
                        myHandler,
                        *asArray(artistDatabase!!.readAllItems())
                    )
                )
                Executors.newSingleThreadExecutor().execute(
                    UpdateMediaItem(baseContext, progressBar, gameDatabase, gameClient, myHandler, *asArray(gameDatabase!!.readAllItems()))
                )
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun asArray(items: List<MediaItem>): Array<MediaItem> {
        return items.toTypedArray()
    }
}