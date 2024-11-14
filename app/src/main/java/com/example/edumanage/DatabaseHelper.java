package com.example.edumanage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public static final String TABLE_TEACHER = "Added_teachers_info";
    public static final String COL_TNAME = "Teacher_Name";
    public static final String COL_TUSERNAME = "Teacher_Username";
    public static final String COL_TQUALIFICATION = "Teacher_Qualification";
    public static final String COL_TDESIGNATION= "Teacher_Designation";
    public static final String COL_TEACHER_IMAGE_URI = "Teacher_Image_Uri";

    ///////////////////////////////////////////////////////////////////////////
    public static final String TABLE_REGISTERTEACHER = "Registered_teachers";
    public static final String COL_RTNAME = "Teacher_Name";
    public static final String COL_RTUSERNAME = "Teacher_Username";
    public static final String COL_RTEMAIL = "Teacher_Email";
    public static final String COL_RTADDRESS = "Teacher_Address";
    public static final String COL_RTDOB = "Teacher_DOB";
    public static final String COL_RTGENDER = "Teacher_Gender";
    public static final String COL_RTMOBILE = "Teacher_Mobile";
    public static final String COL_RTPASS = "Teacher_Password";

    ///////////////////////////////////////////////////////////////////////////
    public static final String TABLE_STUDENT = "Added_students_info";
    public static final String COL_SNAME = "Student_Name";
    public static final String COL_SUSERNAME = "Student_Username";
    public static final String COL_STUDENT_IMAGE_URI = "Student_Image_Uri";

    ///////////////////////////////////////////////////////////////////////////
    public static final String TABLE_REGISTERSTUDENT = "Registered_students";
    public static final String COL_RSNAME = "Student_Name";
    public static final String COL_RSUSERNAME = "Student_Username";
    public static final String COL_RSFATHERNAME = "Father_Name";
    public static final String COL_RSDOB = "Student_DOB";
    public static final String COL_RSGENDER = "Student_Gender";
    public static final String COL_RSEMAIL = "Student_Email";
    public static final String COL_RSADDRESS = "Student_Address";
    public static final String COL_RSMOBILE = "Student_Mobile";
    public static final String COL_RSPASS = "Student_Password";



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

        String createTeacherTable = "CREATE TABLE " + TABLE_TEACHER + " (" +
                COL_TNAME + " TEXT, " +
                COL_TUSERNAME + " TEXT PRIMARY KEY, " +
                COL_TQUALIFICATION + " TEXT, " +
                COL_TDESIGNATION + " TEXT, " +
                COL_TEACHER_IMAGE_URI + " BLOB)";
        db.execSQL(createTeacherTable);

        String createRegisterTeacherTable = "CREATE TABLE " + TABLE_REGISTERTEACHER + " (" +
                COL_RTNAME + " TEXT, " +
                COL_RTUSERNAME + " TEXT PRIMARY KEY, " +
                COL_RTEMAIL + " TEXT, " +
                COL_RTADDRESS + " TEXT, " +
                COL_RTDOB + " TEXT, " +
                COL_RTGENDER + " TEXT, " +
                COL_RTMOBILE + " TEXT, " +
                COL_RTPASS + " TEXT)";
        db.execSQL(createRegisterTeacherTable);

        String createStudentTable = "CREATE TABLE " + TABLE_STUDENT + " (" +
                COL_SNAME + " TEXT, " +
                COL_SUSERNAME + " TEXT PRIMARY KEY, " +
                COL_STUDENT_IMAGE_URI + " BLOB)";
        db.execSQL(createStudentTable);

        String createRegisterStudentTable = "CREATE TABLE " + TABLE_REGISTERSTUDENT + " (" +
                COL_RSNAME + " TEXT, " +
                COL_RSUSERNAME + " TEXT PRIMARY KEY, " +
                COL_RSFATHERNAME + " TEXT, " +
                COL_RSDOB + " TEXT, " +
                COL_RSGENDER + " TEXT, " +
                COL_RSEMAIL + " TEXT, " +
                COL_RSADDRESS + " TEXT, " +
                COL_RSMOBILE + " TEXT, " +
                COL_RSPASS + " TEXT)";
        db.execSQL(createRegisterStudentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTERADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTERTEACHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTERSTUDENT);

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

    public boolean addTeacher(String name, String username, String qualification, String designation, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TNAME, name);
        contentValues.put(COL_TUSERNAME, username);
        contentValues.put(COL_TQUALIFICATION, qualification);
        contentValues.put(COL_TDESIGNATION, designation);
        contentValues.put(COL_TEACHER_IMAGE_URI, imageByteArray);

        long teacher = db.insert(TABLE_TEACHER, null, contentValues);
        Log.d("DatabaseHelper", "addTeacher: result = " + teacher);  // Logging the result

        return teacher != -1;
    }

    public boolean addStudent(String name, String username, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SNAME, name);
        contentValues.put(COL_SUSERNAME, username);
        contentValues.put(COL_STUDENT_IMAGE_URI, imageByteArray);

        long student = db.insert(TABLE_STUDENT, null, contentValues);
        Log.d("DatabaseHelper", "addStudent: result = " + student);  // Logging the result

        return student != -1;
    }

    public boolean registerTeacher(String tName, String tUsername, String tEmail, String tAddress, String tDob, String tGender, String tMobile, String tPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RTNAME, tName);
        contentValues.put(COL_RTUSERNAME, tUsername);
        contentValues.put(COL_RTEMAIL, tEmail);
        contentValues.put(COL_RTADDRESS, tAddress);
        contentValues.put(COL_RTDOB, tDob);
        contentValues.put(COL_RTGENDER, tGender);
        contentValues.put(COL_RTMOBILE, tMobile);
        contentValues.put(COL_RTPASS, tPass);

        long regTeacher = db.insert(TABLE_REGISTERTEACHER, null, contentValues);
        Log.d("DatabaseHelper", "addTeacher: result = " + regTeacher);  // Logging the result

        return regTeacher != -1;
    }

    public boolean registerStudent(String stName, String stUsername, String stFather, String stDob, String stGender, String stEmail, String stAddress, String stMobile, String stPass) {
        SQLiteDatabase db = this.getWritableDatabase();  //writable database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RSNAME, stName);
        contentValues.put(COL_RSUSERNAME, stUsername);
        contentValues.put(COL_RSFATHERNAME, stFather);
        contentValues.put(COL_RSDOB, stDob);
        contentValues.put(COL_RSGENDER, stGender);
        contentValues.put(COL_RSEMAIL, stEmail);
        contentValues.put(COL_RSADDRESS, stAddress);
        contentValues.put(COL_RSMOBILE, stMobile);
        contentValues.put(COL_RSPASS, stPass);

        long updateT = db.insert(TABLE_REGISTERSTUDENT,null, contentValues);  //inserting
        Log.d("DatabaseHelper", "updateTeacher: result = " + updateT);  // Logging the result

        //updateT(result) value, if inserted, then "row number", if not inserted, then -1
        return updateT != -1;
    }

    public School getSchoolDetails(String schoolCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SCHOOL + " WHERE " + COL_SCHOOLCODE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{schoolCode});

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(COL_SCHOOLNAME);
            int codeIndex = cursor.getColumnIndex(COL_SCHOOLCODE);
            int categoryIndex = cursor.getColumnIndex(COL_SCHOOLSTANDARD);
            int addressIndex = cursor.getColumnIndex(COL_SCHOOLADDRESS);

            if (nameIndex >= 0 && codeIndex >= 0 && categoryIndex >= 0 && addressIndex >= 0) {
                String name = cursor.getString(nameIndex);
                String code = cursor.getString(codeIndex);
                String category = cursor.getString(categoryIndex);
                String address = cursor.getString(addressIndex);
                cursor.close();
                return new School(name, code, category, address);
            }
            cursor.close();
        }
        return null;
    }
    public int getTotalTeachers(String schoolCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_TEACHER, null);

        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } else {
            return 0;
        }
    }
    public int getTotalStudents(String schoolCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_STUDENT, null);

        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } else {
            return 0;
        }
    }

    public byte[] getStudentImage(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENT, new String[]{COL_STUDENT_IMAGE_URI}, COL_SUSERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_STUDENT_IMAGE_URI);
            if (columnIndex != -1) {
                byte[] imageBytes = cursor.getBlob(columnIndex);
                cursor.close();
                return imageBytes;
            } else {
                Log.e("DatabaseHelper", "Column not found: " + COL_STUDENT_IMAGE_URI);
            }
        }
        return null;
    }

    public byte[] getTeacherImage(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHER, new String[]{COL_TEACHER_IMAGE_URI}, COL_TUSERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_TEACHER_IMAGE_URI);
            if (columnIndex != -1) {
                byte[] imageBytes = cursor.getBlob(columnIndex);
                cursor.close();
                return imageBytes;
            } else {
                Log.e("DatabaseHelper", "Column not found: " + COL_TEACHER_IMAGE_URI);
            }
        }
        return null;
    }

    // Create a School class to hold the school details
    public static class School {
        private final String name;
        private final String code;
        private final String category;
        private final String address;

        public School(String name, String code, String category, String address) {
            this.name = name;
            this.code = code;
            this.category = category;
            this.address = address;
        }
        public String getName() {
            return name;
        }
        public String getCode() {
            return code;
        }
        public String getCategory() {
            return category;
        }
        public String getAddress() {
            return address;
        }
    }
}