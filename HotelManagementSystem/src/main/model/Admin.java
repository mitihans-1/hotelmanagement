package main.model;

public class Admin extends User {
 public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "admin";
    }
    @Override
    public String toString() {
        return "Admin{" +
                "username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
