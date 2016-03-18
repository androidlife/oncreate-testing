package com.lftechnology.hamropay.db.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 */

public class UserTemp extends RealmObject {


    private long userId;
    private String userName;
    private String userEmail;
    private String phoneNumber;
    private String localPhoto;
    private int resIcon;
    private int type = User.TYPE_NORMAL;
    private int frequency;
    private byte[] qrCode;
    private RealmList<Transaction> transactionRealmList;
    private UserBank userBank;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocalPhoto() {
        return localPhoto;
    }

    public void setLocalPhoto(String localPhoto) {
        this.localPhoto = localPhoto;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public RealmList<Transaction> getTransactionRealmList() {
        return transactionRealmList;
    }


    public void setTransactionRealmList(RealmList<Transaction> transactionRealmList) {
        this.transactionRealmList = transactionRealmList;
    }

    public UserBank getUserBank() {
        return userBank;
    }

    public void setUserBank(UserBank userBank) {
        this.userBank = userBank;
    }
}
