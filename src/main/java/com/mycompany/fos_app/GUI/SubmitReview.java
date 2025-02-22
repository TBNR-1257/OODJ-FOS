package com.mycompany.fos_app.GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class SubmitReview extends javax.swing.JFrame {

    private String idInput;

    
    public SubmitReview() {
        initComponents();
        loadOrders(); //populate order history dropdown
        String idInput = SessionManager.getInstance().getidInput();
 
    }
    
     public void loadOrders() {
        File file = new File("src/main/java/com/mycompany/fos_app/Data/orders.txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Order history file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] parts = line.split(";");

            if (parts.length >= 12) { 
                String orderId = parts[0];  // Order ID
                String items = parts[2];    // Items
                String price = parts[3];    // Price
                String orderType = parts[4]; // Order Type
                String date = parts[8];     // Date
                String orderStatus = parts[7]; // Order Status
                String vendorID = parts[10]; // Vendor ID
               // String vendorName = parts[]
                String runnerID = parts[11]; // Runner ID

            if ("Completed".equalsIgnoreCase(orderStatus)) {
                String orderEntry;

                if ("Delivery".equalsIgnoreCase(orderType)) {
                    // Include runnerID if delivery
                    orderEntry = orderId + " | " + vendorID + " | " + items + " | RM" + price + " | " + date + " | " + runnerID;
                } else {
                    orderEntry = orderId + " | " + vendorID + " | " + items + " | RM" + price + " | " + date;
                }

        orderDropdown.addItem(orderEntry);
    }
}

            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading order history: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private CustomerHome custHome; // Store reference to Home

    public SubmitReview(CustomerHome custHome) {
    this.custHome = custHome;
    initComponents();
    setSize(800, 600); 
    setLocationRelativeTo(null);

    this.idInput = SessionManager.getInstance().getidInput(); // Store ID globally in class
}
    
    private String getCustomerName() {
    File file = new File("src/main/java/com/mycompany/fos_app/Data/customer.txt");

    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] parts = line.split(";");

                if (parts.length > 1 && parts[0].equals(idInput)) { // Match ID
                    return parts[1]; // Return Name
                }
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading customer file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    return "Unknown"; // Default if not found
}

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backbtn = new javax.swing.JButton();
        orderDropdown = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        reviewTextField = new javax.swing.JTextArea();
        ratingDropdown = new javax.swing.JComboBox<>();
        submitButton = new javax.swing.JButton();
        clearbtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backbtn.setText("Back to Home");
        backbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backbtnActionPerformed(evt);
            }
        });

        orderDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderDropdownActionPerformed(evt);
            }
        });

        reviewTextField.setColumns(20);
        reviewTextField.setRows(5);
        jScrollPane1.setViewportView(reviewTextField);

        ratingDropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        clearbtn.setText("Clear");
        clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel1.setText("Submit Review");

        jLabel2.setText("Select order");

        jLabel3.setText("Leave a review!");

        jLabel4.setText("Rate your order:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 276, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(250, 250, 250))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ratingDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(orderDropdown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(backbtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ratingDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(backbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbtnActionPerformed
        custHome.setVisible(true); // back to cust home page
        this.dispose();
    }//GEN-LAST:event_backbtnActionPerformed

    private void orderDropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderDropdownActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orderDropdownActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        saveReviewToFile();
    }//GEN-LAST:event_submitButtonActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        orderDropdown.setSelectedIndex(0);
        ratingDropdown.setSelectedIndex(0);
        reviewTextField.setText(""); 
    }//GEN-LAST:event_clearbtnActionPerformed

    private void saveReviewToFile() {
    String selectedOrder = (String) orderDropdown.getSelectedItem();
    String reviewText = reviewTextField.getText().trim();
    String rating = (String) ratingDropdown.getSelectedItem();

    if (selectedOrder == null || selectedOrder.isEmpty() || reviewText.isEmpty() || rating == null) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields before submitting!", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String customerID = SessionManager.getInstance().getidInput(); // Retrieve customer ID
    String customerName = getCustomerName(); // Retrieve customer name

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/mycompany/fos_app/Data/reviews.txt", true))) {
        String[] orderDetails = selectedOrder.split(" \\| ");

        if (orderDetails.length >= 5) { // Ensure order details are valid
            String orderId = orderDetails[0].trim();
            String vendorID = orderDetails[1].trim();
            String items = orderDetails[2].trim();
            String price = orderDetails[3].replace("RM", "").trim();
            String date = orderDetails[4].trim();
            String runnerID = (orderDetails.length > 5) ? orderDetails[5].trim() : "N/A"; // Handle missing runnerID

            // Format: OrderID;VendorID;RunnerID;Items;Price;Date;CustomerID;CustomerName;Review;Rating
            String reviewEntry = orderId + ";" + vendorID + ";" + runnerID + ";" + items + ";" + price + ";" + date + ";" + customerID + ";" + customerName + ";" + reviewText + ";" + rating;

            writer.write(reviewEntry);
            writer.newLine();
            writer.flush();

            JOptionPane.showMessageDialog(this, "Review submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error processing order details!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving review: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    
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
            java.util.logging.Logger.getLogger(SubmitReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubmitReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubmitReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubmitReview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubmitReview().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backbtn;
    private javax.swing.JButton clearbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> orderDropdown;
    private javax.swing.JComboBox<String> ratingDropdown;
    private javax.swing.JTextArea reviewTextField;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables
}
