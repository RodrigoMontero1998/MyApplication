package com.example.myapplication.DataBaseConection;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Client.class,
                    parentColumns = "client_id",
                    childColumns = "client",
                    onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = CreditType.class,
                parentColumns = "credit_type_id",
                childColumns = "credit_type",
                onDelete = ForeignKey.CASCADE)
})
public class Credit {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer credit_number;
    @NonNull
    public String client;
    @NonNull
    public Double monthly_fee;
    @NonNull
    public Double balance;
    @NonNull
    public Integer credit_type;

    public Credit(@NonNull String client, @NonNull Double monthly_fee,
                  @NonNull Double balance, @NonNull Integer credit_type){
        this.client = client;
        this.monthly_fee = monthly_fee;
        this.balance = balance;
        this.credit_type = credit_type;
    }
}
