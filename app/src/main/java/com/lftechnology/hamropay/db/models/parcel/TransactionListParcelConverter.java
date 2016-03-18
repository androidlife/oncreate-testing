package com.lftechnology.hamropay.db.models.parcel;

import android.os.Parcel;

import com.lftechnology.hamropay.db.models.Transaction;

import org.parceler.Parcels;

/**
 */
public class TransactionListParcelConverter extends RealmListParcelConverter<Transaction> {
    @Override
    public void itemToParcel(Transaction input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Transaction itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Transaction.class.getClassLoader()));
    }
}
