import java.util.LinkedList;
import java.util.Queue;

public class SupportRequestQueue {
    private Queue<SupportRequest> requests = new LinkedList<>();

    public void addRequest(SupportRequest request) {
        requests.add(request);
    }

    public SupportRequest peekRequest() {
        return requests.peek();
    }

    public boolean isEmpty() {
        return requests.isEmpty();
    }

    public String getAllRequests() {
        if (requests.isEmpty()) return "No pending requests.";
        StringBuilder sb = new StringBuilder();
        for (SupportRequest request : requests) {
            sb.append(request).append("\n");
        }
        return sb.toString();
    }
}
