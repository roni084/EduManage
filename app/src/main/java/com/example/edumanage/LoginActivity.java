package com.example.edumanage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //use to get all View from xml layout
        EditText etUsername = findViewById(R.id.et_username);
        EditText etPassword = findViewById(R.id.et_password);
        EditText etCode = findViewById(R.id.et_code);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);

        //If Login button clicked
        btnLogin.setOnClickListener(v -> {
            //takes required data from user
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String code = etCode.getText().toString();

            //Checking non-empty fields
            if (username.isEmpty() || password.isEmpty() || code.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter all info", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseHelper dbc = new DatabaseHelper(LoginActivity.this);

                //checking if user is admin
                if(checkAdminLogin(dbc, username, password, code)){
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);   //Assuming AdminHomeActivity is the activity after login as admin
                    intent.putExtra("admin_username", username);
                    intent.putExtra("school_code", code);
                    startActivity(intent);
                }
                //checking if user is teacher
                else if (checkTeacherLogin(dbc, username, password, code)) {
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, TeacherActivity.class);   //Assuming AdminHomeActivity is the activity after login as admin
                    intent.putExtra("teacher_username", username);
                    intent.putExtra("school_code", code);
                    startActivity(intent);
                }
                //checking if user is student
                else if(checkStudentLogin(dbc, username, password, code)){
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, StudentActivity.class);   //Assuming AdminHomeActivity is the activity after login as admin
                    intent.putExtra("student_username", username);
                    intent.putExtra("school_code", code);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed! Check your credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //If Register button clicked
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    //check data for admin login
    private boolean checkAdminLogin(DatabaseHelper dbc, String username, String password, String code) {
        SQLiteDatabase db = dbc.getReadableDatabase();  //Readable database
        Cursor adminCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_REGISTERADMIN + " WHERE " + DatabaseHelper.COL_USERNAME + "=? AND " + DatabaseHelper.COL_PASSWORD + "=?", new String[]{username, password});
        Cursor schoolCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_SCHOOL + " WHERE " + DatabaseHelper.COL_SCHOOLCODE + "=?", new String[]{code});

        //Move the cursor to the first row.
        boolean adminExists = adminCursor.moveToFirst();
        boolean schoolExists = schoolCursor.moveToFirst();

        //Closes the Cursor
        adminCursor.close();
        schoolCursor.close();

        return adminExists && schoolExists;  //This method will return false if the cursor is empty.
    }
    //check data for teacher login
    private boolean checkTeacherLogin(DatabaseHelper dbc, String username, String password, String code) {
        SQLiteDatabase db = dbc.getReadableDatabase();  //Readable database
        Cursor teacherCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_REGISTERTEACHER + " WHERE " + DatabaseHelper.COL_RTUSERNAME + "=? AND " + DatabaseHelper.COL_RTPASS + "=?", new String[]{username, password});
        Cursor schoolCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_SCHOOL + " WHERE " + DatabaseHelper.COL_SCHOOLCODE + "=?", new String[]{code});

        //Move the cursor to the first row.
        boolean teacherExists = teacherCursor.moveToFirst();
        boolean schoolExists = schoolCursor.moveToFirst();

        //Closes the Cursor
        teacherCursor.close();
        schoolCursor.close();

        return teacherExists && schoolExists;  //This method will return false if the cursor is empty.
    }
    //check data for student login
    private boolean checkStudentLogin(DatabaseHelper dbc, String username, String password, String code) {
        SQLiteDatabase db = dbc.getReadableDatabase();  //Readable database
        Cursor studentCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_REGISTERSTUDENT + " WHERE " + DatabaseHelper.COL_RSUSERNAME + "=? AND " + DatabaseHelper.COL_RSPASS + "=?", new String[]{username, password});
        Cursor schoolCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_SCHOOL + " WHERE " + DatabaseHelper.COL_SCHOOLCODE + "=?", new String[]{code});

        //Move the cursor to the first row.
        boolean studentExists = studentCursor.moveToFirst();
        boolean schoolExists = schoolCursor.moveToFirst();

        //Closes the Cursor
        studentCursor.close();
        schoolCursor.close();

        return studentExists && schoolExists;  //This method will return false if the cursor is empty.
    }
}
