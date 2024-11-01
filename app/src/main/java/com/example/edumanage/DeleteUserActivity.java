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

    private EditText etUsername;
    private EditText etCategory;
    private Button btnBack;
    private Button btnSearch;
    private Button btnDelete;
    private TextView tvUserInfo;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        // Initialize views
        etUsername = findViewById(R.id.et_a_delete_username);
        etCategory = findViewById(R.id.et_a_delete_category);
        btnBack = findViewById(R.id.btn_back);
        btnSearch = findViewById(R.id.btn_delete_search);
        btnDelete = findViewById(R.id.btn_delete_user);
        ivImage = findViewById(R.id.iv_user_photo);
        tvUserInfo = findViewById(R.id.tv_user_info);
        btnDelete.setVisibility(Button.GONE);
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
                    //working
                } else if (category.equalsIgnoreCase("Student")) {
                    //working
                } else {
                    Toast.makeText(DeleteUserActivity.this, "Category does not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
