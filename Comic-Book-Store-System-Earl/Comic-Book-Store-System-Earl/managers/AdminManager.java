package managers;

import entities.Admin;
import utils.FileHandler;
import java.util.List;

public class AdminManager {

    public Admin loadAdmin() {
        List<String> lines = FileHandler.readFile("data/admin.txt");

        if (lines.isEmpty()) return null;

        String[] parts = lines.get(0).split(",");
        return new Admin(parts[0], parts[1]);
    }

    public boolean validateLogin(String username, String password) {
        Admin admin = loadAdmin();

        if (admin == null) return false;

        return admin.getUsername().equals(username) &&
               admin.getPassword().equals(password);
    }
}