package ra.models;

import java.io.Serializable;

public class Rating implements Serializable {
    private int productId;
    private int rating;

    public Rating(int productId, int rating) {
        this.productId = productId;
        this.rating = rating;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "productId=" + productId +
                ", rating=" + rating +
                '}';
    }
}
