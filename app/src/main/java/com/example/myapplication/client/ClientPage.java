package com.example.myapplication.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.R;

public class ClientPage extends AppCompatActivity {

    Button loansButton;
    Button calculatorButton;
    Button savingsButton;
    Button personalInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_page);

        calculatorButton = findViewById(R.id.btnCalculate);
        savingsButton = findViewById(R.id.btnSavings);
        personalInfoButton = findViewById(R.id.btnPersonalInfo);
        loansButton = findViewById(R.id.btnMyLoans);

        calculatorButton.setOnClickListener(v -> calculator());
        savingsButton.setOnClickListener(v -> savings());
        personalInfoButton.setOnClickListener(v -> personalInfo());
        loansButton.setOnClickListener(v -> loans());
    }

    public void calculator(){
        Intent intent = new Intent(this, CalculatorPage.class);
        startActivity(intent);
    }

    public void savings(){
        Intent intent = new Intent(this, MySavingsPage.class);
        startActivity(intent);
    }

    public void personalInfo(){
        Intent intent = new Intent(this, PersonalInfoPage.class);
        startActivity(intent);
    }

    public void loans(){
        Intent intent = new Intent(this, MyLoansPage.class);
        startActivity(intent);
    }
}