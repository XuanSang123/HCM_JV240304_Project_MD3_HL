package ra.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile<T> {
    public static final String USER_PATH = "src/ra/data/user.txt";
    public static final String CATEGORY_PATH = "src/ra/data/category.txt";
    public static final String PRODUCT_PATH = "src/ra/data/product.txt";
    public static final String ORDER_PATH = "src/ra/data/order.txt";
    public static final String CART_PATH = "src/ra/data/cart.txt";
    public static final String PLACE_PATH = "src/ra/data/place.txt";
    public static final String LIKE_PRODUCTS = "src/ra/data/likeproduct.txt";

    // Đọc dữ liệu từ tệp tin
    public static <T> List<T> readFromFile(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<T>) ois.readObject();
        }
    }

    // Ghi dữ liệu vào tệp tin
    public static <T> void writeToFile(String filePath, List<T> objects) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(objects);
        }
    }
}
