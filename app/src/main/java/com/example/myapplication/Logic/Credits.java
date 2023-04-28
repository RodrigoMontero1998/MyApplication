package com.example.myapplication.Logic;

import com.example.myapplication.AppDataBase;
import com.example.myapplication.DataBaseConection.Credit;
import com.example.myapplication.DataBaseConection.CreditType;
import com.example.myapplication.DataBaseConection.Saving;

import java.util.List;
import java.util.stream.Collectors;

public class Credits {
    public AppDataBase db;
    public Credits(AppDataBase db){
        this.db=db;
    }
    public double calculateMonthlyPayment(double amount, int termInYears, double annualInterestRate) {
        double monthlyInterestRate = annualInterestRate / 12.0 / 100.0;
        int termInMonths = termInYears * 12;

        double monthlyPayment = (amount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -termInMonths));

        return Math.round(monthlyPayment * 100.0) / 100.0;
    }

    public List<CreditType> getAllCreditsType(){
        return db.creditTypeDao().getAllCreditTypes();
    }

    public boolean customerMeetsTheRequirements(double salary, Double monthlyPayment) {
        double partOfTheSalary = salary * 0.45;
        return (monthlyPayment <= partOfTheSalary);
    }

    public void AssignLoanToCustomer(String clientId,Integer creditTypeId,Integer termInYears,double amount,double salary) throws Exception {
        CreditType creditType = db.creditTypeDao().getCreditType(creditTypeId);
        Double annualInterestRate = creditType.interest_rate;
        Double monthlyPayment = calculateMonthlyPayment(amount,termInYears,annualInterestRate);
        if(customerMeetsTheRequirements(salary,monthlyPayment)){
            Credit newCredit= new Credit(clientId,monthlyPayment,0.00,creditType.credit_type_id);
            db.creditDao().insertCredit(newCredit);
        }else{
            throw new Exception("la cuota mensual es mayor al 45% del salario");
        }
    }

    public List<Credit> getAllCreditsByClient(String client){
        return db.creditDao().getAllCreditsByClient(client);
    }

    public String getCreditTypeByType(Integer type){
        return db.creditTypeDao().getAllCreditTypes().stream().filter(c -> c.credit_type_id.equals(type)).collect(Collectors.toList()).get(0).name;
    }

}
