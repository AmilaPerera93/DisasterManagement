import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReliefToolApp extends JFrame {
    private ReliefCenterQueue reliefCenterQueue = new ReliefCenterQueue();
    private SupportRequestQueue supportRequestQueue = new SupportRequestQueue();

    private JTextArea displayArea;
    private JTextField reliefCenterField;
    private JTextField locationField;
    private JComboBox<String> requestTypeDropdown;

    public ReliefToolApp() {
        // Frame settings
        setTitle("Disaster Relief Tool");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Disaster Relief Tool", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Center panel for display
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for input and actions
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 1, 5, 5));

        // Relief Center Input
        JPanel reliefCenterPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        reliefCenterField = new JTextField();
        JButton addReliefCenterButton = new JButton("Add Relief Center");
        addReliefCenterButton.addActionListener(new AddReliefCenterListener());
        reliefCenterPanel.add(new JLabel("Relief Center Location:"));
        reliefCenterPanel.add(reliefCenterField);
        reliefCenterPanel.add(addReliefCenterButton);

        // Support Request Input
        JPanel requestPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        locationField = new JTextField();
        requestTypeDropdown = new JComboBox<>(new String[]{"Food", "Water", "Medical Supplies"});
        JButton addRequestButton = new JButton("Add Request");
        addRequestButton.addActionListener(new AddRequestListener());
        requestPanel.add(new JLabel("Request Location:"));
        requestPanel.add(locationField);
        requestPanel.add(requestTypeDropdown);
        requestPanel.add(addRequestButton);

        // Action Buttons
        JPanel actionPanel = new JPanel();
        JButton findClosestButton = new JButton("Find Closest Center");
        findClosestButton.addActionListener(new FindClosestListener());
        JButton viewStatusButton = new JButton("View All Data");
        viewStatusButton.addActionListener(new ViewStatusListener());
        actionPanel.add(findClosestButton);
        actionPanel.add(viewStatusButton);

        bottomPanel.add(reliefCenterPanel);
        bottomPanel.add(requestPanel);
        bottomPanel.add(actionPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateDisplay(String content) {
        displayArea.setText(content);
    }

    // Add Relief Center Listener
    private class AddReliefCenterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String center = reliefCenterField.getText().trim();
            if (center.isEmpty()) {
                JOptionPane.showMessageDialog(ReliefToolApp.this, "Enter a valid relief center location.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            reliefCenterQueue.addCenter(center);
            reliefCenterField.setText("");
            updateDisplay("Relief Center added: " + center);
        }
    }

    // Add Request Listener
    private class AddRequestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String location = locationField.getText().trim();
            String type = (String) requestTypeDropdown.getSelectedItem();
            if (location.isEmpty()) {
                JOptionPane.showMessageDialog(ReliefToolApp.this, "Enter a valid request location.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            supportRequestQueue.addRequest(new SupportRequest(location, type));
            locationField.setText("");
            updateDisplay("Request added: " + location + " [" + type + "]");
        }
    }

    // Find Closest Center Listener
    private class FindClosestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (supportRequestQueue.isEmpty() || reliefCenterQueue.isEmpty()) {
                updateDisplay("No requests or relief centers available to process.");
                return;
            }
            String requestLocation = supportRequestQueue.peekRequest().getLocation();
            String closestCenter = reliefCenterQueue.findClosestCenter(requestLocation);
            updateDisplay("Closest relief center for request at " + requestLocation + " is: " + closestCenter);
        }
    }

    // View All Data Listener
    private class ViewStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String data = "Relief Centers:\n" + reliefCenterQueue.getAllCenters() + "\n\n";
            data += "Support Requests:\n" + supportRequestQueue.getAllRequests();
            updateDisplay(data);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReliefToolApp app = new ReliefToolApp();
            app.setVisible(true);
        });
    }
}
