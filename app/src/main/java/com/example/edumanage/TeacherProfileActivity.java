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
    DatabaseHelper databaseHelper;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        ImageView ivImage = findViewById(R.id.iv_profile_image);
        TextView tvInfo = findViewById(R.id.tv_teacher_info);
        btnBack = findViewById(R.id.btn_teacher_back);

        databaseHelper = new DatabaseHelper(this);

        Intent getIntent = getIntent();
        String teacherUsername = getIntent.getStringExtra("teacher_username");

        showTeacherInfo(teacherUsername, ivImage, tvInfo);
        
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherProfileActivity.this, TeacherActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void showTeacherInfo(String teacherUsername, ImageView ivImage, TextView tvInfo) {
        byte[] imageBytes = databaseHelper.getTeacherImage(teacherUsername);
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            ivImage.setImageBitmap(bitmap);
            ivImage.setVisibility(ImageView.VISIBLE);
        } else {
            Log.e("TeacherProfile", "No image found for username: " + teacherUsername);
            ivImage.setVisibility(ImageView.GONE);
        }

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_REGISTERTEACHER + " WHERE " + DatabaseHelper.COL_RTUSERNAME + "=?", new String[]{teacherUsername});

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTEMAIL));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTADDRESS));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTDOB));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTGENDER));
            String mobile = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTMOBILE));

            String teacherInfo = "Name:          " + name + "\n" +
                    "Username:      " + teacherUsername + "\n" +
                    "Email:         " + email + "\n" +
                    "Address:       " + address + "\n" +
                    "Date of Birth: " + dob + "\n" +
                    "Gender:        " + gender + "\n" +
                    "Mobile:        " + mobile + "\n" +
                    "User:          Teacher";

            tvInfo.setText(teacherInfo);
            tvInfo.setVisibility(TextView.VISIBLE);
            btnBack.setVisibility(Button.VISIBLE);
        } else {
            Toast.makeText(this, "Error retrieving teacher information", Toast.LENGTH_SHORT).show();
            tvInfo.setVisibility(TextView.GONE);
            btnBack.setVisibility(Button.GONE);
        }
        cursor.close();
        db.close();
    }
}