package com.example.myapplication.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

public class MySavingsPage extends AppCompatActivity {

    ImageButton activeChristmasSaving;

    LinearLayout activeSavingLayout;
    TextView tvCurrentClient;
    String CURRENT_CLIENT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_savings_page);

        activeSavingLayout = findViewById(R.id.layActiveSaving);

        tvCurrentClient =  findViewById(R.id.tvCurrentSavingsClient);
        Intent intent = getIntent();
        CURRENT_CLIENT_ID = intent.getStringExtra("CURRENT_CLIENT_ID");
        tvCurrentClient.setText(CURRENT_CLIENT_ID);
    }

    public void activeSaving(View view){
        if (activeSavingLayout.getVisibility() == View.VISIBLE) {
            activeSavingLayout.setVisibility(View.INVISIBLE);
        } else {
            activeSavingLayout.setVisibility(View.VISIBLE);
        }
    }
}