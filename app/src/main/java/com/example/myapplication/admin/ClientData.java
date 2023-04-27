package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AppDataBase;
import com.example.myapplication.DataBaseConection.Client;
import com.example.myapplication.Logic.Clients;
import com.example.myapplication.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDataBase.class,
                "dbASODESUNIDOS2"
        ).allowMainThreadQueries().build();

        clients = new Clients(db);

        setContentView(R.layout.activity_client_data);
        etClientID = findViewById(R.id.etClientId);
        etName = findViewById(R.id.etName);
        etSalary = findViewById(R.id.etSalary);
        etTelephoneNumber = findViewById(R.id.etTelephoneNumber);
        etBirhtdate = findViewById(R.id.etBirhtdate);
        etCivilStatus = findViewById(R.id.etCivilStatus);
        etAddress = findViewById(R.id.etAddress);
        etSearchClientById =  findViewById(R.id.etSearchClientById);

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
    }

    void cleanTableClient(){
        etClientID.setText("");
        etName.setText("");
        etSalary.setText("");
        etTelephoneNumber.setText("");
        etBirhtdate.setText("");
        etCivilStatus.setText("");
        etAddress.setText("");
    }

    public void btnSearchClient(View v){
        String idSearch = etSearchClientById.getText().toString();
        if(!idSearch.isEmpty()){
            try{
                Client clientSearch = loadClient(idSearch);
                loadTableClient(clientSearch);
            }catch (Exception e){
                cleanTableClient();
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            }
        }else{
            etSearchClientById.setError("Campo vacio");
        }
    }
}