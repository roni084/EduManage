package com.example.edumanage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateSchoolActivity extends AppCompatActivity {

    private static final String TAG = "CreateSchoolActivity";
    boolean createSchool = false;
    private EditText etSchoolName;
    private EditText etSchoolCode;
    private EditText etSchoolStandard;
    private EditText etSchoolAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_school);

        //use to get all View from xml layout
        etSchoolName = findViewById(R.id.et_school_name);
        etSchoolCode = findViewById(R.id.et_school_code);
        etSchoolStandard = findViewById(R.id.et_school_standard);
        etSchoolAddress = findViewById(R.id.et_school_address);
        Button btnSchoolRegister = findViewById(R.id.btn_school_create);

        //If Create button clicked
        btnSchoolRegister.setOnClickListener(v -> {
            //takes required data from admin
            String s_name = etSchoolName.getText().toString();
            String s_code = etSchoolCode.getText().toString();
            String s_standard = etSchoolStandard.getText().toString();
            String s_address = etSchoolAddress.getText().toString();

            //Checking non-empty fields
            if (s_name.isEmpty() || s_code.isEmpty() || s_standard.isEmpty() || s_address.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
            else {
                DatabaseHelper dbc = new DatabaseHelper(CreateSchoolActivity.this);  //database connection

                try {
                    createSchool = dbc.insertSchool(s_name, s_code, s_standard, s_address);  //data passed to insertSchool method
                } catch (Exception e) {
                    Log.e(TAG, "Error inserting school data: ", e);
                }

                //if data inserted into School_info table
                if (createSchool) {
                    Toast.makeText(CreateSchoolActivity.this, "School created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateSchoolActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CreateSchoolActivity.this, "Failed to create school!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}