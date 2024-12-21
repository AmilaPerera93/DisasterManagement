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

    public ReliefCenter getClosestReliefCenter(String locationName) {
        RequestLocation location = getRequestLocation(locationName);
        if (location == null) {
            return null;  // Location not found
        }

        ReliefCenter closestCenter = null;
        double shortestDistance = Double.MAX_VALUE;

        for (ReliefCenter center : reliefCenters) {
            double distance = calculateDistance(location.getLatitude(), location.getLongitude(),
                                               center.getLatitude(), center.getLongitude());
            if (distance < shortestDistance) {
                shortestDistance = distance;
                closestCenter = center;
            }
        }

        return closestCenter;
    }

    public RequestLocation getRequestLocation(String locationName) {
        for (RequestLocation location : requestLocations) {
            if (location.getName().equals(locationName)) {
                return location;
            }
        }
        return null;
    }

    public boolean markRequestAsCompleted(String locationName) {
        RequestLocation location = getRequestLocation(locationName);
        if (location != null) {
            location.setCompleted(true);
            requestLocations.remove(location); // Remove from the list after completion
            return true;
        }
        return false;
    }

    // Haversine formula to calculate the distance between two points
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0; // Earth radius in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;  // Distance in kilometers
    }
}



