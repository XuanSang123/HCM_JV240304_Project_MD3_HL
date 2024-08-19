package ra.models;

import java.io.Serializable;

public class Category implements Serializable {
    private static final long serialVersionUID = -3032533522724919110L;


    private int categoryId;
    private String categoryName;
    private static int autoId = 1;

    // Constructor
    public Category(String categoryName) {
        this.categoryId = autoId++;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
