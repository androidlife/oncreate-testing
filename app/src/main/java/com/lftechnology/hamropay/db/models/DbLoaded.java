package com.lftechnology.hamropay.db.models;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * This info can be added to shared prefs
 * but right now as we are playing with Realm, just putting this class
 * to check whether database has been loaded from assets or not
 */
public class DbLoaded extends RealmObject {
    @Ignore
    public static final int ID = 9;
    @PrimaryKey
    private int id;
    private boolean isDbLoaded = false;

    @Ignore
    public static final String DB_LOADED = "isDbLoaded", ID_VALUE = "id";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDbLoaded() {
        return isDbLoaded;
    }

    public void setDbLoaded(boolean dbLoaded) {
        isDbLoaded = dbLoaded;
    }
}
