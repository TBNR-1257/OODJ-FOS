package com.mycompany.fos_app.GUI;

import com.mycompany.fos_app.GUI.PlaceOrder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VendorList extends JFrame {
    public CustomerHome custHome;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JButton refreshButton, backButton;
        
    public VendorList(CustomerHome custHome) {
        String idInput = SessionManager.getInstance().getidInput();
        this.custHome = custHome;
        setupUI();
        loadVendorsIntoPanel();
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    VendorList() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));

        // Top Panel (Header + Buttons)
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Food Outlets", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Delicious delights await!", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        /// Button Panel (Refresh + Back)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5)); // Adds spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); // Moves buttons right

        refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(90, 30));
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.addActionListener(this::refreshVendors);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(90, 30));
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.addActionListener(this::goBackToCustomerHome);

        buttonPanel.add(backButton);
        buttonPanel.add(refreshButton);

        topPanel.add(headerPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);


        // Main Content Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void goBackToCustomerHome(ActionEvent e) {
        this.dispose();
        custHome.setVisible(true);
    }

private void loadVendorsIntoPanel() {
    mainPanel.removeAll();

    List<String[]> vendors = readVendorsFromFile("src/main/java/com/mycompany/fos_app/Data/vendor.txt");

    for (String[] vendor : vendors) {
        if (vendor.length >= 3) {
            String vendorID = vendor[0];
            String vendorName = vendor[1];
//            String vendorDescription = vendor[3];

            JPanel vendorPanel = new JPanel(new BorderLayout());
            vendorPanel.setBorder(BorderFactory.createTitledBorder(vendorID + "  " + vendorName));
            vendorPanel.setPreferredSize(new Dimension(600, 90));
            vendorPanel.setBackground(Color.WHITE);
            vendorPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(vendorID + "  " + vendorName),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));

            // Description Label
//            JLabel descriptionLabel = new JLabel("<html><b>Description:</b> " + vendorDescription + "</html>");
//            descriptionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//            vendorPanel.add(descriptionLabel, BorderLayout.WEST);

            // View Menu Button
            JButton viewMenuButton = new JButton("View Menu");
            viewMenuButton.setPreferredSize(new Dimension(120, 30));
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(viewMenuButton);
            vendorPanel.add(buttonPanel, BorderLayout.EAST);

            // ActionListener to open PlaceOrder.java
            viewMenuButton.addActionListener(e -> openPlaceOrder(vendorID, vendorName));

            JPanel containerPanel = new JPanel();
            containerPanel.setLayout(new BorderLayout());
            containerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            containerPanel.add(vendorPanel, BorderLayout.CENTER);

            mainPanel.add(containerPanel);
        }
    }

    mainPanel.revalidate();
    mainPanel.repaint();
}

// Method to open PlaceOrder.java and pass vendor details
private void openPlaceOrder(String vendorID, String vendorName) {
    this.dispose();
    new PlaceOrder(vendorID, custHome).setVisible(true);
}


    private void refreshVendors(ActionEvent e) {
        loadVendorsIntoPanel();
    }

    private List<String[]> readVendorsFromFile(String filename) {
        List<String[]> vendors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 3) {
                    vendors.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vendors;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Vendor List");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        });
    }

    void setVisible() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}