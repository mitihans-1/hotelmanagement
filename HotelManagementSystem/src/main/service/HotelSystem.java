package main.service;

import java.util.*;
import main.model.*;

public class HotelSystem {
  private final List<User> users = new ArrayList<>();
    private final  List<Room> rooms = new ArrayList<>();

    public HotelSystem() {
        loadUsers();
        loadRooms();
    }

    private void loadUsers() {
        List<String[]> data = FileManager.readFile("src/resources/users.txt");
        for (String[] user : data) {
            if (user[2].equals("admin")) {
                users.add(new Admin(user[0], user[1]));
            } else {
                users.add(new Customer(user[0], user[1]));
            }
        }
    }
    private void loadRooms() {
    List<String[]> data = FileManager.readFile("src/resources/rooms.txt");
    for (String[] entry : data) {
        try {
            int roomNumber = Integer.parseInt(entry[0]);
            String type = entry[1];
            double price = Double.parseDouble(entry[2]);
            boolean isBooked = Boolean.parseBoolean(entry[3]);
            String bookedBy = entry.length > 4 ? entry[4] : null;
            String duration = entry.length > 5 ? entry[5] : null;

            Room room = new Room(roomNumber, type, price);
            if (isBooked) {
                room.book(bookedBy, duration);
            }
            rooms.add(room);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error loading room entry: " + Arrays.toString(entry));
        }
    }
} 
public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    // ...existing code...
public void addProduct(String name, int quantity, double price) {
    String filePath = "src/resources/products.txt";
    List<String[]> products = FileManager.readFile(filePath);
    boolean found = false;

    for (int i = 0; i < products.size(); i++) {
        String[] product = products.get(i);
        if (product[0].equalsIgnoreCase(name)) {
            int existingQty = Integer.parseInt(product[1]);
            double existingPrice = Double.parseDouble(product[2]);

            if (price == existingPrice) {
                int newQty = existingQty + quantity;
                product[1] = String.valueOf(newQty);
                products.set(i, product);
                FileManager.overwriteFile(filePath, products);
                System.out.println("Product exists, updated quantity to " + newQty);
            } else {
                System.out.println("Price mismatch! You cannot add the same product with a different price.");
            }

            found = true;
            break;
        }
    }

    if (!found) {
        String line = name + "," + quantity + "," + price;
        FileManager.writeFile(filePath, line);
        System.out.println("Product added successfully.");
    }
}

public void showProducts() {
    List<String[]> products = FileManager.readFile("src/resources/products.txt");
    System.out.println("\nAvailable Products:");
    for (String[] product : products) {
        if (product.length == 3) {
            System.out.println("Name: " + product[0] + " | Quantity: " + product[1] + " | Price: $" + product[2]);
        }
    }
}
public void orderProduct(String productName, int qty) {
    List<String[]> products = FileManager.readFile("src/resources/products.txt");
    List<String> updatedLines = new ArrayList<>();
    boolean found = false;

    for (String[] product : products) {
        if (product.length == 3 && product[0].equalsIgnoreCase(productName)) {
            int availableQty = Integer.parseInt(product[1]);
            if (availableQty >= qty) {
                int newQty = availableQty - qty;
                updatedLines.add(product[0] + "," + newQty + "," + product[2]);
                found = true;
                System.out.println("Order placed for " + qty + " " + productName + "(s)." );
            } else {
                System.out.println("Not enough stock. Only " + availableQty + " available.");
                return;
            }
        } else {
            updatedLines.add(String.join(",", product));
        }
    }

    if (!found) {
        System.out.println("Product not found.");
    } else {
        FileManager.overwriteFilearray("src/resources/products.txt", updatedLines);
    }
}

public void registerCustomer(String username, String password) {
    // Check if username already exists
    for (User u : users) {
        if (u.getUsername().equalsIgnoreCase(username)) {
            System.out.println("Username already exists. Please try a different one.");
            return;
        }
    }

    // Create new Customer object and add to the users list
    Customer newCustomer = new Customer(username, password);
    users.add(newCustomer);

    // Append new user data to the users.txt file
    String newUserLine = username + "," + password + ",customer";
    FileManager.writeFile("src/resources/users.txt", newUserLine);
}
// ...existing code...
public boolean bookRoom(String username, int roomNumber, String duration) {
    for (Room room : rooms) {
        if (room.getRoomNumber() == roomNumber && !room.isBooked()) {
            room.book(username, duration);
            saveRoomsToFile(); // Save updated room info to file
            System.out.println("Room " + roomNumber + " booked for " + duration + " nights  by " + username + ".");
            return true;
        }
    }
    System.out.println("Room " + roomNumber + " is not available.");
    return false;
}

private void saveRoomsToFile() {
    List<String> roomLines = new ArrayList<>();
    for (Room room : rooms) {
        roomLines.add(room.toFileString());
    }
    FileManager.overwriteFilearray("src/resources/rooms.txt", roomLines);
}

// ...existing code...

public void showAvailableRooms() {
    System.out.println("Available Rooms:");
    for (Room room : rooms) {
        if (!room.isBooked()) {
            System.out.println("Room " + room.getRoomNumber());
        }
    }
}

}
