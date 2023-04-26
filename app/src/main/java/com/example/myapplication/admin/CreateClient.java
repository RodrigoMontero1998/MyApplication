package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.myapplication.AppDataBase;
import com.example.myapplication.Logic.Clients;
import com.example.myapplication.R;

public class CreateClient extends AppCompatActivity {

    Spinner spinnerResult;
    AppDataBase db = null;
    Clients client;
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
    }
}