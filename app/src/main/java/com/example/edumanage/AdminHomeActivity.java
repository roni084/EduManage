package com.example.edumanage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        //use to get all View from xml layout
        Button btnSchool = findViewById(R.id.btn_school);
        Button btnTeacher = findViewById(R.id.btn_teacher);
        Button btnStudent = findViewById(R.id.btn_student);
        Button btnDeleteUser = findViewById(R.id.btn_dltuser);
        Button btnLogout = findViewById(R.id.btn_logout);

        Intent getIntent = getIntent();
        String adminUsername = getIntent.getStringExtra("admin_username");
        String schoolCode = getIntent.getStringExtra("school_code");

        btnSchool.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, ViewSchoolActivity.class);
            intent.putExtra("school_code", schoolCode);
            startActivity(intent);
        });
        btnTeacher.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AddTeacherActivity.class);
            startActivity(intent);
        });
        btnStudent.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });
        btnDeleteUser.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, DeleteUserActivity.class);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}