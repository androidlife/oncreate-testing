package com.lftechnology.hamropay.db.models;

import io.realm.annotations.RealmModule;

/**
 */
@RealmModule(classes = {User.class, UserServices.class, Transaction.class, UserBank.class, UserCard.class, Bank.class,BankLocation.class,DbLoaded.class})
public class DbModels {
}
