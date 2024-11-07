package com.example.edumanage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddStudentActivity extends AppCompatActivity {

    private byte[] imageByteArray;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Get all views from XML layout
        EditText etStudentName = findViewById(R.id.et_a_student_name);
        EditText etStudentUsername = findViewById(R.id.et_a_student_username);
        ImageView ivSImage = findViewById(R.id.iv_a_student_image);
        Button btnSUpload = findViewById(R.id.btn_a_upload);
        Button btnSAdd = findViewById(R.id.btn_a_student_add);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ivSImage.setImageBitmap(imageBitmap);
                    imageByteArray = bitmapToByteArray(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSUpload.setOnClickListener(v -> {
            showImageSelectionDialog();
        });

        btnSAdd.setOnClickListener(v -> {
            String name = etStudentName.getText().toString();
            String username = etStudentUsername.getText().toString();

            DatabaseHelper dbc = new DatabaseHelper(AddStudentActivity.this);

            if (name.isEmpty() || username.isEmpty() || imageByteArray == null) {
                Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            } else {
                boolean addedStudent = dbc.addStudent(name, username, imageByteArray);

                if (addedStudent) {
                    Toast.makeText(AddStudentActivity.this, "Student has been successfully added to the database", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddStudentActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddStudentActivity.this, "Failed to add student. Please try again.", Toast.LENGTH_SHORT).show();
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
