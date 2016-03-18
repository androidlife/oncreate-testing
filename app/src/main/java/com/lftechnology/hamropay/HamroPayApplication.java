package com.lftechnology.hamropay;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.lftechnology.hamropay.db.DbManager;

import timber.log.Timber;

/**
 */
public class HamroPayApplication extends Application {


    private static String FONT_PATH = "fonts/black_jack.ttf";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        Timber.plant(new Timber.DebugTree());

        DbManager.getInstance().initDbConfig(this);
    }

    public static Context getContext() {
        return context;
    }

    public static Typeface getBlackJackFont() {
        // Loading Font Face
        return Typeface.createFromAsset(getContext().getAssets(), FONT_PATH);
    }

}
