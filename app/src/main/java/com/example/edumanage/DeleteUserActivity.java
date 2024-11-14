package com.example.edumanage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteUserActivity extends AppCompatActivity {
    private EditText etUsername, etCategory;
    private Button btnBack, btnSearch, btnDelete;
    private TextView tvUserInfo;
    private ImageView ivImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        etUsername = findViewById(R.id.et_a_delete_username);
        etCategory = findViewById(R.id.et_a_delete_category);
        btnBack = findViewById(R.id.btn_back);
        btnSearch = findViewById(R.id.btn_delete_search);
        btnDelete = findViewById(R.id.btn_delete_user);
        ivImage = findViewById(R.id.iv_user_photo);
        tvUserInfo = findViewById(R.id.tv_user_info);
        btnDelete.setVisibility(Button.GONE);

        DatabaseHelper dbc = new DatabaseHelper(DeleteUserActivity.this);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(DeleteUserActivity.this, AdminHomeActivity.class);   // AdminHomeActivity is the activity after Back button clicked
            startActivity(intent);
        });

        btnSearch.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String category = etCategory.getText().toString();

            if (username.isEmpty() || category.isEmpty()) {
                Toast.makeText(DeleteUserActivity.this, "Please enter all info", Toast.LENGTH_SHORT).show();
            }
            else {
                if (category.equalsIgnoreCase("Teacher")) {
                    if (checkTeacher(dbc, username)) {
                        displayTeacherInfo(dbc, username);
                    } else {
                        Toast.makeText(DeleteUserActivity.this, "Teacher not found in database", Toast.LENGTH_SHORT).show();
                    }
                } else if (category.equalsIgnoreCase("Student")) {
                    if (checkStudent(dbc, username)) {
                        displayStudentInfo(dbc, username);
                    } else {
                        Toast.makeText(DeleteUserActivity.this, "Student not found in database", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DeleteUserActivity.this, "Category does not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String category = etCategory.getText().toString();

            if (category.equalsIgnoreCase("Teacher")) {
                deleteTeacher(dbc, username);
            } else if (category.equalsIgnoreCase("Student")) {
                deleteStudent(dbc, username);
            }
            ivImage.setVisibility(ImageView.GONE);
            tvUserInfo.setVisibility(TextView.GONE);
            btnDelete.setVisibility(Button.GONE);
            Toast.makeText(DeleteUserActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean checkStudent(DatabaseHelper dbc, String username) {
        SQLiteDatabase db = dbc.getReadableDatabase();
        Cursor sCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_STUDENT + " WHERE " + DatabaseHelper.COL_SUSERNAME + "=?", new String[]{username});
        boolean exists = sCursor.moveToFirst();
        sCursor.close();
        return exists;
    }

    private boolean checkTeacher(DatabaseHelper dbc, String username) {
        SQLiteDatabase db = dbc.getReadableDatabase();
        Cursor tCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_TEACHER + " WHERE " + DatabaseHelper.COL_TUSERNAME + "=?", new String[]{username});
        boolean exists = tCursor.moveToFirst();
        tCursor.close();
        return exists;
    }

    private void displayStudentInfo(DatabaseHelper dbc, String username) {
        byte[] imageBytes = dbc.getStudentImage(username);
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            ivImage.setImageBitmap(bitmap);
        } else {
            Log.e("StudentProfile", "No image found for username: " + username);
        }

        SQLiteDatabase db = dbc.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_REGISTERSTUDENT + " WHERE " + DatabaseHelper.COL_RSUSERNAME + "=?", new String[]{username});

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSNAME));
            String f_name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSFATHERNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSEMAIL));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSADDRESS));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSDOB));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSGENDER));
            String mobile = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RSMOBILE));

            String userInfo = "Name:          " + name + "\n" +
                    "username:      " + username + "\n" +
                    "Father Name:   " + f_name + "\n" +
                    "Email:         " + email + "\n" +
                    "Address:       " + address + "\n" +
                    "Date of Birth: " + dob + "\n" +
                    "Gender:        " + gender + "\n" +
                    "Mobile:        " + mobile + "\n" +
                    "User:          Student";

            tvUserInfo.setText(userInfo);
            ivImage.setVisibility(ImageView.VISIBLE);
            tvUserInfo.setVisibility(TextView.VISIBLE);
            btnDelete.setVisibility(Button.VISIBLE);
        } else {
            Toast.makeText(this, "Error retrieving student information", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    private void displayTeacherInfo(DatabaseHelper dbc, String username) {
        byte[] imageBytes = dbc.getTeacherImage(username);
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            ivImage.setImageBitmap(bitmap);
        } else {
            Log.e("TeacherProfile", "No image found for username: " + username);
        }


        SQLiteDatabase db = dbc.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_REGISTERTEACHER + " WHERE " + DatabaseHelper.COL_RTUSERNAME + "=?", new String[]{username});

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTEMAIL));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTADDRESS));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTDOB));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTGENDER));
            String mobile = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RTMOBILE));


            String userInfo = "Name:          " + name + "\n" +
                    "username:      " + username + "\n" +
                    "Email:         " + email + "\n" +
                    "Address:       " + address + "\n" +
                    "Date of Birth: " + dob + "\n" +
                    "Gender:        " + gender + "\n" +
                    "Mobile:        " + mobile + "\n" +
                    "User:          Teacher";

            tvUserInfo.setText(userInfo);
            ivImage.setVisibility(ImageView.VISIBLE);
            tvUserInfo.setVisibility(TextView.VISIBLE);
            btnDelete.setVisibility(Button.VISIBLE);
        } else {
            Toast.makeText(this, "Error retrieving teacher information", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    private void deleteTeacher(DatabaseHelper dbc, String username) {
        SQLiteDatabase db = dbc.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_TEACHER, DatabaseHelper.COL_TUSERNAME + "=?", new String[]{username});
        db.delete(DatabaseHelper.TABLE_REGISTERTEACHER, DatabaseHelper.COL_RTUSERNAME + "=?", new String[]{username});
    }

    private void deleteStudent(DatabaseHelper dbc, String username) {
        SQLiteDatabase db = dbc.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_STUDENT, DatabaseHelper.COL_SUSERNAME + "=?", new String[]{username});
        db.delete(DatabaseHelper.TABLE_REGISTERSTUDENT, DatabaseHelper.COL_RSUSERNAME + "=?", new String[]{username});
    }
}