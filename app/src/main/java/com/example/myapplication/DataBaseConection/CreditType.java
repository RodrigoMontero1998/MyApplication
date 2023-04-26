package com.example.myapplication.DataBaseConection;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CreditType {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer credit_type_id;
    @NonNull
    public Double interest_rate;
    @NonNull
    public String name;

    public CreditType(@NonNull Double interest_rate, @NonNull String name){
        this.interest_rate=interest_rate;
        this.name=name;
    }
}

