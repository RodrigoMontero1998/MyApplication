package com.example.myapplication.DataBaseConection;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CreditTypeDao {
    @Insert
    void insertCreditTypes(CreditType... CreditTypes);

    @Query("SELECT * FROM CreditType WHERE credit_type_id = :credit_type_id")
    CreditType getCreditType(Integer credit_type_id);

    @Query("SELECT * FROM CreditType")
    List<CreditType> getAllCreditTypes();
}
