package com.example.myapplication.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class MySavingsPage extends AppCompatActivity {

    ImageButton activeChristmasSaving;

    LinearLayout activeSavingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_savings_page);

        activeSavingLayout = findViewById(R.id.layActiveSaving);
    }

    public void activeSaving(View view){
        if (activeSavingLayout.getVisibility() == View.VISIBLE) {
            activeSavingLayout.setVisibility(View.INVISIBLE);
        } else {
            activeSavingLayout.setVisibility(View.VISIBLE);
        }
    }
}