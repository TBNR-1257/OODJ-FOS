/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.fos_app.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

public class VendorRevenueDashboard extends javax.swing.JFrame {
    public VendorRevenueDashboard() {
        initComponents();        
        populateVendorComboBox(); // Load vendors into vendorcbx
        showLineChart("Weekly");
    }


    private void populateVendorComboBox() {
        Set<String> vendors = new HashSet<>(); // To store unique vendors
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/mycompany/fos_app/Data/orders.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 10) {
                    vendors.add(parts[10]); // Vendor name at index 10
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        vendorcbx.removeAllItems(); // Clear existing values
        vendorcbx.addItem("All Vendors"); // Default option
        for (String vendor : vendors) {
            vendorcbx.addItem(vendor);
        }
    }

    public void showLineChart(String filterType) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> revenueData = new LinkedHashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat weekLabelFormat = new SimpleDateFormat("d MMM");
        Calendar cal = Calendar.getInstance();

        String selectedVendor = (String) vendorcbx.getSelectedItem(); // Get selected vendor
        if (selectedVendor == null) selectedVendor = "All Vendors"; // Default

        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        List<String> weeklyLabels = new ArrayList<>();
        List<Date[]> weekRanges = new ArrayList<>();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        while (cal.get(Calendar.MONTH) == currentMonth) {
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || cal.get(Calendar.DAY_OF_MONTH) == 1) { 
                Date weekStart = cal.getTime();
                cal.add(Calendar.DAY_OF_MONTH, 6);
                if (cal.get(Calendar.MONTH) != currentMonth) { 
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
                Date weekEnd = cal.getTime();

                String weekLabel = weekLabelFormat.format(weekStart) + " - " + weekLabelFormat.format(weekEnd);
                weeklyLabels.add(weekLabel);
                weekRanges.add(new Date[]{weekStart, weekEnd});
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/mycompany/fos_app/Data/orders.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 11 && parts[7].equals("Completed")) {
                    double price = Double.parseDouble(parts[3]);
                    Date orderDate = dateFormat.parse(parts[8]);
                    String vendor = parts[10]; // Vendor at index 10

                    // Filter by vendor
                    if (!selectedVendor.equals("All Vendors") && !vendor.equals(selectedVendor)) {
                        continue; // Skip if not the selected vendor
                    }

                    cal.setTime(orderDate);
                    if (filterType.equals("Weekly") && cal.get(Calendar.MONTH) == currentMonth && cal.get(Calendar.YEAR) == currentYear) {
                        for (int i = 0; i < weekRanges.size(); i++) {
                            Date weekStart = weekRanges.get(i)[0];
                            Date weekEnd = weekRanges.get(i)[1];

                            if (!orderDate.before(weekStart) && !orderDate.after(weekEnd)) {
                                String weekLabel = weeklyLabels.get(i);
                                revenueData.put(weekLabel, revenueData.getOrDefault(weekLabel, 0.0) + price);
                                break;
                            }
                        }
                            } else if (filterType.equals("Monthly") && cal.get(Calendar.YEAR) == currentYear) {
                                String monthKey = new SimpleDateFormat("MMM yyyy").format(orderDate);
                                revenueData.put(monthKey, revenueData.getOrDefault(monthKey, 0.0) + price);
                            }

                            // Ensure all months (Jan - Dec) are present on the X-axis
                            if (filterType.equals("Monthly")) {
                                String[] allMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                                // Ensure all months appear in order: Jan -> Dec
                                List<String> monthOrder = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
                                Map<String, Double> sortedMonthlyData = new LinkedHashMap<>();

                                for (String month : monthOrder) {
                                    String monthLabel = month + " " + currentYear;
                                    if (revenueData.containsKey(monthLabel)) {
                                        sortedMonthlyData.put(monthLabel, revenueData.get(monthLabel));
                                    } else {
                                        sortedMonthlyData.put(monthLabel, 0.0); // Ensure missing months have 0 revenue
                                    }
                                }
                                revenueData = sortedMonthlyData; // Replace with sorted version

                            } else if (filterType.equals("Anually")) {  // <- Moved this block outside
                                String yearKey = new SimpleDateFormat("yyyy").format(orderDate);
                                revenueData.put(yearKey, revenueData.getOrDefault(yearKey, 0.0) + price);
                            }

                            // Ensure at least 5 years appear on the X-axis (modify as needed)
                            if (filterType.equals("Anually")) {
                                int startYear = currentYear - 3; // Show last 5 years including current year
                                // Ensure years are sorted from oldest to newest
                                List<Integer> yearList = new ArrayList<>();
                                for (String key : revenueData.keySet()) {
                                    if (key.matches("\\d{4}")) { // Check if it's a year (e.g., "2021")
                                        yearList.add(Integer.parseInt(key));
                                    }
                                }
                                Collections.sort(yearList); // Sort in ascending order

                                Map<String, Double> sortedYearlyData = new LinkedHashMap<>();
                                for (int year : yearList) {
                                    sortedYearlyData.put(String.valueOf(year), revenueData.getOrDefault(String.valueOf(year), 0.0));
                                }
                                revenueData = sortedYearlyData; // Replace with sorted version

                            }
                    }
            }

