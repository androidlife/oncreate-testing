package com.lftechnology.hamropay.db.models;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.UserServicesRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 */
@Parcel(implementations = {UserServicesRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {UserServices.class})
public class UserServices extends RealmObject {
    @PrimaryKey
    private long serviceId;
    private String serviceName;
    private long userId;

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
