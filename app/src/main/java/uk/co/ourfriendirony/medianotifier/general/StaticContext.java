package uk.co.ourfriendirony.medianotifier.general;

import android.app.Application;
import android.content.Context;

public class StaticContext extends Application {
    private static Context context;

    public static Context getStaticContext() {
        return StaticContext.context;
    }

    public void onCreate() {
        super.onCreate();
        StaticContext.context = getApplicationContext();
    }
}
