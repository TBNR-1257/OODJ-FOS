package com.mycompany.fos_app.GUI;

import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class CustomerHome extends javax.swing.JFrame {
    
    public CustomerHome() {
        initComponents();
        loadCreditBalance();
        setSize(800, 600); 
        setLocationRelativeTo(null);
        displayCustomerID();
        String idInput = SessionManager.getInstance().getidInput();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        viewVendors = new javax.swing.JButton();
        submitReviewbtn = new javax.swing.JButton();
        ordertransacHistorybtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        creditBal = new javax.swing.JLabel();
        logoutbtn = new javax.swing.JButton();
        notifbtn = new javax.swing.JButton();
        viewReviewsbtn = new javax.swing.JButton();
        complaintbtn = new javax.swing.JButton();
        orderstatusbtn = new javax.swing.JButton();
        lblCustomerID = new javax.swing.JLabel();
        transactionbtn = new javax.swing.JButton();

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel3.setText("Welcome,");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel1.setText("Welcome,");

        viewVendors.setText("View Food Outlets");
        viewVendors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewVendorsActionPerformed(evt);
            }
        });

        submitReviewbtn.setText("Submit Reviews");
        submitReviewbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitReviewbtnActionPerformed(evt);
            }
        });

        ordertransacHistorybtn.setText("Order History");
        ordertransacHistorybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordertransacHistorybtnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel5.setText("!");

        jLabel6.setText("Credit Balance: ");

        creditBal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        logoutbtn.setText("Log Out");
        logoutbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutbtnActionPerformed(evt);
            }
        });

        notifbtn.setText("Notifications");
        notifbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notifbtnActionPerformed(evt);
            }
        });

        viewReviewsbtn.setText("View Reviews");
        viewReviewsbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewReviewsbtnActionPerformed(evt);
            }
        });

        complaintbtn.setText("Submit Complaint");
        complaintbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                complaintbtnActionPerformed(evt);
            }
        });

        orderstatusbtn.setText("Check Order Status");
        orderstatusbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderstatusbtnActionPerformed(evt);
            }
        });

        lblCustomerID.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N

        transactionbtn.setText("Transaction History");
        transactionbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transactionbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(290, 290, 290)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCustomerID, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(270, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(viewVendors, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(complaintbtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ordertransacHistorybtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(viewReviewsbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(submitReviewbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(transactionbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(208, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(creditBal, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(logoutbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(327, 327, 327))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(orderstatusbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(274, 274, 274))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(notifbtn)
                        .addGap(25, 25, 25))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(notifbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomerID, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(viewVendors, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(submitReviewbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(complaintbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewReviewsbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel5))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(transactionbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ordertransacHistorybtn, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(orderstatusbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel6)
                        .addGap(37, 37, 37)
                        .addComponent(logoutbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(creditBal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void displayCustomerID() {
    String customerID = SessionManager.getInstance().getidInput();
    String customerName = getCustomerName(customerID);

    System.out.println("CustomerHome - Retrieved Name: " + customerName); 
    lblCustomerID.setText(customerName);
}


    private String getCustomerName(String customerID) {
    File file = new File("src/main/java/com/mycompany/fos_app/Data/customer.txt");

    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] parts = line.split(";");
                if (parts.length >= 2 && parts[0].equals(customerID)) {
                    return parts[1];
                }
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading customer file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    return "Unknown"; // If ID is not found
}

    
    private void viewVendorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewVendorsActionPerformed
        VendorList vl = new VendorList(this);
        vl.setVisible(true); // open the vendor list page
        this.setVisible(false);
    }//GEN-LAST:event_viewVendorsActionPerformed

    private void ordertransacHistorybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordertransacHistorybtnActionPerformed
        ViewOrderHistory voh = new ViewOrderHistory(this);
        voh.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_ordertransacHistorybtnActionPerformed

    private void submitReviewbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitReviewbtnActionPerformed
        SubmitReview sr = new SubmitReview(this);
        sr.loadOrders();
        sr.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_submitReviewbtnActionPerformed

    private void notifbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notifbtnActionPerformed
        Notifications notif = new Notifications(this);
        notif.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_notifbtnActionPerformed

    private void viewReviewsbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewReviewsbtnActionPerformed
        ViewReviews viewReviews = new ViewReviews(this); 
        viewReviews.setVisible(true);
        this.setVisible(false); 
    }//GEN-LAST:event_viewReviewsbtnActionPerformed

    private void complaintbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_complaintbtnActionPerformed
        SubmitComplaint comp = new SubmitComplaint(this);
        comp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_complaintbtnActionPerformed

    private void orderstatusbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderstatusbtnActionPerformed
        String fileName = "src/main/java/com/mycompany/fos_app/Data/orders.txt";
    String idInput = SessionManager.getInstance().getidInput(); // Get the logged-in customer's ID

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        boolean orderFound = false;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");

            if (data.length > 8) { 
                String orderId = data[0].trim();   // Order ID
                String customerId = data[9].trim(); // Customer ID
                String item = data[2].trim();      // Item Name
                String orderType = data[5].trim(); // Order Type 
                String orderStatus = data[7].trim(); // Order Status

                // Check if the order belongs to the logged-in customer
                if (customerId.equals(idInput)) { 
                    // Ensure "Out for Delivery" only shows for Delivery orders
                    if (orderStatus.equals("Out for Delivery") && !orderType.equals("Delivery")) {
                        continue; // Skip non-Delivery orders with this status
                    }

                    // Format the popup message
                    String message = "Order ID " + orderId + " - " + item + " | Status: " + orderStatus;
                    JOptionPane.showMessageDialog(null, message, "Order Status", JOptionPane.INFORMATION_MESSAGE);
                    
                    orderFound = true;
                    break; // Stop after displaying the first matching order
                }
            }
        }

        if (!orderFound) {
            JOptionPane.showMessageDialog(null, "No orders found for your account!", "Order Status", JOptionPane.WARNING_MESSAGE);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_orderstatusbtnActionPerformed

    // Credit balance
    
    private void loadCreditBalance() {
    String fileName = "src/main/java/com/mycompany/fos_app/Data/customer.txt";
    String idInput = SessionManager.getInstance().getidInput(); // Get logged-in user's ID

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");

            if (data.length > 3) { // Ensure there's enough data
                String customerId = data[0].trim(); // ID is at index [0]
                String creditBalance = data[3].trim(); // Credit balance is at index [3]

                if (customerId.equals(idInput)) { // Check if it's the logged-in user
                    creditBal.setText("      RM " + creditBalance); // Update JLabel
                    return; // Stop after finding the user
                }
            }
        }
        creditBal.setText("Balance: RM 0.00"); // Default if not found
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading balance: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    
    private void logoutbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutbtnActionPerformed
        this.dispose();
        new Cust_Login().setVisible(true); // back to login page
    }//GEN-LAST:event_logoutbtnActionPerformed

    private void transactionbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transactionbtnActionPerformed
        TransactionHistory th = new TransactionHistory(this);
        th.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_transactionbtnActionPerformed

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton complaintbtn;
    private javax.swing.JLabel creditBal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblCustomerID;
    private javax.swing.JButton logoutbtn;
    private javax.swing.JButton notifbtn;
    private javax.swing.JButton orderstatusbtn;
    private javax.swing.JButton ordertransacHistorybtn;
    private javax.swing.JButton submitReviewbtn;
    private javax.swing.JButton transactionbtn;
    private javax.swing.JButton viewReviewsbtn;
    private javax.swing.JButton viewVendors;
    // End of variables declaration//GEN-END:variables
}
