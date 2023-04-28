package com.example.myapplication.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AppDataBase;
import com.example.myapplication.DataBaseConection.Client;
import com.example.myapplication.Logic.Admin;
import com.example.myapplication.Logic.Clients;
import com.example.myapplication.R;
import com.example.myapplication.admin.CreateClient;

import java.util.Calendar;


public class PersonalInfoPage extends AppCompatActivity {

    String name = "Bryan Mendoza";

    String spinnerItemSelected;
    AppDataBase db = null;
    Clients clients;

    Client client;
    Admin admin;

    EditText nameInfo, salaryInfo, phoneInfo, birthdateInfo, addressInfo;

    TextView idClient;

    Spinner spinnerStatus;

    Button updateButton, backButton;

    String CURRENT_CLIENT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_page);

        idClient = findViewById(R.id.txtViewId);
        Intent intent = getIntent();
        CURRENT_CLIENT_ID = intent.getStringExtra("CURRENT_CLIENT_ID");
        idClient.setText(CURRENT_CLIENT_ID);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDataBase.class,
                "dbASODESUNIDOS2"
        ).allowMainThreadQueries().build();

        clients = new Clients(db);
        admin = new Admin(db);

        nameInfo = findViewById(R.id.txtInfoName);
        salaryInfo = findViewById(R.id.txtInfoSalary);
        phoneInfo = findViewById(R.id.txtInfoPhone);
        birthdateInfo = findViewById(R.id.txtInfoBirthdate);
        addressInfo = findViewById(R.id.txtInfoAddress);

        spinnerStatus = findViewById(R.id.spinnerInfoStatus);

        updateButton = findViewById(R.id.btnUpdateInfo);
        backButton = findViewById(R.id.btnInfoBack);

        spinnerStatus = findViewById(R.id.spinnerInfoStatus);
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,clients.getAllCivilStatus());

        spinnerStatus.setAdapter(adaptador1);

        ImageButton buttonDatePicker = findViewById(R.id.btnDatePickerInfo);
        buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PersonalInfoPage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                birthdateInfo.setText(day + "/" + (month + 1) + "/" + year);
                                birthdateInfo.setError(null);
                            }
                        }, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el valor seleccionado del spinner
                String selectedValue = parent.getItemAtPosition(position).toString();
                spinnerItemSelected = selectedValue;
                // Hacer algo con el valor seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        nameInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vista, boolean hasFocus) {
                if (!hasFocus) {
                    ValidateName(nameInfo);
                }
            }
        });

        salaryInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vista, boolean hasFocus) {
                if (!hasFocus) {
                    ValidateSalary(salaryInfo);
                }
            }
        });

        phoneInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vista, boolean hasFocus) {
                if (!hasFocus) {
                    ValidatePhoneNumber(phoneInfo);
                }
            }
        });

        addressInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vista, boolean hasFocus) {
                if (!hasFocus) {
                    ValidateAddress(addressInfo);
                }
            }
        });

        try {
            client = loadClient(CURRENT_CLIENT_ID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        nameInfo.setText(client.name);
        salaryInfo.setText(String.valueOf(client.salary));
        phoneInfo.setText(client.telefone_number);
        addressInfo.setText(client.address);
        birthdateInfo.setText(client.birthdate);
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
        boolean errorName = ValidateName(nameInfo);
        boolean errorSalary = ValidateSalary(salaryInfo);
        boolean PhoneNumber = ValidatePhoneNumber(phoneInfo);
        boolean BirthDate = ValidateBirthDate(birthdateInfo);
        boolean Address = ValidateAddress(addressInfo);
        return errorName && errorSalary && PhoneNumber &&
                BirthDate && Address;
    }

    private void updateInfoClient() throws Exception{
        String name = nameInfo.getText().toString();
        String id = CURRENT_CLIENT_ID;
        Double salary = Double.parseDouble(salaryInfo.getText().toString());
        String status = spinnerItemSelected;
        String phone = phoneInfo.getText().toString();
        String birthDate = birthdateInfo.getText().toString();
        String address = addressInfo.getText().toString();
        clients.UpdateClientInfo(id,name,salary,phone,birthDate,status,address);
    }

    public void btnUpdateClient(View vista){
        if(IsError()) {
            try{
                updateInfoClient();
                Toast.makeText(this, "Cliente Actualizado!", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Verificar que todos los campos tengan el formato correcto", Toast.LENGTH_SHORT).show();
        }
    }

    Client loadClient(String ClientId) throws Exception {
        return clients.getClientInfo(ClientId);
    }


}