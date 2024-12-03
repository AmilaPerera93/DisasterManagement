public class SupportRequest {
    private String location;
    private String type;

    public SupportRequest(String location, String type) {
        this.location = location;
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return location + " [" + type + "]";
    }
}
