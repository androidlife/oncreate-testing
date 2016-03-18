package com.lftechnology.hamropay.db.models;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.UserCardRealmProxy;

/**
 */
@Parcel(implementations = {UserCardRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {UserCard.class})
public class UserCard extends RealmObject {
    private int cardId;
    private long cardNumber;
    private int bankId;
    private long userId;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
