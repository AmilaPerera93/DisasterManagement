import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ReliefQueue reliefQueue = new ReliefQueue(10);

        System.out.println("=== Disaster Relief Supply Chain Simulator ===");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add Supply Request");
            System.out.println("2. Process Next Request");
            System.out.println("3. View Pending Requests");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter request details (e.g., 'Food for Center A'): ");
                    String request = scanner.nextLine();
                    reliefQueue.enqueue(request);
                    break;

                case 2:
                    reliefQueue.dequeue();
                    break;

                case 3:
                    reliefQueue.displayQueue();
                    break;

                case 4:
                    System.out.println("Exiting the simulator. Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
