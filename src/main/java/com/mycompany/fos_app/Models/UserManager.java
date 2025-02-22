/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fos_app.Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author User
 */
public class UserManager {
    
    public static void addUser(String id, String name, String password, String role) {
        String filePath = "src/main/java/com/mycompany/fos_app/Data/" + role.toLowerCase() + ".txt";
        User user = null;

        switch (role.toLowerCase()) {
            case "customer":
                user = new Customer(id, name, password, 0.0);
            break;
            case "admin":
                user = new Admin(id, name, password);
                break;
            case "vendor":
                user = new Vendor(id, name, password);
                break;
            case "runner":
                user = new Runner(id, name, password);
                break;
            case "manager":
                user = new Manager(id, name, password);
                break;
            default:
                System.out.println("Invalid role!");
                return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.toFileString());
            writer.newLine();
            System.out.println("User added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

}
