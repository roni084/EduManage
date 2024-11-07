package com.example.edumanage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "EduManage_DB";  //Database name
    public static final int DATABASE_VERSION = 1;    //Database version

    ////////////////////////////////////////////////////////////////////////////
    public static final String TABLE_REGISTERADMIN = "Admin_info";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "Name";
    public static final String COL_USERNAME = "Username";
    public static final String COL_EMAIL = "Email";
    public static final String COL_MOBILE = "Mobile";
    public static final String COL_PASSWORD = "Password";

    ///////////////////////////////////////////////////////////////////////////
    public static final String TABLE_SCHOOL = "School_info";
    public static final String COL_SCHOOLNAME = "School_Name";
    public static final String COL_SCHOOLCODE = "School_Code";
    public static final String COL_SCHOOLSTANDARD = "School_Standard";
    public static final String COL_SCHOOLADDRESS = "School_Address";

    ///////////////////////////////////////////////////////////////////////////

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRegisterAdminTable = "CREATE TABLE " + TABLE_REGISTERADMIN + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_USERNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_MOBILE + " TEXT, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createRegisterAdminTable);

        String createSchoolTable = "CREATE TABLE " + TABLE_SCHOOL + " (" +
                COL_SCHOOLNAME + " TEXT, " +
                COL_SCHOOLCODE + " TEXT PRIMARY KEY, " +
                COL_SCHOOLSTANDARD + " TEXT, " +
                COL_SCHOOLADDRESS + " TEXT)";
        db.execSQL(createSchoolTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTERADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOL);

        onCreate(db);
    }

    public boolean insertAdmin(String name, String username, String email, String mobile, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_MOBILE, mobile);
        contentValues.put(COL_PASSWORD, password);

        long admin = db.insert(TABLE_REGISTERADMIN, null, contentValues);
        Log.d("DatabaseHelper", "admin: result = " + admin);  // Logging the result

        return admin != -1;
    }

    public boolean insertSchool(String sName, String sCode, String sStandard, String sAddress) {
        SQLiteDatabase db = this.getWritableDatabase();  //writable database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SCHOOLNAME, sName);
        contentValues.put(COL_SCHOOLCODE, sCode);
        contentValues.put(COL_SCHOOLSTANDARD, sStandard);
        contentValues.put(COL_SCHOOLADDRESS, sAddress);

        long school = db.insert(TABLE_SCHOOL, null, contentValues);  //inserting
        Log.d("DatabaseHelper", "insertSchool: result = " + school);  // Logging the result

        //school(result) value, if inserted, then "row number", if not inserted, then -1
        return school != -1;
    }


}