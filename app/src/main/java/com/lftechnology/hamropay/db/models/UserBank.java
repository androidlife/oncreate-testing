package com.lftechnology.hamropay.db.models;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.UserBankRealmProxy;

/**
 */
@Parcel(implementations = {UserBankRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {UserBank.class})
public class UserBank extends RealmObject {
    private int bankId;
    private long accountNumber;
    private long userId;
    private int locationId;

    private String ebankUname;
    private String ebankPwd;

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getEbankUname() {
        return ebankUname;
    }

    public void setEbankUname(String ebankUname) {
        this.ebankUname = ebankUname;
    }

    public String getEbankPwd() {
        return ebankPwd;
    }

    public void setEbankPwd(String ebankPwd) {
        this.ebankPwd = ebankPwd;
    }
}
