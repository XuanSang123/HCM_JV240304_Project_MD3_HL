package ra.models;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = -4729301310936583908L;

    private int productId;
    private String productName;
    private String productDes;
    private double productPrice;
    private int quantity;
    private Category category;
    private static int autoId = 1;

    // Default constructor
    public Product() {
    }

    public Product(String productName, String productDes, double productPrice, int quantity, Category category) {
        this.productId = autoId++;
        this.productName = productName;
        this.productDes = productDes;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.category = category;
    }

    public Product(int id, String name, double price, int quantity) {
        this.productId = id;
        this.productName = name;
        this.productPrice = price;
        this.quantity = quantity;
        this.productDes = "";
        this.category = null;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static int getAutoId() {
        return autoId;
    }

    public static void setAutoId(int autoId) {
        Product.autoId = autoId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDes='" + productDes + '\'' +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                ", category=" + (category != null ? category.getCategoryName() : "null") +
                '}';
    }
    public String toStringForOrderHistory() {
        return String.format("Product{name='%s', price=%.2f, quantity=%d}", productName, productPrice, quantity);
    }
}
