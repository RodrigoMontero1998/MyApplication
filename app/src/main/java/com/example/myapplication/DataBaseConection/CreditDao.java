package com.example.myapplication.DataBaseConection;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CreditDao {
    @Insert
    void insertCredit(Credit... credit);

    @Query("SELECT * FROM credit WHERE client = :client")
    List<Credit> getAllCreditsByClient(String client);

    @Query("SELECT * FROM credit WHERE credit_number = :credit_number")
    Credit getCreditsByNumber( String credit_number);

    @Query("UPDATE credit SET balance = :balance  WHERE credit_number = :credit_number")
    void updateBalance(String credit_number,Double balance);

    @Delete
    void deleteCreditByNumber(Credit credit);
}
