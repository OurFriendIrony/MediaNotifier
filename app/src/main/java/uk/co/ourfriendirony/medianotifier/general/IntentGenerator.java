package uk.co.ourfriendirony.medianotifier.general;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

public class IntentGenerator {
    @NonNull
    public static Intent getWebPageIntent(String webAddress) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(webAddress));
        return intent;
    }
}
