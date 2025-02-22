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
    private double creditBalance;

    public Customer(String id, String name, String password, double creditBalance) {
        super(id, name, password);
        this.creditBalance = creditBalance;
    }

    public Customer(String id, String name, String password, String phone) {
        this(id, name, password, 0.0);
    }

    public double getCreditBalance() { return creditBalance; }
    public void setCreditBalance(double creditBalance) { this.creditBalance = creditBalance; }

    public String toFileString() {
        return String.format("%s;%s;%s;%.2f", id, name, password, creditBalance);
    }

    public static Customer fromFileString(String fileString) {
        String[] parts = fileString.split(";");
        return new Customer(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
    }
}
