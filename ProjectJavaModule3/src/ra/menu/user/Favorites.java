package ra.menu.user;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ra.util.IOFile.CART_PATH;
import static ra.util.IOFile.LIKE_PRODUCTS;

public class Favorites {
    static Scanner sc = new Scanner(System.in);
    public static void showFavorites(){
        while (true){
            System.out.println("----------SẢN PHẨM YÊU THÍCH----------------");
            System.out.println("1. Danh sách sản phẩm yêu thích");
            System.out.println("2. Xoá sản phẩm yêu thích");
            System.out.println("3. Thêm vào giỏ hàng");
            System.out.println("4. Trở về");
            System.out.print("Hãy lựa chọn chức năng: ");
            byte choice = Byte.parseByte(sc.nextLine());
            switch (choice){
                case 1:
                    displayFavorites();
                    break;
                case 2:
                    deleteFavorite();
                    break;
                case 3:
                    addToCartFavorites();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Lựa chọn sai.Vui lòng nhập lại");
            }
        }
    }
    private static void displayFavorites() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LIKE_PRODUCTS))) {
            String line;
            System.out.println("Danh sách sản phẩm yêu thích:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Có lỗi xảy ra khi đọc danh sách sản phẩm yêu thích: " + e.getMessage());
        }
    }

    private static void deleteFavorite() {
        System.out.print("Nhập tên sản phẩm cần xóa: ");
        String productNameToDelete = sc.nextLine().trim();

        File inputFile = new File(LIKE_PRODUCTS);
        File tempFile = new File("src/ra/data/likeproduct_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(productNameToDelete)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

            if (found) {
                System.out.println("Sản phẩm đã được xóa.");
            } else {
                System.out.println("Không tìm thấy sản phẩm để xóa.");
            }

        } catch (IOException e) {
            System.out.println("Có lỗi xảy ra khi xóa sản phẩm yêu thích: " + e.getMessage());
        }

        if (!inputFile.delete()) {
            System.out.println("Không thể xóa tệp gốc.");
        }

        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Không thể đổi tên tệp tạm thời.");
        }
    }

    private static void addToCartFavorites() {
        System.out.print("Nhập tên sản phẩm cần thêm vào giỏ hàng: ");
        String productNameToAdd = sc.nextLine().trim();

        // Giả sử bạn có một phương thức để thêm sản phẩm vào giỏ hàng
        boolean added = addProductToCart(productNameToAdd);

        if (added) {
            System.out.println("Sản phẩm đã được thêm vào giỏ hàng.");
        } else {
            System.out.println("Không thể thêm sản phẩm vào giỏ hàng.");
        }
    }

    private static boolean addProductToCart(String productName) {
        // Đọc sản phẩm từ danh sách yêu thích
        List<String> favorites = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LIKE_PRODUCTS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                favorites.add(line);
            }
        } catch (IOException e) {
            System.out.println("Có lỗi xảy ra khi đọc danh sách yêu thích: " + e.getMessage());
            return false;
        }

        if (!favorites.contains(productName)) {
            System.out.println("Sản phẩm không có trong danh sách yêu thích.");
            return false;
        }

        // Thêm sản phẩm vào giỏ hàng
        File cartFile = new File(CART_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cartFile, true))) {
            writer.write(productName);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng: " + e.getMessage());
            return false;
        }

        return true;
    }


}
