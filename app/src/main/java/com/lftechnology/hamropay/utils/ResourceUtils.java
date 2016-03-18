package com.lftechnology.hamropay.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.lftechnology.hamropay.HamroPayApplication;


/**
 * A wrapper class taking context of {@link HamroPayApplication}
 * and be used by any class in entire application to use
 * this application resources
 */
public class ResourceUtils {

    private static int screenHeightInPx = 0, screenWidthInPx = 0, statusBarHeight = 0, contentHeight = 0, topViewHeight = 0;
    private static boolean isToolbarShown = true;
    private static int thresholdYForVideos = 0;

    /**
     * Taking application context to get resources of the entire app
     */
    private static Resources getResources() {
        return getContext().getResources();
    }

    private static Context getContext() {
        return HamroPayApplication.getContext();
    }

    /**
     * START
     * ##### Methods related to Dimensions
     * #####
     */

    /**
     * Method to convert pixel to density
     */
    public static int pixelsToDp(int pixels) {
        return (int) (pixels / getResources().getDisplayMetrics().density);
    }

    /**
     * Method to convert density to pixel
     */
    public static int dpToPixels(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

    public static int dpToPixels(String dp) {
        return dpToPixels(Integer.valueOf(dp));
    }

    public static float spToPixels(int sp) {
        return (getResources().getDisplayMetrics().scaledDensity * sp);
    }

    /**
     * Method to get dimension from app resource
     */
    public static int getDimension(int dimenId) {
        return getResources().getDimensionPixelSize(dimenId);
    }

    /**
     * END
     * ##### Methods related to Dimensions
     * #####
     */

    public static int getColor(int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }


    /**
     * Method to get string from app resource
     */
    public static String getString(int resId) {
        return getString(resId, null);
    }

    /**
     * Method to fetch string with replaceable content from app resources
     */
    public static String getString(int resId, String... args) {
        return getResources().getString(resId, args);
    }

    /**
     * Get the device screen height in pixels.
     *
     * @return screenHeight in PX.
     */
    public static int getScreenHeight() {
        if (screenHeightInPx == 0) {
            WindowManager windowManager = (WindowManager) HamroPayApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            screenHeightInPx = displayMetrics.heightPixels;
        }
        return screenHeightInPx;
    }

    /**
     * Get the device screen width in pixels.
     *
     * @return screenWidth in PX.
     */
    public static int getScreenWidth() {
        if (screenWidthInPx == 0) {
            WindowManager windowManager = (WindowManager) HamroPayApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            screenWidthInPx = displayMetrics.widthPixels;

        }
        return screenWidthInPx;

    }

    /**
     * Return asset manager from resources
     *
     * @return
     */
    public static AssetManager getAssets() {
        return HamroPayApplication.getContext().getResources().getAssets();
    }

}

