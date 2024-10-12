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

                //checking if user is admin
                if (username.equals("A")) {
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();

                }
                //checking if user is teacher
                else if (username.equals("T")) {
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();

                }
                //checking if user is student
                else if (username.equals("S")) {
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginActivity.this, "Login failed! Check your credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //If Register button clicked
        btnRegister.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Register button clicked!", Toast.LENGTH_SHORT).show();

        });
    }
}
