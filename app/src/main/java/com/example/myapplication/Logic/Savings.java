package com.example.myapplication.Logic;

import android.widget.Toast;

import com.example.myapplication.AppDataBase;
import com.example.myapplication.DataBaseConection.Saving;
import com.example.myapplication.MainActivity;

import java.util.List;

public class Savings {

    public AppDataBase db;

    public Savings(AppDataBase db){
        this.db=db;
    }

    public List<Saving> getAllSavingsByClient(String client){
        return db.savingDao().getAllSavingsByClient(client);
    }

    public void AllocateSavingToClient(String clientId, Double monthly_amount,
                                               Double balance, String savingType,Boolean isActive) throws Exception {
        Saving saving = new Saving(clientId,
                monthly_amount,balance,savingType,isActive);
        try {
            db.savingDao().insertSaving(saving);
        }catch (Exception e){
            throw new Exception("Error al asignar el ahorro al cliente");
        }
    }

    public void depositToSavings(Integer saving_number,Double amount) throws Exception {
        Saving saving = GetSavingByNumber(saving_number);
        Double balance = saving.balance;
        Double newBalance = balance + amount;
        try {
            db.savingDao().updateBalance(String.valueOf(saving_number),newBalance);
        }catch (Exception e){
            throw new Exception("Error al depositar al ahorro");
        }
    }

    public void withdrawFromSavings(Integer saving_number,
                                         Double amount) throws Exception {
        Saving saving = GetSavingByNumber(saving_number);
        Double balance = saving.balance;
        Double newBalance = balance - amount;
        if (newBalance < 0){
            throw new Exception("Saldo Insuficiente");
        }
        try {
            db.savingDao().updateBalance(String.valueOf(saving_number),newBalance);
        }catch (Exception e){
            throw new Exception("Error al intentar retirar del ahorro");
        }
    }

    public Saving GetSavingByNumber(Integer saving_number) throws Exception {
        try {
            return db.savingDao().getSavingsByNumber(String.valueOf(saving_number));
        }catch (Exception e){
            throw new Exception("Error al intentar retirar del ahorro");
        }
    }

}
