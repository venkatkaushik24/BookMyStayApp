import java.util.HashMap;
import java.util.Map;


// Abstract Room class
abstract class Room {

    private int beds;
    private double size;
    private double price;

    public Room(int beds, double size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Room Size: " + size + " sq.ft");
        System.out.println("Price per night: ₹" + price);
    }
}


// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 200, 2000);
    }
}


// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 350, 3500);
    }
}


// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 500, 6000);
    }
}


// Centralized Inventory Class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Retrieve availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\n===== Current Room Inventory =====");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Available: " + entry.getValue());
        }
    }
}


// Application Entry Point
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== BookMyStay - Room Inventory System =====");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display room details
        System.out.println("\nSingle Room Details");
        single.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Single Room"));

        System.out.println("\nDouble Room Details");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        System.out.println("\nSuite Room Details");
        suite.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Suite Room"));

        // Display full inventory
        inventory.displayInventory();
    }
}