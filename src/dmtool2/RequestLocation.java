package reliefcenterapp;

import java.util.HashMap;
import java.util.Map;

public class RequestLocation {
    private String name;
    private double latitude;
    private double longitude;
    private String contactNumber;
    private String district;
    private String disasterType;
    private Map<String, Integer> requestedResources;  // Requested resources

    public RequestLocation(String name, double latitude, double longitude, String contactNumber, String district, String disasterType) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactNumber = contactNumber;
        this.district = district;
        this.disasterType = disasterType;
        this.requestedResources = new HashMap<>();
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

    public String getDistrict() {
        return district;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public Map<String, Integer> getRequestedResources() {
        return requestedResources;
    }

    // Add requested resource
    public void addRequestedResource(String resource, int quantity) {
        requestedResources.put(resource, quantity);
    }

    // Remove requested resource after allocation
    public void removeRequestedResource(String resource) {
        requestedResources.remove(resource);
    }

    @Override
    public String toString() {
        return name + " (" + disasterType + ")";
    }
}
