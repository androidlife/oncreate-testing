package com.lftechnology.hamropay.db.models;

import org.parceler.Parcel;

import io.realm.BankRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 */
@Parcel(implementations = {BankRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {Bank.class})
public class Bank extends RealmObject {
    @PrimaryKey
    private int bankId;
    private String bankName;
    private int backIcon;
//    private String bankLocation;

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getBackIcon() {
        return backIcon;
    }

    public void setBackIcon(int backIcon) {
        this.backIcon = backIcon;
    }

//    public String getBankLocation() {
//        return bankLocation;
//    }
//
//    public void setBankLocation(String bankLocation) {
//        this.bankLocation = bankLocation;
//    }
}
