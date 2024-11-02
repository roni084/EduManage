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

public class TeacherProfileActivity extends AppCompatActivity {

    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        ImageView ivImage = findViewById(R.id.iv_profile_image);
        TextView tvInfo = findViewById(R.id.tv_teacher_info);
        btnBack = findViewById(R.id.btn_teacher_back);

        Intent getIntent = getIntent();
        String teacherUsername = getIntent.getStringExtra("teacher_username");

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherProfileActivity.this, TeacherActivity.class);
            startActivity(intent);
            finish();
        });
    }
}