package ra.services.impl;

import ra.models.Product;
import ra.services.CRUD;
import ra.util.IOFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ra.util.IOFile.PRODUCT_PATH;

public class ProductServiceImpl implements CRUD<Product> {
    private Map<String, Product> products = new HashMap<>();

    // Constructor initializes the product map from the file
    public ProductServiceImpl() {
        loadProductsFromFile();
    }

    @Override
    public void create(Product product) {
        // Ensure productId is set before adding to map
        if (products.containsKey(String.valueOf(product.getProductId()))) {
            System.out.println("Product with ID " + product.getProductId() + " already exists.");
        } else {
            product.setProductId(Product.getAutoId()); // Set ID from auto-increment
            products.put(String.valueOf(product.getProductId()), product);
            saveProductsToFile();
        }
    }

    @Override
    public Product read(String id) {
        Product product = products.get(id);
        if (product == null) {
            System.out.println("Product with ID " + id + " not found.");
        }
        return product;
    }

    @Override
    public void update(Product product) {
        if (products.containsKey(String.valueOf(product.getProductId()))) {
            products.put(String.valueOf(product.getProductId()), product);
            saveProductsToFile();
        } else {
            System.out.println("Product with ID " + product.getProductId() + " not found for update.");
        }
    }

    @Override
    public void delete(String id) {
        if (products.remove(id) != null) {
            saveProductsToFile();
        } else {
            System.out.println("Product with ID " + id + " not found for deletion.");
        }
    }

    @Override
    public List<Product> getAll() {
        return products.values().stream().collect(Collectors.toList());
    }

    // Load products from a file and populate the map
    private void loadProductsFromFile() {
        IOFile<Product> productIOFile = new IOFile<>();
        try {
            List<Product> loadedProducts = productIOFile.readFromFile(PRODUCT_PATH);
            for (Product product : loadedProducts) {
                products.put(String.valueOf(product.getProductId()), product);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Unable to read products from file");
            e.printStackTrace();
        }
    }

    // Save all products to a file
    private void saveProductsToFile() {
        IOFile<Product> productIOFile = new IOFile<>();
        try {
            productIOFile.writeToFile(PRODUCT_PATH, getAll());
            System.out.println("Products saved to file successfully!");
        } catch (IOException e) {
            System.err.println("Unable to write products to file");
            e.printStackTrace();
        }
    }

    // Search products by keyword in the product name
    public List<Product> search(String keyword) {
        return products.values().stream()
                .filter(product -> product.getProductName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    public List<Product> getProductsByCategory(int categoryId) {
        return products.values().stream()
                .filter(product -> product.getCategory() != null && product.getCategory().getCategoryId() == categoryId)
                .collect(Collectors.toList());
    }

    // Get a product by ID
    public Product getProductById(int productId) {
        return products.get(String.valueOf(productId));
    }
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        try {
            List<Product> products = IOFile.readFromFile(PRODUCT_PATH);
            return products.stream()
                    .filter(product -> product.getProductPrice() >= minPrice && product.getProductPrice() <= maxPrice)
                    .collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }
}
