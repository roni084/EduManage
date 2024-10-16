package com.example.edumanage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.Calendar;

public class RegisterStudentActivity extends AppCompatActivity {

    private EditText etStudentDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        // Use to get all views from XML layout
        EditText etStudentName = findViewById(R.id.et_r_student_name);
        EditText etStudentUsername = findViewById(R.id.et_r_student_username);
        EditText etStudentCode = findViewById(R.id.et_r_student_code);
        EditText etStudentFather = findViewById(R.id.et_r_student_father_name);
        etStudentDOB = findViewById(R.id.et_r_student_dob);
        EditText etStudentGender = findViewById(R.id.et_r_student_gender);
        EditText etStudentEmail = findViewById(R.id.et_r_student_email);
        EditText etStudentAddress = findViewById(R.id.et_r_student_address);
        EditText etStudentMobile = findViewById(R.id.et_r_student_phone);
        EditText etStudentPassword = findViewById(R.id.et_r_student_password);
        EditText etStudentConfirmPassword = findViewById(R.id.et_r_student_confirm_password);
        Button btnStudentRegister = findViewById(R.id.btn_r_student_register);
        Button btnStudentLogin = findViewById(R.id.btn_r_student_login);

        etStudentDOB.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        // If Register button is clicked
        btnStudentRegister.setOnClickListener(v -> {
            // Take required data from user
            String st_name = etStudentName.getText().toString();
            String st_username = etStudentUsername.getText().toString();
            String st_code = etStudentCode.getText().toString();
            String st_father = etStudentFather.getText().toString();
            String st_dob = etStudentDOB.getText().toString();
            String st_gender = etStudentGender.getText().toString();
            String st_email = etStudentEmail.getText().toString();
            String st_address = etStudentAddress.getText().toString();
            String st_mobile = etStudentMobile.getText().toString();
            String st_pass = etStudentPassword.getText().toString();
            String st_confirmPass = etStudentConfirmPassword.getText().toString();

            // Checking password and non-empty fields
            if (!st_name.isEmpty() && !st_username.isEmpty() && !st_code.isEmpty() && !st_father.isEmpty() && !st_dob.isEmpty() && !st_gender.isEmpty() && !st_email.isEmpty() && !st_address.isEmpty() && !st_mobile.isEmpty()) {
                if (st_pass.equals(st_confirmPass)) {
                    Toast.makeText(RegisterStudentActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterStudentActivity.this, LoginActivity.class);  // Assuming LoginActivity is the activity after registered as teacher
                    startActivity(intent);

                } else {
                    Toast.makeText(RegisterStudentActivity.this, "Password don't match! Try again", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterStudentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        // If Login button is clicked
        btnStudentLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterStudentActivity.this, LoginActivity.class);  // Assuming LoginActivity is the activity for login as student after Login button clicked
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
                etStudentDOB.setText(date);
            }
        }, year, month, day);

        datePickerDialog.show();
    }


}