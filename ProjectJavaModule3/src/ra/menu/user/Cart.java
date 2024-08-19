//package ra.menu.user;
//import ra.models.CartItem;
//import ra.models.Product;
//import ra.util.IOFile;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class Cart {
//    static Scanner sc = new Scanner(System.in);
//    private static final String CART_PATH = "src/ra/data/cart.txt";
//    private static IOFile<CartItem> cartIOFile = new IOFile<>();
//
//    public static void showCart() throws IOException {
//        while (true) {
//            System.out.println("----------GIỎ HÀNG----------");
//            System.out.println("1. Hiển thị danh sách sản phẩm trong giỏ hàng");
//            System.out.println("2. Thay đổi số lượng đặt hàng");
//            System.out.println("3. Xóa một sản phẩm trong giỏ hàng");
//            System.out.println("4. Xoá toàn bộ");
//            System.out.println("5. Tiến hành đặt hàng");
//            System.out.println("6. Trở về");
//            System.out.print("Hãy nhập lựa chọn: ");
//            byte choice = Byte.parseByte(sc.nextLine());
//            switch (choice) {
//                case 1:
//                    viewCart();
//                    break;
//                case 2:
//                    updateQuantity();
//                    break;
//                case 3:
//                    deleteOneProduct();
//                    break;
//                case 4:
//                    clearCart();
//                    break;
//                case 5:
//                    checkout();
//                    break;
//                case 6:
//                    System.out.println("Trở về trang chủ.");
//                    return;
//                default:
//                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
//            }
//        }
//    }
//
//    public static List<CartItem> loadCart() {
//        List<CartItem> cartItems = new ArrayList<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(CART_PATH))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Tách các phần tử bằng dấu phẩy và khoảng trắng
//                String[] parts = line.split(", ");
//                if (parts.length == 4) {
//                    try {
//                        // Phân tích từng phần tử và tách giá trị
//                        int productId = Integer.parseInt(parts[0].split(": ")[1]);
//                        String productName = parts[1].split(": ")[1];
//                        double productPrice = Double.parseDouble(parts[2].split(": ")[1]);
//                        int quantity = Integer.parseInt(parts[3].split(": ")[1]);
//
//                        // Tạo đối tượng Product và CartItem
//                        Product product = new Product(productId, productName, productPrice, quantity);
//                        CartItem item = new CartItem(product, quantity);
//                        cartItems.add(item);
//                    } catch (NumberFormatException e) {
//                        System.err.println("Dữ liệu không hợp lệ trong tệp giỏ hàng: " + line);
//                    }
//                } else {
//                    System.err.println("Dữ liệu không hợp lệ trong tệp giỏ hàng: " + line);
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Lỗi khi đọc tệp giỏ hàng: " + e.getMessage());
//        }
//
//        return cartItems;
//    }
//
//
//
//
//    public static void saveCart(List<CartItem> cartItems) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_PATH))) {
//            for (CartItem item : cartItems) {
//                writer.write("ID: " + item.getProduct().getProductId() + ", " +
//                        "Tên: " + item.getProduct().getProductName() + ", " +
//                        "Giá: " + item.getProduct().getProductPrice() + ", " +
//                        "Số lượng: " + item.getQuantity());
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            System.err.println("Không thể lưu dữ liệu giỏ hàng: " + e.getMessage());
//        }
//    }
//
//
//    public static void viewCart() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(CART_PATH))) {
//            String line;
//            boolean isEmpty = true;
//            System.out.println("----------GIỎ HÀNG----------");
//            System.out.println("Danh sách sản phẩm trong giỏ hàng:");
//
//            while ((line = reader.readLine()) != null) {
//                isEmpty = false;
//                System.out.println(line);
//            }
//            if (isEmpty) {
//                System.out.println("Giỏ hàng của bạn đang trống.");
//            }
//        } catch (IOException e) {
//            System.err.println("Không thể đọc dữ liệu giỏ hàng: " + e.getMessage());
//        }
//    }
//
//    public static void updateQuantity() {
//        List<CartItem> cartItems = loadCart();
//        if (cartItems == null || cartItems.isEmpty()) {
//            System.out.println("Giỏ hàng của bạn đang trống.");
//            return;
//        }
//
//        System.out.println("Danh sách sản phẩm trong giỏ hàng:");
//        for (CartItem item : cartItems) {
//            System.out.println("ID: " + item.getProduct().getProductId() +
//                    ", Tên: " + item.getProduct().getProductName() +
//                    ", Giá: " + item.getProduct().getProductPrice() +
//                    ", Số lượng: " + item.getQuantity());
//        }
//
//        System.out.print("Nhập ID sản phẩm để thay đổi số lượng: ");
//        int productId = Integer.parseInt(sc.nextLine().trim());
//
//        CartItem selectedItem = null;
//        for (CartItem item : cartItems) {
//            if (item.getProduct().getProductId() == productId) {
//                selectedItem = item;
//                break;
//            }
//        }
//
//        if (selectedItem == null) {
//            System.out.println("Sản phẩm với ID " + productId + " không có trong giỏ hàng.");
//            return;
//        }
//
//        System.out.println("Sản phẩm có ID: " + productId +
//                ", Tên: " + selectedItem.getProduct().getProductName() +
//                ", Giá: " + selectedItem.getProduct().getProductPrice() +
//                ", Số lượng hiện tại: " + selectedItem.getQuantity());
//
//        System.out.print("Nhập số lượng mới: ");
//        int newQuantity = Integer.parseInt(sc.nextLine().trim());
//        if (newQuantity <= 0) {
//            System.out.println("Số lượng phải lớn hơn 0.");
//            return;
//        }
//        selectedItem.setQuantity(newQuantity);
//        saveCart(cartItems);
//        System.out.println("Số lượng sản phẩm đã được cập nhật.");
//    }
//
//
//
//    public static void deleteOneProduct() {
//        List<CartItem> cartItems = loadCart();
//        if (cartItems == null || cartItems.isEmpty()) {
//            System.out.println("Giỏ hàng của bạn đang trống.");
//            return;
//        }
//
//        System.out.print("Nhập ID sản phẩm để xóa: ");
//        int productId;
//        try {
//            productId = Integer.parseInt(sc.nextLine().trim());
//        } catch (NumberFormatException e) {
//            System.out.println("ID sản phẩm không hợp lệ.");
//            return;
//        }
//
//        boolean found = cartItems.removeIf(item -> item.getProduct().getProductId() == productId);
//        if (found) {
//            saveCart(cartItems);
//            System.out.println("Sản phẩm đã được xóa khỏi giỏ hàng.");
//        } else {
//            System.out.println("Sản phẩm không có trong giỏ hàng.");
//        }
//    }
//
//    public static void clearCart() {
//        saveCart(new ArrayList<>());
//        System.out.println("Giỏ hàng đã được xóa.");
//    }
//
//    public static void checkout() {
//        List<CartItem> cartItems = loadCart();
//        if (cartItems == null || cartItems.isEmpty()) {
//            System.out.println("Giỏ hàng trống, không thể đặt hàng.");
//        } else {
//            clearCart();
//            System.out.println("Đặt hàng thành công.");
//        }
//    }
//
//}



