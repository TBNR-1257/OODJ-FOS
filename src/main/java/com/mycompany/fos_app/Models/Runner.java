/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fos_app.Models;

/**
 *
 * @author User
 */
public class Runner extends User {
    
    public Runner(String id, String name, String password) {
        super(id, name, password);
    }

    public String toFileString() {
        return String.format("%s;%s;%s", id, name, password);
    }

    public static Runner fromFileString(String fileString) {
        String[] parts = fileString.split(";");
        return new Runner(parts[0], parts[1], parts[2]);
    }
}
