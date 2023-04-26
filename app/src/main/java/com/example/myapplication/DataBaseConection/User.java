package com.example.myapplication.DataBaseConection;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    public String username;
    @NonNull
    public String password;
    @NonNull
    public Integer rol;

    public User(@NonNull String username,String password,Integer rol){
        this.username=username;
        this.password=password;
        this.rol=rol;
    }

}

