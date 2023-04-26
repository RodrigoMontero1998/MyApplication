package com.example.myapplication.Logic;

import com.example.myapplication.AppDataBase;
import com.example.myapplication.DataBaseConection.Client;

import java.util.ArrayList;
import java.util.Arrays;

public class Clients {
    public AppDataBase db;

    public Clients(AppDataBase db){
        this.db=db;
    }

    public void UpdateClientInfo( String clientId, String name, Double salary,
                               String telefoneNumber, String birthdate, String civilStatus, String address) throws Exception {
        try {
            db.clientDao().updateClient(clientId,name,
                    salary, telefoneNumber, birthdate, civilStatus, address);
        }catch (Exception e){
            throw new Exception("Error al intentar actualizar el cliente");
        }
    }

    public ArrayList<String> getAllCivilStatus() {
        return new ArrayList<>(Arrays.asList("Soltero(a)", "Casado(a)","Divorsiado", "Viudo(a)", "Union libre"));
    }

    public Client getClientInfo(String clientId) throws Exception {
        try {
            return db.clientDao().getClientByUserByClientId(clientId);
        }catch (Exception e){
            throw new Exception("Error al intentar taer la informacion del estudiante");
        }
    }
}
