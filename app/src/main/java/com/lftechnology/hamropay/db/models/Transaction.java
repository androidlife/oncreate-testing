package com.lftechnology.hamropay.db.models;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.TransactionRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 */
@Parcel(implementations = {TransactionRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {Transaction.class})
public class Transaction extends RealmObject {
    public static final int TYPE_SEND_MONEY = 0x1, TYPE_RECEIVE_MONEY = 0x2, TYPE_MESSAGE = 0x3;
    @PrimaryKey
    private long transactionId;
    private long userId;
    private long serviceId;
    private int transactionType;
    private boolean isTransactionComplete;
    private String transactionDetail;

    private String snippet;
    private String serviceProviderName;

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isTransactionComplete() {
        return isTransactionComplete;
    }

    public void setTransactionComplete(boolean transactionComplete) {
        isTransactionComplete = transactionComplete;
    }

    public String getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(String transactionDetail) {
        this.transactionDetail = transactionDetail;
    }
}
