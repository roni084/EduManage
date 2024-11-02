package com.example.edumanage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentProfileActivity extends AppCompatActivity {

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        ImageView ivImage = findViewById(R.id.iv_profile_image);
        TextView tvInfo = findViewById(R.id.tv_student_info);
        btnBack = findViewById(R.id.btn_student_back);

        Intent getintent = getIntent();
        String studentUsername = getintent.getStringExtra("student_username");

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(StudentProfileActivity.this, StudentActivity.class);
            startActivity(intent);
            finish();
        });
    }
}