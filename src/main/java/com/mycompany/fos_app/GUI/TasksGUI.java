/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.fos_app.GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;  
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;


/**
 *
 * @author khail
 */
public class TasksGUI extends javax.swing.JFrame {
    

    private DefaultTableModel model = new DefaultTableModel();
    private String[] columnNames = {"Delivery ID", "Order ID", "Location", "Earnings", "Status"};
    
    public TasksGUI() {
        initComponents();
        ordertxt.setEditable(false);
        addresstxt.setEditable(false);
        pricetxt.setEditable(false);
        RunnerTasks.displayOrder(ordertxt, addresstxt, pricetxt);
        setTitle("Tasks Page");
        
        // Initialize table model and set column names
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        

        // Create a TableRowSorter to allow sorting of rows
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        

        // Load the data from the text file and add it to the table model
        try {
            FileReader fr = new FileReader("src/main/java/com/mycompany/fos_app/Data/delivery.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                if (values.length >= 5 && values[4].trim().equalsIgnoreCase("Unassigned")) {
                    model.addRow(values); 
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Sort the table by the "Earnings" column (4th column, index 3) in descending order
        sorter.setSortKeys(java.util.Collections.singletonList(new RowSorter.SortKey(3, SortOrder.DESCENDING)));
        sorter.sort(); // Apply the sort
    }
    
   
    public static void AcceptBtnOrder(String orderID) {
        String ordersFilePath = "src/main/java/com/mycompany/fos_app/Data/orders.txt";
        StringBuilder content = new StringBuilder();
        boolean matchFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");

                // Check if values[0] matches the provided orderID
                if (values.length > 7 && values[0].trim().equalsIgnoreCase(orderID.trim())) {
                    // Update index [7] to "Out for Delivery"
                    values[7] = "Out for Delivery";
                    matchFound = true;
                }
                // Reconstruct the line and append to content
                content.append(String.join(";", values)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while processing the file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!matchFound) {
            JOptionPane.showMessageDialog(null, "No matching order ID found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Write back line by line using BufferedWriter.newLine()
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ordersFilePath))) {
            String[] lines = content.toString().split("\n");
            for (String outputLine : lines) {
                writer.write(outputLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while writing to the file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, "Order status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void AcceptBtnDelivery(String orderID) {
        String deliveryFilePath = "src/main/java/com/mycompany/fos_app/Data/delivery.txt";
        StringBuilder content = new StringBuilder();
        boolean matchFound = false;

        // Read the file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(deliveryFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                // Check if index [2] matches the provided orderID and ensure there are enough indices
                if (values.length > 2 && values[2].trim().equalsIgnoreCase(orderID.trim())) {
                    // Change index [1] to "Occupied"
                    if (values.length > 1) {
                        values[1] = "Occupied";
                        matchFound = true;
                    }
                }
                // Reconstruct the line and append a newline
                content.append(String.join(";", values)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while processing the delivery file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!matchFound) {
            JOptionPane.showMessageDialog(null, "No matching order ID found in delivery file!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Write back the file line by line using BufferedWriter.newLine()
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(deliveryFilePath))) {
            String[] lines = content.toString().split("\n");
            for (String outputLine : lines) {
                writer.write(outputLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while writing to the delivery file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    
    private void reassignDeliveryRecord(JTextField ordertxt) {
        String deliveryFilePath = "src/main/java/com/mycompany/fos_app/Data/delivery.txt";
        String targetOrderID = ordertxt.getText().trim();

        // Read file into a list.
        java.util.List<String> lines = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(deliveryFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error reading delivery file", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find the detailed row (where value[2] matches targetOrderID).
        int detailedRowIndex = -1;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(";");
            if (parts.length >= 3 && parts[2].trim().equalsIgnoreCase(targetOrderID)) {
                detailedRowIndex = i;
                break;
            }
        }
        if (detailedRowIndex == -1) {
            javax.swing.JOptionPane.showMessageDialog(null, "No row found with order ID '" + targetOrderID + "' in column 3.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find the first row that has "Available" in value[1] and store its value[0].
        int availableRowIndex = -1;
        String availableID = ""; // available row's first field
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(";");
            if (parts.length >= 2 && parts[1].trim().equalsIgnoreCase("Available")) {
                availableRowIndex = i;
                availableID = parts[0].trim();
                break;
            }
        }
        if (availableRowIndex == -1) {
            javax.swing.JOptionPane.showMessageDialog(null, "No row with 'Available' in column 2 found.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Retrieve the detailed row and split it.
        String detailedRow = lines.get(detailedRowIndex);
        String[] detailParts = detailedRow.split(";");
        if(detailParts.length < 5) {
            javax.swing.JOptionPane.showMessageDialog(null, "The detailed row does not have enough fields.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Build the new detailed row using the available row's value[0].
        String newDetailedRow = availableID + ";" 
                + "Assigned" + ";" 
                + detailParts[2].trim() + ";" 
                + detailParts[3].trim() + ";" 
                + detailParts[4].trim();

        // Build the simplified "Available" row for the detailed row's original position 
        // using the detailed row's original value[0] (i.e. detailParts[0]).
        String newSimpleRow = detailParts[0].trim() + ";" + "Available";

        // Swap the rows.
        lines.set(availableRowIndex, newDetailedRow);
        lines.set(detailedRowIndex, newSimpleRow);

        // Write back the file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(deliveryFilePath))) {
            for (String outLine : lines) {
                writer.write(outLine);
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error writing delivery file", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(null, "Different runner is assigned", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    
    private void countIncrease(JTextField ordertxt) {
        String target = ordertxt.getText().trim();

        // Step 1: Open delivery.txt and find the row where value[2] matches the target.
        String deliveryFilePath = "src/main/java/com/mycompany/fos_app/Data/delivery.txt";
        String runnerID = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(deliveryFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3 && parts[2].trim().equalsIgnoreCase(target)) {
                    // Store the value from column 0
                    runnerID = parts[0].trim();
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error reading delivery file", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (runnerID == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "No matching row found in delivery.txt for order: " + target, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 2: Open orders.txt and read all lines.
        String ordersFilePath = "src/main/java/com/mycompany/fos_app/Data/orders.txt";
        java.util.List<String> orderLines = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orderLines.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error reading orders file", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 3: Find the order row where value[0] equals target, update value[11] with runnerID,
        // and increment value[12] by 1.
        boolean orderFound = false;
        for (int i = 0; i < orderLines.size(); i++) {
            String[] parts = orderLines.get(i).split(";");
            if (parts.length > 0 && parts[0].trim().equalsIgnoreCase(target)) {
                // Ensure there are at least 13 fields (indices 0 to 12)
                if (parts.length < 13) {
                    String[] newParts = new String[13];
                    System.arraycopy(parts, 0, newParts, 0, parts.length);
                    for (int j = parts.length; j < 13; j++) {
                        newParts[j] = "";
                    }
                    parts = newParts;
                }
                // Update column 12 (index 11) with runnerID.
                parts[11] = runnerID;

                // Update column 13 (index 12): increment its numeric value by 1.
                int count = 0;
                try {
                    count = Integer.parseInt(parts[12].trim());
                } catch (NumberFormatException e) {
                    // If it is not a valid number, we'll assume it's 0.
                }
                count++;
                parts[12] = String.valueOf(count);

                // Rebuild the line.
                orderLines.set(i, String.join(";", parts));
                orderFound = true;
                break;
            }
        }

        if (!orderFound) {
            javax.swing.JOptionPane.showMessageDialog(null, "No matching order found in orders.txt for order: " + target, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 4: Write the updated orders.txt back to disk.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ordersFilePath))) {
            for (String outLine : orderLines) {
                writer.write(outLine);
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error writing orders file", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    private void cancelOrdersWithCount2() {
        String ordersFilePath = "src/main/java/com/mycompany/fos_app/Data/orders.txt";
        java.util.List<String> lines = new java.util.ArrayList<>();

        // Step 1: Read all lines from orders.txt
        try (BufferedReader br = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error reading orders file: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 2: Process each line, checking if value[12] equals "2". If so, change value[7] to "Cancelled"
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(";");
            // Ensure there are enough fields. We need index 7 and index 12.
            if (parts.length > 12 && parts.length > 7) {
                if (parts[12].trim().equals("2")) {  // exactly "2", adjust if needed to ignore case or spaces
                    parts[7] = "Cancelled";
                    // Rebuild the line.
                    lines.set(i, String.join(";", parts));
                }
            }
        }

        // Step 3: Write back the updated lines to orders.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ordersFilePath))) {
            for (String outLine : lines) {
                writer.write(outLine);
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error writing orders file: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
    }


    
    
    
    
    
    
    

        


    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backbtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        ordertxt = new javax.swing.JTextField();
        pricetxt = new javax.swing.JTextField();
        addresstxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rejectbtn = new javax.swing.JButton();
        acceptbtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backbtn.setBackground(new java.awt.Color(204, 204, 204));
        backbtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        backbtn.setText("Back");
        backbtn.setFocusPainted(false);
        backbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backbtnActionPerformed(evt);
            }
        });
        getContentPane().add(backbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        jLabel1.setText("Tasks");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 46, -1, -1));

        ordertxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordertxtActionPerformed(evt);
            }
        });
        getContentPane().add(ordertxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 136, 97, -1));
        getContentPane().add(pricetxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 227, 71, -1));

        addresstxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addresstxtActionPerformed(evt);
            }
        });
        getContentPane().add(addresstxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 183, 210, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Order ID : ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 132, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Price :  ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 223, 60, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Address : ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 179, 83, -1));

        rejectbtn.setBackground(new java.awt.Color(255, 204, 204));
        rejectbtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rejectbtn.setText("Reject");
        rejectbtn.setFocusPainted(false);
        rejectbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectbtnActionPerformed(evt);
            }
        });
        getContentPane().add(rejectbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 321, -1, -1));

        acceptbtn.setBackground(new java.awt.Color(204, 255, 204));
        acceptbtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        acceptbtn.setText("Accept");
        acceptbtn.setFocusPainted(false);
        acceptbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptbtnActionPerformed(evt);
            }
        });
        getContentPane().add(acceptbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(418, 321, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 420));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbtnActionPerformed
        this.dispose();
        MainMenuGUI mainmenu = new MainMenuGUI();
        mainmenu.setVisible(true);
    }//GEN-LAST:event_backbtnActionPerformed

    private void addresstxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addresstxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addresstxtActionPerformed

    private void rejectbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectbtnActionPerformed
        reassignDeliveryRecord(ordertxt);
        countIncrease(ordertxt);
        ordertxt.setText("");
        addresstxt.setText("");
        pricetxt.setText("");
        cancelOrdersWithCount2();
        
    }//GEN-LAST:event_rejectbtnActionPerformed

    private void ordertxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordertxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ordertxtActionPerformed

    private void acceptbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptbtnActionPerformed
        String orderID = ordertxt.getText();
        AcceptBtnOrder(orderID);
        AcceptBtnDelivery(orderID);
        ordertxt.setText("");
        addresstxt.setText("");
        pricetxt.setText("");
        
    }//GEN-LAST:event_acceptbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TasksGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TasksGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TasksGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TasksGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TasksGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptbtn;
    private javax.swing.JTextField addresstxt;
    private javax.swing.JButton backbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField ordertxt;
    private javax.swing.JTextField pricetxt;
    private javax.swing.JButton rejectbtn;
    // End of variables declaration//GEN-END:variables
}
