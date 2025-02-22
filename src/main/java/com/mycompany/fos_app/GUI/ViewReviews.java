package com.mycompany.fos_app.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ViewReviews extends JFrame {
    private JComboBox<String> vendorDropdown;
    private JPanel reviewPanel;
    private JScrollPane scrollPane;
    private JButton refreshButton, backButton;
    private CustomerHome custHome; 

public ViewReviews(CustomerHome custHome) {
    this.custHome = custHome;
    setTitle("Vendor Reviews");
    setSize(600, 500);
    setLayout(new BorderLayout());
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Top panel for dropdown and buttons
    JPanel topPanel = new JPanel(new BorderLayout());

    // Dropdown section
    JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    vendorDropdown = new JComboBox<>();
    vendorDropdown.addActionListener(this::onVendorSelected);
    dropdownPanel.add(new JLabel("Select Vendor:"));
    dropdownPanel.add(vendorDropdown);

    // Button panel (aligned to right)
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    refreshButton = new JButton("Refresh");
    refreshButton.addActionListener(e -> refreshReviews());
    backButton = new JButton("Back");
    backButton.addActionListener(e -> goBack());
    buttonPanel.add(refreshButton);
    buttonPanel.add(backButton);

    // Combine panels
    topPanel.add(dropdownPanel, BorderLayout.WEST);
    topPanel.add(buttonPanel, BorderLayout.EAST);

    // Review panel (Scrollable)
    reviewPanel = new JPanel();
    reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
    scrollPane = new JScrollPane(reviewPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    add(topPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);

    loadVendors();
}


    public void loadVendors() {
        vendorDropdown.removeAllItems(); // Clear existing items
        File file = new File("src/main/java/com/mycompany/fos_app/Data/vendor.txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Vendors file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(";");
                    if (parts.length >= 4) {
                        String vendorID = parts[0];  
                        String vendorName = parts[2]; 
                        vendorDropdown.addItem(vendorID + " | " + vendorName);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading vendor file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onVendorSelected(ActionEvent e) {
        String selectedItem = (String) vendorDropdown.getSelectedItem();
        if (selectedItem != null) {
            String vendorID = selectedItem.split(" \\| ")[0]; // Extract Vendor ID
            loadReviews(vendorID);
        }
    }

    private void loadReviews(String vendorID) {
        reviewPanel.removeAll(); // Clear previous reviews
        File file = new File("src/main/java/com/mycompany/fos_app/Data/reviews.txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Reviews file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            boolean hasReviews = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(";");

                    if (parts.length >= 8 && parts[1].equals(vendorID)) { // Match Vendor ID
                        hasReviews = true;
                        String item = parts[3];    // Item
                        String date = parts[5];    // Date
                        String custName = parts[7]; // Cust Name
                        String review = parts[8];  // Review comment
                        String rating = parts[9];  // Rating

                        // Create review panel
                        JPanel reviewItem = new JPanel();
                        reviewItem.setLayout(new GridLayout(4, 1));
                        reviewItem.setBorder(BorderFactory.createTitledBorder("Customer Review"));
                        reviewItem.setBackground(Color.WHITE);
                        reviewItem.setPreferredSize(new Dimension(550, 80));

                        reviewItem.add(new JLabel("Name: " + custName));
                        reviewItem.add(new JLabel("Item: " + item));
                        reviewItem.add(new JLabel("Date: " + date));
                        reviewItem.add(new JLabel("Review: " + review));
                        reviewItem.add(new JLabel("Rating: " + rating + "/5"));

                        reviewPanel.add(reviewItem);
                    }
                }
            }

            if (!hasReviews) {
                reviewPanel.add(new JLabel("No reviews available for this vendor."));
            }

            reviewPanel.revalidate();
            reviewPanel.repaint();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading reviews file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshReviews() {
        String selectedItem = (String) vendorDropdown.getSelectedItem();
        if (selectedItem != null) {
            String vendorID = selectedItem.split(" \\| ")[0];
            loadReviews(vendorID);
        }
    }

    private void goBack() {
    dispose(); 
    if (custHome != null) {
        custHome.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Error: Customer Home is not available!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
