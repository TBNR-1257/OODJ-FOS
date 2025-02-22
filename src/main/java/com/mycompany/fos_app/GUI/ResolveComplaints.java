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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author khail
 */
public class ResolveComplaints extends javax.swing.JFrame {
    private DefaultTableModel model;
    private String[] columnNames = {"Customer ID", "Customer Name", "Complaint", "Solution"};
    /**
     * Creates new form ResolveComplaints
     */
        public ResolveComplaints() {
            initComponents();
            setTitle("Resolve Complaints");

            model = new DefaultTableModel(columnNames, 0);
            complaintstable.setModel(model);

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/mycompany/fos_app/Data/complaints.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                model.addRow(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Set a default selection (if there is at least one row).
        if (model.getRowCount() > 0) {
            complaintstable.setRowSelectionInterval(0, 0);
        }

        // Add a selection listener to update the text fields when a row is selected.
        complaintstable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displaySelectedRow(complaintstable, custidtxt, custname, complainttxt,solutiontxt
                );
            }
        });

        // Optionally, call displaySelectedRow if a row is already selected.
        displaySelectedRow(complaintstable, custidtxt, custname, complainttxt, solutiontxt);
}

private void displaySelectedRow(JTable complaintstable, JTextField custidtxt, JTextField custname, JTextField complainttxt, JTextField solutiontxt) {
    int selectedRow = complaintstable.getSelectedRow();
    if (selectedRow != -1) {
        // Convert the selected row index to the model index.
        selectedRow = complaintstable.convertRowIndexToModel(selectedRow);
        TableModel m = complaintstable.getModel();
        // Retrieve values from the model.
        String custID = m.getValueAt(selectedRow, 0).toString();
        String name = m.getValueAt(selectedRow, 1).toString();
        String complaint = m.getValueAt(selectedRow, 2).toString();
        String solution = m.getValueAt(selectedRow, 3).toString();

        // Update the text fields.
        custidtxt.setText(custID);
        custname.setText(name);
        complainttxt.setText(complaint);
        solutiontxt.setText(solution);
    } else {
        custidtxt.setText("");
        custname.setText("");
        complainttxt.setText("");
        solutiontxt.setText("");
        
    }
}
        
private void updateSelectedRowWithSolution(JTable complaintstable, JTextField solutiontxt) {
    int selectedRow = complaintstable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "No row selected.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Convert view index to model index
    selectedRow = complaintstable.convertRowIndexToModel(selectedRow);
    TableModel model = complaintstable.getModel();
    
    // Retrieve existing values safely
    String custID = (model.getValueAt(selectedRow, 0) != null) ? model.getValueAt(selectedRow, 0).toString() : "";
    String name = (model.getValueAt(selectedRow, 1) != null) ? model.getValueAt(selectedRow, 1).toString() : "";
    String complaint = (model.getValueAt(selectedRow, 2) != null) ? model.getValueAt(selectedRow, 2).toString() : "";
    String currentValue = (model.getValueAt(selectedRow, 3) != null) ? model.getValueAt(selectedRow, 3).toString() : "";
    
    // Get the solution text.
    String solution = solutiontxt.getText().trim();
    
    // Append the solution text to the current value.
    String newValue = currentValue.isEmpty() ? solution :solution;
    
    // Update the model
    if (model instanceof DefaultTableModel) {
        DefaultTableModel dtm = (DefaultTableModel) model;
        dtm.setValueAt(newValue, selectedRow, 3);
    } else {
        JOptionPane.showMessageDialog(null, "Table model is not editable.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Rewrite the file to avoid duplicate rows
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/mycompany/fos_app/Data/complaints.txt"))) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String rowCustID = (model.getValueAt(i, 0) != null) ? model.getValueAt(i, 0).toString() : "";
            String rowName = (model.getValueAt(i, 1) != null) ? model.getValueAt(i, 1).toString() : "";
            String rowComplaint = (model.getValueAt(i, 2) != null) ? model.getValueAt(i, 2).toString() : "";
            String rowSolution = (model.getValueAt(i, 3) != null) ? model.getValueAt(i, 3).toString() : "";
            
            writer.write(rowCustID + ";" + rowName + ";" + rowComplaint + ";" + rowSolution);
            writer.newLine();
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error writing to file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        complaintstable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        custname = new javax.swing.JTextField();
        custidtxt = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        complainttxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        solutiontxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        resolvebtn = new javax.swing.JButton();

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

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setViewportView(complaintstable);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 607, 347));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        jLabel1.setText("Resolve Complaints");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, -1, -1));

        custname.setEditable(false);
        jPanel1.add(custname, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 570, 119, -1));

        custidtxt.setEditable(false);
        jPanel1.add(custidtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 520, 119, -1));

        jTextField3.setText("jTextField1");
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 688, 119, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Complaint :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 500, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Customer Name: ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 550, -1, -1));

        complainttxt.setEditable(false);
        jPanel1.add(complainttxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 520, 110, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Customer ID :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, -1, -1));
        jPanel1.add(solutiontxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 520, 170, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Solution :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 500, -1, -1));

        resolvebtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        resolvebtn.setText("Resolve");
        resolvebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resolvebtnActionPerformed(evt);
            }
        });
        jPanel1.add(resolvebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 580, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 630));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbtnActionPerformed
        MainMenuManagerGUI mmg = new MainMenuManagerGUI();
        mmg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backbtnActionPerformed

    private void resolvebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resolvebtnActionPerformed
        updateSelectedRowWithSolution(complaintstable,solutiontxt);
    }//GEN-LAST:event_resolvebtnActionPerformed

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
            java.util.logging.Logger.getLogger(ResolveComplaints.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResolveComplaints.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResolveComplaints.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResolveComplaints.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResolveComplaints().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backbtn;
    private javax.swing.JTable complaintstable;
    private javax.swing.JTextField complainttxt;
    private javax.swing.JTextField custidtxt;
    private javax.swing.JTextField custname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JButton resolvebtn;
    private javax.swing.JTextField solutiontxt;
    // End of variables declaration//GEN-END:variables
}
