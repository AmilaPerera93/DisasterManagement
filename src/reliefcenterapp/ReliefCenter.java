
package reliefcenterapp;

import java.util.HashMap;
import java.util.Map;

public class ReliefCenter {
    private String name;
    private double latitude;
    private double longitude;
    private String contactNumber;
    private String email;
    private Map<String, Integer> availableResources;  // Store resources and their available quantities

    public ReliefCenter(String name, double latitude, double longitude, String contactNumber, String email) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactNumber = contactNumber;
        this.email = email;
        this.availableResources = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }
    
    public Map<String, Integer> getAvailableResources() {
        return availableResources;
    }

    // Add a resource to the relief center's available resources
   public void addResource(Resource resourceType) {
    availableResources.put(resourceType.getName(), availableResources.getOrDefault(resourceType.getName(), 0) + resourceType.getQuantity());
}

    // Allocate resources to a request location and update available resources
    public boolean allocateResources(RequestLocation requestLocation) {
        Map<String, Integer> requestedResources = requestLocation.getRequestedResources();

        for (String resourceType : requestedResources.keySet()) {
            int requestedQty = requestedResources.get(resourceType);
            int availableQty = availableResources.getOrDefault(resourceType, 0);

            if (requestedQty > availableQty) {
                // If not enough resources, allocation fails
                return false;
            } else {
                // Allocate the resources and update quantities
                availableResources.put(resourceType, availableQty - requestedQty);
                requestLocation.removeRequestedResource(resourceType);
            }
        }

        return true;
    }

    // Method to get a report of available resources at the relief center
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Relief Center: ").append(name).append("\n");

        for (Map.Entry<String, Integer> entry : availableResources.entrySet()) {
            report.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return report.toString();
    }

    @Override
    public String toString() {
        return name + " (" + availableResources.size() + " resources)";
}
}