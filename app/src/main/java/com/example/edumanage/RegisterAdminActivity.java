package com.example.edumanage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class RegisterAdminActivity extends AppCompatActivity {

    // Regex patterns for validation
    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Z][a-z]+(?:\\s[A-Z][a-z]+)?");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-z0-9][\\w.-]+@[\\w.-]+\\.[a-z]{2,}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("01[356789]\\d{8}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\W)[\\S]{6,}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        // Views from XML layout
        EditText etAdminName = findViewById(R.id.et_r_admin_name);
        EditText etAdminUsername = findViewById(R.id.et_r_admin_username);
        EditText etAdminEmail = findViewById(R.id.et_r_admin_email);
        EditText etAdminMobile = findViewById(R.id.et_r_admin_phone);
        EditText etAdminPassword = findViewById(R.id.et_r_admin_password);
        EditText etAdminConfirmPassword = findViewById(R.id.et_r_admin_confirm_password);
        Button btnAdminRegister = findViewById(R.id.btn_r_admin_register);
        Button btnAdminLogin = findViewById(R.id.btn_r_admin_login);

        // Register button clicked
        btnAdminRegister.setOnClickListener(v -> {
            String name = etAdminName.getText().toString();
            String username = etAdminUsername.getText().toString();
            String email = etAdminEmail.getText().toString();
            String mobile = etAdminMobile.getText().toString();
            String password = etAdminPassword.getText().toString();
            String confirmPassword = etAdminConfirmPassword.getText().toString();

            if(name.isEmpty() || username.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            // Validate fields
            if (!NAME_PATTERN.matcher(name).matches()) {
                etAdminName.setError("Invalid name! Only letters allowed.");
                return;
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                etAdminEmail.setError("Invalid email address!");
                return;
            }
            if (!PHONE_PATTERN.matcher(mobile).matches()) {
                etAdminMobile.setError("Invalid phone format. Try again.");
                return;
            }
            if (!PASSWORD_PATTERN.matcher(password).matches()) {
                etAdminPassword.setError("Password must be at least 6 characters, include an uppercase letter and a special character.");
                return;
            }
            if (!password.equals(confirmPassword)) {
                etAdminConfirmPassword.setError("Passwords do not match! Try again.");
                return;
            }

            DatabaseHelper dbc = new DatabaseHelper(RegisterAdminActivity.this); // Database connection
            boolean adminInserted = dbc.insertAdmin(name, username, email, mobile, password); // Insert data
            Log.d("RegisterAdminActivity", "adminInserted: " + adminInserted); // Log result

            if (adminInserted) {
                Toast.makeText(RegisterAdminActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterAdminActivity.this, CreateSchoolActivity.class); // Navigate to next activity
                startActivity(intent);
            } else {
                Toast.makeText(RegisterAdminActivity.this, "Registration failed! Try again.", Toast.LENGTH_SHORT).show();
            }
        });

        // Login button clicked
        btnAdminLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterAdminActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
