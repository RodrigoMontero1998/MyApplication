package com.example.myapplication.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;



public class MyLoansPage extends AppCompatActivity {

    LinearLayout payLayout;
    EditText payAmount;
    Button payButton;

    ImageButton payMortgageLoan;
    ImageButton payEducationLoan;
    ImageButton payPersonalLoan;
    ImageButton payTravelLoan;

    TextView quotaMortageCell;
    TextView quotaEducationCell;
    TextView quotaTravelCell;
    TextView quotaPersonalCell;

    TextView balanceMortageCell;
    TextView balanceEducationCell;
    TextView balanceTravelCell;
    TextView balancePersonalCell;

    int buttonId;
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

        quotaMortageCell = findViewById(R.id.quotaMortgage);
        quotaEducationCell = findViewById(R.id.quotaEducation);
        quotaPersonalCell = findViewById(R.id.quotaPersonal);
        quotaTravelCell = findViewById(R.id.quotaTravel);

        balanceMortageCell = findViewById(R.id.balanceMortgage);
        balanceEducationCell = findViewById(R.id.balanceEducation);
        balancePersonalCell = findViewById(R.id.balancePersonal);
        balanceTravelCell = findViewById(R.id.balanceTravel);

        balanceMortageCell.setText("50000");


        payMortgageLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonId = R.id.btnPayMortgage;
                String balance = balanceMortageCell.getText().toString();
                if(balance.equals("0")){
                    Toast.makeText(getApplicationContext(), "No hay un préstamo de la categoria", Toast.LENGTH_SHORT).show();
                    payLayout.setVisibility(View.INVISIBLE);
                }else{
                    payLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        payEducationLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonId = R.id.btnPayEducation;
                String balance = balanceEducationCell.getText().toString();
                if(balance.equals("0")){
                    Toast.makeText(getApplicationContext(), "No hay un préstamo de la categoria", Toast.LENGTH_SHORT).show();
                    payLayout.setVisibility(View.INVISIBLE);
                }else{
                    payLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        payPersonalLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonId = R.id.btnPayPersonal;
                String balance = balancePersonalCell.getText().toString();
                if(balance.equals("0")){
                    Toast.makeText(getApplicationContext(), "No hay un préstamo de la categoria", Toast.LENGTH_SHORT).show();
                    payLayout.setVisibility(View.INVISIBLE);
                }else{
                    payLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        payTravelLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonId = R.id.btnPayTravel;
                String balance = balanceTravelCell.getText().toString();
                if(balance.equals("0")){
                    Toast.makeText(getApplicationContext(), "No hay un préstamo de la categoria", Toast.LENGTH_SHORT).show();
                    payLayout.setVisibility(View.INVISIBLE);
                }else{
                    payLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        payAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatePayAmount(payAmount);
                }
            }
        });
    }

    public void toPay(View view) {
        if (validatePayAmount(payAmount)) {
            updatePayment();
            Toast.makeText(this, "Pago Realizado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error en el pago de la cuota", Toast.LENGTH_SHORT).show();
        }
    }

    public void payLoan(View view) {
        if (payLayout.getVisibility() == View.VISIBLE) {

        } else {
            payLayout.setVisibility(View.VISIBLE);
        }
    }

    boolean validatePayAmount(EditText editText){
        if (editTextisEmpty(editText)){
            editText.setError("Campo Obligatorio");
            return false;
        } else if (!payAmount(editText)) {
            editText.setError("Monto menor a los 5000 colones");
            return false;
        }else {
            return true;
        }
    }




    boolean payAmount(EditText editText) {
        String txtAmount = editText.getText().toString();
        double amount = Double.parseDouble(txtAmount);
        return !(amount < 0);
    }

    boolean editTextisEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    public void updatePayment(){
        String pay;
        double payDouble;
        double balance;
        double result;

        if (buttonId == R.id.btnPayMortgage){
            pay = payAmount.getText().toString();
            payDouble = Double.parseDouble(pay);
            balance = Double.parseDouble(balanceMortageCell.getText().toString());
            result = balance-payDouble;
            balanceMortageCell.setText(Double.toString(result));
            payAmount.setText("");
        } else if (buttonId == R.id.btnPayEducation) {
            pay = payAmount.getText().toString();
            payDouble = Double.parseDouble(pay);
            balance = Double.parseDouble(balanceEducationCell.getText().toString());
            result = balance-payDouble;
            balanceEducationCell.setText(Double.toString(result));
            payAmount.setText("");
        }else if (buttonId == R.id.btnPayPersonal) {
            pay = payAmount.getText().toString();
            payDouble = Double.parseDouble(pay);
            balance = Double.parseDouble(balancePersonalCell.getText().toString());
            result = balance-payDouble;
            balancePersonalCell.setText(Double.toString(result));
            payAmount.setText("");
        }else {
            pay = payAmount.getText().toString();
            payDouble = Double.parseDouble(pay);
            balance = Double.parseDouble(balanceTravelCell.getText().toString());
            result = balance-payDouble;
            balanceTravelCell.setText(Double.toString(result));
            payAmount.setText("");
        }
    }

}