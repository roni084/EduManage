package com.example.edumanage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        //use to get all View from xml layout
        EditText etAdminName = findViewById(R.id.et_r_admin_name);
        EditText etAdminUsername = findViewById(R.id.et_r_admin_username);
        EditText etAdminEmail = findViewById(R.id.et_r_admin_email);
        EditText etAdminMobile = findViewById(R.id.et_r_admin_phone);
        EditText etAdminPassword = findViewById(R.id.et_r_admin_password);
        EditText etAdminConfirmPassword = findViewById(R.id.et_r_admin_confirm_password);
        Button btnAdminRegister = findViewById(R.id.btn_r_admin_register);
        Button btnAdminLogin = findViewById(R.id.btn_r_admin_login);

        //If Register button clicked
        btnAdminRegister.setOnClickListener(v -> {
            //takes required data from admin
            String name = etAdminName.getText().toString();
            String username = etAdminUsername.getText().toString();
            String email = etAdminEmail.getText().toString();
            String mobile = etAdminMobile.getText().toString();
            String password = etAdminPassword.getText().toString();
            String confirmPassword = etAdminConfirmPassword.getText().toString();

            //Checking non-empty fields
            if (password.equals(confirmPassword) && !username.isEmpty() && !password.isEmpty()) {
                Toast.makeText(RegisterAdminActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            } else {
                if (username.isEmpty()) {
                    Toast.makeText(RegisterAdminActivity.this, "Empty username! Try again.", Toast.LENGTH_SHORT).show();
                }
                if (password.isEmpty()) {
                    Toast.makeText(RegisterAdminActivity.this, "Empty password! Try again.", Toast.LENGTH_SHORT).show();
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterAdminActivity.this, "Passwords do not match! Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //If Login button clicked
        btnAdminLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterAdminActivity.this, LoginActivity.class);  //Assuming LoginActivity is the activity for login as admin after Login button clicked
            startActivity(intent);
        });
    }
}