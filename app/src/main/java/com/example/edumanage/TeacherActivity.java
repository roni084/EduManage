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

public class TeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        //use to get all View from xml layout
        Button btnCreateCourse = findViewById(R.id.btn_teacher_class);
        Button btnAttend = findViewById(R.id.btn_teacher_attend);
        Button btnUploadGrade = findViewById(R.id.btn_teacher_upgrade);
        Button btnProfile = findViewById(R.id.btn_teacher_profile);
        Button btnLogout = findViewById(R.id.btn_logout);

        Intent getIntent = getIntent();
        String teacherUsername = getIntent.getStringExtra("teacher_username");
        String schoolCode = getIntent.getStringExtra("school_code");

        btnCreateCourse.setOnClickListener(v -> {
            Toast.makeText(TeacherActivity.this, "Under construction!", Toast.LENGTH_SHORT).show();

        });
        btnAttend.setOnClickListener(v -> {
            Toast.makeText(TeacherActivity.this, "Under construction!", Toast.LENGTH_SHORT).show();

        });
        btnUploadGrade.setOnClickListener(v -> {
            Toast.makeText(TeacherActivity.this, "Under construction!", Toast.LENGTH_SHORT).show();

        });
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherActivity.this, TeacherProfileActivity.class);
            intent.putExtra("teacher_username", teacherUsername);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
