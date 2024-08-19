package ra.menu.user;

import ra.models.Category;
import ra.models.Product;
import ra.services.impl.CartServiceImpl;
import ra.services.impl.CategoryServiceImpl;
import ra.services.impl.ProductServiceImpl;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import static ra.util.IOFile.CART_PATH;

public class Home {
    private static Scanner sc = new Scanner(System.in);
    private static ProductServiceImpl productService = new ProductServiceImpl();
    static CategoryServiceImpl categoryService = new CategoryServiceImpl();
    private static CartServiceImpl cartService = new CartServiceImpl();

    public static void home() {
        while (true) {
            System.out.println("----------TRANG CHỦ----------");
            System.out.println("1. Tìm kiếm sản phẩm");
            System.out.println("2. Danh sách sản phẩm");
            System.out.println("3. Hiển thị sản phẩm theo danh mục");
            System.out.println("4. Thêm vào giỏ hàng");
            System.out.println("5. Xem giỏ hàng");
            System.out.println("6. Trở về");
            System.out.print("Hãy lựa chọn chức năng: ");
            byte choice = Byte.parseByte(sc.nextLine());
            switch (choice) {
                case 1:
                    searchProduct();
                    break;
                case 2:
                    displayAllProducts();
                    break;
                case 3:
                    displayProductsByCategory();
                    break;
                case 4:
                    addToCart();
                    break;
                case 5:
                    viewCart();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    public static void searchProduct() {
        System.out.print("Nhập từ khóa tìm kiếm: ");
        String keyword = sc.nextLine().trim();
        List<Product> products = productService.search(keyword);
        if (products.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm nào.");
        } else {
            System.out.println("Sản phẩm tìm thấy:");
            products.forEach(System.out::println);
        }
    }

    public static void displayAllProducts() {
        List<Product> products = productService.getAll();
        if (products.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống.");
        } else {
            System.out.println("Danh sách tất cả sản phẩm:");
            products.forEach(System.out::println);
        }
    }

    public static void displayProductsByCategory() {
        // Lấy danh sách các danh mục
        List<Category> categories = categoryService.getAll();

        if (categories.isEmpty()) {
            System.out.println("Không có danh mục nào.");
            return;
        }

        // Hiển thị danh sách các danh mục
        System.out.println("Danh sách các danh mục:");
        categories.forEach(category -> System.out.println(category.getCategoryId() + ". " + category.getCategoryName()));

        // Cho phép người dùng chọn danh mục
        System.out.print("Nhập ID danh mục mà bạn muốn xem sản phẩm: ");
        int categoryId = Integer.parseInt(sc.nextLine().trim());

        // Lấy danh sách sản phẩm thuộc danh mục đã chọn
        List<Product> products = productService.getProductsByCategory(categoryId);

        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào thuộc danh mục này.");
        } else {
            System.out.println("Sản phẩm theo danh mục:");
            products.forEach(System.out::println);
        }
    }

    public static void addToCart() {
        // Lấy danh sách tất cả sản phẩm
        List<Product> products = productService.getAll();

        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào để hiển thị.");
            return;
        }

        // Hiển thị danh sách sản phẩm
        System.out.println("Danh sách sản phẩm:");
        products.forEach(product ->
                System.out.println("ID: " + product.getProductId() + ", Tên: " + product.getProductName() + ", Giá: " + product.getProductPrice() + ", Số lượng: " + product.getQuantity())
        );

        System.out.print("Nhập ID sản phẩm để thêm vào giỏ hàng: ");
        int productId = Integer.parseInt(sc.nextLine().trim());

        // Kiểm tra sản phẩm với ID nhập vào
        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Sản phẩm không tồn tại.");
        } else {
            System.out.print("Nhập số lượng muốn thêm vào giỏ hàng: ");
            int quantity = Integer.parseInt(sc.nextLine().trim());

            // Kiểm tra số lượng có hợp lệ không
            if (quantity > product.getQuantity()) {
                System.out.println("Số lượng yêu cầu vượt quá số lượng hiện có. Không thể thêm vào giỏ hàng.");
            } else {
                // Cập nhật số lượng sản phẩm
                product.setQuantity(product.getQuantity() - quantity);

                // Thêm sản phẩm vào giỏ hàng
                cartService.addProductToCart(product, quantity);
                System.out.println("Sản phẩm đã được thêm vào giỏ hàng.");
            }
        }
    }




    public static void viewCart() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_PATH))) {
            String line;
            boolean isEmpty = true;

            System.out.println("Giỏ hàng của bạn:");
            while ((line = reader.readLine()) != null) {
                isEmpty = false;
                System.out.println(line);
            }

            if (isEmpty) {
                System.out.println("Giỏ hàng của bạn đang trống.");
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc từ tệp giỏ hàng: " + e.getMessage());
        }
    }
}
