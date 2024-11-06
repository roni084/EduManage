package com.example.edumanage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.regex.Pattern;

public class RegisterStudentActivity extends AppCompatActivity {

    private EditText etStudentDOB;
    private RadioGroup rgStudentGender;
    private RadioButton rbSelectedGender;
    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Z][a-z]+(?:\\s[A-Z][a-z]+)?");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-z0-9][\\w.-]+@[\\w.-]+\\.[a-z]{2,}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("01[356789]\\d{8}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\W)[\\S]{6,}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        // Views from XML layout
        EditText etStudentName = findViewById(R.id.et_r_student_name);
        EditText etStudentUsername = findViewById(R.id.et_r_student_username);
        EditText etStudentCode = findViewById(R.id.et_r_student_code);
        EditText etStudentFather = findViewById(R.id.et_r_student_father_name);
        etStudentDOB = findViewById(R.id.et_r_student_dob);
        rgStudentGender = findViewById(R.id.rg_student_gender);
        EditText etStudentEmail = findViewById(R.id.et_r_student_email);
        EditText etStudentAddress = findViewById(R.id.et_r_student_address);
        EditText etStudentMobile = findViewById(R.id.et_r_student_phone);
        EditText etStudentPassword = findViewById(R.id.et_r_student_password);
        EditText etStudentConfirmPassword = findViewById(R.id.et_r_student_confirm_password);
        Button btnStudentRegister = findViewById(R.id.btn_r_student_register);
        Button btnStudentLogin = findViewById(R.id.btn_r_student_login);

        // Set up DatePicker
        etStudentDOB.setOnClickListener(v -> showDatePickerDialog());

        // Register button clicked
        btnStudentRegister.setOnClickListener(v -> {
            // Retrieve input data
            String st_name = etStudentName.getText().toString();
            String st_username = etStudentUsername.getText().toString();
            String st_code = etStudentCode.getText().toString();
            String st_father = etStudentFather.getText().toString();
            String st_dob = etStudentDOB.getText().toString();
            String st_email = etStudentEmail.getText().toString();
            String st_address = etStudentAddress.getText().toString();
            String st_mobile = etStudentMobile.getText().toString();
            String st_pass = etStudentPassword.getText().toString();
            String st_confirmPass = etStudentConfirmPassword.getText().toString();

            // Get selected gender
            int selectedGenderId = rgStudentGender.getCheckedRadioButtonId();
            if (selectedGenderId != -1) {
                rbSelectedGender = findViewById(selectedGenderId);
                String st_gender = rbSelectedGender.getText().toString();

                // Validate non-empty fields and password match
                if (st_name.isEmpty() || st_username.isEmpty() || st_code.isEmpty() || st_father.isEmpty() || st_dob.isEmpty() || st_email.isEmpty() || st_address.isEmpty() || st_mobile.isEmpty() || st_pass.isEmpty() || st_confirmPass.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!NAME_PATTERN.matcher(st_name).matches()) {
                    etStudentName.setError("Invalid name! Only letters allowed.");
                    return;
                }
                if (!NAME_PATTERN.matcher(st_father).matches()) {
                    etStudentFather.setError("Invalid name! Only letters allowed.");
                    return;
                }
                if (!EMAIL_PATTERN.matcher(st_email).matches()) {
                    etStudentEmail.setError("Invalid email address!");
                    return;
                }
                if (!PHONE_PATTERN.matcher(st_mobile).matches()) {
                    etStudentMobile.setError("Invalid phone format. Try again.");
                    return;
                }
                if (!PASSWORD_PATTERN.matcher(st_pass).matches()) {
                    etStudentPassword.setError("Password must be at least 6 characters, include an uppercase letter and a special character.");
                    return;
                }
                if (!st_pass.equals(st_confirmPass)) {
                    etStudentConfirmPassword.setError("Passwords do not match! Try again.");
                    return;
                }

                // Registration successful message
                Toast.makeText(RegisterStudentActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            }
        });

        // Login button clicked
        btnStudentLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterStudentActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            String date = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year1);
            etStudentDOB.setText(date);
        }, year, month, day);

        datePickerDialog.show();
    }
}
