package com.example.myapplication.DataBaseConection;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavingDao {
    @Insert
    void insertSaving(Saving... savings);

    @Query("SELECT * FROM saving WHERE client = :client")
    List<Saving> getAllSavingsByClient(String client);

    @Query("SELECT * FROM saving WHERE saving_number = :saving_number")
    Saving getSavingsByNumber( String saving_number);

    @Query("UPDATE saving SET balance = :balance  WHERE saving_number = :saving_number")
    void updateBalance(String saving_number,Double balance);

    @Query("UPDATE saving " +
            "SET monthly_amount = :monthly_amount, " +
            "is_active = :is_active " +
            "WHERE saving_number = :saving_number")
    void updateSaving(String saving_number,Double monthly_amount,Boolean is_active);

    @Delete
    void deleteSavingByNumber(Saving saving);
}
