package com.lftechnology.hamropay.db.models;


import org.parceler.Parcel;

import io.realm.BankLocationRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 */
@Parcel(implementations = {BankLocationRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {BankLocation.class})
public class BankLocation extends RealmObject {
    @PrimaryKey
    private int locationId;
    private String locationName;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
