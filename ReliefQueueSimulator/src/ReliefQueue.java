public class ReliefQueue {
    private String[] queue;
    private int front, rear, size, capacity;

    public ReliefQueue(int capacity) {
        this.capacity = capacity;
        queue = new String[capacity];
        front = 0;
        size = 0;
        rear = -1;
    }

    public void enqueue(String request) {
        if (size == capacity) {
            System.out.println("Queue is full! Cannot add more requests.");
            return;
        }
        rear = (rear + 1) % capacity;
        queue[rear] = request;
        size++;
        System.out.println("Added to queue: " + request);
    }

    public void dequeue() {
        if (size == 0) {
            System.out.println("Queue is empty. No requests to process.");
            return;
        }
        System.out.println("Processed: " + queue[front]);
        front = (front + 1) % capacity;
        size--;
    }

    public void displayQueue() {
        if (size == 0) {
            System.out.println("No pending requests in the queue.");
            return;
        }
        System.out.println("Pending Requests:");
        for (int i = 0; i < size; i++) {
            System.out.println("- " + queue[(front + i) % capacity]);
        }
    }
}
