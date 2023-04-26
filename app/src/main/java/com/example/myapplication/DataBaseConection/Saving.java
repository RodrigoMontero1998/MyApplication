package com.example.myapplication.DataBaseConection;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Client.class,
        parentColumns = "client_id",
        childColumns = "client",
        onDelete = ForeignKey.CASCADE)
})
public class Saving {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer saving_number;
    @NonNull
    public String client;
    @NonNull
    public Double monthly_amount;
    @NonNull
    public Double balance;
    @NonNull
    public String saving_type;
    @NonNull
    public boolean is_active;

    public Saving(@NonNull String client,@NonNull Double monthly_amount,
                  @NonNull Double balance,@NonNull String saving_type,@NonNull Boolean is_active){
        this.client = client;
        this.monthly_amount = monthly_amount;
        this.balance = balance;
        this.saving_type = saving_type;
        this.is_active = is_active;
    }
}
