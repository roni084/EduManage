package com.example.edumanage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //use to get all View from xml layout
        Button btnRegisterAdmin = findViewById(R.id.btn_register_admin);
        Button btnRegisterTeacher = findViewById(R.id.btn_register_teacher);
        Button btnRegisterStudent = findViewById(R.id.btn_register_student);

        //If Admin button clicked
        btnRegisterAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, RegisterAdminActivity.class); //Assuming RegisterAdminActivity is the activity for Registration as admin after Admin button clicked
            startActivity(intent);
        });
        //If Teacher button clicked
        btnRegisterTeacher.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, RegisterTeacherActivity.class); //Assuming RegisterTeacherActivity is the activity for Registration as user after Teacher button clicked
            startActivity(intent);
        });
        //If Student button clicked
        btnRegisterStudent.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, RegisterStudentActivity.class); //Assuming RegisterStudentActivity is the activity for Registration as user after Student button clicked
            startActivity(intent);
        });
    }
}