            if (filterType.equals("Weekly")) {
                for (String week : weeklyLabels) {
                    revenueData.putIfAbsent(week, 0.0);
                }
            }

            for (Map.Entry<String, Double> entry : revenueData.entrySet()) {
                dataset.setValue(entry.getValue(), "Revenue", entry.getKey());
            }

            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Revenue Chart (" + filterType + ")",
                    "Time",
                    "Revenue",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false,
                    true,
                    false);

            CategoryPlot categoryPlot = lineChart.getCategoryPlot();
            categoryPlot.setBackgroundPaint(Color.WHITE);

            LineAndShapeRenderer renderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
            renderer.setSeriesPaint(0, new Color(204, 0, 51));

            CategoryAxis xAxis = categoryPlot.getDomainAxis();
            if ("Monthly".equals(filterType)) {
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
            } else {
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            }

            ChartPanel lineChartPanel = new ChartPanel(lineChart);
            panelLineChart.removeAll();
            panelLineChart.add(lineChartPanel, BorderLayout.CENTER);
            panelLineChart.validate();

        } catch (Exception e) {
            e.printStackTrace();
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

        panelLineChart = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        filtercbx = new javax.swing.JComboBox<>();
        vendorcbx = new javax.swing.JComboBox<>();
        backbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLineChart.setBackground(new java.awt.Color(255, 255, 255));
        panelLineChart.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelLineChart.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panelLineChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 132, 579, 297));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setText("VIEW REVENUE");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(241, 41, -1, -1));

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        filtercbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Weekly", "Monthly", "Anually" }));
        filtercbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtercbxActionPerformed(evt);
            }
        });
        jPanel1.add(filtercbx, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 120, -1));

        vendorcbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendorcbxActionPerformed(evt);
            }
        });
        jPanel1.add(vendorcbx, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, 120, -1));

        backbtn.setBackground(new java.awt.Color(204, 204, 204));
        backbtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        backbtn.setText("Back");
        backbtn.setFocusPainted(false);
        backbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backbtnActionPerformed(evt);
            }
        });
        jPanel1.add(backbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void filtercbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtercbxActionPerformed
        String selectedFilter = (String) filtercbx.getSelectedItem();
        showLineChart(selectedFilter);
    }//GEN-LAST:event_filtercbxActionPerformed

    private void vendorcbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendorcbxActionPerformed
        String selectedFilter = (String) filtercbx.getSelectedItem();
        showLineChart(selectedFilter);
    }//GEN-LAST:event_vendorcbxActionPerformed

    private void backbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbtnActionPerformed
        MainMenuManagerGUI mmg = new MainMenuManagerGUI();
        mmg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backbtnActionPerformed

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
            java.util.logging.Logger.getLogger(VendorRevenueDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VendorRevenueDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VendorRevenueDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VendorRevenueDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VendorRevenueDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backbtn;
    private javax.swing.JComboBox<String> filtercbx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelLineChart;
    private javax.swing.JComboBox<String> vendorcbx;
    // End of variables declaration//GEN-END:variables
}
