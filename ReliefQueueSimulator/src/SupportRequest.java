public class SupportRequest {
    private String locationName;
    private double latitude;
    private double longitude;
    private String requestType;

    public SupportRequest(String locationName, double latitude, double longitude, String requestType) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.requestType = requestType;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getRequestType() {
        return requestType;
    }

    @Override
    public String toString() {
        return requestType + " needed at " + locationName + " (" + latitude + ", " + longitude + ")";
    }
}
