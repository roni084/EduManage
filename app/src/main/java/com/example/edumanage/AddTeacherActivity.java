package com.example.edumanage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;

import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddTeacherActivity extends AppCompatActivity {

    private byte[] imageByteArray;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        //use to get all View from xml layout
        EditText etTeacherName = findViewById(R.id.et_a_teacher_name);
        EditText etTeacherUsername = findViewById(R.id.et_a_teacher_username);
        EditText etTeacherQualification = findViewById(R.id.et_a_teacher_qualification);
        EditText etTeacherDesignation = findViewById(R.id.et_a_teacher_designation);
        ImageView ivImage = findViewById(R.id.iv_a_teacher_image);
        Button btnTUpload = findViewById(R.id.btn_a_upload);
        Button btnTAdd = findViewById(R.id.btn_a_teacher_add);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ivImage.setImageBitmap(imageBitmap);
                    imageByteArray = bitmapToByteArray(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnTUpload.setOnClickListener(v -> {
            showImageSelectionDialog();
        });

        btnTAdd.setOnClickListener(v -> {
            String name = etTeacherName.getText().toString();
            String username = etTeacherUsername.getText().toString();
            String qualification = etTeacherQualification.getText().toString();
            String designation = etTeacherDesignation.getText().toString();

            DatabaseHelper dbc = new DatabaseHelper(AddTeacherActivity.this);

            if (name.isEmpty() || username.isEmpty() || qualification.isEmpty() || designation.isEmpty() || imageByteArray == null) {
                Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            }
            else {
                boolean addedTeacher = dbc.addTeacher(name, username, qualification, designation, imageByteArray);

                if (addedTeacher) {
                    Toast.makeText(AddTeacherActivity.this, "Teacher added to the database.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddTeacherActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddTeacherActivity.this, "Failed to add teacher. Please try again.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showImageSelectionDialog() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        imagePickerLauncher.launch(pickIntent);
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}