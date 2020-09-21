package com.example.myapplication.Database;

import android.provider.BaseColumns;

/**
 * Created by acer on 08-02-2018.
 */

public final class RegistrationColumn implements BaseColumns {

    public static final String DATABASE_NAME = "my.db";
    public static final String TABLE_NAME ="tbl_reg";
    public static final String ID = BaseColumns._ID;
    public static final String reg_photo = "reg_photo";
    public static final String reg_name = "reg_name";
    public static final String reg_phone = "reg_phone";
    public static final String reg_email = "reg_email";

}
