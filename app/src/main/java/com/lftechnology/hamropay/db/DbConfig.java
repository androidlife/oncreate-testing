package com.lftechnology.hamropay.db;

import android.content.Context;

import com.lftechnology.hamropay.db.models.DbModels;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 */
public class DbConfig {
    private static RealmConfiguration realmConfiguration;
    private static final int SCHEMA_VERSION = 7;
    public static final String REALM_NAME = "RealmDb.realm";

    protected static void initDbConfig(Context context) {
        realmConfiguration = new RealmConfiguration.Builder(context)
                .name(REALM_NAME)
                .schemaVersion(SCHEMA_VERSION)
                .setModules(new DbModels())
                .migration(dbMigration)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    protected static RealmConfiguration getDbConfig() {
        return realmConfiguration;
    }

    private static RealmMigration dbMigration = new RealmMigration() {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            //do all the update and other stuff
            RealmSchema realmSchema = realm.getSchema();
            if (oldVersion == 1) {
                realmSchema.create("BankLocation")
                        .addField("locationId", int.class, FieldAttribute.PRIMARY_KEY)
                        .addField("locationName", String.class);
                realmSchema.get("UserBank")
                        .addField("locationId", int.class);
                realmSchema.get("Bank")
                        .removeField("bankLocation");
                oldVersion++;
            }

            if (oldVersion == 2) {
                realmSchema.create("DbLoaded")
                        .addField("id", int.class, FieldAttribute.PRIMARY_KEY)
                        .addField("isDbLoaded", boolean.class);
                oldVersion++;
            }

            if (oldVersion == 3) {
                realmSchema.get("User")
                        .addRealmListField("transactionRealmList", realmSchema.get("Transaction"));
                oldVersion++;
            }

            if (oldVersion == 4) {
                realmSchema.get("User")
                        .addRealmObjectField("userBank", realmSchema.get("UserBank"));
                oldVersion++;
            }

            if (oldVersion == 5) {
                realmSchema.get("Transaction")
                        .addField("snippet", String.class)
                        .addField("serviceProviderName", String.class);
                oldVersion++;
            }

            if (oldVersion == 6) {
                realmSchema.get("UserBank")
                        .addField("ebankUname", String.class)
                        .addField("ebankPwd", String.class);
                oldVersion++;
            }
        }
    };
}
