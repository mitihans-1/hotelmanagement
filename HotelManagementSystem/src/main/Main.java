package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.model.Room;
import main.model.User;
import main.service.FileManager;
import main.service.HotelSystem;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            HotelSystem system = new HotelSystem();
             
            
            System.out.println("Welcome to Hotel Inventory System");
            System.out.println("1. Admin Dashboard");
            System.out.println("2. User Interface");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

        switch (option) {
            case 1 -> {
                // Admin login
                System.out.print("Admin Username: ");
                String username = scanner.nextLine();
                System.out.print("Admin Password: ");
                String password = scanner.nextLine();

                User admin = system.login(username, password);
                if (admin != null && admin.getRole().equalsIgnoreCase("admin")) {
                    boolean exit = false;
                    while (!exit) {
                       boolean addingProducts = true;
System.out.print("Do you want to add products? (yes/no): ");
String wantToAddProduct = scanner.nextLine();
if (wantToAddProduct.equalsIgnoreCase("yes")) {
    while (addingProducts) {
        System.out.print("\nEnter product name: ");
        String product = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        system.addProduct(product, quantity, price);

        System.out.print("Do you want to add another product? (yes/no): ");
        String again = scanner.nextLine();
        if (!again.equalsIgnoreCase("yes")) {
            addingProducts = false;
        }
    }
}

                      

//                        system.addProduct(product, quantity, price);
                        // Show all products after adding
                        
                        System.out.print("Do you want to add rooms? (yes/no): ");
String addRooms = scanner.nextLine();
if (addRooms.equalsIgnoreCase("yes")) {
    boolean addingRooms = true;
    

while (addingRooms) {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Check for duplicate room number
        List<String[]> existingRooms = FileManager.readFile("src/resources/rooms.txt");
        boolean roomExists = false;
        for (String[] r : existingRooms) {
            if (Integer.parseInt(r[0]) == roomNumber) {
                roomExists = true;
                break;
            }
        }

        if (roomExists) {
            System.out.println("Room number already exists! Please enter a unique room number.");
        } else {
            System.out.print("Enter room type (single/double): ");
            String type = scanner.nextLine().toLowerCase();

            if (!type.equals("single") && !type.equals("double")) {
                System.out.println("Invalid room type. Please enter 'single' or 'double'.");
            } else {
                System.out.print("Enter room price: ");
                double price = scanner.nextDouble();
                scanner.nextLine();

                String roomLine = roomNumber + "," + type + "," + price + ",false,,";
                FileManager.writeFile("src/resources/rooms.txt", roomLine);
                System.out.println("Room added successfully.");
            }
        }

        System.out.print("Do you want to add another Room? (yes/no): ");
        String another = scanner.nextLine();
        if (!another.equalsIgnoreCase("yes")) {
            addingRooms = false;
        }
    }
}
    

                        System.out.println("do you want products or rooms? (yes/no):");
                        String again = scanner.nextLine();
                        if (!again.equalsIgnoreCase("yes")) {
                            exit = true;
                        }
                    }
                } else {
                    System.out.println("Invalid admin login.");
                }
            }
           case 2 ->{
    System.out.print("Do you want to order a product or book a room? (product/room): ");
    String choice = scanner.nextLine();

    List<String[]> orderedItems = new ArrayList<>();
    List<Room> bookedRooms = new ArrayList<>();
    double totalCost = 0;

    // Product ordering section
    if (choice.equalsIgnoreCase("product")) {
        boolean ordering = true;
        while (ordering) {
            system.showProducts();

            System.out.print("Enter product name: ");
            String productName = scanner.nextLine();

            System.out.print("Enter quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine(); // consume newline

            List<String[]> products = FileManager.readFile("src/resources/products.txt");
            boolean found = false;

            for (String[] product : products) {
                if (product[0].equalsIgnoreCase(productName)) {
                    int availableQty = Integer.parseInt(product[1]);
                    double price = Double.parseDouble(product[2]);

                    if (availableQty >= qty) {
                        system.orderProduct(productName, qty);
                        orderedItems.add(new String[]{productName, String.valueOf(qty), String.valueOf(price)});
                        System.out.printf("Ordered %d  %s for $%.2f%n", qty, productName, price * qty);
                        found = true;
                    } else {
                        System.out.println("Not enough stock.");
                    }
                    break;
                }
            }

            if (!found) System.out.println("Product not found.");

            System.out.print("Do you want to order another product? (yes/no): ");
            String again = scanner.nextLine();
            if (!again.equalsIgnoreCase("yes")) ordering = false;
        }
    }

    // Ask if user wants to continue to room booking
    System.out.print("Do you want to book a room? (yes/no): ");
    String wantRoom = scanner.nextLine();

    if (wantRoom.equalsIgnoreCase("yes")) {
        boolean booking = true;
        while (booking) {
            system.showAvailableRooms();
            System.out.print("Which type of room do you want? (single/double): ");
            String type = scanner.nextLine();

            List<String[]> data = FileManager.readFile("src/resources/rooms.txt");
            boolean roomFound = false;
            for (String[] room : data) {
                if (room[1].equalsIgnoreCase(type) && room[3].equalsIgnoreCase("false")) {
                    int roomNumber = Integer.parseInt(room[0]);
                    double price = Double.parseDouble(room[2]);

                   System.out.print("Enter your name: ");
String name = scanner.nextLine();

System.out.print("How many nights: ");
int nights = scanner.nextInt();
scanner.nextLine(); // consume newline

if (system.bookRoom(name, roomNumber, String.valueOf(nights))) {
    double totalRoomPrice = price * nights;
    Room bookedRoom = new Room(roomNumber, type, totalRoomPrice);
    bookedRooms.add(bookedRoom);
    System.out.printf("Booked room %d (%s) for %d nights. Total: $%.2f%n", roomNumber, type, nights, totalRoomPrice);
    roomFound = true;
    break;
}
                }
            }

            if (!roomFound) System.out.println("No available rooms of selected type.");

            System.out.print("Do you want to book another room? (yes/no): ");
            String another = scanner.nextLine();
            if (!another.equalsIgnoreCase("yes")) booking = false;
        }
    }

    // Final Summary
    if (!orderedItems.isEmpty() || !bookedRooms.isEmpty()) {
        System.out.println("\n--- Final Summary ---");

        if (!orderedItems.isEmpty()) {
            System.out.println("Ordered Products:");
            for (String[] order : orderedItems) {
                String name = order[0];
                int quantity = Integer.parseInt(order[1]);
                double price = Double.parseDouble(order[2]);
                System.out.printf("Product: %-15s Qty: %-5d Price: $%-8.2f Subtotal: $%.2f%n", name, quantity, price, quantity * price);
                totalCost += price * quantity;
            }
        }

        if (!bookedRooms.isEmpty()) {
            System.out.println("\nBooked Rooms:");
            for (Room r : bookedRooms) {
                System.out.printf("Room Number: %-5d Type: %-10s Price: $%.2f%n", r.getRoomNumber(), r.getType(), r.getPrice());
                totalCost += r.getPrice();
            }
        }

        System.out.printf("\nTotal Amount Due: $%.2f%n", totalCost);
    } else {
        System.out.println("No orders or bookings were made.");
    }
    break;
}

            default -> System.out.println("Invalid option.");
        }

        scanner.close();
    }
    }
}
