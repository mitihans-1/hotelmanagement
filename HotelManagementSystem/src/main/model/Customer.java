package main.model;

public class Customer extends User {
 public Customer(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "customer";
    }
}
