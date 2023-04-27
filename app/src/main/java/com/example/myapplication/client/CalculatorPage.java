package com.example.myapplication.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AppDataBase;
import com.example.myapplication.Logic.Credits;
import com.example.myapplication.R;
import com.example.myapplication.admin.CreateClient;

public class CalculatorPage extends AppCompatActivity {

    AppDataBase db = null;
    Credits credits = null;

    EditText etAmount;
    RadioGroup rdGrpTermInYers;
    RadioGroup rdGrpCreditType;
    TextView monthlyPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_page);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDataBase.class,
                "dbASODESUNIDOS2"
        ).allowMainThreadQueries().build();

        credits = new Credits(db);

        etAmount =  findViewById(R.id.etAmount);
        rdGrpTermInYers = findViewById(R.id.rdGrpTermInYers);
        rdGrpCreditType = findViewById(R.id.rdGrpCreditType);
        monthlyPayment = findViewById(R.id.monthlyPayment);

    }
    Integer getTermInYers(RadioGroup rbtnGrp){
        int selectedRadioButtonId = rbtnGrp.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            return Integer.parseInt(selectedRadioButton.getTag().toString());
        }
        return -1;
    }

    Double getAnnualInterestRate(RadioGroup rbtnGrp){
        int selectedRadioButtonId = rbtnGrp.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            return Double.parseDouble(selectedRadioButton.getTag().toString());
        }
        return 0.00;
    }
    Double calculateMonthlyPayment() throws Exception {
        String amountValue =  etAmount.getText().toString();
        if(amountValue.isEmpty()){
            etAmount.setError("Campo obligatorio");
        }else{
            double amount= Double.parseDouble(amountValue);
            if(amount < 1){
                throw new Exception("No se permiten valores menoras a 1");
            }
            int termInYears= getTermInYers(rdGrpTermInYers);
            if(termInYears == -1){
                throw new Exception("Error: Tipo de prestamo no seleccionado");
            }
            double annualInterestRate = getAnnualInterestRate(rdGrpCreditType);
            if(annualInterestRate== 0.00){
                throw new Exception("Error: Plazo no seleccionado");
            }
            return credits.calculateMonthlyPayment(amount,termInYears,annualInterestRate);
        }
        return 0.00;
    }

    public void btncalculateMonthlyPayment(View vista){
       // if(IsError()) {
            try{
                monthlyPayment.setText(calculateMonthlyPayment().toString());
            }
            catch (Exception e) {
                Toast.makeText(CalculatorPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
      //  }else{
       //     Toast.makeText(CreateClient.this, "Verificar que todos los campos tengan el formato correcto", Toast.LENGTH_SHORT).show();
       // }
    }



}