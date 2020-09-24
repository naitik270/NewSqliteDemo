package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.Classes.ClsRegistrationGetSet;

import java.util.ArrayList;
import java.util.List;

public class DataBaseOperations {

    private static DBHelper mDbHelper;
    private static AppExecutor mAppExecutor;
    private List<ClsRegistrationGetSet> mList;

    static long rowsDeleted;

    public DataBaseOperations(Context context) {
        mDbHelper = new DBHelper(context);
        mAppExecutor = new AppExecutor();
    }

    public void insert(String reg_photo, String reg_name, String reg_phone, String reg_email) {
        mAppExecutor.diskIO().execute(() -> {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(RegistrationColumn.reg_photo, reg_photo);
            contentValues.put(RegistrationColumn.reg_name, reg_name);
            contentValues.put(RegistrationColumn.reg_phone, reg_phone);
            contentValues.put(RegistrationColumn.reg_email, reg_email);

            long count = db.insert(RegistrationColumn.TABLE_NAME, null, contentValues);
            Log.i("--insert--", "count: " + count);
            db.close();

        });

    }


    public ArrayList<ClsRegistrationGetSet> getAllMembers() {

        ArrayList<ClsRegistrationGetSet> arrList = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + RegistrationColumn.TABLE_NAME + "", null);


        if (cursor.moveToFirst()) {
            int columnID = cursor.getColumnIndex(RegistrationColumn.ID);
            int regPhoto = cursor.getColumnIndex(RegistrationColumn.reg_photo);
            int regName = cursor.getColumnIndex(RegistrationColumn.reg_name);
            int regPhone = cursor.getColumnIndex(RegistrationColumn.reg_phone);
            int regEmail = cursor.getColumnIndex(RegistrationColumn.reg_email);
            do {
                int id = cursor.getInt(columnID);
                String photo = cursor.getString(regPhoto);
                String name = cursor.getString(regName);
                String phone = cursor.getString(regPhone);
                String email = cursor.getString(regEmail);
                arrList.add(new ClsRegistrationGetSet(id, photo, name, phone, email));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
            Log.i("--mList--", "List: " + arrList);
        }

        return arrList;
    }

    public static long deleteRecord(int id) {

        mAppExecutor.diskIO().execute(() -> {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            String reg_id = RegistrationColumn.ID + "=?";
            String[] selectionArgs = {String.valueOf(id)};
            rowsDeleted = db.delete(RegistrationColumn.TABLE_NAME, reg_id, selectionArgs);
            db.close();
        });

        return rowsDeleted;
    }

    public ClsRegistrationGetSet fetchData(int _id) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ClsRegistrationGetSet obj = new ClsRegistrationGetSet();

        String[] columns = new String[]{RegistrationColumn._ID, RegistrationColumn.reg_photo,
                RegistrationColumn.reg_name, RegistrationColumn.reg_phone, RegistrationColumn.reg_email};

        String reg_id = RegistrationColumn.ID + "=?";
        String[] selectionArgs = {String.valueOf(_id)};

        Cursor cursor = db.query(RegistrationColumn.TABLE_NAME, columns, reg_id,
                selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int columnID = cursor.getColumnIndex(RegistrationColumn.ID);
            int regPhoto = cursor.getColumnIndex(RegistrationColumn.reg_photo);
            int regName = cursor.getColumnIndex(RegistrationColumn.reg_name);
            int regPhone = cursor.getColumnIndex(RegistrationColumn.reg_phone);
            int regEmail = cursor.getColumnIndex(RegistrationColumn.reg_email);
            do {
                int id = cursor.getInt(columnID);
                String photo = cursor.getString(regPhoto);
                String name = cursor.getString(regName);
                String phone = cursor.getString(regPhone);
                String email = cursor.getString(regEmail);
                obj = new ClsRegistrationGetSet(id, photo, name, phone, email);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return obj;
    }


    public static boolean checkAlreadyExist(String tableName, String dbField, String fieldValue) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String Query = "Select * from " + tableName + " where " + dbField + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

}
