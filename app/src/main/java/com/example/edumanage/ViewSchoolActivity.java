package com.example.edumanage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewSchoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_school);

        TextView tvSchoolName = findViewById(R.id.tv_school_name);
        TextView tvSchoolCode = findViewById(R.id.tv_school_code);
        TextView tvSchoolCategory = findViewById(R.id.tv_school_category);
        TextView tvSchoolAddress = findViewById(R.id.tv_school_address);
        TextView tvTotalTeachers = findViewById(R.id.tv_total_teachers);
        TextView tvTotalStudents = findViewById(R.id.tv_total_students);
        Button btnBack = findViewById(R.id.btn_back);

        Intent getIntent = getIntent();
        String schoolCode = getIntent.getStringExtra("school_code");

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Fetch school details
        DatabaseHelper.School school = dbHelper.getSchoolDetails(schoolCode);
        int totalTeachers = dbHelper.getTotalTeachers(schoolCode);
        int totalStudents = dbHelper.getTotalStudents(schoolCode);

        if (school != null) {
            tvSchoolName.setText("School Name: " + school.getName());
            tvSchoolCode.setText("School Code:    " + school.getCode());
            tvSchoolCategory.setText("Category:           " + school.getCategory());
            tvSchoolAddress.setText("Address:             " + school.getAddress());
            tvTotalTeachers.setText("Total Teachers: " + totalTeachers);
            tvTotalStudents.setText("Total Students: " + totalStudents);
        }

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ViewSchoolActivity.this, AdminHomeActivity.class);
            startActivity(intent);
        });
    }
}
