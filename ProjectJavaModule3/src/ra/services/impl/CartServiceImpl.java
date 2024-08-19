package ra.services.impl;

import ra.models.CartItem;
import ra.models.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ra.util.IOFile.CART_PATH;

public class CartServiceImpl {
    private List<CartItem> cartItems = new ArrayList<>(); // Khởi tạo danh sách giỏ hàng

    public void addProductToCart(Product product, int quantity) {
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        CartItem existingCartItem = cartItems.stream()
                .filter(item -> item.getProduct().getProductId() == product.getProductId())
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, thêm sản phẩm mới với số lượng được chỉ định
            cartItems.add(new CartItem(product, quantity));
        }

        saveCart(cartItems); // Lưu giỏ hàng sau khi cập nhật
    }

    // Phương thức để lưu giỏ hàng vào tệp
    private void saveCart(List<CartItem> cartItems) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_PATH))) {
            for (CartItem item : cartItems) {
                writer.write("ID: " + item.getProduct().getProductId() + ", Tên: " + item.getProduct().getProductName() + ", Giá: " + item.getProduct().getProductPrice() + ", Số lượng: " + item.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi vào tệp giỏ hàng: " + e.getMessage());
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeProductFromCart(int productId) {
        cartItems.removeIf(item -> item.getProduct().getProductId() == productId);
        saveCart(cartItems);
        System.out.println("Product removed from the cart.");
    }

    // Hiển thị giỏ hàng
    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            System.out.println("Products in the cart:");
            for (CartItem item : cartItems) {
                System.out.println("ID: " + item.getProduct().getProductId() + ", Tên: " + item.getProduct().getProductName() + ", Giá: " + item.getProduct().getProductPrice() + ", Số lượng: " + item.getQuantity());
            }
        }
    }
}
