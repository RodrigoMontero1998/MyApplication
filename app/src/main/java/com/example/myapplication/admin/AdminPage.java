package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.R;

public class AdminPage extends AppCompatActivity {

    Button createClientButton;
    Button assignLoanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        createClientButton = findViewById(R.id.btnNewClient);
        assignLoanButton = findViewById(R.id.btnLoan);

        createClientButton.setOnClickListener(v -> createClient());
        assignLoanButton.setOnClickListener(v -> assignLoan());
    }

    public void createClient(){
        Intent intent = new Intent(this, CreateClient.class);
        startActivity(intent);
    }

    public void assignLoan(){
        Intent intent = new Intent(this,ClientData.class);
        startActivity(intent);
    }
}