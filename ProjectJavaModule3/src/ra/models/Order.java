package ra.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private static final long serialVersionUID = -3032533522724919110L;

    private String orderId;
    private String userId;
    private List<Product> products;
    private double totalAmount;
    private LocalDate date;
    private boolean status;
    private boolean delivered;

    public Order(String orderId, String userId, List<Product> products, double totalAmount, LocalDate date, boolean status, boolean delivered) {
        this.orderId = orderId;
        this.userId = userId;
        this.products = products;
        this.totalAmount = totalAmount;
        this.date = date;
        this.status = status;
        this.delivered = delivered;
    }

    // Default constructor
    public Order() {}

    public Order(String orderId, Date date, double totalAmount, boolean status) {
    }

    public Order(String orderId, List<Product> products, double totalAmount, LocalDate date, boolean status, boolean delivered) {
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", products=" + products +
                ", totalAmount=" + totalAmount +
                ", date=" + date +
                ", status=" + status +
                ", delivered=" + delivered +
                '}';
    }

    // Phương thức để in hóa đơn
    public String toStringForOrderHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(50)).append("\n");
        sb.append("                          HÓA ĐƠN\n");
        sb.append("=".repeat(50)).append("\n");

        sb.append(String.format("Mã Đơn Hàng: %-15s\n", this.orderId));
        sb.append(String.format("Ngày: %s\n", this.date));
        sb.append(String.format("Tình Trạng: %-15s\n", this.status ? "Đã Hoàn Thành" : "Đang Xử Lý"));
        sb.append(String.format("Đã Giao: %-15s\n", this.delivered ? "Có" : "Chưa"));

        sb.append("-".repeat(50)).append("\n");
        sb.append(String.format("| %-12s | %-25s | %-10s | %-10s |\n", "Số Thứ Tự", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng"));
        sb.append("-".repeat(50)).append("\n");

        // In danh sách sản phẩm với số thứ tự
        int index = 1;
        for (Product product : this.products) {
            sb.append(String.format("| %-12d | %-25s | %-10.2f | %-10d |\n",
                    index++,  // Số thứ tự tự động tăng
                    product.getProductName(),
                    product.getProductPrice(),
                    product.getQuantity()));
        }

        sb.append("-".repeat(50)).append("\n");
        sb.append(String.format("Tổng Tiền: %.2f VND\n", this.totalAmount));
        sb.append("=".repeat(50)).append("\n");

        return sb.toString();
    }

}
