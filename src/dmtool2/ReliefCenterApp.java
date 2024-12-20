package reliefcenterapp;

import dmtool2.ReliefCenter;
import java.awt.*;
import javax.swing.*;

public class ReliefCenterApp extends JFrame {
    private final ReliefQueue reliefQueue;

    private final DefaultListModel<String> reliefCenterListModel;
    private final DefaultListModel<String> requestLocationListModel;
    private final JComboBox<String> requestDropdown;

    public ReliefCenterApp() {
        reliefQueue = new ReliefQueue();
        reliefCenterListModel = new DefaultListModel<>();
        requestLocationListModel = new DefaultListModel<>();
        requestDropdown = new JComboBox<>();

        initComponents();
    }

    private void initComponents() {
        // Apply Nimbus Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to default
        }

        setTitle("Relief Supply Management System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Relief Centers", createReliefCenterPanel());
        tabbedPane.addTab("Request Locations", createRequestLocationPanel());
        tabbedPane.addTab("Manage Requests", createRequestManagementPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createReliefCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Relief Center"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input Fields
        JTextField centerNameField = new JTextField(15);
        JTextField latitudeField = new JTextField(15);
        JTextField longitudeField = new JTextField(15);
        JTextField contactField = new JTextField(15);
        JTextField emailField = new JTextField(15);

        // Add Labels and Fields to Input Panel
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Relief Center Name:"), gbc);
        gbc.gridx = 1; inputPanel.add(centerNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Latitude:"), gbc);
        gbc.gridx = 1; inputPanel.add(latitudeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Longitude:"), gbc);
        gbc.gridx = 1; inputPanel.add(longitudeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1; inputPanel.add(contactField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; inputPanel.add(emailField, gbc);

        // Add Button
        JButton addCenterButton = new JButton("Add Relief Center");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addCenterButton, gbc);

        addCenterButton.addActionListener(e -> {
            String name = centerNameField.getText().trim();
            try {
                double latitude = Double.parseDouble(latitudeField.getText().trim());
                double longitude = Double.parseDouble(longitudeField.getText().trim());
                String contact = contactField.getText().trim();
                String email = emailField.getText().trim();

                if (!name.isEmpty() && !contact.isEmpty() && !email.isEmpty()) {
                    reliefQueue.addReliefCenter(new ReliefCenter(name, latitude, longitude, contact, email));
                    reliefCenterListModel.addElement(name);

                    // Clear Fields
                    centerNameField.setText("");
                    latitudeField.setText("");
                    longitudeField.setText("");
                    contactField.setText("");
                    emailField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Latitude and Longitude must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Relief Centers List
        JList<String> reliefCenterList = new JList<>(reliefCenterListModel);
        JScrollPane listScrollPane = new JScrollPane(reliefCenterList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Available Relief Centers"));

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(listScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRequestLocationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Request Location"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input Fields
        JTextField locationNameField = new JTextField(15);
        JTextField latitudeField = new JTextField(15);
        JTextField longitudeField = new JTextField(15);
        JTextField contactField = new JTextField(15);
        JTextField districtField = new JTextField(15);
        JComboBox<String> disasterTypeBox = new JComboBox<>(new String[]{"Flood", "Earthquake", "Cyclone", "Fire", "Other"});

        // Add Labels and Fields to Input Panel
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Request Location Name:"), gbc);
        gbc.gridx = 1; inputPanel.add(locationNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Latitude:"), gbc);
        gbc.gridx = 1; inputPanel.add(latitudeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Longitude:"), gbc);
        gbc.gridx = 1; inputPanel.add(longitudeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1; inputPanel.add(contactField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("District:"), gbc);
        gbc.gridx = 1; inputPanel.add(districtField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        inputPanel.add(new JLabel("Disaster Type:"), gbc);
        gbc.gridx = 1; inputPanel.add(disasterTypeBox, gbc);

        // Add Button
        JButton addLocationButton = new JButton("Add Request Location");
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addLocationButton, gbc);

        addLocationButton.addActionListener(e -> {
            String name = locationNameField.getText().trim();
            try {
                double latitude = Double.parseDouble(latitudeField.getText().trim());
                double longitude = Double.parseDouble(longitudeField.getText().trim());
                String contact = contactField.getText().trim();
                String district = districtField.getText().trim();
                String disasterType = (String) disasterTypeBox.getSelectedItem();

                if (!name.isEmpty() && !contact.isEmpty() && !district.isEmpty()) {
                    RequestLocation location = new RequestLocation(name, latitude, longitude, contact, district, disasterType);
                    reliefQueue.addRequestLocation(location);
                    requestLocationListModel.addElement(name);
                    requestDropdown.addItem(name);

                    // Clear Fields
                    locationNameField.setText("");
                    latitudeField.setText("");
                    longitudeField.setText("");
                    contactField.setText("");
                    districtField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Latitude and Longitude must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Request Locations List
        JList<String> requestLocationList = new JList<>(requestLocationListModel);
        JScrollPane listScrollPane = new JScrollPane(requestLocationList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Request Locations"));

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(listScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRequestManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Select Request Location:"));
        topPanel.add(requestDropdown);

        JLabel closestReliefCenterLabel = new JLabel("Closest Relief Center: None");
        JButton findClosestButton = new JButton("Find Closest Relief Center");

        findClosestButton.addActionListener(e -> {
            String selectedLocationName = (String) requestDropdown.getSelectedItem();
            if (selectedLocationName != null) {
                ReliefCenter closestCenter = reliefQueue.getClosestReliefCenter(selectedLocationName);
                RequestLocation selectedLocation = reliefQueue.getRequestLocation(selectedLocationName);

                if (closestCenter != null && selectedLocation != null) {
                    closestReliefCenterLabel.setText("Closest Relief Center: " + closestCenter.getName());

                    // Display the map using the MapboxApp class
                    double startLat = selectedLocation.getLatitude();
                    double startLng = selectedLocation.getLongitude();
                    double endLat = closestCenter.getLatitude();
                    double endLng = closestCenter.getLongitude();

                    MapboxApp.displayRoute(startLat, startLng, endLat, endLng);
                } else {
                    closestReliefCenterLabel.setText("Closest Relief Center: Not Found");
                    JOptionPane.showMessageDialog(panel, "Could not find a closest relief center.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // New button for marking the request location as completed
        JButton markAsCompletedButton = new JButton("Mark Request as Completed");
        markAsCompletedButton.addActionListener(e -> {
            String selectedLocationName = (String) requestDropdown.getSelectedItem();
            if (selectedLocationName != null) {
                boolean success = reliefQueue.markRequestAsCompleted(selectedLocationName);
                if (success) {
                    JOptionPane.showMessageDialog(panel, "Request location marked as completed.");
                    // Optionally, you can remove the location from the dropdown
                    requestDropdown.removeItem(selectedLocationName);
                } else {
                    JOptionPane.showMessageDialog(panel, "Failed to mark the request location as completed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(findClosestButton);
        buttonPanel.add(markAsCompletedButton); // Add the new button here

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(closestReliefCenterLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ReliefCenterApp().setVisible(true);
        });
    }
}