package ra.menu.user;

import ra.models.CartItem;
import ra.models.Product;
import ra.util.IOFile;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cart {
    static Scanner sc = new Scanner(System.in);
    private static final String CART_PATH = "src/ra/data/cart.txt";
    private static final String PLACE_PATH = "src/ra/data/place.txt";
    private static IOFile<CartItem> cartIOFile = new IOFile<>();

    public static void showCart() throws IOException {
        while (true) {
            System.out.println("----------GIỎ HÀNG----------");
            System.out.println("1. Hiển thị danh sách sản phẩm trong giỏ hàng");
            System.out.println("2. Thay đổi số lượng đặt hàng");
            System.out.println("3. Xóa một sản phẩm trong giỏ hàng");
            System.out.println("4. Xoá toàn bộ");
            System.out.println("5. Tiến hành đặt hàng");
            System.out.println("6. Trở về");
            System.out.print("Hãy nhập lựa chọn: ");
            byte choice = Byte.parseByte(sc.nextLine());
            switch (choice) {
                case 1:
                    viewCart();
                    break;
                case 2:
                    updateQuantity();
                    break;
                case 3:
                    deleteOneProduct();
                    break;
                case 4:
                    clearCart();
                    break;
                case 5:
                    checkout();
                    break;
                case 6:
                    System.out.println("Trở về trang chủ.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    public static List<CartItem> loadCart() {
        List<CartItem> cartItems = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 4) {
                    try {
                        int productId = Integer.parseInt(parts[0].split(": ")[1]);
                        String productName = parts[1].split(": ")[1];
                        double productPrice = Double.parseDouble(parts[2].split(": ")[1]);
                        int quantity = Integer.parseInt(parts[3].split(": ")[1]);

                        Product product = new Product(productId, productName, productPrice, quantity);
                        CartItem item = new CartItem(product, quantity);
                        cartItems.add(item);
                    } catch (NumberFormatException e) {
                        System.err.println("Dữ liệu không hợp lệ trong tệp giỏ hàng: " + line);
                    }
                } else {
                    System.err.println("Dữ liệu không hợp lệ trong tệp giỏ hàng: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc tệp giỏ hàng: " + e.getMessage());
        }
        return cartItems;
    }

    public static void saveCart(List<CartItem> cartItems) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_PATH))) {
            for (CartItem item : cartItems) {
                writer.write("ID: " + item.getProduct().getProductId() + ", " +
                        "Tên: " + item.getProduct().getProductName() + ", " +
                        "Giá: " + item.getProduct().getProductPrice() + ", " +
                        "Số lượng: " + item.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Không thể lưu dữ liệu giỏ hàng: " + e.getMessage());
        }
    }

    public static void viewCart() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_PATH))) {
            String line;
            boolean isEmpty = true;
            System.out.println("----------GIỎ HÀNG----------");
            System.out.println("Danh sách sản phẩm trong giỏ hàng:");
            while ((line = reader.readLine()) != null) {
                isEmpty = false;
                System.out.println(line);
            }
            if (isEmpty) {
                System.out.println("Giỏ hàng của bạn đang trống.");
            }
        } catch (IOException e) {
            System.err.println("Không thể đọc dữ liệu giỏ hàng: " + e.getMessage());
        }
    }

    public static void updateQuantity() {
        List<CartItem> cartItems = loadCart();
        if (cartItems == null || cartItems.isEmpty()) {
            System.out.println("Giỏ hàng của bạn đang trống.");
            return;
        }
        System.out.println("Danh sách sản phẩm trong giỏ hàng:");
        for (CartItem item : cartItems) {
            System.out.println("ID: " + item.getProduct().getProductId() +
                    ", Tên: " + item.getProduct().getProductName() +
                    ", Giá: " + item.getProduct().getProductPrice() +
                    ", Số lượng: " + item.getQuantity());
        }
        System.out.print("Nhập ID sản phẩm để thay đổi số lượng: ");
        int productId = Integer.parseInt(sc.nextLine().trim());
        CartItem selectedItem = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == productId) {
                selectedItem = item;
                break;
            }
        }
        if (selectedItem == null) {
            System.out.println("Sản phẩm với ID " + productId + " không có trong giỏ hàng.");
            return;
        }
        System.out.println("Sản phẩm có ID: " + productId +
                ", Tên: " + selectedItem.getProduct().getProductName() +
                ", Giá: " + selectedItem.getProduct().getProductPrice() +
                ", Số lượng hiện tại: " + selectedItem.getQuantity());
        System.out.print("Nhập số lượng mới: ");
        int newQuantity = Integer.parseInt(sc.nextLine().trim());
        if (newQuantity <= 0) {
            System.out.println("Số lượng phải lớn hơn 0.");
            return;
        }
        selectedItem.setQuantity(newQuantity);
        saveCart(cartItems);
        System.out.println("Số lượng sản phẩm đã được cập nhật.");
    }

    public static void deleteOneProduct() {
        List<CartItem> cartItems = loadCart();
        if (cartItems == null || cartItems.isEmpty()) {
            System.out.println("Giỏ hàng của bạn đang trống.");
            return;
        }
        System.out.print("Nhập ID sản phẩm để xóa: ");
        int productId;
        try {
            productId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID sản phẩm không hợp lệ.");
            return;
        }
        boolean found = cartItems.removeIf(item -> item.getProduct().getProductId() == productId);
        if (found) {
            saveCart(cartItems);
            System.out.println("Sản phẩm đã được xóa khỏi giỏ hàng.");
        } else {
            System.out.println("Sản phẩm không có trong giỏ hàng.");
        }
    }

    public static void clearCart() {
        saveCart(new ArrayList<>());
        System.out.println("Giỏ hàng đã được xóa.");
    }

    public static void checkout() {
        List<CartItem> cartItems = loadCart();
        if (cartItems == null || cartItems.isEmpty()) {
            System.out.println("Giỏ hàng trống, không thể đặt hàng.");
        } else {
            saveOrder(cartItems); // Save order to PLACE_PATH
            clearCart(); // Clear cart after saving order
            System.out.println("Đặt hàng thành công.");
        }
    }

    private static void saveOrder(List<CartItem> cartItems) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLACE_PATH))) {
            for (CartItem item : cartItems) {
                writer.write("ID: " + item.getProduct().getProductId() + ", " +
                        "Tên: " + item.getProduct().getProductName() + ", " +
                        "Giá: " + item.getProduct().getProductPrice() + ", " +
                        "Số lượng: " + item.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Không thể lưu đơn hàng: " + e.getMessage());
        }
    }
}
