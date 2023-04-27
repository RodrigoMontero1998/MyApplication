package com.example.myapplication.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class MySavingsPage extends AppCompatActivity {

    ImageButton activeChristmasSaving;
    ImageButton activeSchoolSaving;
    ImageButton activeMarchamoSaving;
    ImageButton activeExtraSaving;
    LinearLayout activeSavingLayout;

    TextView quotaChristmasCell;
    TextView quotaSchoolCell;
    TextView quotaMarchamoCell;
    TextView quotaExtraCell;

    TextView balanceChristmasCell;
    TextView balanceSchoolCell;
    TextView balanceMarchamoCell;
    TextView balanceExtraCell;

    Button activeSaving;

    EditText savingAmount;
    TextView tvCurrentClient;
    String CURRENT_CLIENT_ID;

    int buttonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_savings_page);

        activeSavingLayout = findViewById(R.id.layActiveSaving);

        activeChristmasSaving = findViewById(R.id.btnActiveChristmas);
        activeSchoolSaving = findViewById(R.id.btnActiveSchool);
        activeMarchamoSaving = findViewById(R.id.btnActiveMarchamo);
        activeExtraSaving = findViewById(R.id.btnActiveExtra);

        quotaChristmasCell = findViewById(R.id.quotaChristmas);
        quotaSchoolCell = findViewById(R.id.quotaSchool);
        quotaMarchamoCell = findViewById(R.id.quotaMarchamo);
        quotaExtraCell = findViewById(R.id.quotaExtra);

        balanceChristmasCell = findViewById(R.id.balanceChristmas);
        balanceSchoolCell = findViewById(R.id.balanceSchool);
        balanceMarchamoCell = findViewById(R.id.balanceMarchamo);
        balanceExtraCell = findViewById(R.id.balanceExtra);

        activeSaving = findViewById(R.id.btnActiveSaving);
        savingAmount = findViewById(R.id.txtSavingAmount);

        activeChristmasSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeSavingLayout(v);
                buttonId = R.id.btnActiveChristmas;
            }
        });

        activeSchoolSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeSavingLayout(v);
                buttonId = R.id.btnActiveSchool;

            }
        });

        activeMarchamoSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeSavingLayout(v);
                buttonId = R.id.btnActiveMarchamo;
            }
        });

        activeExtraSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeSavingLayout(v);
                buttonId = R.id.btnActiveExtra;
            }
        });

        tvCurrentClient =  findViewById(R.id.tvCurrentSavingsClient);
        Intent intent = getIntent();
        CURRENT_CLIENT_ID = intent.getStringExtra("CURRENT_CLIENT_ID");
        tvCurrentClient.setText(CURRENT_CLIENT_ID);

        savingAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateSavingAmount(savingAmount);
                }
            }
        });
    }


    public void activeSavingLayout(View view){
        if (quotaChristmasCell.getText().toString().isEmpty()) {
            activeSavingLayout.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Ya se encuentra activo un ahorro", Toast.LENGTH_SHORT);
        } else {
            if (activeSavingLayout.getVisibility() != View.VISIBLE){
                activeSavingLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void toSaving(View view) {
        if (validateSavingAmount(savingAmount)) {
            updateSavings();
            Toast.makeText(this, "Ahorro Activado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Problemas en la activación del ahorro", Toast.LENGTH_SHORT).show();
        }
    }

    boolean validateSavingAmount(EditText editText){
        if (editTextisEmpty(editText)){
            editText.setError("Campo Obligatorio");
            return false;
        } else if (!savingAmount(editText)) {
            editText.setError("Monto menor a los 5000 colones");
            return false;
        }else {
            return true;
        }



    }
    boolean editTextisEmpty(EditText editText){return editText.getText().toString().isEmpty();}

    boolean savingAmount(EditText editText){
        String txtAmount = editText.getText().toString();
        double amount = Double.parseDouble(txtAmount);
        if (amount < 5000) {
            return false;
        }else{
            return true;
        }
    }



    public void updateSavings(){
        String saving;
        if (buttonId == R.id.btnActiveChristmas){
            saving = savingAmount.getText().toString();
            quotaChristmasCell.setText(saving);
            balanceChristmasCell.setText(saving);
            savingAmount.setText("");
        } else if (buttonId == R.id.btnActiveSchool) {
            saving = savingAmount.getText().toString();
            quotaSchoolCell.setText(saving);
            balanceSchoolCell.setText(saving);
            savingAmount.setText("");
        }else if (buttonId == R.id.btnActiveMarchamo) {
            saving = savingAmount.getText().toString();
            quotaMarchamoCell.setText(saving);
            balanceMarchamoCell.setText(saving);
            savingAmount.setText("");
        }else {
            saving = savingAmount.getText().toString();
            quotaExtraCell.setText(saving);
            balanceExtraCell.setText(saving);
            savingAmount.setText("");
        }
    }
}