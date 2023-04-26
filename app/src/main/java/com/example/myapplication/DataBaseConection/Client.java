package com.example.myapplication.DataBaseConection;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "username",
        childColumns = "user",
        onDelete = ForeignKey.CASCADE)
})
public class Client {
    @PrimaryKey
    @NonNull
    public String client_id;
    @NonNull
    public String user;
    @NonNull
    public String  name;
    @NonNull
    public Double salary;
    @NonNull
    public String telefone_number;
    @NonNull
    public String birthdate;
    @NonNull
    public String civil_status;
    @NonNull
    public String address;

    public Client(@NonNull String client_id,@NonNull String user,@NonNull String name,@NonNull Double salary,
                  @NonNull String telefone_number,@NonNull String birthdate,@NonNull String civil_status,@NonNull String address){
        this.client_id=client_id;
        this.user=user;
        this.name=name;
        this.salary=salary;
        this.telefone_number=telefone_number;
        this.birthdate=birthdate;
        this.civil_status=civil_status;
        this.address=address;
    }
}
