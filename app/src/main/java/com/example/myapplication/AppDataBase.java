package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.DataBaseConection.Client;
import com.example.myapplication.DataBaseConection.ClientDao;
import com.example.myapplication.DataBaseConection.Credit;
import com.example.myapplication.DataBaseConection.CreditDao;
import com.example.myapplication.DataBaseConection.CreditType;
import com.example.myapplication.DataBaseConection.CreditTypeDao;
import com.example.myapplication.DataBaseConection.Saving;
import com.example.myapplication.DataBaseConection.SavingDao;
import com.example.myapplication.DataBaseConection.User;
import com.example.myapplication.DataBaseConection.UserDao;

@Database(
        entities = {User.class, Client.class, Saving.class, Credit.class, CreditType.class},
        version = 1
)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ClientDao clientDao();
    public abstract SavingDao savingDao();
    public abstract CreditDao creditDao();
    public abstract CreditTypeDao creditTypeDao();
}
