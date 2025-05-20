package main.model;

public class Room {
    private final int roomNumber;
    private final String type;
    private final double price;
    private boolean booked;
    private String bookedBy;
    private String duration;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.booked = false;
        this.bookedBy = null;
        this.duration = null;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBooked() {
        return booked;
    }

    public void book(String username, String duration) {
        this.booked = true;
        this.bookedBy = username;
        this.duration = duration;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public String getDuration() {
        return duration;
    }

    public String toFileString() {
        return roomNumber + "," + type + "," + price + "," + booked + "," +
               (bookedBy == null ? "" : bookedBy) + "," + (duration == null ? "" : duration);
    }
}
