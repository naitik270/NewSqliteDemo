package com.example.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DataBaseVersion = 1;

    public DBHelper(Context context) {
        super(context, RegistrationColumn.DATABASE_NAME, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + RegistrationColumn.TABLE_NAME + "("
                + RegistrationColumn.ID + " INTEGER PRIMARY KEY, "
                + RegistrationColumn.reg_photo + " TEXT, "
                + RegistrationColumn.reg_name + " TEXT, "
                + RegistrationColumn.reg_phone + " TEXT, "
                + RegistrationColumn.reg_email + " TEXT " + ")";
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
