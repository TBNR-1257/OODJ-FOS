
package com.mycompany.fos_app.GUI;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class RunnerTasks {
    public static void displayValues(JTextField currentOrderTxt, JTextField locationTxt) {
       // File path for the deliverystatus.txt
       String deliveryStatusFilePath = "src/main/java/com/mycompany/fos_app/Data/order.txt";
       String runnerId = SessionManager.getInstance().getidInput();
       
       try (BufferedReader reader = new BufferedReader(new FileReader(deliveryStatusFilePath))) {
           String line;

           while ((line = reader.readLine()) != null) {
               String[] values = line.split(";");

               if (values.length >= 5 && values[0].trim().equalsIgnoreCase(runnerId)) {
                   // Set the 2nd column value in currentordertxt and the 3rd column in locationtxt
                   currentOrderTxt.setText(values[2].trim()); // 2nd column (Order ID)
                   locationTxt.setText(values[4].trim());    // 3rd column (Location)
                   return; // Stop after the first match
               }
           }

           // If no matching row is found, clear the text fields or show a message
           currentOrderTxt.setText("No match found");
           locationTxt.setText("No match found");

       } catch (IOException e) {
           e.printStackTrace();
           currentOrderTxt.setText("Error");
           locationTxt.setText("Error");
       }
   }
    
    
        public static void displayOrder(JTextField ordertxt, JTextField addresstxt, JTextField pricetxt ) {
       // File path for the deliverystatus.txt
       String deliveryStatusFilePath = "src/main/java/com/mycompany/fos_app/Data/delivery.txt";
       String runnerId = SessionManager.getInstance().getidInput();
       
       try (BufferedReader reader = new BufferedReader(new FileReader(deliveryStatusFilePath))) {
           String line;

           while ((line = reader.readLine()) != null) {
               String[] values = line.split(";");

               if (values.length >= 5 && values[0].trim().equalsIgnoreCase(runnerId)) {
                   // Set the 2nd column value in currentordertxt and the 3rd column in locationtxt
                   ordertxt.setText(values[2].trim()); // 2nd column (Order ID)
                   pricetxt.setText(values[3].trim());  // 3rd column (Price)
                   addresstxt.setText(values[4].trim());    // 4th column (Location)
                   return; // Stop after the first match
               }
           }

           // If no matching row is found, clear the text fields or show a message
           ordertxt.setText("No match found");
           addresstxt.setText("No match found");
           pricetxt.setText("No match found");

       } catch (IOException e) {
           e.printStackTrace();
           ordertxt.setText("Error");
           addresstxt.setText("Error");
           pricetxt.setText("Error");
           
       }
   }
    
    public static void completeOrder(JTextField currentordertxt) {
        String ordersFilePath = "src/main/java/com/mycompany/fos_app/Data/orders.txt";
        StringBuilder content = new StringBuilder();
        boolean matchFound = false;
        String orderID = currentordertxt.getText().trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                // Check if we have at least 8 columns and index[0] matches orderID
                if (values.length > 7 && values[0].trim().equalsIgnoreCase(orderID)) {
                    values[7] = "Completed";
                    matchFound = true;
                }
                // Reconstruct the line
                content.append(String.join(";", values)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "An error occurred while processing the orders file.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!matchFound) {
            javax.swing.JOptionPane.showMessageDialog(null, "No matching order ID found!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Write the content back to the file line by line
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ordersFilePath))) {
            String[] lines = content.toString().split("\n");
            for (String outputLine : lines) {
                writer.write(outputLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "An error occurred while writing to the orders file.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(null, "Order updated to Completed successfully!", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void completeDeliveryOrder(JTextField currentOrderTxt) {
        String deliveryFilePath = "src/main/java/com/mycompany/fos_app/Data/delivery.txt";
        StringBuilder content = new StringBuilder();
        boolean matchFound = false;
        String orderID = currentOrderTxt.getText().trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(deliveryFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");

                // Check if we have at least 3 columns and index[2] matches the orderID.
                if (values.length > 2 && values[2].trim().equalsIgnoreCase(orderID)) {
                    // Build a new row that only retains value[0] and value[1] set to "Available".
                    String newRow = values[0].trim() + ";" + "Available";
                    content.append(newRow).append("\n");
                    matchFound = true;
                } else {
                    // If not matching, write the row as-is.
                    content.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "An error occurred while processing the delivery file.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!matchFound) {
            javax.swing.JOptionPane.showMessageDialog(null, "No matching delivery record found!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Write the updated content back to the file line by line.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(deliveryFilePath))) {
            String[] lines = content.toString().split("\n");
            for (String outputLine : lines) {
                writer.write(outputLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "An error occurred while writing to the delivery file.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}

