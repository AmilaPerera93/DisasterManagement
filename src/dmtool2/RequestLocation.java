package reliefcenterapp;

import java.util.Map;

public class RequestLocation {
    private String name;
    private double latitude;
    private double longitude;
    private String contact;
    private String district;
    private String disasterType;
    private boolean completed;  // New field to track completion status

    public RequestLocation(String name, double latitude, double longitude, String contact, String district, String disasterType) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contact = contact;
        this.district = district;
        this.disasterType = disasterType;
        this.completed = false;
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

    public String getContact() {
        return contact;
    }

    public String getDistrict() {
        return district;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Map<String, Integer> getRequestedResources() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void removeRequestedResource(String resourceType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
