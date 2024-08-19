package ra.menu.user;

import ra.models.Feedback;
import ra.models.Product;
import ra.models.Rating;
import ra.util.IOFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FeedBack {
    static Scanner sc = new Scanner(System.in);
    private static IOFile<Product> productIOFile = new IOFile<>();
    private static IOFile<Feedback> feedbackIOFile = new IOFile<>();
    private static IOFile<Rating> ratingIOFile = new IOFile<>();
    private static final String PRODUCT_PATH = "src/ra/data/product.txt";
    private static final String FEEDBACK_PATH = "src/ra/data/feedback.txt";
    private static final String RATING_PATH = "src/ra/data/rating.txt";

    public static void feedBack() {
        while (true) {
            System.out.println("----------Đánh giá và gửi phản hồi----------");
            System.out.println("1. Đánh giá sản phẩm");
            System.out.println("2. Gửi phản hồi");
            System.out.println("3. Xem đánh giá và phản hồi");
            System.out.println("4. Trở về");
            System.out.print("Hãy nhập lựa chọn: ");
            byte choice = Byte.parseByte(sc.nextLine().trim());
            switch (choice) {
                case 1:
                    rateProduct();
                    break;
                case 2:
                    sendFeedback();
                    break;
                case 3:
                    viewRatingsAndFeedback();
                    break;
                case 4:
                    System.out.println("Trở về trang chủ.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    public static void rateProduct() {
        List<Product> products = loadProducts();
        if (products.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống. Không thể đánh giá.");
            return;
        }

        displayProducts(products);

        System.out.print("Nhập ID sản phẩm bạn muốn đánh giá: ");
        int productId = Integer.parseInt(sc.nextLine().trim());

        Product product = getProductById(products, productId);
        if (product == null) {
            System.out.println("Sản phẩm với ID " + productId + " không tồn tại.");
            return;
        }

        System.out.print("Nhập điểm đánh giá (1-5): ");
        int rating = Integer.parseInt(sc.nextLine().trim());

        if (rating < 1 || rating > 5) {
            System.out.println("Điểm đánh giá phải trong khoảng từ 1 đến 5.");
            return;
        }

        Rating ratingObj = new Rating(productId, rating);

        try {
            List<Rating> ratings = ratingIOFile.readFromFile(RATING_PATH);
            if (ratings == null) {
                ratings = new ArrayList<>();
            }
            ratings.add(ratingObj);
            ratingIOFile.writeToFile(RATING_PATH, ratings);
            System.out.println("Đánh giá sản phẩm thành công.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Không thể ghi đánh giá vào file.");
            e.printStackTrace();
        }
    }

    private static Product getProductById(List<Product> products, int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static void sendFeedback() {
        System.out.print("Nhập ID người dùng của bạn: ");
        String userId = sc.nextLine().trim();
        System.out.print("Nhập phản hồi của bạn: ");
        String feedbackText = sc.nextLine().trim();

        if (feedbackText.isEmpty()) {
            System.out.println("Phản hồi không thể để trống.");
            return;
        }

        Feedback feedbackObj = new Feedback(userId, feedbackText);

        try {
            List<Feedback> feedbacks = feedbackIOFile.readFromFile(FEEDBACK_PATH);
            if (feedbacks == null) {
                feedbacks = new ArrayList<>();
            }
            feedbacks.add(feedbackObj);
            feedbackIOFile.writeToFile(FEEDBACK_PATH, feedbacks);
            System.out.println("Phản hồi đã được gửi thành công.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Không thể ghi phản hồi vào file.");
            e.printStackTrace();
        }
    }

    private static List<Product> loadProducts() {
        try {
            List<Product> products = productIOFile.readFromFile(PRODUCT_PATH);
            return products != null ? products : new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Có lỗi xảy ra khi đọc danh sách sản phẩm.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void displayProducts(List<Product> products) {
        System.out.println("Danh sách sản phẩm:");
        for (Product product : products) {
            System.out.println("ID: " + product.getProductId() + ", Tên: " + product.getProductName());
        }
    }

    private static void viewRatingsAndFeedback() {
        List<Product> products = loadProducts();
        if (products.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống. Không có đánh giá hoặc phản hồi nào.");
            return;
        }

        List<Rating> ratings = loadRatings();
        List<Feedback> feedbacks = loadFeedbacks();

        for (Product product : products) {
            System.out.println("Sản phẩm: " + product.getProductName() + " (ID: " + product.getProductId() + ")");

            System.out.println("  Đánh giá:");
            boolean hasRatings = false;
            for (Rating rating : ratings) {
                if (rating.getProductId() == product.getProductId()) {
                    System.out.println("    Điểm: " + rating.getRating());
                    hasRatings = true;
                }
            }
            if (!hasRatings) {
                System.out.println("    Chưa có đánh giá nào.");
            }

            System.out.println("  Phản hồi:");
            boolean hasFeedbacks = false;
            for (Feedback feedback : feedbacks) {
                System.out.println("    " + feedback.getUserId() + ": " + feedback.getFeedback());
                hasFeedbacks = true;
            }
            if (!hasFeedbacks) {
                System.out.println("    Chưa có phản hồi nào.");
            }

            System.out.println();
        }
    }

    private static List<Rating> loadRatings() {
        try {
            List<Rating> ratings = ratingIOFile.readFromFile(RATING_PATH);
            return ratings != null ? ratings : new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Có lỗi xảy ra khi đọc danh sách đánh giá.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static List<Feedback> loadFeedbacks() {
        try {
            List<Feedback> feedbacks = feedbackIOFile.readFromFile(FEEDBACK_PATH);
            return feedbacks != null ? feedbacks : new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Có lỗi xảy ra khi đọc danh sách phản hồi.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
