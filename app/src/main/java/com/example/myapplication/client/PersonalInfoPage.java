package com.example.myapplication.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.myapplication.R;



public class PersonalInfoPage extends AppCompatActivity {

    String name = "Bryan Mendoza";

    EditText nameInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_page);

        nameInfo = findViewById(R.id.txtInfoName);
        nameInfo.setKeyListener(null);
        nameInfo.setText(name);


    }
}