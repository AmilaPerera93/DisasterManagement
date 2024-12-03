import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ReliefToolApp {
    private JFrame frame;
    private JTextArea outputArea;
    private JTextField reliefCenterField, centerLatitudeField, centerLongitudeField;
    private JTextField locationField, locationLatitudeField, locationLongitudeField;
    private JComboBox<String> requestTypeDropdown;
    private Queue<ReliefCenter> reliefCenters;
    private Queue<SupportRequest> supportRequests;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReliefToolApp().createAndShowGUI());
    }

    public ReliefToolApp() {
        reliefCenters = new LinkedList<>();
        supportRequests = new LinkedList<>();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Relief Supply Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        frame.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Relief Center Input
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Relief Center Location:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        reliefCenterField = new JTextField(10);
        panel.add(reliefCenterField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Latitude:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        centerLatitudeField = new JTextField(10);
        panel.add(centerLatitudeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Longitude:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        centerLongitudeField = new JTextField(10);
        panel.add(centerLongitudeField, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 3;
        JButton addReliefCenterButton = new JButton("Add Relief Center");
        addReliefCenterButton.addActionListener(new AddReliefCenterListener());
        panel.add(addReliefCenterButton, gbc);

        // Support Request Input
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridheight = 1;
        panel.add(new JLabel("Request Location:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        locationField = new JTextField(10);
        panel.add(locationField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Latitude:"), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        locationLatitudeField = new JTextField(10);
        panel.add(locationLatitudeField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Longitude:"), gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        locationLongitudeField = new JTextField(10);
        panel.add(locationLongitudeField, gbc);

        gbc.gridx = 2; gbc.gridy = 3; gbc.gridheight = 3;
        requestTypeDropdown = new JComboBox<>(new String[]{"Food", "Water", "Medical Supplies"});
        panel.add(requestTypeDropdown, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        JButton addRequestButton = new JButton("Add Request");
        addRequestButton.addActionListener(new AddRequestListener());
        panel.add(addRequestButton);

        JButton findClosestButton = new JButton("Find Closest Relief Center");
        findClosestButton.addActionListener(new FindClosestListener());
        panel.add(findClosestButton);

        return panel;
    }

    private class AddReliefCenterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = reliefCenterField.getText();
            double latitude = Double.parseDouble(centerLatitudeField.getText());
            double longitude = Double.parseDouble(centerLongitudeField.getText());

            reliefCenters.add(new ReliefCenter(name, latitude, longitude));
            outputArea.append("Relief Center added: " + name + " (" + latitude + ", " + longitude + ")\n");

            reliefCenterField.setText("");
            centerLatitudeField.setText("");
            centerLongitudeField.setText("");
        }
    }

    private class AddRequestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String locationName = locationField.getText();
            double latitude = Double.parseDouble(locationLatitudeField.getText());
            double longitude = Double.parseDouble(locationLongitudeField.getText());
            String requestType = (String) requestTypeDropdown.getSelectedItem();

            supportRequests.add(new SupportRequest(locationName, latitude, longitude, requestType));
            outputArea.append("Request added: " + requestType + " at " + locationName + " (" + latitude + ", " + longitude + ")\n");

            locationField.setText("");
            locationLatitudeField.setText("");
            locationLongitudeField.setText("");
        }
    }

    private class FindClosestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (supportRequests.isEmpty() || reliefCenters.isEmpty()) {
                outputArea.append("No relief centers or support requests available.\n");
                return;
            }

            SupportRequest request = supportRequests.peek(); // Get the first request
            ReliefCenter closestCenter = null;
            double minDistance = Double.MAX_VALUE;

            for (ReliefCenter center : reliefCenters) {
                double distance = center.distanceTo(request.getLatitude(), request.getLongitude());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCenter = center;
                }
            }

            if (closestCenter != null) {
                outputArea.append("Closest Relief Center to " + request.getLocationName() + ": "
                        + closestCenter.getName() + " (" + String.format("%.2f", minDistance) + " km)\n");
            }
        }
    }
}
