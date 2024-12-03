import java.util.ArrayList;

public class ReliefCenterQueue {
    private ArrayList<String> centers = new ArrayList<>();

    public void addCenter(String center) {
        centers.add(center);
    }

    public boolean isEmpty() {
        return centers.isEmpty();
    }

    public String findClosestCenter(String location) {
        // For simplicity, return the first available center (expand this with actual mapping logic).
        return centers.isEmpty() ? "No available centers." : centers.get(0);
    }

    public String getAllCenters() {
        if (centers.isEmpty()) return "No relief centers available.";
        return String.join("\n", centers);
    }
}
