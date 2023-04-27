package com.example.myapplication.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class MyLoansPage extends AppCompatActivity {

    LinearLayout payLayout;
    EditText payAmount;
    Button payButton;

    ImageButton payMortgageLoan;
    ImageButton payEducationLoan;
    ImageButton payPersonalLoan;
    ImageButton payTravelLoan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_loans_page);

        payLayout = findViewById(R.id.payLoanLayout);
        payAmount = findViewById(R.id.txtPayAmount);
        payButton = findViewById(R.id.btnPay);
        payMortgageLoan = findViewById(R.id.btnPayMortgage);
        payPersonalLoan = findViewById(R.id.btnPayPersonal);
        payEducationLoan = findViewById(R.id.btnPayEducation);
        payTravelLoan = findViewById(R.id.btnPayTravel);
    }

    public void payLoan(View view){
        if (payLayout.getVisibility() == View.VISIBLE) {
            payLayout.setVisibility(View.INVISIBLE);
        } else {
            payLayout.setVisibility(View.VISIBLE);
        }
    }
}