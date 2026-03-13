import java.util.*;


// Abstract Room
abstract class Room {

    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }
}


// Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}


// Inventory Service
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory: " + inventory);
    }
}


// Reservation Request
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


// Booking Queue
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}


// Booking Service (Allocation)
class BookingService {

    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();
    private int roomCounter = 1;

    public void processRequests(BookingRequestQueue queue, RoomInventory inventory) {

        System.out.println("\n===== Processing Booking Requests =====");

        while (queue.hasRequests()) {

            Reservation request = queue.getNextRequest();
            String roomType = request.getRoomType();

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                // Generate unique room ID
                String roomId = roomType.replace(" ", "") + "-" + roomCounter++;

                // Get or create set for room type
                allocatedRooms.putIfAbsent(roomType, new HashSet<>());

                // Ensure uniqueness
                allocatedRooms.get(roomType).add(roomId);

                // Update inventory
                inventory.decreaseAvailability(roomType);

                System.out.println("Reservation confirmed for "
                        + request.getGuestName()
                        + " → Room ID: " + roomId);

            } else {
                System.out.println("Reservation failed for "
                        + request.getGuestName()
                        + " (No rooms available)");
            }
        }
    }

    public void displayAllocations() {

        System.out.println("\n===== Allocated Rooms =====");

        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}


// Application Entry
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== BookMyStay Reservation System =====");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();

        // Booking Requests
        queue.addRequest(new Reservation("Arjun", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Double Room"));
        queue.addRequest(new Reservation("Rahul", "Suite Room"));
        queue.addRequest(new Reservation("Meena", "Suite Room")); // may fail

        // Booking Service
        BookingService service = new BookingService();

        service.processRequests(queue, inventory);
        service.displayAllocations();

        inventory.displayInventory();
    }
}