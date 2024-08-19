package ra.menu.admin;

import ra.models.Category;
import ra.models.Product;
import ra.services.impl.CategoryServiceImpl;
import ra.util.IOFile;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ProductManagement {
    static CategoryServiceImpl categoryService = new CategoryServiceImpl();
    static Scanner sc = new Scanner(System.in);
    public static void showProduct() {
        while (true) {
            System.out.println("1. Hiển thị sản phẩm");
            System.out.println("2. Thêm sản phẩm");
            System.out.println("3. Sửa sản phẩm");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm theo tên");
            System.out.println("6. Hiển thị sản phẩm theo danh mục");
            System.out.println("7. Thoát");
            System.out.print("Hãy nhập lựa chọn: ");
            byte choice = Byte.parseByte(sc.nextLine());
            switch (choice) {
                case 1:
                    displayProduct();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    searchProductByName();
                    break;
                case 6:
                    displayProductByCategory();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Nhập sai vui lòng nhập lại");
            }
        }
    }
    public static void displayProduct() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống.");
            } else {
                System.out.println("Danh sách sản phẩm:");
                for (Product product : products) {
                    System.out.println(product);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi đọc dữ liệu sản phẩm: " + e.getMessage());
        }
    }

    public static void addProduct() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            List<Category> categories = categoryService.getAll();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Nhập tên sản phẩm: ");
            String productName = scanner.nextLine().trim();

            System.out.print("Nhập mô tả sản phẩm: ");
            String productDes = scanner.nextLine().trim();

            System.out.print("Nhập giá sản phẩm: ");
            double productPrice = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Nhập số lượng sản phẩm: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());

            // Display all categories
            System.out.println("Chọn danh mục cho sản phẩm:");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
            }

            // Let the user select a category
            System.out.print("Nhập số tương ứng với danh mục: ");
            int categoryChoice = Integer.parseInt(scanner.nextLine().trim());

            // Validate the user's choice
            if (categoryChoice < 1 || categoryChoice > categories.size()) {
                System.out.println("Lựa chọn không hợp lệ.");
                return;
            }

            // Get the selected category
            Category selectedCategory = categories.get(categoryChoice - 1);

            // Create the new product and add it to the list
            Product newProduct = new Product(productName, productDes, productPrice, quantity, selectedCategory);
            products.add(newProduct);

            // Write the updated product list to the file
            productIO.writeToFile(IOFile.PRODUCT_PATH, products);
            System.out.println("Sản phẩm đã được thêm thành công!");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại.");
        }
    }



    public static void updateProduct() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống.");
                return;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập mã sản phẩm cần sửa: ");
            String inputId = scanner.nextLine();
            int id;
            try {
                id = Integer.parseInt(inputId);
            } catch (NumberFormatException e) {
                System.out.println("Mã sản phẩm không hợp lệ. Vui lòng nhập lại.");
                return;
            }
            Product productToUpdate = null;
            for (Product product : products) {
                if (product.getProductId() == id) {
                    productToUpdate = product;
                    break;
                }
            }
            if (productToUpdate == null) {
                System.out.println("Không tìm thấy sản phẩm với mã: " + id);
                return;
            }
            System.out.print("Nhập tên sản phẩm mới (hiện tại: " + productToUpdate.getProductName() + "): ");
            String name = scanner.nextLine().trim();
            System.out.print("Nhập giá sản phẩm mới (hiện tại: " + productToUpdate.getProductPrice() + "): ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Nhập số lượng sản phẩm mới (hiện tại: " + productToUpdate.getQuantity() + "): ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            List<Category> categories = categoryService.getAll();
            System.out.println("Chọn danh mục mới cho sản phẩm (danh mục hiện tại: " + productToUpdate.getCategory().getCategoryName() + "):");
            for (int i = 0; i < categories.size(); i++) {
                if (!categories.get(i).getCategoryName().equals(productToUpdate.getCategory().getCategoryName())) {
                    System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
                }
            }
            System.out.print("Nhập số tương ứng với danh mục mới: ");
            int categoryChoice = Integer.parseInt(scanner.nextLine().trim());
            if (categoryChoice < 1 || categoryChoice > categories.size()) {
                System.out.println("Lựa chọn không hợp lệ.");
                return;
            }
            Category selectedCategory = categories.get(categoryChoice - 1);
            productToUpdate.setProductName(name);
            productToUpdate.setProductPrice(price);
            productToUpdate.setQuantity(quantity);
            productToUpdate.setCategory(selectedCategory);
            productIO.writeToFile(IOFile.PRODUCT_PATH, products);
            System.out.println("Sản phẩm đã được cập nhật thành công!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại.");
        }
    }


    private static Category getCategoryByName(String categoryName) {
        IOFile<Category> categoryIO = new IOFile<>();
        try {
            List<Category> categories = categoryIO.readFromFile(IOFile.CATEGORY_PATH);
            return categories.stream()
                    .filter(cat -> cat.getCategoryName().equalsIgnoreCase(categoryName))
                    .findFirst()
                    .orElse(null);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi đọc danh mục: " + e.getMessage());
            return null;
        }
    }


    public static void deleteProduct() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            // Read the list of products from the file
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập mã sản phẩm cần xóa: ");
            // Read the product ID from the user input
            String inputId = scanner.nextLine();
            int id;
            try {
                // Convert the input to an integer
                id = Integer.parseInt(inputId);
            } catch (NumberFormatException e) {
                System.out.println("Mã sản phẩm không hợp lệ. Vui lòng nhập lại.");
                return;
            }

            Product productToDelete = null;
            for (Product product : products) {
                // Compare the product ID using primitive int comparison
                if (product.getProductId() == id) {
                    productToDelete = product;
                    break;
                }
            }
            if (productToDelete == null) {
                System.out.println("Không tìm thấy sản phẩm với mã: " + id);
                return;
            }

            // Remove the product from the list and write the updated list to the file
            products.remove(productToDelete);
            productIO.writeToFile(IOFile.PRODUCT_PATH, products);
            System.out.println("Sản phẩm đã được xóa thành công!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
    }


    public static void searchProductByName() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống.");
                return;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập tên sản phẩm cần tìm: ");
            String name = scanner.nextLine().trim().toLowerCase();
            boolean found = false;
            for (Product product : products) {
                if (product.getProductName().toLowerCase().contains(name)) {
                    System.out.println("Tìm thấy sản phẩm:");
                    System.out.println(product);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Không tìm thấy sản phẩm với tên: " + name);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
        }
    }


    public static void displayProductByCategory() {
        IOFile<Product> productIO = new IOFile<>();
        try {
            List<Product> products = productIO.readFromFile(IOFile.PRODUCT_PATH);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống.");
                return;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập danh mục sản phẩm cần xem: ");
            String categoryName = scanner.nextLine().trim();
            boolean found = false;
            System.out.println("Sản phẩm theo danh mục " + categoryName + ":");
            for (Product product : products) {
                if (product.getCategory().getCategoryName().equalsIgnoreCase(categoryName)) {
                    System.out.println(product);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Không tìm thấy sản phẩm với danh mục: " + categoryName);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi hiển thị sản phẩm theo danh mục: " + e.getMessage());
        }
    }


}
