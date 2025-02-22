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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Rauf
 */
public class CompletedOrderManagementForm extends javax.swing.JFrame {

    private DefaultTableModel model = new DefaultTableModel();
    private final String[] columnName = {"Order ID", "Customer Name", "Items", "Total Amount", 
        "Order Type", "Address", "Contact Number", "Status", "Date"};
    private List<String> allOrders = new ArrayList<>();
    private List<String> vendorOrders = new ArrayList<>();
    private int row = -1;

    // Creates new form OrderManagementForm
    public CompletedOrderManagementForm() {
        String vendorId = SessionManager.getInstance().getidInput();
        initComponents(); // Initialize components first!
        model.setColumnIdentifiers(columnName);

        loadOrders(vendorId);
        refreshTable(vendorId);// Load orders based on the vendor ID
        ordersTable.setModel(model);  // Display orders in the table

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

                // Ensure the line has at least 8 fields
                if (orderDetails.length < 9) {
                    System.err.println("Invalid line: " + line); // Log invalid lines for debugging
                    continue; // Skip this line
                }

                if (orderDetails[10].equalsIgnoreCase(vendorId) && orderDetails[7].equalsIgnoreCase("Completed")) { // Check for "Pending" status
                    vendorOrders.add(line); // Add to vendor-specific orders
                    String[] orderData = {
                        orderDetails[0], // Order ID
                        orderDetails[1], // Customer Name
                        orderDetails[2], // Items
                        orderDetails[3], // Total Price
                        orderDetails[4], // Order Type
                        orderDetails[5], // Address
                        orderDetails[6], // Contact Number
                        orderDetails[7],  // Status
                        orderDetails[8],  // Date
                        orderDetails[9], // Customer ID
                        orderDetails[10] // Vendor ID
                    };
                    model.addRow(orderData); // Add only Pending orders to JTable
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

                // Add to table only if status is "Pending"
                if (orderData[10].equalsIgnoreCase(vendorId) && orderData[7].equalsIgnoreCase("Completed")) {
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
    
    private void updateSearchComboBox(String selectedColumn, String vendorId) {
        // Clear the search combo box before adding new items
        searchcbx.removeAllItems();

        // Filter orders based on their status being "Completed"
        List<String> filteredOrders = new ArrayList<>();
        for (String orderLine : allOrders) {
            String[] orderDetails = orderLine.split(";", -1);
            if (orderDetails[10].equalsIgnoreCase(vendorId) && orderDetails.length > 7 && orderDetails[7].equalsIgnoreCase("Completed")) {
                filteredOrders.add(orderLine);
            }
        }

        // Find the column index and extract corresponding values
        switch (selectedColumn) {
            case "Order ID":
                filteredOrders.stream()
                        .map(line -> line.split(";", -1)[0]) // Get Order ID
                        .distinct()
                        .forEach(searchcbx::addItem);
                break;
            case "Customer Name":
                filteredOrders.stream()
                        .map(line -> line.split(";", -1)[1]) // Get Customer Name
                        .distinct()
                        .forEach(searchcbx::addItem);
                break;
            case "Order Type":
                filteredOrders.stream()
                        .map(line -> line.split(";", -1)[4]) // Get Order Type
                        .distinct()
                        .forEach(searchcbx::addItem);
                break;
            default:
                break;
        }
    }
    
    private void filterDataByDate(String selectedFilter, String vendorId) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date today = new Date();
        long todayMillis = today.getTime();

        model.setRowCount(0); // Clear the table

        try {
            for (String orderLine : allOrders) {
                String[] orderDetails = orderLine.split(";", -1);
                String orderDateStr = orderDetails[8];
                Date orderDate = sdf.parse(orderDateStr);

                long diffInMillis = todayMillis - orderDate.getTime();
                long diffInDays = diffInMillis / (1000 * 60 * 60 * 24); // Convert millis to days

                // Check based on the selected filter
                boolean shouldAdd = false;

                switch (selectedFilter) {
                    case "Monthly":
                        if (diffInDays <= 30) shouldAdd = true;
                        break;
                }

                // Add the row if it meets the filter criteria
                if (orderDetails[10].equalsIgnoreCase(vendorId) && shouldAdd && orderDetails[7].equalsIgnoreCase("Completed")) {
                    model.addRow(orderDetails);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateTableBasedOnSearch(String selectedColumn, String selectedSearchValue, String vendorId) {
        model.setRowCount(0); // Clear the table before adding filtered rows

        for (String orderLine : allOrders) {
            String[] orderDetails = orderLine.split(";", -1);
            boolean match = false;
                
            switch (selectedColumn) {
                case "Order ID":
                    match = orderDetails[0].equals(selectedSearchValue);
                    break;
                case "Customer Name":
                    match = orderDetails[1].equals(selectedSearchValue);
                    break;
                case "Order Type":
                    match = orderDetails[4].equals(selectedSearchValue);
                    break;
            }

            // Add the row if it matches both the search value and the date filter
            if (orderDetails[10].equalsIgnoreCase(vendorId) && match && orderDetails[7].equalsIgnoreCase("Completed")) {
                model.addRow(orderDetails);
            }
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
        jLabel10 = new javax.swing.JLabel();
        deliveryTimeField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        filtercbx = new javax.swing.JComboBox<>();
        columncbx = new javax.swing.JComboBox<>();
        searchcbx = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backBtn.setText("<");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        getContentPane().add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 23, 42, 37));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setText("ORDER HISTORY");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 28, -1, -1));

        ordersTable.setModel(model);
        ordersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ordersTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(ordersTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 110, 627, 267));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Details:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 389, -1, -1));
        getContentPane().add(orderIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 450, 118, -1));
        getContentPane().add(customerNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 506, 118, -1));
        getContentPane().add(contactNumField, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 558, 118, -1));
        getContentPane().add(totalAmtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 450, 117, -1));

        deliveryAddressField.setColumns(20);
        deliveryAddressField.setRows(5);
        jScrollPane3.setViewportView(deliveryAddressField);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 630, 385, -1));

        jLabel3.setText("Order ID");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 432, -1, -1));

        jLabel4.setText("Customer Name");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 484, -1, -1));

        jLabel5.setText("Contact Number");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 540, -1, -1));

        jLabel6.setText("Order Status");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 490, -1, -1));

        jLabel7.setText("Order Items");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(242, 432, -1, -1));

        jLabel8.setText("Delivery Address");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 602, -1, -1));

        jLabel9.setText("Total Amount");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 432, -1, -1));

        orderItemsField.setColumns(20);
        orderItemsField.setRows(5);
        jScrollPane4.setViewportView(orderItemsField);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(242, 450, -1, 134));
        getContentPane().add(orderStatusField, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 512, 117, -1));

        jLabel10.setText("Order Type");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 546, -1, -1));
        getContentPane().add(deliveryTimeField, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 568, 117, -1));

        jLabel11.setText("Search");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 85, 43, -1));

        jLabel14.setText("Date");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 608, -1, -1));
        getContentPane().add(dateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 630, 117, -1));

        filtercbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monthly" }));
        filtercbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtercbxActionPerformed(evt);
            }
        });
        getContentPane().add(filtercbx, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 82, 97, -1));

        columncbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Order ID", "Customer Name", "Order Type" }));
        columncbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                columncbxActionPerformed(evt);
            }
        });
        getContentPane().add(columncbx, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 82, -1, -1));

        searchcbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchcbxActionPerformed(evt);
            }
        });
        getContentPane().add(searchcbx, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 82, 193, -1));

        jLabel12.setText("Filter");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 85, 31, -1));

        jLabel13.setText("Column");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 85, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 750));

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
            orderStatusField.setText(model.getValueAt(row, 7).toString()); // Status\
            dateField.setText(model.getValueAt(row, 8).toString()); // Date
        }
    }//GEN-LAST:event_ordersTableMouseReleased

    private void columncbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_columncbxActionPerformed
        String selectedColumn = (String) columncbx.getSelectedItem();
        String vendorId = SessionManager.getInstance().getidInput();
        // Check if selectedColumn is null before proceeding
        if (selectedColumn != null) {
            updateSearchComboBox(selectedColumn, vendorId);
        }
    }//GEN-LAST:event_columncbxActionPerformed

    private void searchcbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchcbxActionPerformed
        String selectedSearchValue = (String) searchcbx.getSelectedItem();
        String vendorId = SessionManager.getInstance().getidInput();

        // Check if selectedSearchValue is null before proceeding
        if (selectedSearchValue != null) {
            String selectedColumn = (String) columncbx.getSelectedItem();
            
            // Check if selectedColumn is null before proceeding
            if (selectedColumn != null) {
                updateTableBasedOnSearch(selectedColumn, selectedSearchValue, vendorId);
            }
        }
    }//GEN-LAST:event_searchcbxActionPerformed

    private void filtercbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtercbxActionPerformed
        String vendorId = SessionManager.getInstance().getidInput();
        String selectedFilter = filtercbx.getSelectedItem().toString();
        filterDataByDate(selectedFilter, vendorId);
    }//GEN-LAST:event_filtercbxActionPerformed

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
            java.util.logging.Logger.getLogger(CompletedOrderManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompletedOrderManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompletedOrderManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompletedOrderManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CompletedOrderManagementForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JComboBox<String> columncbx;
    private javax.swing.JTextField contactNumField;
    private javax.swing.JTextField customerNameField;
    private javax.swing.JTextField dateField;
    private javax.swing.JTextArea deliveryAddressField;
    private javax.swing.JTextField deliveryTimeField;
    private javax.swing.JComboBox<String> filtercbx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JComboBox<String> searchcbx;
    private javax.swing.JTextField totalAmtField;
    // End of variables declaration//GEN-END:variables
}
