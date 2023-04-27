package com.example.myapplication.client;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AppDataBase;
import com.example.myapplication.DataBaseConection.Saving;
import com.example.myapplication.Logic.Admin;
import com.example.myapplication.Logic.Credits;
import com.example.myapplication.Logic.Login;
import com.example.myapplication.Logic.Savings;
import com.example.myapplication.R;

import java.util.List;
import java.util.stream.Collectors;

public class MySavingsPage extends AppCompatActivity {

    Button activeChristmasSaving;
    Button activeSchoolSaving;
    Button activeMarchamoSaving;
    Button activeExtraSaving;
    LinearLayout activeSavingLayout;
    AppDataBase db;
    Savings savings;
    TextView quotaChristmasCell;
    TextView quotaSchoolCell;
    TextView quotaMarchamoCell;
    TextView quotaExtraCell;

    TextView balanceChristmasCell;
    TextView balanceSchoolCell;
    TextView balanceMarchamoCell;
    TextView balanceExtraCell;
    Switch switchChristmas;
    Switch switchSchool;
    Switch switchLabel;
    Switch switchExtra;

    Saving ChristmasSaving;
    Saving SchoolSaving;
    Saving LabelSaving;
    Saving ExtraSaving;

    Button activeSaving;

    EditText savingAmount;
    TextView tvCurrentClient;
    String CURRENT_CLIENT_ID;

