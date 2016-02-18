package com.spree.spree_app;

import android.database.sqlite.SQLiteDatabase;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by A on 2/14/2016.
 */
public class Database {

    public static SQLiteDatabase create_db(){
        SQLiteDatabase db;
        db=openOrCreateDatabase("/data/data/com.spree.spree_app/databases/store.db",null, null);
        return db;
    }
}
