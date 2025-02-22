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

public class RevenueDashboardForm extends javax.swing.JFrame {
    public RevenueDashboardForm() {
        String vendorId = SessionManager.getInstance().getidInput();
        initComponents();        
        
        showLineChart("Weekly", vendorId);
    }

    public void showLineChart(String filterType, String vendorId) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> revenueData = new LinkedHashMap<>(); // Maintain order
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat weekLabelFormat = new SimpleDateFormat("d MMM"); // Format for week range labels
        Calendar cal = Calendar.getInstance();

        // Get current month and year
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH); // 0-based (0 = Jan, 1 = Feb, etc.)

        // Weekly filter logic: Get start and end dates of weeks in the month
        List<String> weeklyLabels = new ArrayList<>();
        List<Date[]> weekRanges = new ArrayList<>();
        
        cal.set(Calendar.DAY_OF_MONTH, 1); // Start at the first day of the month
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // Store the starting weekday for reference
        while (cal.get(Calendar.MONTH) == currentMonth) {
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || cal.get(Calendar.DAY_OF_MONTH) == 1) { 
                // If it's a Monday OR the 1st day of the month, start a new week
                Date weekStart = cal.getTime();
                cal.add(Calendar.DAY_OF_MONTH, 6); // Move to the end of the week
                if (cal.get(Calendar.MONTH) != currentMonth) { 
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Last day of month if overflows
                }
                Date weekEnd = cal.getTime();
                
                // Create label like "1 Feb - 7 Feb"
                String weekLabel = weekLabelFormat.format(weekStart) + " - " + weekLabelFormat.format(weekEnd);
                weeklyLabels.add(weekLabel);
                weekRanges.add(new Date[]{weekStart, weekEnd});
            }
            cal.add(Calendar.DAY_OF_MONTH, 1); // Move to next day
        }

        // Read the file and process revenue
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/mycompany/fos_app/Data/orders.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 9 && parts[7].equals("Completed")) {
                    
                    double price = Double.parseDouble(parts[3]);
                    Date orderDate = dateFormat.parse(parts[8]);
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
                }
            }

            // Ensure all weekly labels exist with 0 revenue if missing
            if (filterType.equals("Weekly")) {
                for (String week : weeklyLabels) {
                    revenueData.putIfAbsent(week, 0.0);
                }
            }

            // Add data to dataset
            for (Map.Entry<String, Double> entry : revenueData.entrySet()) {
                dataset.setValue(entry.getValue(), "Revenue", entry.getKey());
            }

            // Create line chart
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

            // Customize line color
            LineAndShapeRenderer renderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
            renderer.setSeriesPaint(0, new Color(204, 0, 51));

            // Adjust X-axis labels
            CategoryAxis xAxis = categoryPlot.getDomainAxis();
            if ("Monthly".equals(filterType)) {
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
            } else {
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            }

            // Display chart in panel
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
        filtercbx = new javax.swing.JComboBox<>();
        backBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

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

        filtercbx.setBackground(new java.awt.Color(255, 255, 204));
        filtercbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Weekly", "Monthly" }));
        filtercbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtercbxActionPerformed(evt);
            }
        });
        getContentPane().add(filtercbx, new org.netbeans.lib.awtextra.AbsoluteConstraints(512, 41, 115, -1));

        backBtn.setText("<");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        getContentPane().add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 36, 42, 37));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        VendorDashboardForm vdf = new VendorDashboardForm();
        this.dispose();
        vdf.setVisible(true);
    }//GEN-LAST:event_backBtnActionPerformed

    private void filtercbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtercbxActionPerformed
        String vendorId = SessionManager.getInstance().getidInput();
        String selectedFilter = (String) filtercbx.getSelectedItem();
        showLineChart(selectedFilter, vendorId);
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
            java.util.logging.Logger.getLogger(RevenueDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RevenueDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RevenueDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RevenueDashboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RevenueDashboardForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JComboBox<String> filtercbx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelLineChart;
    // End of variables declaration//GEN-END:variables
}
