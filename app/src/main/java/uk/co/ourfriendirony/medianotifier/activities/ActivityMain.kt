package uk.co.ourfriendirony.medianotifier.activities

import android.content.Intent
import android.os.Bundle
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

class ActivityMain : AppCompatActivity() {
    private val tvShowClient: Client = TVClient()
    private val movieClient: Client = MovieClient()
    private val artistClient: Client = ArtistClient()
    private val gameClient: Client = GameClient()
    private var tvShowDatabase: Database? = null
    private var movieDatabase: Database? = null
    private var artistDatabase: Database? = null
    private var gameDatabase: Database? = null
    private var main_button_tvshow_notification: TextView? = null
    private var main_button_movie_notification: TextView? = null
    private var main_button_artist_notification: TextView? = null
    private var main_button_game_notification: TextView? = null
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.main_progress)
        tvShowDatabase = TVShowDatabase(applicationContext)
        movieDatabase = MovieDatabase(applicationContext)
        artistDatabase = ArtistDatabase(applicationContext)
        gameDatabase = GameDatabase(applicationContext)
        val main_button_tvshow_find = findViewById<FloatingActionButton>(R.id.main_button_tv_find)
        val main_button_movie_find = findViewById<FloatingActionButton>(R.id.main_button_movie_find)
        val main_button_artist_find = findViewById<FloatingActionButton>(R.id.main_button_artist_find)
        val main_button_game_find = findViewById<FloatingActionButton>(R.id.main_button_game_find)
        main_button_tvshow_notification = findViewById(R.id.main_button_tv_notification)
        main_button_movie_notification = findViewById(R.id.main_button_movie_notification)
        main_button_artist_notification = findViewById(R.id.main_button_artist_notification)
        main_button_game_notification = findViewById(R.id.main_button_game_notification)
        val main_button_tvshow_library = findViewById<Button>(R.id.main_button_tv)
        val main_button_movie_library = findViewById<Button>(R.id.main_button_movie)
        val main_button_artist_library = findViewById<Button>(R.id.main_button_artist)
        val main_button_game_library = findViewById<Button>(R.id.main_button_game)
        val tmdbImage = findViewById<ImageView>(R.id.badge_tmdb)
        val musicbrainzImage = findViewById<ImageView>(R.id.badge_musicbrainz)
        val rawgImage = findViewById<ImageView>(R.id.badge_rawg)
        prepButton(main_button_tvshow_find, ActivityFind::class.java, TVSHOW)
        prepButton(main_button_movie_find, ActivityFind::class.java, MOVIE)
        prepButton(main_button_artist_find, ActivityFind::class.java, ARTIST)
        prepButton(main_button_game_find, ActivityFind::class.java, GAME)
        prepButton(main_button_tvshow_library, ActivityLibrary::class.java, TVSHOW)
        prepButton(main_button_movie_library, ActivityLibrary::class.java, MOVIE)
        prepButton(main_button_artist_library, ActivityLibrary::class.java, ARTIST)
        prepButton(main_button_game_library, ActivityLibrary::class.java, GAME)
        prepButton(main_button_tvshow_notification, ActivityUnplayed::class.java, TVSHOW)
        prepButton(main_button_movie_notification, ActivityUnplayed::class.java, MOVIE)
        prepButton(main_button_artist_notification, ActivityUnplayed::class.java, ARTIST)
        prepButton(main_button_game_notification, ActivityUnplayed::class.java, GAME)
        tmdbImage.setOnClickListener { view: View? -> startActivity(getWebPageIntent("https://www.themoviedb.org/")) }
        musicbrainzImage.setOnClickListener { view: View? -> startActivity(getWebPageIntent("https://musicbrainz.org/")) }
        rawgImage.setOnClickListener { view: View? -> startActivity(getWebPageIntent("https://rawg.io/")) }
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
        main_button_tvshow_notification!!.text = getNotificationNumber(numEpisodes)
        main_button_tvshow_notification!!.background = tvNotifyBG
        main_button_movie_notification!!.background = movieNotifyBG
        main_button_movie_notification!!.text = getNotificationNumber(numMovies)
        main_button_artist_notification!!.background = albumNotifyBG
        main_button_artist_notification!!.text = getNotificationNumber(numAlbums)
        main_button_game_notification!!.background = gameNotifyBG
        main_button_game_notification!!.text = getNotificationNumber(numGames)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_about).isEnabled = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_settings) {
            val intent = Intent(this, ActivitySettings::class.java)
            startActivity(intent)
            true
        } else if (item.itemId == R.id.action_contact) {
            startActivity(contactEmailIntent)
            true
        } else if (item.itemId == R.id.action_refresh) {
            UpdateMediaItem(baseContext, progressBar, tvShowDatabase, tvShowClient).execute(*asArray(tvShowDatabase!!.readAllItems()))
            UpdateMediaItem(baseContext, progressBar, movieDatabase, movieClient).execute(*asArray(movieDatabase!!.readAllItems()))
            UpdateMediaItem(baseContext, progressBar, artistDatabase, artistClient).execute(*asArray(artistDatabase!!.readAllItems()))
            UpdateMediaItem(baseContext, progressBar, gameDatabase, gameClient).execute(*asArray(gameDatabase!!.readAllItems()))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun asArray(items: List<MediaItem>): Array<MediaItem> {
        var itemsArray = arrayOfNulls<MediaItem>(items.size)
        return items.toTypedArray()
//        return items.toArray(itemsArray)
    }
}