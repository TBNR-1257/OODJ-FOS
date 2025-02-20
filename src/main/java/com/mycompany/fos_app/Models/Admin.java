/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fos_app.Models;

/**
 *
 * @author User
 */
public class Admin extends User {


    public Admin(String id, String email, String password) {
        super(id, email, password);
    }
    
//    public Admin(String id, String email, String password) {
//        this(id, email, password); // Initialize with 0 credit
//    }

    // Method to format customer data for file storage
    public String toFileString() {
        return String.format("%s;%s;%s", 
            id, email, password);
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
