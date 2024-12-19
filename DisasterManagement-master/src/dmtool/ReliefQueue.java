package dmtool;


import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

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

    public ReliefCenter getClosestReliefCenter(String requestLocationName) {
        RequestLocation requestLocation = getRequestLocationByName(requestLocationName);
        if (requestLocation == null || reliefCenters.isEmpty()) {
            return null;
        }

        ReliefCenter closestCenter = null;
        double minDistance = Double.MAX_VALUE;

        for (ReliefCenter center : reliefCenters) {
            double distance = calculateDistance(
                    requestLocation.getLatitude(),
                    requestLocation.getLongitude(),
                    center.getLatitude(),
                    center.getLongitude()
            );
            if (distance < minDistance) {
                minDistance = distance;
                closestCenter = center;
            }
        }
        return closestCenter;
    }

    public RequestLocation getRequestLocationByName(String name) {
        return requestLocations.stream()
                .filter(location -> location.getName().equals(name))
                .findFirst()
                .orElse(null);
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

