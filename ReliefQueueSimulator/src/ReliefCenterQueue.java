import java.util.ArrayList;

public class ReliefCenterQueue {
    private ArrayList<ReliefCenter> centers = new ArrayList<>();

    public void addCenter(ReliefCenter center) {
        centers.add(center);
    }

    public ReliefCenter findClosestCenter(int x, int y) {
        if (centers.isEmpty()) return null;

        ReliefCenter closest = null;
        double minDistance = Double.MAX_VALUE;

        for (ReliefCenter center : centers) {
            double distance = center.distanceTo(x, y);
            if (distance < minDistance) {
                minDistance = distance;
                closest = center;
            }
        }

        return closest;
    }

    public String getAllCenters() {
        if (centers.isEmpty()) return "No relief centers available.";
        StringBuilder sb = new StringBuilder();
        for (ReliefCenter center : centers) {
            sb.append(center).append("\n");
        }
        return sb.toString();
    }
}
