package com.example.edumanage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterTeacherActivity extends AppCompatActivity {

    private EditText etTeacherDOB;
    private RadioGroup rgTeacherGender;
    private RadioButton rbTeacherGender;

    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Z][a-z]+(?:\\s[A-Z][a-z]+)?");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-z0-9][\\w.-]+@[\\w.-]+\\.[a-z]{2,}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("01[356789]\\d{8}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\W)[\\S]{6,}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_teacher);

        // Use to get all views from XML layout
        EditText etTeacherName = findViewById(R.id.et_r_teacher_name);
        EditText etTeacherUsername = findViewById(R.id.et_r_teacher_username);
        EditText etTeacherCode = findViewById(R.id.et_r_teacher_code);
        EditText etTeacherEmail = findViewById(R.id.et_r_teacher_email);
        EditText etTeacherAddress = findViewById(R.id.et_r_teacher_address);
        etTeacherDOB = findViewById(R.id.et_r_teacher_dob);
        rgTeacherGender = findViewById(R.id.rg_teacher_gender);
        EditText etTeacherMobile = findViewById(R.id.et_r_teacher_phone);
        EditText etTeacherPassword = findViewById(R.id.et_r_teacher_password);
        EditText etTeacherConfirmPassword = findViewById(R.id.et_r_teacher_confirm_password);
        Button btnTeacherRegister = findViewById(R.id.btn_r_teacher_register);
        Button btnTeacherLogin = findViewById(R.id.btn_r_teacher_login);

        etTeacherDOB.setOnClickListener(v -> {
            showDatePickerDialog();
        });


        // If Register button is clicked
        btnTeacherRegister.setOnClickListener(v -> {
            // Take required data from user
            String t_name = etTeacherName.getText().toString().trim();
            String t_username = etTeacherUsername.getText().toString().trim();
            String t_code = etTeacherCode.getText().toString().trim();
            String t_email = etTeacherEmail.getText().toString().trim();
            String t_address = etTeacherAddress.getText().toString().trim();
            String t_dob = etTeacherDOB.getText().toString().trim();
            String t_mobile = etTeacherMobile.getText().toString().trim();
            String t_pass = etTeacherPassword.getText().toString().trim();
            String t_confirmPass = etTeacherConfirmPassword.getText().toString().trim();

            DatabaseHelper dbc = new DatabaseHelper(RegisterTeacherActivity.this);  // Database connection

            // Get selected gender
            int selectedGenderId = rgTeacherGender.getCheckedRadioButtonId();
            if (selectedGenderId != -1) {
                rbTeacherGender = findViewById(selectedGenderId);
                String t_gender = rbTeacherGender.getText().toString();

                // Validate non-empty fields and password match
                if (t_name.isEmpty() || t_username.isEmpty() || t_code.isEmpty() || t_email.isEmpty() || t_address.isEmpty() || t_dob.isEmpty() || t_mobile.isEmpty() || t_pass.isEmpty() || t_confirmPass.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!NAME_PATTERN.matcher(t_name).matches()) {
                    etTeacherName.setError("Invalid name! Only letters allowed.");
                    return;
                }
                if (!EMAIL_PATTERN.matcher(t_email).matches()) {
                    etTeacherEmail.setError("Invalid email address!");
                    return;
                }
                if (!PHONE_PATTERN.matcher(t_mobile).matches()) {
                    etTeacherMobile.setError("Invalid phone format. Try again.");
                    return;
                }
                if (!PASSWORD_PATTERN.matcher(t_pass).matches()) {
                    etTeacherPassword.setError("Password must be at least 6 characters, include an uppercase letter and a special character.");
                    return;
                }
                if (!t_pass.equals(t_confirmPass)) {
                    etTeacherConfirmPassword.setError("Passwords do not match! Try again.");
                    return;
                }

                if (checkTeacher(dbc, t_name, t_username, t_code)) {
                    boolean teacherRegistered = dbc.registerTeacher(t_name, t_username, t_email, t_address, t_dob, t_gender, t_mobile, t_pass);  // Data passed to insertTeacher method
                    Log.d("RegisterTeacherActivity", "teacherRegistered: " + teacherRegistered);  // Log the result
                    if (teacherRegistered) {
                        Toast.makeText(RegisterTeacherActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterTeacherActivity.this, LoginActivity.class);  // Assuming LoginActivity is the activity after registered as teacher
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterTeacherActivity.this, "Registration failed! Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterTeacherActivity.this, "User not found in database", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            }
        });


        // If Login button is clicked
        btnTeacherLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterTeacherActivity.this, LoginActivity.class);  // LoginActivity is the activity for login as teacher after Login button clicked
            startActivity(intent);
        });
    }

    private boolean checkTeacher(DatabaseHelper dbc, String tName, String tUsername, String tCode) {
        SQLiteDatabase db = dbc.getReadableDatabase();  // Readable database
        Cursor teacherCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_TEACHER + " WHERE " + DatabaseHelper.COL_TNAME + "=? AND " + DatabaseHelper.COL_TUSERNAME + "=?", new String[]{tName, tUsername});
        Cursor schoolCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_SCHOOL + " WHERE " + DatabaseHelper.COL_SCHOOLCODE + "=?", new String[]{tCode});

        // Move the cursor to the first row
        boolean teacherExists = teacherCursor.moveToFirst();
        boolean schoolExists = schoolCursor.moveToFirst();

        // Close the Cursor
        teacherCursor.close();
        schoolCursor.close();

        return teacherExists && schoolExists;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                etTeacherDOB.setText(date);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

}