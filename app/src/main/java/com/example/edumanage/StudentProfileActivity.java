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
    DatabaseHelper databaseHelper;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        ImageView ivImage = findViewById(R.id.iv_profile_image);
        TextView tvInfo = findViewById(R.id.tv_student_info);
        btnBack = findViewById(R.id.btn_student_back);

        databaseHelper = new DatabaseHelper(this);

        Intent getintent = getIntent();
        String studentUsername = getintent.getStringExtra("student_username");

        showStudentInfo(studentUsername, ivImage, tvInfo);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(StudentProfileActivity.this, StudentActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void showStudentInfo(String studentUsername, ImageView ivImage, TextView tvInfo) {
        byte[] imageBytes = databaseHelper.getStudentImage(studentUsername);
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            ivImage.setImageBitmap(bitmap);
            ivImage.setVisibility(ImageView.VISIBLE);
        } else {
            Log.e("StudentProfile", "No image found for username: " + studentUsername);
            ivImage.setVisibility(ImageView.GONE);
        }

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_REGISTERSTUDENT + " WHERE " + DatabaseHelper.COL_RSUSERNAME + "=?", new String[]{studentUsername});

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSNAME));
            String f_name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSFATHERNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSEMAIL));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSADDRESS));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSDOB));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSGENDER));
            String mobile = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSMOBILE));

            String userInfo = "Name:          " + name + "\n" +
                    "Username:      " + studentUsername + "\n" +
                    "Father Name:   " + f_name + "\n" +
                    "Email:         " + email + "\n" +
                    "Address:       " + address + "\n" +
                    "Date of Birth: " + dob + "\n" +
                    "Gender:        " + gender + "\n" +
                    "Mobile:        " + mobile;

            tvInfo.setText(userInfo);
            tvInfo.setVisibility(TextView.VISIBLE);
            btnBack.setVisibility(Button.VISIBLE);
        } else {
            Toast.makeText(this, "Error retrieving student information", Toast.LENGTH_SHORT).show();
            tvInfo.setVisibility(TextView.GONE);
            btnBack.setVisibility(Button.GONE);
        }

        cursor.close();
        db.close();
    }
}