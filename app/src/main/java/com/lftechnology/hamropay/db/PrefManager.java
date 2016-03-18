package com.lftechnology.hamropay.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.lftechnology.hamropay.HamroPayApplication;
import com.lftechnology.hamropay.db.models.User;

/**
 */
public class PrefManager {
    private static final String USER_HOST = "userHost";
    static volatile PrefManager singleton = null;

    public static final String PREF_NAME = "HamroPayPref";
    private SharedPreferences sharedPreferences;

    private static final String USER_NAME = "userName", USER_PHOTO = "userPhoto", USER_ID = "userId",
            USER_EMAIL = "userEmail", USER_PHONE = "userPhone";


    private PrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static PrefManager getInstance() {
        if (singleton == null) {
            synchronized (PrefManager.class) {
                if (singleton == null)
                    singleton = new PrefManager(HamroPayApplication.getContext());
            }
        }
        return singleton;
    }

    public void registerNewUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, user.getUserName());
        editor.putLong(USER_ID, user.getUserId());
        editor.putString(USER_EMAIL, user.getUserEmail());
        editor.putString(USER_PHONE, user.getPhoneNumber());
        editor.putString(USER_PHOTO, user.getLocalPhoto());
        editor.apply();
    }

    public User getRegisteredUser() {
        User user = new User();
        if (sharedPreferences.getLong(USER_ID, -1) == -1 || sharedPreferences.getString(USER_PHONE, null) == null)
            return null;
        user.setUserId(sharedPreferences.getLong(USER_ID, 0));
        user.setUserEmail(sharedPreferences.getString(USER_EMAIL, ""));
        user.setUserName(sharedPreferences.getString(USER_NAME, ""));
        user.setLocalPhoto(sharedPreferences.getString(USER_PHOTO, ""));
        user.setPhoneNumber(sharedPreferences.getString(USER_PHONE, ""));
        return user;
    }

    public boolean isHost() {
        return sharedPreferences.getBoolean(USER_HOST, false);
    }

    public void setHost(boolean hostEnabled) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(USER_HOST, hostEnabled);
        editor.apply();
    }

    public long getRegisteredUserId() {
        return sharedPreferences.getLong(USER_ID, -1);
    }
}
