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

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Button btnProfile = findViewById(R.id.btn_student_profile);
        Button btnGrade = findViewById(R.id.btn_student_grade);
        Button btnAttendance = findViewById(R.id.btn_student_attend);
        Button btnLogout = findViewById(R.id.btn_logout);

        Intent getIntent = getIntent();
        String studentUsername = getIntent.getStringExtra("student_username");
        String schoolCode = getIntent.getStringExtra("school_code");

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(StudentActivity.this, StudentProfileActivity.class);
            intent.putExtra("student_username", studentUsername);
            startActivity(intent);
        });

        btnGrade.setOnClickListener(v -> {
            Toast.makeText(StudentActivity.this, "Grade will be updated", Toast.LENGTH_SHORT).show();

        });
        btnAttendance.setOnClickListener(v -> {
            Toast.makeText(StudentActivity.this, "Attendance will be updated", Toast.LENGTH_SHORT).show();
        });
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(StudentActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}