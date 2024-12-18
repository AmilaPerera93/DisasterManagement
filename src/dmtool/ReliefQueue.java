package dmtool;
import java.util.ArrayList;
import java.util.List;

public class ReliefQueue {
    private final List<ReliefCenter> reliefCenters;
    private final List<RequestLocation> requestLocations;

    public ReliefQueue() {
        reliefCenters = new ArrayList<>();
        requestLocations = new ArrayList<>();
    }

    public void addReliefCenter(ReliefCenter center) {
        reliefCenters.add(center);
    }

    public void addRequestLocation(RequestLocation location) {
        requestLocations.add(location);
    }

    public String findClosestReliefCenter(String requestLocationName) {
        RequestLocation selectedLocation = requestLocations.stream()
                .filter(location -> location.getName().equals(requestLocationName))
                .findFirst()
                .orElse(null);

        if (selectedLocation == null || reliefCenters.isEmpty()) {
            return "No Relief Center Available";
        }

        ReliefCenter closestCenter = null;
        double minDistance = Double.MAX_VALUE;

        for (ReliefCenter center : reliefCenters) {
            double distance = calculateDistance(
                    selectedLocation.getLatitude(),
                    selectedLocation.getLongitude(),
                    center.getLatitude(),
                    center.getLongitude()
            );
            if (distance < minDistance) {
                minDistance = distance;
                closestCenter = center;
            }
        }

        return closestCenter != null ? closestCenter.getName() : "No Relief Center Available";
    }

    public void markAsCompleted(String requestLocationName) {
        requestLocations.removeIf(location -> location.getName().equals(requestLocationName));
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formula for distance calculation
        final int R = 6371; // Radius of the Earth in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }
}
