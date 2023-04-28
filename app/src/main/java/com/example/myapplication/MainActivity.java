package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataBaseConection.Client;
import com.example.myapplication.DataBaseConection.Credit;
import com.example.myapplication.DataBaseConection.CreditType;
import com.example.myapplication.DataBaseConection.Saving;
import com.example.myapplication.Logic.*;
import com.example.myapplication.admin.AdminPage;
import com.example.myapplication.client.ClientPage;

public class MainActivity extends AppCompatActivity {

    AppDataBase db = null;

    String CURRENT_USER = null;
    Client CURRENT_CLIENT = null;

    Admin admin = null;
    Credits credits = null;
    Login login= null;
    Savings savings = null;

    EditText username;
    EditText password;

    Button buttonLogin;
    Button buttonClose;


    TextView tvClients;
    TextView  tvAhorros;
    TextView  tvLoginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDataBase.class,
                "dbASODESUNIDOS2"
        ).allowMainThreadQueries().build();

        admin = new Admin(db);
        credits = new Credits(db);
        login= new Login(db);
        savings = new Savings(db);

        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        buttonClose = findViewById(R.id.btnClose);
        tvLoginError= findViewById(R.id.tvLoginError);

        buttonLogin.setOnClickListener(v -> LoginMain());
        buttonClose.setOnClickListener(v -> finishAffinity());

        //createAdminUser();
        //createClients();

        //getSavingByClients();

        //InsertTypeCredits();
        //getTypeCredits();

       // getSavingByClients();

        //AssignLoanToCustomers();
       // LoginMain();

      //  if(CURRENT_CLIENT != null){
       //     getSavingCurrentClient();
       // }
       // getSavingByClients();
    }

    @Override
    public void finishAffinity() {
        super.finishAffinity();
    }

    public void createAdminUser(){
        try {
            CURRENT_USER = admin.SaveNewUser("Admin","1234",1);
        } catch (Exception e) {
            tvClients.setText(e.getMessage());
        }
    }

    public void createClients(){
        try {
            admin.SaveNewClient("702600508","Rodrigo",
                                5000.00,"86411100","09/05/1998",
                                "Soltero(a)","Heredia");
            admin.SaveNewClient("102600508","Saray",
                    5000.00,"86411100","09/05/1998",
                    "Soltero(a)","Heredia");
            admin.SaveNewClient("202600508","Joselyn",
                    5000.00,"86411100","09/05/1998",
                    "Soltero(a)","Heredia");

        } catch (Exception e) {
            tvAhorros.setText(e.getMessage());
        }
    }

    public void InsertTypeCredits(){
        try {
            db.creditTypeDao().insertCreditTypes(new CreditType(0.075,"Hipotecario"));
            db.creditTypeDao().insertCreditTypes(new CreditType(0.8,"Educación"));
            db.creditTypeDao().insertCreditTypes(new CreditType(0.10,"Personal"));
            db.creditTypeDao().insertCreditTypes(new CreditType(0.12,"Viajes"));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void LoginMain(){
        try{
            Intent intent;
            String user = username.getText().toString();
            String pass = password.getText().toString();
            if(isEmpty(user, pass)) {
                CURRENT_USER = login.Login(user, pass);
                //tvCurrentUser.setText( CURRENT_USER);

                if (CURRENT_USER.equals("Admin")) {
                    intent = new Intent(this, AdminPage.class);
                    Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    CURRENT_CLIENT = login.getCurrentClient(CURRENT_USER);
                    intent = new Intent(this, ClientPage.class);
                    intent.putExtra("CURRENT_CLIENT_ID", CURRENT_CLIENT.client_id);
                    Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
            else
                Toast.makeText(getApplicationContext(), "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            tvLoginError.setText(e.getMessage());
            Toast.makeText(getApplicationContext(), "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isEmpty(String user, String pass){
        if (user.isEmpty()){
            username.setError("El campo usuario está vacío");
            return false;
        }
        else if (pass.isEmpty()) {
            password.setError("El campo contraseña está vacío");
            return false;
        }
        return true;
    }

    public void getSavingCurrentClient(){
        try {
            String clients = "";
            Client c = CURRENT_CLIENT;
            clients = clients + c.name +" " + c.client_id + " "+ c.salary+"\nAHORROS\n";

            for (Saving s:savings.getAllSavingsByClient(c.client_id)) {
                clients = clients + "\t" + s.saving_type +"\n";
            }
            clients = clients + "CREDITOS\n";
            for (Credit ct:credits.getAllCreditsByClient(c.client_id)) {
                clients = clients + "\t" + ct.credit_number + " " + ct.credit_type + " " + ct.monthly_fee+"\n";
            }
            tvClients.setText( clients);
        } catch (Exception e) {
            tvAhorros.setText(e.getMessage());
        }
    }

}