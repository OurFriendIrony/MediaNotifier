package uk.co.ourfriendirony.medianotifier.general;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

public class IntentGenerator {
    @NonNull
    public static Intent getWebPageIntent(String webAddress) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(webAddress));
        return intent;
    }

    public static Intent getContactEmailIntent() {
        String emailToAddress = "ourfriendirony@gmail.com";
        String emailSubject = "[MediaNotifier] I've got this to say about your app...";
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailToAddress});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);

        return emailIntent;
    }
}
