package dmtool;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MapboxApp {
    private static final String ACCESS_TOKEN = "sk.eyJ1IjoibWFsaW5kdWluZHV3YXJhIiwiYSI6ImNtNHUyd3ZkZjBrdzEya3M0NWdvYzJ1MmYifQ.1KQqAFTFvjQkQoYrOheGLQ";

    private static double currentLat = 6.9271; // Default latitude (Colombo)
    private static double currentLng = 79.8612; // Default longitude (Colombo)
    private static int zoomLevel = 14; // Default zoom level
    private static String currentPolyline = null; // To store the current route polyline

    public static void displayRoute(double startLat, double startLng, double endLat, double endLng) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mapbox Route Finder");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            JLabel mapLabel = new JLabel("Map will appear here", SwingConstants.CENTER);
            frame.add(mapLabel, BorderLayout.CENTER);

            JPanel controlPanel = new JPanel();
            frame.add(controlPanel, BorderLayout.SOUTH);

            // Zoom and Pan Buttons
            JButton zoomInButton = new JButton("Zoom In");
            JButton zoomOutButton = new JButton("Zoom Out");
            JButton panUpButton = new JButton("Pan Up");
            JButton panDownButton = new JButton("Pan Down");
            JButton panLeftButton = new JButton("Pan Left");
            JButton panRightButton = new JButton("Pan Right");

            controlPanel.add(zoomInButton);
            controlPanel.add(zoomOutButton);
            controlPanel.add(panUpButton);
            controlPanel.add(panDownButton);
            controlPanel.add(panLeftButton);
            controlPanel.add(panRightButton);

            try {
                // Fetch route polyline
                currentPolyline = getRoutePolyline(startLat, startLng, endLat, endLng);

                // Display the map with the route
                updateMap(mapLabel);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error loading map: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Add Action Listeners
            zoomInButton.addActionListener(e -> {
                if (zoomLevel < 22) {
                    zoomLevel++;
                    updateMap(mapLabel);
                }
            });

            zoomOutButton.addActionListener(e -> {
                if (zoomLevel > 0) {
                    zoomLevel--;
                    updateMap(mapLabel);
                }
            });

            panUpButton.addActionListener(e -> {
                currentLat += 0.05;
                updateMap(mapLabel);
            });

            panDownButton.addActionListener(e -> {
                currentLat -= 0.05;
                updateMap(mapLabel);
            });

            panLeftButton.addActionListener(e -> {
                currentLng -= 0.05;
                updateMap(mapLabel);
            });

            panRightButton.addActionListener(e -> {
                currentLng += 0.05;
                updateMap(mapLabel);
            });

            frame.setVisible(true);
        });
    }

    private static String getRoutePolyline(double startLat, double startLng, double endLat, double endLng) throws Exception {
        String routeUrl = "https://api.mapbox.com/directions/v5/mapbox/driving/"
                + startLng + "," + startLat + ";" + endLng + "," + endLat
                + "?geometries=polyline&access_token=" + ACCESS_TOKEN;

        URL url = new URL(routeUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            JsonNode root = new ObjectMapper().readTree(response.toString());
            JsonNode route = root.get("routes").get(0);
            return route.get("geometry").asText(); // Return the polyline
        }
    }

    private static void updateMap(JLabel mapLabel) {
        try {
            String mapUrl = "https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/"
                    + (currentPolyline != null ? "path-5+ff0000-1(" + currentPolyline + ")/" : "")
                    + currentLng + "," + currentLat + "," + zoomLevel + ",0/800x600"
                    + "?access_token=" + ACCESS_TOKEN;

            BufferedImage mapImage = ImageIO.read(new URL(mapUrl));
            mapLabel.setIcon(new ImageIcon(mapImage));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mapLabel.getTopLevelAncestor(), "Error updating map: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
