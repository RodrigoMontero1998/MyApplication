package com.example.myapplication.Logic;

import androidx.room.Room;

import com.example.myapplication.AppDataBase;
import com.example.myapplication.DataBaseConection.Client;

public class Login {
    public AppDataBase db;

    public Login(AppDataBase db){
        this.db=db;
    }

    public String Login(String username, String password) throws Exception {
        try {
            Integer rol = db.userDao().getRol(username, password);
            if(rol == 1 || rol == 2)
                return username;
            else{
                throw new Exception("Credenciales incorrectos");
            }
        }catch (Exception e){
            throw new Exception("Credenciales incorrectos");
        }
    }

    public Client getCurrentClient(String currentUser){
        if(!currentUser.equals("Admin") && !currentUser.equals(null)){
            return db.clientDao().getClientByUser(currentUser);
        }
        return null;
    }
}
