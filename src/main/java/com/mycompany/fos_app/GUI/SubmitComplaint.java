package com.mycompany.fos_app.GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class SubmitComplaint extends javax.swing.JFrame {
private CustomerHome custHome; 
    public SubmitComplaint(CustomerHome custHome) {
        
        String idInput = SessionManager.getInstance().getidInput();
        this.custHome = custHome;
            initComponents();
         //   setSize(800, 600); 
            setLocationRelativeTo(null);
    }     
  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        orderDropdown = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        complaintField = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        submitbtn1 = new javax.swing.JButton();
        clearbtn2 = new javax.swing.JButton();
        backbtn2 = new javax.swing.JButton();

        jLabel3.setText("Leave a review!");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel1.setText("Submit Complaint");

        complaintField.setColumns(20);
        complaintField.setRows(5);
        jScrollPane1.setViewportView(complaintField);

        jLabel4.setText("Tell us what happened in detail:");

        jLabel5.setText("We're sorry to hear about your experience, please be patient as we review your complaint.");

        submitbtn1.setText("Submit");
        submitbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitbtn1ActionPerformed(evt);
            }
        });

        clearbtn2.setText("Clear");
        clearbtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtn2ActionPerformed(evt);
            }
        });

        backbtn2.setText("Back");
        backbtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backbtn2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 102, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(268, 268, 268)
                        .addComponent(backbtn2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(submitbtn1)
                        .addGap(54, 54, 54)
                        .addComponent(clearbtn2)
                        .addGap(211, 211, 211))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(59, 59, 59))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearbtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(backbtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backbtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbtn2ActionPerformed
        custHome.setVisible(true); // back to cust home page
        this.dispose();
    }//GEN-LAST:event_backbtn2ActionPerformed

    private void submitbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitbtn1ActionPerformed
        saveComplaintToFile();
    }//GEN-LAST:event_submitbtn1ActionPerformed

    private void clearbtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtn2ActionPerformed
        complaintField.setText("");
    }//GEN-LAST:event_clearbtn2ActionPerformed

private void saveComplaintToFile() {
    String idInput = SessionManager.getInstance().getidInput();
    String custName = getCustomerNameById(idInput); // Get customer name from file
    String complaintText = complaintField.getText().trim();

    if (complaintText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a complaint before submitting!", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/mycompany/fos_app/Data/complaints.txt", true))) {
        writer.write(idInput + ";" + custName + ";" + complaintText);
        writer.newLine();
        writer.flush();

        JOptionPane.showMessageDialog(this, "Complaint submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving complaint: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private String getCustomerNameById(String customerId) {
    File file = new File("src/main/java/com/mycompany/fos_app/Data/customer.txt"); // Same file as used in reviews
    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";"); 
            if (parts.length > 1 && parts[0].equals(customerId)) {
                return parts[1];
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading customer data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return "Unknown"; // Default if not found
}


    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backbtn2;
    private javax.swing.JButton clearbtn2;
    private javax.swing.JTextArea complaintField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> orderDropdown;
    private javax.swing.JButton submitbtn1;
    // End of variables declaration//GEN-END:variables
}
