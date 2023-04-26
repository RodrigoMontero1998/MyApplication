package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.AppDataBase;
import com.example.myapplication.Logic.Admin;
import com.example.myapplication.Logic.Clients;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.Calendar;

public class CreateClient extends AppCompatActivity {

    Spinner spinnerResult;
    AppDataBase db = null;
    Clients client;
    Admin admin;

    EditText txtClientBirthdate,txtClientId,txtClientName,
             txtInfoSalary,txtClientPhone,
             txtClientAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDataBase.class,
                "dbASODESUNIDOS2"
        ).allowMainThreadQueries().build();

        client = new Clients(db);
        admin = new Admin(db);

        spinnerResult = findViewById(R.id.spinnerClientStatus);
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,client.getAllCivilStatus());

        spinnerResult.setAdapter(adaptador1);

        txtClientBirthdate = findViewById(R.id.txtClientBirthdate);
        txtClientBirthdate.setKeyListener(null);
        txtClientName = findViewById(R.id.txtClientName);
        txtClientId =  findViewById(R.id.txtClientId);
        txtInfoSalary = findViewById(R.id.txtInfoSalary);
        txtClientPhone = findViewById(R.id.txtClientPhone);
        txtClientAddress= findViewById(R.id.txtClientAddress);

        Button buttonDatePicker = findViewById(R.id.buttonDatePicker);
        buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateClient.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                txtClientBirthdate.setText(day + "/" + (month + 1) + "/" + year);
                                txtClientBirthdate.setError(null);
                            }
                        }, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        txtClientName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vista, boolean hasFocus) {
                if (!hasFocus) {
                    ValidateName(txtClientName);
                }
            }
        });

        txtClientId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vista, boolean hasFocus) {
                if (!hasFocus) {
                    ValidateClientID(txtClientId);
                }
            }
        });

        txtInfoSalary.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vista, boolean hasFocus) {
                if (!hasFocus) {
                    ValidateSalary(txtInfoSalary);
                }
            }
        });

        txtClientPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vista, boolean hasFocus) {
                if (!hasFocus) {
                    ValidatePhoneNumber(txtClientPhone);
                }
            }
        });

        txtClientAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vista, boolean hasFocus) {
                if (!hasFocus) {
                    ValidateAddress(txtClientAddress);
                }
            }
        });
    }

    boolean EditTextIsEmpty(EditText pEdtTxt){ return pEdtTxt.getText().toString().isEmpty();}
    boolean IsAlphabetic(EditText pEdtTxt){ return pEdtTxt.getText().toString().matches("^[a-zA-Z ]+$");}
    boolean maxLength(EditText pEdtTxt, int num){ return pEdtTxt.getText().toString().length() > num;}
    boolean ValidateName(EditText pEdtTxt){
        if(EditTextIsEmpty(pEdtTxt)){
            pEdtTxt.setError("Campo obligatorio");
            return false;
        }else{
            if(!IsAlphabetic(pEdtTxt)) {
                pEdtTxt.setError("Solo se admiten letras");
                return false;
            }
        }
        return true;
    }
    boolean ValidateClientID(EditText pEdtTxt){
        if(EditTextIsEmpty(pEdtTxt)){
            pEdtTxt.setError("Campo obligatorio");
            return false;
        }else{
            if(maxLength(pEdtTxt,15)) {
                pEdtTxt.setError("No se permite mas de 15 caracteres");
                return false;
            }
        }
        return true;
    }

    boolean ValidateSalary(EditText pEdtTxt){
        if(EditTextIsEmpty(pEdtTxt)){
            pEdtTxt.setError("Campo obligatorio");
            return false;
        }else{
            if(maxLength(pEdtTxt,60)) {
                pEdtTxt.setError("No se permite mas de 60 caracteres");
                return false;
            }
        }
        return true;
    }

    boolean ValidatePhoneNumber(EditText pEdtTxt){
        if(EditTextIsEmpty(pEdtTxt)){
            pEdtTxt.setError("Campo obligatorio");
            return false;
        }else{
            if(maxLength(pEdtTxt,60)) {
                pEdtTxt.setError("No se permite mas de 60 caracteres");
                return false;
            }
        }
        return true;
    }

    boolean ValidateBirthDate(EditText pEdtTxt){
        if(pEdtTxt.getText().toString().isEmpty()){
            pEdtTxt.setError("Campo obligatorio");
            return false;
        }
        return true;
    }

    boolean ValidateAddress(EditText pEdtTxt){
        if(EditTextIsEmpty(pEdtTxt)){
            pEdtTxt.setError("Campo obligatorio");
            return false;
        }else{
            if(maxLength(pEdtTxt,150)) {
                pEdtTxt.setError("No se permite mas de 150 caracteres");
                return false;
            }
        }
        return true;
    }

    boolean IsError(){
        boolean errorName = ValidateName(txtClientName);
        boolean errorClientID = ValidateClientID(txtClientId);
        boolean errorSalary = ValidateSalary(txtInfoSalary);
        boolean PhoneNumber = ValidatePhoneNumber(txtClientPhone);
        boolean BirthDate = ValidateBirthDate(txtClientBirthdate);
        boolean Address = ValidateAddress(txtClientAddress);
        return errorName && errorClientID &&
                errorSalary && PhoneNumber &&
                BirthDate && Address;
    }

    private void createClients() throws Exception {
        String name = txtClientName.getText().toString();
        String clientId = txtClientId.getText().toString();
        Double salary = Double.parseDouble(txtInfoSalary.getText().toString());
        String clientPhone = txtClientPhone.getText().toString();
        String birthdate = txtClientBirthdate.getText().toString();
        String address = txtClientAddress.getText().toString();
        admin.SaveNewClient(clientId,name,salary,clientPhone,birthdate,"",address);
    }

    public void btnCreateClient(View vista){
        if(IsError()) {
            try{
                createClients();
                Toast.makeText(CreateClient.this, "Insertar cliente", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                Toast.makeText(CreateClient.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(CreateClient.this, "Verificar que todos los campos tengan el formato correcto", Toast.LENGTH_SHORT).show();
        }
    }


}