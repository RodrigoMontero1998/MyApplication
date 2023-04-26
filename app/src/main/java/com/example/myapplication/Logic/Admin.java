package com.example.myapplication.Logic;



import com.example.myapplication.AppDataBase;
import com.example.myapplication.DataBaseConection.Client;
import com.example.myapplication.DataBaseConection.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin {

    public AppDataBase db;

    public Admin(AppDataBase db){
        this.db=db;
    }

    public List<Client> getAllClients(){
        return db.clientDao().getAllClients();
    }

    public String SaveNewUser(String username, String password, Integer rol) throws Exception {
        User user = new User(username, password, rol);
        try {
            db.userDao().insertUsers(user);
            return username;
        }catch (Exception e){
            throw new Exception("Error al intentar ingresar el usuario");
        }
    }

    public void SaveNewClient( String clientId, String name, Double salary,
                                      String telefoneNumber, String birthdate, String civilStatus, String address) throws Exception {
        String vUsername = SaveNewUser(clientId,"1234",2);
        try {
            db.clientDao().insertClients(new Client(clientId, vUsername,name,
                    salary, telefoneNumber, birthdate, civilStatus, address));
            AllocateSavingToNewClient(clientId,0.00,0.00,false);
        }catch (Exception e){
            throw new Exception("Error al intentar registrar el cliente");
        }
    }

    public void AllocateSavingToNewClient(String clientId, Double monthly_amount,
                                          Double balance,Boolean isActive) throws Exception {
        Savings saving = new Savings(db);
        ArrayList<String> savingTypes = getSavingTypes();
        for (String savingType:savingTypes) {
            saving.AllocateSavingToClient(clientId,monthly_amount,balance,savingType,isActive);
        }
    }

    public ArrayList<String> getSavingTypes() {
       return new ArrayList<>(Arrays.asList("Navideño", "Escolar", "Marchamo", "Extraordinario"));
    }

}
