package dmtool;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ReliefSupplyApp extends JFrame {
    private final ReliefQueue reliefQueue;
    private final DefaultListModel<String> reliefCenterListModel;
    private final DefaultListModel<String> requestLocationListModel;
    private final JComboBox<String> requestDropdown;
    
   
    

    public ReliefSupplyApp() {
        reliefQueue = new ReliefQueue();
        reliefCenterListModel = new DefaultListModel<>();
        requestLocationListModel = new DefaultListModel<>();
        requestDropdown = new JComboBox<>();
        JComboBox<Object> disasterType = new JComboBox<>();
        JComboBox<Object> requestedResources = new JComboBox<>();
        initComponents();
    }

    private void initComponents() {
        setTitle("Relief Supply Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Relief Center Management Tab
        tabbedPane.addTab("Relief Centers", createReliefCenterPanel());

        // Request Location Management Tab
        tabbedPane.addTab("Request Locations", createRequestLocationPanel());

        // Queue Management Tab
        tabbedPane.addTab("Manage Requests", createRequestManagementPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createReliefCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Relief Center"));
        JTextField centerNameField = new JTextField();
        JTextField latitudeField = new JTextField();
        JTextField longitudeField = new JTextField();
        JTextField emailField=new JTextField();
        JTextField contactNumberField =new JTextField();
        

        inputPanel.add(new JLabel("Relief Center Name:"));
        inputPanel.add(centerNameField);
        inputPanel.add(new JLabel("Latitude:"));
        inputPanel.add(latitudeField);
        inputPanel.add(new JLabel("Longitude:"));
        inputPanel.add(longitudeField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Contact Number:"));
        inputPanel.add(contactNumberField);

        // Add Relief Center Button
        JButton addCenterButton = new JButton("Add Relief Center");
        addCenterButton.addActionListener(e -> {
            String name = centerNameField.getText().trim();
            double latitude = Double.parseDouble(latitudeField.getText().trim());
            double longitude = Double.parseDouble(longitudeField.getText().trim());
            String email=emailField.getText().trim();
            
            reliefQueue.addReliefCenter(new ReliefCenter(name, latitude, longitude));
            reliefCenterListModel.addElement(name);
            centerNameField.setText("");
            latitudeField.setText("");
            longitudeField.setText("");
        });

        // Relief Centers List
        JList<String> reliefCenterList = new JList<>(reliefCenterListModel);
        JScrollPane listScrollPane = new JScrollPane(reliefCenterList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Available Relief Centers"));

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(listScrollPane, BorderLayout.CENTER);
        panel.add(addCenterButton, BorderLayout.SOUTH);

        return panel;
    }

    @SuppressWarnings("empty-statement")
    private JPanel createRequestLocationPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Request Location"));
        JTextField locationNameField = new JTextField();
        JTextField latitudeField = new JTextField();
        JTextField longitudeField = new JTextField();
  
       
        String[] disasterType= {"Floods","Land Slides"};
        JComboBox<String> comboBox = new JComboBox<>(disasterType);
        
        
        String[] requestedResources={"Food","Water","Medicines"};
        JComboBox<String> comboBox1 = new JComboBox<>(requestedResources);
       

        inputPanel.add(new JLabel("Request Location Name:"));
        inputPanel.add(locationNameField);
        inputPanel.add(new JLabel("Latitude:"));
        inputPanel.add(latitudeField);
        inputPanel.add(new JLabel("Longitude:"));
        inputPanel.add(longitudeField);
        inputPanel.add(new JLabel ("Disaster Type:"));
        inputPanel.add(comboBox);
        inputPanel.add(new JLabel("Requested Resources:"));
        inputPanel.add(comboBox1);

        // Add Request Location Button
        JButton addLocationButton = new JButton("Add Request Location");
        addLocationButton.addActionListener((var e) -> {
            String name = locationNameField.getText().trim();
            double latitude = Double.parseDouble(latitudeField.getText().trim());
            double longitude = Double.parseDouble(longitudeField.getText().trim());
         

            RequestLocation location = new RequestLocation(name, latitude, longitude);
            reliefQueue.addRequestLocation(location);
            requestLocationListModel.addElement(name);
            requestDropdown.addItem(name);
            locationNameField.setText("");
            latitudeField.setText("");
            longitudeField.setText("");
        
        });

        // Request Locations List
        JList<String> requestLocationList = new JList<>(requestLocationListModel);
        JScrollPane listScrollPane = new JScrollPane(requestLocationList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Request Locations"));

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(listScrollPane, BorderLayout.CENTER);
        panel.add(addLocationButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRequestManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Dropdown for Selecting Request Location
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Request Location:"));
        topPanel.add(requestDropdown);

        // Display Closest Relief Center
        JLabel closestReliefCenterLabel = new JLabel("Closest Relief Center: None");
        JButton findClosestButton = new JButton("Find Closest Relief Center");
        findClosestButton.addActionListener(e -> {
            String selectedLocationName = (String) requestDropdown.getSelectedItem();
            if (selectedLocationName != null) {
                String closestCenter = reliefQueue.findClosestReliefCenter(selectedLocationName);
                closestReliefCenterLabel.setText("Closest Relief Center: " + closestCenter);
            }
        });

        // Allocate Resources and Mark Complete
        JButton allocateResourcesButton = new JButton("Allocate Resources and Mark Complete");
        allocateResourcesButton.addActionListener(e -> {
            String selectedLocationName = (String) requestDropdown.getSelectedItem();
            if (selectedLocationName != null) {
                reliefQueue.markAsCompleted(selectedLocationName);
                requestDropdown.removeItem(selectedLocationName);
                requestLocationListModel.removeElement(selectedLocationName);
                closestReliefCenterLabel.setText("Closest Relief Center: None");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(findClosestButton);
        buttonPanel.add(allocateResourcesButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(closestReliefCenterLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReliefSupplyApp().setVisible(true));
    }
}