    int buttonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_savings_page);

        tvCurrentClient =  findViewById(R.id.tvCurrentSavingsClient);
        Intent intent = getIntent();
        CURRENT_CLIENT_ID = intent.getStringExtra("CURRENT_CLIENT_ID");
        tvCurrentClient.setText(CURRENT_CLIENT_ID);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDataBase.class,
                "dbASODESUNIDOS2"
        ).allowMainThreadQueries().build();
        
        savings = new Savings(db);

        activeSavingLayout = findViewById(R.id.layActiveSaving);

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

        ChristmasSaving = getChristmasSaving();
        SchoolSaving = getSchoolSaving();
        LabelSaving = getLabelSaving();
        ExtraSaving = getExtraSaving();

        switchChristmas = (Switch) findViewById(R.id.switch1);
        switchSchool = (Switch) findViewById(R.id.switch2);
        switchLabel = (Switch) findViewById(R.id.switch3);
        switchExtra = (Switch) findViewById(R.id.switch4);

        LoadALLSavings();

        switchChristmas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    buttonId = switchChristmas.getId();
                    activeSavingLayout.setVisibility(View.VISIBLE);
                } else {
                    mostrarAlerta("Seguro desea desactivar el ahorro navideno",switchChristmas,ChristmasSaving);
                    //activeSavingLayout.setVisibility(View.INVISIBLE);
                }
            }
        });


        switchSchool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    buttonId = switchSchool.getId();
                    activeSavingLayout.setVisibility(View.VISIBLE);
                } else {
                    mostrarAlerta("Seguro desea desactivar el ahorro escolar",switchSchool,SchoolSaving);
                    //activeSavingLayout.setVisibility(View.INVISIBLE);
                }
            }
        });
        switchLabel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    buttonId = switchLabel.getId();
                    activeSavingLayout.setVisibility(View.VISIBLE);
                } else {
                    mostrarAlerta("Seguro desea desactivar el ahorro de marchamo",switchLabel,LabelSaving);
                    //activeSavingLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        switchExtra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    buttonId = switchExtra.getId();
                    activeSavingLayout.setVisibility(View.VISIBLE);
                } else {
                    mostrarAlerta("Seguro desea desactivar el ahorro extraordinario?",switchExtra,ExtraSaving);
                    //activeSavingLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        savingAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateSavingAmount(savingAmount);
                }
            }
        });
    }

    public void toSaving(View view) {
        if (validateSavingAmount(savingAmount)) {
            updateSavings();
           // ActivateSaving(SchoolSaving);
            //LoadALLSavings();
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

    void mostrarAlerta(String msj,Switch mySwitch,Saving saving){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msj);

        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                activeSavingLayout.setVisibility(View.INVISIBLE);
                DesactivateSaving(saving,mySwitch.getId());
                Toast.makeText(MySavingsPage.this, "Ahorro desActivado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(MySavingsPage.this, "Ahorro desActivado", Toast.LENGTH_SHORT).show();
                mySwitch.setChecked(true);
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    Saving getChristmasSaving(){
        return savings.getAllSavingsByClientandType(CURRENT_CLIENT_ID, "Navideño");
    }
    Saving getSchoolSaving(){
        return savings.getAllSavingsByClientandType(CURRENT_CLIENT_ID,"Escolar");
    }
    Saving getLabelSaving(){
        return savings.getAllSavingsByClientandType(CURRENT_CLIENT_ID,"Marchamo");
    }
    Saving getExtraSaving(){
        return savings.getAllSavingsByClientandType(CURRENT_CLIENT_ID,"Extraordinario");
    }

    public void LoadALLSavings(){
        quotaChristmasCell.setText(ChristmasSaving.monthly_amount.toString());
        balanceChristmasCell.setText(ChristmasSaving.balance.toString());
        switchChristmas.setChecked(ChristmasSaving.is_active);

        savingAmount.setText("");

        quotaSchoolCell.setText(SchoolSaving.monthly_amount.toString());
        balanceSchoolCell.setText(SchoolSaving.balance.toString());
        savingAmount.setText("");
        switchSchool.setChecked(SchoolSaving.is_active);

        quotaMarchamoCell.setText(LabelSaving.monthly_amount.toString());
        balanceMarchamoCell.setText(LabelSaving.balance.toString());
        savingAmount.setText("");
        switchLabel.setChecked(LabelSaving.is_active);

        quotaExtraCell.setText(ExtraSaving.monthly_amount.toString());
        balanceExtraCell.setText(ExtraSaving.balance.toString());
        savingAmount.setText("");
        switchExtra.setChecked(ExtraSaving.is_active);
    }

    public void updateSavings(){
        String saving;
        if (buttonId == R.id.switch1){
            saving = savingAmount.getText().toString();
            quotaChristmasCell.setText(saving);
            balanceChristmasCell.setText(saving);
            ActivateSaving(ChristmasSaving,Double.parseDouble(saving));
            savingAmount.setText("");
        } else if (buttonId == R.id.switch2) {
            saving = savingAmount.getText().toString();
            quotaSchoolCell.setText(saving);
            balanceSchoolCell.setText(saving);
            ActivateSaving(SchoolSaving,Double.parseDouble(saving));
            savingAmount.setText("");
        }else if (buttonId == R.id.switch3) {
            saving = savingAmount.getText().toString();
            quotaMarchamoCell.setText(saving);
            balanceMarchamoCell.setText(saving);
            ActivateSaving(LabelSaving,Double.parseDouble(saving));
            savingAmount.setText("");
        }else {
            saving = savingAmount.getText().toString();
            quotaExtraCell.setText(saving);
            balanceExtraCell.setText(saving);
            ActivateSaving(ExtraSaving,Double.parseDouble(saving));
            savingAmount.setText("");
        }
    }

    public void updateSavingsDesactive(Integer mySwitchID){
        if (mySwitchID == R.id.switch1){
            quotaChristmasCell.setText("0.00");
            savingAmount.setText("");
        } else if (mySwitchID == R.id.switch2) {
            quotaSchoolCell.setText("0.00");
            savingAmount.setText("");
        }else if (mySwitchID == R.id.switch3) {
            quotaMarchamoCell.setText("0.00");
            savingAmount.setText("");
        }else {
            quotaExtraCell.setText("0.00");
            savingAmount.setText("");
        }
    }

    void ActivateSaving(Saving saving,Double mountlyAmount){
        try{
            savings.ActivateSavingByNumber(saving.saving_number.toString(),mountlyAmount);
        }catch (Exception e){

        }
    }

    void DesactivateSaving(Saving saving,Integer mySwitchID){
        try{
            savings.DesactivateSavingByNumber(saving.saving_number.toString());
            updateSavingsDesactive(mySwitchID);
        }catch (Exception e){

        }
    }

}