package com.example.gzde.credit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "CREDIT";

    // Table columns
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String CREDIT = "credit";

    // Database Information
    static final String DB_NAME = "BANK_CREDIT.DB";

    // database version
    static final int DB_VERSION = 1;

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, " + CREDIT + " DOUBLE, "
            + DATE + " DATE" + ")";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}