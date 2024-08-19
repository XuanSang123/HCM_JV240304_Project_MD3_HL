package ra.services.impl;

import ra.models.User;
import ra.services.CRUD;
import ra.util.IOFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ra.util.IOFile.*;

public class UserServiceImpl implements CRUD<User> {
    private Map<Integer, User> users = new HashMap<>();
    private User currentUser;  // To track the currently logged-in user

    public UserServiceImpl() {
        loadUsersFromFile();
    }

    @Override
    public void create(User user) {
        if (users.containsKey(user.getId())) {
            System.out.println("User with ID " + user.getId() + " already exists.");
        } else {
            users.put(user.getId(), user);
            saveUsersToFile();
        }
    }

    @Override
    public User read(String id) {
        User user = users.get(Integer.parseInt(id));
        if (user == null) {
            System.out.println("User with ID " + id + " not found.");
        }
        return user;
    }

    @Override
    public void update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            saveUsersToFile();
        } else {
            System.out.println("User with ID " + user.getId() + " not found for update.");
        }
    }

    @Override
    public void delete(String id) {
        if (users.remove(Integer.parseInt(id)) != null) {
            saveUsersToFile();
        } else {
            System.out.println("User with ID " + id + " not found for deletion.");
        }
    }

    @Override
    public List<User> getAll() {
        return users.values().stream().collect(Collectors.toList());
    }

    public User getUserById(int id) {
        return users.get(id);
    }


    public boolean isValidPassword(String email, String password) {
        User user = users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        return user != null && user.getPassword().equals(password);
    }

    public boolean updatePassword(String email, String newPassword) throws IOException, ClassNotFoundException {
        List<User> users = readFromFile(USER_PATH);
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                user.setPassword(newPassword); // Update the password
                writeToFile(USER_PATH, users); // Save the updated user list
                return true;
            }
        }
        return false;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    // Set the current user
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private void loadUsersFromFile() {
        IOFile<User> userIOFile = new IOFile<>();
        try {
            List<User> loadedUsers = readFromFile(USER_PATH);
            for (User user : loadedUsers) {
                users.put(user.getId(), user);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Unable to read users from file");
            e.printStackTrace();
        }
    }

    private void saveUsersToFile() {
        IOFile<User> userIOFile = new IOFile<>();
        try {
            writeToFile(USER_PATH, getAll());
            System.out.println("Users saved to file successfully!");
        } catch (IOException e) {
            System.err.println("Unable to write users to file");
            e.printStackTrace();
        }
    }

    // Sử dụng phương thức update() đã có
    public void updateUser(User user) {
        update(user);
    }
}
