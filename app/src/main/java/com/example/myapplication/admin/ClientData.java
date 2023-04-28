package com.example.myapplication.admin;

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
import com.example.myapplication.DataBaseConection.Client;
import com.example.myapplication.DataBaseConection.Credit;
import com.example.myapplication.Logic.Clients;
import com.example.myapplication.Logic.Credits;
import com.example.myapplication.R;

import java.util.List;

public class ClientData extends AppCompatActivity {
    TextView etClientID;
    TextView etName;
    TextView etSalary;
    TextView etTelephoneNumber;
    TextView etBirhtdate;
    TextView etCivilStatus;
    TextView etAddress;
    EditText etSearchClientById;

    AppDataBase db = null;
    Clients clients;

    TextView creditMonthlyPayment;
    EditText etCreditAmount;

    RadioGroup radioPlazo;
    RadioGroup radioCreditsTypes;
    Credits credits = null;

    String ID_SEARCH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDataBase.class,
                "dbASODESUNIDOS2"
        ).allowMainThreadQueries().build();

        clients = new Clients(db);

        credits = new Credits(db);

        setContentView(R.layout.activity_client_data);
        etClientID = findViewById(R.id.etClientId);
        etName = findViewById(R.id.etName);
        etSalary = findViewById(R.id.etSalary);
        etTelephoneNumber = findViewById(R.id.etTelephoneNumber);
        etBirhtdate = findViewById(R.id.etBirhtdate);
        etCivilStatus = findViewById(R.id.etCivilStatus);
        etAddress = findViewById(R.id.etAddress);
        etSearchClientById =  findViewById(R.id.etSearchClientById);

        creditMonthlyPayment = findViewById(R.id.creditMonthlyPayment);
        etCreditAmount = findViewById(R.id.etCreditAmount);
        radioPlazo = findViewById(R.id.radioPlazo);
        radioCreditsTypes= findViewById(R.id.radioCreditsTypes);


    }

    Client loadClient(String ClientId) throws Exception {
            return clients.getClientInfo(ClientId);
    }
    void loadTableClient(Client c){
        etClientID.setText(c.client_id);
        etName.setText(c.name);
        etSalary.setText(c.salary.toString());
        etTelephoneNumber.setText(c.telefone_number);
        etBirhtdate.setText(c.birthdate);
        etCivilStatus.setText(c.civil_status);
        etAddress.setText(c.address);
        ID_SEARCH = c.client_id;
    }

    void cleanTableClient(){
        etClientID.setText("");
        etName.setText("");
        etSalary.setText("");
        etTelephoneNumber.setText("");
        etBirhtdate.setText("");
        etCivilStatus.setText("");
        etAddress.setText("");
        ID_SEARCH = null;
    }

    public void btnSearchClient(View v){
        String id_search = etSearchClientById.getText().toString();
        if(!id_search.isEmpty()){
            try{
                Client clientSearch = loadClient(id_search);
                loadTableClient(clientSearch);
            }catch (Exception e){
                cleanTableClient();
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            }
        }else{
            etSearchClientById.setError("Campo vacio");
        }
    }

    int getTermInYers(RadioGroup rbtnGrp){
        int selectedRadioButtonId = rbtnGrp.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            return Integer.parseInt(selectedRadioButton.getTag().toString());
        }
        return -1;
    }

    double getAnnualInterestRate(RadioGroup rbtnGrp){
        int selectedRadioButtonId = rbtnGrp.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            return Double.parseDouble(selectedRadioButton.getTag().toString());
        }
        return 0.00;
    }
    double calculateMonthlyPayment() throws Exception {
        String amountValue =  etCreditAmount.getText().toString();
        if(amountValue.isEmpty()){
            etCreditAmount.setError("Campo obligatorio");
        }else{
            double amount= Double.parseDouble(amountValue);
            if(amount < 1){
                throw new Exception("No se permiten valores menoras a 1");
            }
            int termInYears= getTermInYers(radioPlazo);
            if(termInYears == -1){
                throw new Exception("Error: Plazo no seleccionado");
            }
            double annualInterestRate = getAnnualInterestRate(radioCreditsTypes);
            if(annualInterestRate== 0.00){
                throw new Exception("Error: Tipo de crédito no seleccionado");
            }
            return credits.calculateMonthlyPayment(amount,termInYears,annualInterestRate);
        }
        return 0.00;
    }

    public void btncalculateMonthlyPayment(View vista){
        try{
            creditMonthlyPayment.setText(String.valueOf(calculateMonthlyPayment()));
        }
        catch (Exception e) {
            Toast.makeText(ClientData.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    void validateCreditType(Integer typeCredit) throws Exception {
        if(typeCredit > 0){
            List<Credit> creditList = credits.getAllCreditsByClient(ID_SEARCH);
            if(!creditList.isEmpty()){
                for (Credit c:creditList) {
                    if(c.credit_type.equals(typeCredit)){
                        throw new Exception("Error: Ya posee un credito " + credits.getCreditTypeByType(typeCredit));
                    }
                }
            }
        }else{
            throw new Exception("Error: valor 0 ");
        }
    }
    int getTypeCredit(RadioGroup rbtnGrp) throws Exception {
        int type;
            String AnnualInterestRate = String.valueOf(getAnnualInterestRate(rbtnGrp));

            switch (AnnualInterestRate) {
                case "0.075":
                    type = 1;
                    break;
                case "0.08":
                    type = 2;
                    break;
                case "0.10":
                    type = 3;
                    break;
                default:
                    type = 4;
                    break;

            }
            validateCreditType(type);
        return type;
    }

    public void btnAssignLoanToCustomer(View vista){
        String amount_credits = etCreditAmount.getText().toString();
        if(!amount_credits.isEmpty()) {
            double amount_credit = Double.parseDouble(etCreditAmount.getText().toString());
            if (amount_credit > 0) {
                if (!ID_SEARCH.isEmpty()) {
                    try {
                        credits.AssignLoanToCustomer(
                                ID_SEARCH,
                                getTypeCredit(radioCreditsTypes),
                                getTermInYers(radioPlazo),
                                amount_credit,
                                Double.parseDouble(etSalary.getText().toString())
                        );
                        Toast.makeText(ClientData.this, "Se asigno el credito con exito!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(ClientData.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ClientData.this, "Debe asignar un cliente", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ClientData.this, "Verifique los campos del formulario", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ClientData.this, "Campo del monto esta vacio", Toast.LENGTH_SHORT).show();
        }

    }
}