package com.example.appfirmadigital;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SignaturesDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "signatures.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "signatures";

    // Columnas de la tabla
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DIGITAL_SIGNATURE = "digital_signature";

    public SignaturesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DIGITAL_SIGNATURE + " BLOB);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizaciones de la base de datos, si es necesario
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addSignature(String description, byte[] digitalSignature) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DIGITAL_SIGNATURE, digitalSignature);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getAllSignatures() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
