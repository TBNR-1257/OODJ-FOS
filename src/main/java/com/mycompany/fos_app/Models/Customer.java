/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fos_app.Models;

/**
 *
 * @author User
 */
public class Customer extends User {
    private String phone;
    private double creditBalance;

    public Customer(String id, String email,String password, 
                   String phone, double creditBalance) {
        super(id, email, password);
        this.phone = phone;
        this.creditBalance = creditBalance;
    }
    
    public Customer(String id, String email, String password, String phone) {
        this(id, email, password, phone, 0.0); // Initialize with 0 credit
    }

    public double getCreditBalance() { 
        return creditBalance; 
    }
    public void setCreditBalance(double creditBalance) { 
        this.creditBalance = creditBalance; 
    }

    // Method to format customer data for file storage
    public String toFileString() {
        return String.format("%s;%s;%s;%s;%.2f", 
            id, email, password, phone, creditBalance);
    }

    // Method to create Customer object from file string
    public static Customer fromFileString(String fileString) {
        String[] parts = fileString.split(";");
        return new Customer(
            parts[0], // id
            parts[1], // email
            parts[2], // password
            parts[3], // phone
            Double.parseDouble(parts[4]) // creditBalance
        );
    }
}
