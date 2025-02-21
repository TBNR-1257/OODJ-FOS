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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Admin_Dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Admin_Dashboard
     */
    public Admin_Dashboard() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        regBtn = new javax.swing.JButton();
        topupBtn = new javax.swing.JButton();
        roleComboBox = new javax.swing.JComboBox<>();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(userTable);

        regBtn.setText("Vendor/Runner Registration");
        regBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regBtnActionPerformed(evt);
            }
        });

        topupBtn.setText("Top-Up Customer Credit");
        topupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topupBtnActionPerformed(evt);
            }
        });

        roleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Customer", "Vendor", "Manager", "Runner" }));
        roleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleComboBoxActionPerformed(evt);
            }
        });

        editBtn.setText("Edit");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Montserrat Thin", 3, 36)); // NOI18N
        jLabel1.setText("Dashboard");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(roleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(regBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(topupBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121))))
            .addGroup(layout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(editBtn)
                            .addComponent(deleteBtn))
                        .addContainerGap(101, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(topupBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(regBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadUserData(String role) {
        String filename = "";

        // Determine the correct file based on role selection
        switch (role) {
            case "Admin":
                filename = "src/main/java/com/mycompany/fos_app/Data/admin.txt";
                break;
            case "Customer":
                filename = "src/main/java/com/mycompany/fos_app/Data/customer.txt";
                break;
            case "Vendor":
                filename = "src/main/java/com/mycompany/fos_app/Data/vendor.txt";
                break;
            case "Runner":
                filename = "src/main/java/com/mycompany/fos_app/Data/runner.txt";
                break;
            case "Manager":
                filename = "src/main/java/com/mycompany/fos_app/Data/runner.txt";
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid Role Selected!");
                return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();

            if (line == null) {
                DefaultTableModel model = (DefaultTableModel) userTable.getModel();
                model.setRowCount(0);
                JOptionPane.showMessageDialog(this, "No data found for " + role);
                return;
            }

            // Get column headers dynamically based on role
            String[] columnNames = getColumnHeaders(role);
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            // Populate table with data
            do {
                model.addRow(line.split(";"));
            } while ((line = br.readLine()) != null);

            userTable.setModel(model);
            br.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
        }
    }

    
    private String[] getColumnHeaders(String role) {
        switch (role) {
            case "Admin":
                return new String[]{"ID", "Name", "Password"};
            case "Customer":
                return new String[]{"ID", "Email", "Password", "Phone", "Credit Balance"};
            case "Vendor":
                return new String[]{"ID", "Name", "Password"};
//            case "Runner":
//                return new String[]{"ID", "Name", "Password", "Vehicle Type"};
//            case "Manager":
//                return new String[]{"ID", "Name", "Password", "Vehicle Type"};
            default:
                return new String[]{};
        }
    }
    
    private void regBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regBtnActionPerformed
        // TODO add your handling code here:
        this.dispose(); 
        new Staff_Register().setVisible(true);
    }//GEN-LAST:event_regBtnActionPerformed

    private void topupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topupBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_topupBtnActionPerformed

    private void saveUpdatedData(String role, DefaultTableModel model) {
        String filename = "";

        switch (role) {
            case "Admin": filename = "src/main/java/com/mycompany/fos_app/Data/admin.txt"; break;
            case "Customer": filename = "src/main/java/com/mycompany/fos_app/Data/customer.txt"; break;
            case "Vendor": filename = "src/main/java/com/mycompany/fos_app/Data/vendor.txt"; break;
            case "Runner": filename = "src/main/java/com/mycompany/fos_app/Data/runner.txt"; break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid role selected!");
                return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    line.append(model.getValueAt(i, j));
                    if (j < model.getColumnCount() - 1) {
                        line.append(";"); // Preserve the original file format
                    }
                }
                bw.write(line.toString());
                bw.newLine();
            }
            JOptionPane.showMessageDialog(this, "Data updated successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
        }
    }

    
    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = userTable.getSelectedRow(); // Get selected row index

        if (selectedRow == -1) { // No row selected
            JOptionPane.showMessageDialog(this, "Please select a row to edit.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        int columnCount = model.getColumnCount(); // Get total number of columns

        // Array to store updated values
        String[] updatedData = new String[columnCount];

        for (int i = 0; i < columnCount; i++) {
            String currentValue = model.getValueAt(selectedRow, i).toString(); // Get current value
            updatedData[i] = JOptionPane.showInputDialog(this, "Edit " + model.getColumnName(i), currentValue);

            // If cancel is pressed, stop updating
            if (updatedData[i] == null) {
                return;
            }
        }

        // Update table with new values
        for (int i = 0; i < columnCount; i++) {
            model.setValueAt(updatedData[i], selectedRow, i);
        }

        // Save updated data back to the text file
        saveUpdatedData(roleComboBox.getSelectedItem().toString(), model);
    }//GEN-LAST:event_editBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = userTable.getSelectedRow(); // Get selected row index

        if (selectedRow == -1) { // No row selected
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) userTable.getModel();
            model.removeRow(selectedRow); // Remove row from table

            // Save the updated data back to the text file
            saveUpdatedData(roleComboBox.getSelectedItem().toString(), model);
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void roleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleComboBoxActionPerformed
        // TODO add your handling code here:
        String selectedRole = roleComboBox.getSelectedItem().toString();
        loadUserData(selectedRole);
    }//GEN-LAST:event_roleComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton regBtn;
    private javax.swing.JComboBox<String> roleComboBox;
    private javax.swing.JButton topupBtn;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
