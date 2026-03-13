import java.util.HashMap;
import java.util.Map;


// Abstract Room class
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

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: ₹" + price);
    }
}


// Concrete room classes
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


// Inventory Class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example unavailable room
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


// Search Service (Read-only)
class RoomSearchService {

    public void searchAvailableRooms(Room[] rooms, RoomInventory inventory) {

        System.out.println("\n===== Available Rooms =====");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println();
            }
        }
    }
}


// Application Entry
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== BookMyStay Room Search =====");

        // Room domain objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] rooms = {single, doubleRoom, suite};

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Guest searches available rooms
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("\nSearch completed. Inventory unchanged.");
    }
}