package com.example.edumanage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterTeacherActivity extends AppCompatActivity {

    private EditText etTeacherDOB;

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
        EditText etTeacherGender = findViewById(R.id.et_r_teacher_gender);
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
            String t_gender = etTeacherGender.getText().toString().trim();
            String t_mobile = etTeacherMobile.getText().toString().trim();
            String t_pass = etTeacherPassword.getText().toString().trim();
            String t_confirmPass = etTeacherConfirmPassword.getText().toString().trim();

            // Checking password and non-empty fields
            if (!t_name.isEmpty() && !t_username.isEmpty() && !t_code.isEmpty() && !t_dob.isEmpty() && !t_gender.isEmpty() && !t_email.isEmpty() && !t_address.isEmpty() && !t_mobile.isEmpty()) {
                Toast.makeText(RegisterTeacherActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterTeacherActivity.this, LoginActivity.class);  // Assuming LoginActivity is the activity after registered as teacher
                startActivity(intent);
            }
        });
        // If Login button is clicked
        btnTeacherLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterTeacherActivity.this, LoginActivity.class);  // LoginActivity is the activity for login as teacher after Login button clicked
            startActivity(intent);
        });
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