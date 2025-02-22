/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.fos_app.GUI;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Rauf
 */
public class AcceptedOrderManagementForm extends javax.swing.JFrame {

    private DefaultTableModel model = new DefaultTableModel();
    private final String[] columnName = {"Order ID", "Customer Name", "Items", "Total Amount", 
        "Order Type", "Address", "Contact Number", "Status", "Date", "Customer ID", "Vendor ID"};
    private int row = -1;
    private List<String> allOrders = new ArrayList<>();
    private List<String> vendorOrders = new ArrayList<>();

    // Creates new form OrderManagementForm
    public AcceptedOrderManagementForm() {
        String vendorId = SessionManager.getInstance().getidInput();
        initComponents(); // Initialize components first!
        model.setColumnIdentifiers(columnName);

        loadOrders(vendorId); 
        refreshTable(vendorId);// Load orders based on the vendor ID // Load orders when form initializes
        ordersTable.setModel(model);  // Display orders in the table
        
        // Hide the columns with Vendor ID and Customer ID after table is set up
        TableColumnModel columnModel = ordersTable.getColumnModel();
        columnModel.getColumn(9).setMinWidth(0);
        columnModel.getColumn(9).setMaxWidth(0);
        columnModel.getColumn(9).setWidth(0);
        columnModel.getColumn(9).setResizable(false);

        columnModel.getColumn(10).setMinWidth(0);
        columnModel.getColumn(10).setMaxWidth(0);
        columnModel.getColumn(10).setWidth(0);
        columnModel.getColumn(10).setResizable(false);
        

        // Add row selection listener for table
        ordersTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            row = ordersTable.getSelectedRow();  // Update selected row index
        });
        
        orderIdField.setEditable(false);
        customerNameField.setEditable(false);
        orderItemsField.setEditable(false);
        totalAmtField.setEditable(false);
        deliveryTimeField.setEditable(false);
        deliveryAddressField.setEditable(false);
        contactNumField.setEditable(false);
        orderStatusField.setEditable(false);
        dateField.setEditable(false);

    }
    
    private void loadOrders(String vendorId) {
        try {
            FileReader fr = new FileReader("src/main/java/com/mycompany/fos_app/Data/orders.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            allOrders.clear(); // Clear previous data
            vendorOrders.clear(); // Clear vendor specific orders
            model.setRowCount(0); // Clear the JTable model

            while ((line = br.readLine()) != null) {
                allOrders.add(line); // Store the line in allOrders
                String[] orderDetails = line.split(";", -1); // Split with -1 to keep empty fields

                // Ensure the line has at least 11 fields (including Customer ID and Vendor ID)
                if (orderDetails.length < 11) {
                    System.err.println("Invalid line: " + line); // Log invalid lines for debugging
                    continue; // Skip this line
                }

                // Only display "Accepted" or "Prepared" status orders, with conditions for "Delivery"
                if (orderDetails[10].equalsIgnoreCase(vendorId) && orderDetails[7].equalsIgnoreCase("Accepted") || orderDetails[7].equalsIgnoreCase("Prepared")) {
                    // Check the order type (if it's "Delivery", handle the "Prepared" status separately)
                    if (orderDetails[10].equalsIgnoreCase(vendorId) && orderDetails[7].equalsIgnoreCase("Prepared") && orderDetails[4].equalsIgnoreCase("Delivery")) {
                        // Do not add this row if status is "Prepared" and order type is "Delivery"
                        continue;
                    }
                    vendorOrders.add(line); // Add to vendor-specific orders
                    // Only add the row if the status is "Accepted" or if "Prepared" and the order type is not "Delivery"
                    String[] orderData = {
                        orderDetails[0], // Order ID
                        orderDetails[1], // Customer Name
                        orderDetails[2], // Items
                        orderDetails[3], // Total Price
                        orderDetails[4], // Order Type
                        orderDetails[5], // Address
                        orderDetails[6], // Contact Number
                        orderDetails[7], // Status
                        orderDetails[8], // Date
                        orderDetails[9], // Customer ID
                        orderDetails[10] // Vendor ID
                    };

                    model.addRow(orderData); // Add row for valid orders
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void saveToFile() {
        try {
            FileWriter fw = new FileWriter("src/main/java/com/mycompany/fos_app/Data/orders.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (String orderLine : allOrders) { // Loop through all orders in the `allOrders` list
                String[] orderDetails = orderLine.split(";", -1);

                // Match each order in `allOrders` with the corresponding JTable row for updates
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (orderDetails[0].equals(model.getValueAt(i, 0).toString())) { 
                        // Rebuild the order line based on the JTable data
                        StringBuilder updatedLine = new StringBuilder();

                        updatedLine.append(model.getValueAt(i, 0)) // Order ID
                                   .append(";")
                                   .append(model.getValueAt(i, 1)) // Customer Name
                                   .append(";")
                                   .append(model.getValueAt(i, 2)) // Items
                                   .append(";")
                                   .append(model.getValueAt(i, 3)) // Total Amount
                                   .append(";")
                                   .append(model.getValueAt(i, 4)) // Order Type
                                   .append(";")
                                   .append(model.getValueAt(i, 5)) // Address
                                   .append(";")
                                   .append(model.getValueAt(i, 6)) // Contact Number
                                   .append(";")
                                   .append(model.getValueAt(i, 7)) // Status
                                    .append(";")
                                   .append(model.getValueAt(i, 8)) // Date
                                    .append(";")
                                   .append(model.getValueAt(i, 9)) // Customer ID
                                    .append(";")
                                   .append(model.getValueAt(i, 10)); // Vendor ID

                        // If the order is assigned to a runner, append "; ;0"
                        if (model.getValueAt(i, 7).toString().equals("Prepared") && 
                            model.getValueAt(i, 4).toString().equals("Delivery")) {
                            updatedLine.append("; ;0");
                        }

                        orderLine = updatedLine.toString(); // Replace the original line with the updated line
                        break;
                    }
                }

                bw.write(orderLine); // Write either updated or original line
                bw.newLine();
            }

            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void clearTextField() {
        // Reset fields
        orderIdField.setText("");
        customerNameField.setText("");
        orderItemsField.setText("");
        totalAmtField.setText("");
        deliveryAddressField.setText("");
        deliveryTimeField.setText("");
        contactNumField.setText("");
        orderStatusField.setText("");
        dateField.setText("");
    }

    private void updateOrderStatus(String newStatus) {
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update status in the JTable
        model.setValueAt(newStatus, row, 7); // Update the status column

        // Update the corresponding entry in allOrders
        String orderId = model.getValueAt(row, 0).toString(); // Order ID
        for (int i = 0; i < allOrders.size(); i++) {
            String[] orderDetails = allOrders.get(i).split(";", -1); // Split using semicolon
            if (orderDetails[0].equals(orderId)) { // Match by Order ID
                orderDetails[7] = newStatus; // Update the status
                allOrders.set(i, String.join(";", orderDetails)); // Update the list with the modified line
                break;
            }
        }

        // Save the updated orders back to the file
        saveToFile();
        JOptionPane.showMessageDialog(this, "Order status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshTable(String vendorId) {
        // Clear existing rows in the model
        model.setRowCount(0);

        try {
            FileReader fr = new FileReader("src/main/java/com/mycompany/fos_app/Data/orders.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(";", -1); // Split using semicolon

                // Populate the order data for the JTable
                String[] orderData = new String[11];
                orderData[0] = orderDetails[0]; // Order ID
                orderData[1] = orderDetails[1]; // Customer Name
                orderData[2] = orderDetails[2]; // Items
                orderData[3] = orderDetails[3]; // Total Price
                orderData[4] = orderDetails[4]; // Order Type
                orderData[5] = orderDetails[5]; // Address and City combined
                orderData[6] = orderDetails[6]; // Contact Number
                orderData[7] = orderDetails[7]; // Status
                orderData[8] = orderDetails[8]; // Date
                orderData[9] = orderDetails[9]; // Customer ID
                orderData[10] = orderDetails[10]; // Vendor ID

                // Only add the row if the status is "Accepted"
                if (orderData[10].equalsIgnoreCase(vendorId) && orderData[7].equalsIgnoreCase("Accepted")) {
                    model.addRow(orderData);
                } 
                // For "Prepared" orders, only add if the order type is not "Delivery"
                else if (orderData[10].equalsIgnoreCase(vendorId) && orderData[7].equalsIgnoreCase("Prepared") && !orderData[4].equalsIgnoreCase("Delivery")) {
                    model.addRow(orderData);
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error refreshing table: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void assignDeliveryRunner(String[] orderData) {
        File runnerFile = new File("src/main/java/com/mycompany/fos_app/Data/delivery.txt");
        List<String> fileLines = new ArrayList<>();
        boolean runnerAssigned = false; // To track if a runner is assigned
        try (BufferedReader br = new BufferedReader(new FileReader(runnerFile))) {
            String line;

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                String[] runnerDetails = line.split(";",-1);
                // Check if the runner is available and not yet assigned
                if (!runnerAssigned && runnerDetails[1].equals("Available")) {
                    // Update runner details to "Assigned" with order details
                    String addressAndCity = orderData[5]; // Address + City
                    String contactNumber = orderData[6]; // Contact Number
                    String orderId = orderData[0];       // Order ID
                    String totalPrice = orderData[3];    // Total Price

                    line = runnerDetails[0] + ";Assigned;" + orderId + ";" + totalPrice + ";" + addressAndCity + ";" + contactNumber;
                    runnerAssigned = true; // Mark runner as assigned
                }

                fileLines.add(line); // Add the (possibly updated) line back to the list
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading runner file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If no runner was available, show an error message
        if (!runnerAssigned) {
            JOptionPane.showMessageDialog(this, "No available runner to assign the order.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Write the updated lines back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(runnerFile))) {
            for (String fileLine : fileLines) {
                bw.write(fileLine);
                bw.newLine();
            }
            JOptionPane.showMessageDialog(this, "Order successfully assigned to a delivery runner!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to runner file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ordersTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        orderIdField = new javax.swing.JTextField();
        customerNameField = new javax.swing.JTextField();
        contactNumField = new javax.swing.JTextField();
        totalAmtField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        deliveryAddressField = new javax.swing.JTextArea();
        preparedOrderBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        orderItemsField = new javax.swing.JTextArea();
        orderStatusField = new javax.swing.JTextField();
        completedOrderBtn = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        deliveryTimeField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backBtn.setText("<");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        getContentPane().add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 23, 42, 37));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setText("ACCEPTED ORDERS");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 43, -1, -1));

        ordersTable.setModel(model);
        ordersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ordersTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(ordersTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 93, 627, 267));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Details:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 378, -1, -1));
        getContentPane().add(orderIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 439, 118, -1));
        getContentPane().add(customerNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 495, 118, -1));
        getContentPane().add(contactNumField, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 547, 118, -1));
        getContentPane().add(totalAmtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 439, 117, -1));

        deliveryAddressField.setColumns(20);
        deliveryAddressField.setRows(5);
        jScrollPane3.setViewportView(deliveryAddressField);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 613, 385, -1));

        preparedOrderBtn.setText("Prepared");
        preparedOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preparedOrderBtnActionPerformed(evt);
            }
        });
        getContentPane().add(preparedOrderBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 665, 117, -1));

        jLabel3.setText("Order ID");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 421, -1, -1));

        jLabel4.setText("Customer Name");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 473, -1, -1));

        jLabel5.setText("Contact Number");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 529, -1, -1));

        jLabel6.setText("Order Status");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 479, -1, -1));

        jLabel7.setText("Order Items");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 421, -1, -1));

        jLabel8.setText("Delivery Address");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 591, -1, -1));

        jLabel9.setText("Total Amount");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 421, -1, -1));

        orderItemsField.setColumns(20);
        orderItemsField.setRows(5);
        jScrollPane4.setViewportView(orderItemsField);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 439, -1, 134));
        getContentPane().add(orderStatusField, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 501, 117, -1));

        completedOrderBtn.setText("Completed");
        completedOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedOrderBtnActionPerformed(evt);
            }
        });
        getContentPane().add(completedOrderBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 694, 117, -1));

        jLabel10.setText("Order Type");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 535, -1, -1));
        getContentPane().add(deliveryTimeField, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 557, 117, -1));

        jLabel14.setText("Date");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 591, -1, -1));
        getContentPane().add(dateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 613, 117, -1));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 760));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        OrderManagementForm itm = new OrderManagementForm();
        this.dispose();
        itm.setVisible(true);
    }//GEN-LAST:event_backBtnActionPerformed

    private void ordersTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ordersTableMouseReleased
        row = ordersTable.getSelectedRow();

        if (row != -1) {
            // Populate text fields with the selected order's details
            orderIdField.setText(model.getValueAt(row, 0).toString()); // Order ID
            customerNameField.setText(model.getValueAt(row, 1).toString()); // Customer Name
            orderItemsField.setText(model.getValueAt(row, 2).toString()); // Items (comma-separated)
            totalAmtField.setText(model.getValueAt(row, 3).toString()); // Total Amount
            deliveryTimeField.setText(model.getValueAt(row, 4).toString()); // Delivery Time
            deliveryAddressField.setText(model.getValueAt(row, 5).toString()); // Address + City
            contactNumField.setText(model.getValueAt(row, 6).toString()); // Contact Number
            orderStatusField.setText(model.getValueAt(row, 7).toString()); // Status
            dateField.setText(model.getValueAt(row, 8).toString()); // Date
        }
    }//GEN-LAST:event_ordersTableMouseReleased

    private void preparedOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preparedOrderBtnActionPerformed
        String vendorId = SessionManager.getInstance().getidInput();
        // Get the selected row index
        int selectedRow = ordersTable.getSelectedRow();

        // Check if a row is selected
        if (selectedRow >= 0) {
            // Populate orderData with the selected row's data
            String[] orderData = new String[ordersTable.getColumnCount()];
            for (int i = 0; i < ordersTable.getColumnCount(); i++) {
                orderData[i] = (String) model.getValueAt(selectedRow, i);
            }

            // Check if delivery type is "Delivery" and current status is "Accepted"
            if (orderData[7].equals("Accepted") && orderData[4].equals("Delivery")) { 
                // Assuming column 6 is delivery type and 7 is status
                updateOrderStatus("Prepared"); // Update status to "Prepared"

                // Assign a delivery runner
                assignDeliveryRunner(orderData);

                refreshTable(vendorId);                // Refresh the table view
                clearTextField();              // Clear input fields
            } else if (orderData[7].equals("Accepted") && !orderData[4].equals("Delivery")) {
                updateOrderStatus("Prepared");
                refreshTable(vendorId);
                clearTextField();

            } else {
                JOptionPane.showMessageDialog(this, "Order is not eligible for assignment.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to accept.");
        }

    }//GEN-LAST:event_preparedOrderBtnActionPerformed

    private void completedOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedOrderBtnActionPerformed
        String vendorId = SessionManager.getInstance().getidInput();        
        // Get the selected row index
        int selectedRow = ordersTable.getSelectedRow();

        // Check if a row is selected
        if (selectedRow >= 0) {
            // Populate orderData with the selected row's data
            String[] orderData = new String[ordersTable.getColumnCount()];
            for (int i = 0; i < ordersTable.getColumnCount(); i++) {
                orderData[i] = (String) model.getValueAt(selectedRow, i);
            }

            // Check if the current status is "Accepted"
            if (orderData[7].equals("Prepared")) { // Assuming column 7 is the status column
                updateOrderStatus("Completed"); // Update status to "Prepared"
                refreshTable(vendorId);                // Refresh the table view
                clearTextField();              // Clear input fields
            } else {
                JOptionPane.showMessageDialog(this, "Order is not prepared yet");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to accept.");
        }
    }//GEN-LAST:event_completedOrderBtnActionPerformed

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
            java.util.logging.Logger.getLogger(AcceptedOrderManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AcceptedOrderManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AcceptedOrderManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AcceptedOrderManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AcceptedOrderManagementForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JButton completedOrderBtn;
    private javax.swing.JTextField contactNumField;
    private javax.swing.JTextField customerNameField;
    private javax.swing.JTextField dateField;
    private javax.swing.JTextArea deliveryAddressField;
    private javax.swing.JTextField deliveryTimeField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField orderIdField;
    private javax.swing.JTextArea orderItemsField;
    private javax.swing.JTextField orderStatusField;
    private javax.swing.JTable ordersTable;
    private javax.swing.JButton preparedOrderBtn;
    private javax.swing.JTextField totalAmtField;
    // End of variables declaration//GEN-END:variables
}
