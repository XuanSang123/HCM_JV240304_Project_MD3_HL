package ra.models;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {
    private static final long serialVersionUID = -3032533522724919110L;

    private int userId;
    private List<Product> products;

    // Default constructor
    public Cart() {
    }

    // Constructor with parameters
    public Cart(int userId, List<Product> products) {
        this.userId = userId;
        this.products = products;
    }

    // Getter and Setter methods
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", products=" + products +
                '}';
    }
}
