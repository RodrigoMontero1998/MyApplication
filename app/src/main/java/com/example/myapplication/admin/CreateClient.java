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

import com.example.myapplication.AppDataBase;
import com.example.myapplication.Logic.Clients;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.Calendar;

public class CreateClient extends AppCompatActivity {

    Spinner spinnerResult;
    AppDataBase db = null;
    Clients client;

    EditText txtClientBirthdate;
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

        spinnerResult = findViewById(R.id.spinnerClientStatus);
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,client.getAllCivilStatus());

        spinnerResult.setAdapter(adaptador1);

        txtClientBirthdate = findViewById(R.id.txtClientBirthdate);
        txtClientBirthdate.setKeyListener(null);
        Button buttonDatePicker = findViewById(R.id.buttonDatePicker);
        buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea el diálogo personalizado
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateClient.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                // Actualiza el texto del EditText con la fecha seleccionada
                                txtClientBirthdate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                // Muestra el diálogo personalizado
                datePickerDialog.show();
            }
        });
    }
}