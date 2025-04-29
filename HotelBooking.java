import java.util.*;

// Hotel class to store hotel data and implement Comparable for sorting
class Hotel implements Comparable<Hotel> {
    static String sortParam = "name";
    String name;
    int roomAvailable;
    String location;
    int rating;
    int pricePerRoom;

    // Constructor
    public Hotel(String name, int roomAvailable, String location, int rating, int pricePerRoom) {
        this.name = name;
        this.roomAvailable = roomAvailable;
        this.location = location;
        this.rating = rating;
        this.pricePerRoom = pricePerRoom;
    }

    // Static methods to change the sort parameter
    public static void sortByName() { sortParam = "name"; }
    public static void sortByRating() { sortParam = "rating"; }
    public static void sortByRoomAvailability() { sortParam = "roomAvailable"; }
    public static void sortByPrice() { sortParam = "price"; }

    @Override
    public int compareTo(Hotel other) {
        switch (sortParam) {
            case "name": return this.name.compareTo(other.name);
            case "rating": return Integer.compare(other.rating, this.rating); // higher rating first
            case "roomAvailable": return Integer.compare(other.roomAvailable, this.roomAvailable);
            case "price": return Integer.compare(this.pricePerRoom, other.pricePerRoom); // cheaper first
            default: return 0;
        }
    }

    @Override
    public String toString() {
        return String.format(
            "Hotel Name: %-10s | Rooms Available: %-3d | Location: %-10s | Rating: %-2d | Price/Room: ₹%-4d",
            name, roomAvailable, location, rating, pricePerRoom
        );
    }
}

// User class to store user data
class User {
    String username;
    int userId;
    int bookingCost;

    public User(String username, int userId, int bookingCost) {
        this.username = username;
        this.userId = userId;
        this.bookingCost = bookingCost;
    }

    @Override
    public String toString() {
        return String.format("User Name: %-5s | User ID: %-2d | Booking Cost: ₹%-4d", username, userId, bookingCost);
    }
}

public class Main {

    // Display hotel data
    public static void printHotelData(List<Hotel> hotels) {
        if (hotels.isEmpty()) {
            System.out.println("No hotels available.");
        }
        for (Hotel hotel : hotels) {
            System.out.println(hotel);
        }
    }

    // Sort and display hotels
    public static void sortAndDisplayHotels(List<Hotel> hotels, String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "name": 
                Hotel.sortByName(); 
                System.out.println("\n--- Hotels sorted by Name ---");
                break;
            case "rating": 
                Hotel.sortByRating(); 
                System.out.println("\n--- Hotels sorted by Rating ---");
                break;
            case "rooms":
                Hotel.sortByRoomAvailability(); 
                System.out.println("\n--- Hotels sorted by Rooms Available ---");
                break;
            case "price":
                Hotel.sortByPrice();
                System.out.println("\n--- Hotels sorted by Price ---");
                break;
            default:
                System.out.println("Invalid sort parameter.");
                return;
        }
        Collections.sort(hotels);
        printHotelData(hotels);
    }

    // Display hotels by city
    public static void displayHotelsByCity(String city, List<Hotel> hotels) {
        System.out.println("\n--- Hotels in " + city + " ---");
        List<Hotel> filtered = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if (hotel.location.equalsIgnoreCase(city)) {
                filtered.add(hotel);
            }
        }
        printHotelData(filtered);
    }

    // Book a room in a hotel
    public static void bookRoom(String hotelName, List<Hotel> hotels, int roomsToBook) {
        for (Hotel hotel : hotels) {
            if (hotel.name.equalsIgnoreCase(hotelName)) {
                if (hotel.roomAvailable >= roomsToBook) {
                    hotel.roomAvailable -= roomsToBook;
                    System.out.println("Successfully booked " + roomsToBook + " room(s) at " + hotelName);
                } else {
                    System.out.println("Not enough rooms available at " + hotelName);
                }
                return;
            }
        }
        System.out.println("Hotel not found: " + hotelName);
    }

    // Print user data along with booked hotel
    public static void printUserData(String[] userName, int[] userId, int[] bookingCost, List<Hotel> hotels) {
        System.out.println("\n--- User Bookings ---");
        for (int i = 0; i < userName.length; i++) {
            User user = new User(userName[i], userId[i], bookingCost[i]);
            System.out.println(user + " | Booked Hotel: " + hotels.get(i % hotels.size()).name);
        }
    }

    // Main management function
    public static void hotelManagement(String[] userName, int[] userId, String[] hotelName, int[] bookingCost,
                                        int[] rooms, String[] locations, int[] ratings, int[] prices) {
        List<Hotel> hotels = new ArrayList<>();

        for (int i = 0; i < hotelName.length; i++) {
            hotels.add(new Hotel(hotelName[i], rooms[i], locations[i], ratings[i], prices[i]));
        }

        System.out.println("\n=== Initial Hotel Data ===");
        printHotelData(hotels);

        sortAndDisplayHotels(hotels, "name");
        sortAndDisplayHotels(hotels, "rating");
        displayHotelsByCity("Bangalore", hotels);
        sortAndDisplayHotels(hotels, "rooms");
        sortAndDisplayHotels(hotels, "price");

        // Example booking
        bookRoom("H1", hotels, 2);
        bookRoom("H2", hotels, 6); // should show not enough rooms

        printUserData(userName, userId, bookingCost, hotels);
    }

    // Driver Code
    public static void main(String[] args) {
        // Initialize hotel and user data
        String[] userName = {"U1", "U2", "U3"};
        int[] userId = {2, 3, 4};
        String[] hotelName = {"H1", "H2", "H3"};
        int[] bookingCost = {1000, 1200, 1100};
        int[] rooms = {4, 5, 6};
        String[] locations = {"Bangalore", "Bangalore", "Mumbai"};
        int[] ratings = {5, 5, 3};
        int[] prices = {100, 200, 100};

        // Run hotel management operations
        hotelManagement(userName, userId, hotelName, bookingCost, rooms, locations, ratings, prices);
    }
}
