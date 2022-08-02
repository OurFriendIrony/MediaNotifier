package uk.co.ourfriendirony.medianotifier.general

import android.content.Intent
import android.net.Uri

object IntentGenerator {
    @JvmStatic
    fun getWebPageIntent(webAddress: String?): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(webAddress)
        return intent
    }

    @JvmStatic
    val contactEmailIntent: Intent
        get() {
            val emailToAddress = "ourfriendirony@gmail.com"
            val emailSubject = "[MediaNotifier] I've got this to say about your app..."
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "plain/text"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailToAddress))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            return emailIntent
        }
}