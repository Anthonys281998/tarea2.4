package com.example.appfirmadigital;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnection {

    private static final String DATABASE_NAME = "signatures.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseConnection instance;
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    private DatabaseConnection(Context context) {
        dbHelper = new SignaturesDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public static synchronized DatabaseConnection getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseConnection(context.getApplicationContext());
        }
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
