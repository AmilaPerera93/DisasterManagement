public class ReliefCenter {
    private String name;
    private double latitude;
    private double longitude;

    public ReliefCenter(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double distanceTo(double latitude, double longitude) {
        final int R = 6371; // Earth's radius in kilometers
        double latDistance = Math.toRadians(latitude - this.latitude);
        double lonDistance = Math.toRadians(longitude - this.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                 + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(latitude))
                 * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }

    @Override
    public String toString() {
        return name + " (" + latitude + ", " + longitude + ")";
    }
}
