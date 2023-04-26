package com.example.myapplication.DataBaseConection;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insertUsers(User... users);

    @Query("SELECT rol FROM user WHERE username = :username and password = :password")
    Integer getRol(String username,String password);
}
