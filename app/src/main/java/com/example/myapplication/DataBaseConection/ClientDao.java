package com.example.myapplication.DataBaseConection;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClientDao {
    @Insert
    void insertClients(Client... clients);

    @Query("SELECT * FROM client WHERE user = :username")
    Client getClientByUser( String username);

    @Query("SELECT * FROM client WHERE client_id = :client_id")
    Client getClientByUserByClientId( String client_id);

    @Query("SELECT * FROM client")
    List<Client> getAllClients();

    @Query("UPDATE client SET " +
            "name = :name,  " +
            "birthdate = :salary, " +
            "telefone_number = :telefone_number, " +
            "birthdate = :birthdate, " +
            "civil_status = :civil_status, " +
            "address = :address " +
            "WHERE client_id = :client_id")
    void updateClient(String client_id,String name,Double salary,String telefone_number,
                      String birthdate,String civil_status,String address);
}
