/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fos_app.GUI;

/**
 *
 * @author Rauf
 */
public class AddMenuItem {
    // Attributes
    private String itemId;
    private String name;
    private double price;
    private String description;
    private String category;
    private String availability;

    // Constructor
    public AddMenuItem(String itemId, String name, String description, double price, String category, String availability) {
        // Validation checks
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (description.length() > 255) {
            throw new IllegalArgumentException("Description cannot exceed 255 characters.");
        }

        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.availability = availability;
    }

    // Getters
    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
    
    public String getCategory() {
        return category;
    }

    public String getAvailability() {
         return availability;
    }

    // Setters with Validation
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public void setDescription(String description) {
        if (description.length() > 255) {
            throw new IllegalArgumentException("Description cannot exceed 255 characters.");
        }
        this.description = description;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
