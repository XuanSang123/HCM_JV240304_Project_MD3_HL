package ra.services.impl;

import ra.models.Order;
import ra.services.CRUD;
import ra.util.IOFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ra.util.IOFile.ORDER_PATH;

public class OrderServiceImpl implements CRUD<Order> {
    private Map<String, Order> orders = new HashMap<>();

    public OrderServiceImpl() {
        loadOrdersFromFile();
    }

    @Override
    public void create(Order order) {
        orders.put(order.getOrderId(), order);
        saveOrdersToFile();
    }

    @Override
    public Order read(String id) {
        Order order = orders.get(id);
        if (order == null) {
            System.out.println("Order with ID " + id + " not found.");
        }
        return order;
    }

    @Override
    public void update(Order order) {
        if (orders.containsKey(order.getOrderId())) {
            orders.put(order.getOrderId(), order);
            saveOrdersToFile();
        } else {
            System.out.println("Order with ID " + order.getOrderId() + " not found for update.");
        }
    }

    @Override
    public void delete(String id) {
        if (orders.remove(id) != null) {
            saveOrdersToFile();
        } else {
            System.out.println("Order with ID " + id + " not found for deletion.");
        }
    }

    @Override
    public List<Order> getAll() {
        return orders.values().stream().collect(Collectors.toList());
    }

    // Get all orders for a specific user
    public List<Order> getAll(int userId) {
        return orders.values().stream()
                .filter(order -> order.getUserId().equals(String.valueOf(userId)))
                .collect(Collectors.toList());
    }

    private void loadOrdersFromFile() {
        IOFile<Order> orderIOFile = new IOFile<>();
        try {
            List<Order> loadedOrders = orderIOFile.readFromFile(ORDER_PATH);
            for (Order order : loadedOrders) {
                orders.put(order.getOrderId(), order);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Unable to read orders from file");
            e.printStackTrace();
        }
    }

    // Save all orders to a file
    private void saveOrdersToFile() {
        IOFile<Order> orderIOFile = new IOFile<>();
        try {
            orderIOFile.writeToFile(ORDER_PATH, getAll());
            System.out.println("Orders saved to file successfully!");
        } catch (IOException e) {
            System.err.println("Unable to write orders to file");
            e.printStackTrace();
        }
    }
    public List<Order> getOrdersByUserId(int userId) {
        try {
            List<Order> orders = IOFile.readFromFile(ORDER_PATH);
            return orders.stream()
                    .filter(order -> order.getUserId().equals(userId))
                    .collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

}
