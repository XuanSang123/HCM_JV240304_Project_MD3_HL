package ra.run;
import ra.menu.admin.AdminMenu;
import ra.menu.user.UserInfo;
import ra.menu.user.UserMenu;
import ra.models.User;
import ra.util.IOFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

import static ra.menu.user.UserInfo.currentUser;

public class Main {
    static UserMenu userMenu = new UserMenu();
     static AdminMenu adminMenu = new AdminMenu();
    static Scanner sc = new Scanner(System.in);
    private static final String LOGIN_STATUS_PATH = "login_status.txt";
    private static final String USER_DATA_PATH = IOFile.USER_PATH;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        while (true) {
            if (isUserLoggedIn()) {
                userMenu.userMenu();
            } else {
                System.out.println("----------CHÀO MỪNG BẠN ĐẾN VỚI SIÊU THỊ ONLINE----------");
                System.out.println("1. Đăng nhập Admin");
                System.out.println("2. Đăng nhập");
                System.out.println("3. Đăng ký");
                System.out.println("4. Thoát");
                System.out.print("Hãy nhập lựa chọn của bạn: ");
                byte choice = Byte.parseByte(sc.nextLine());

                switch (choice) {
                    case 1:
                        adminLogin();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        register();
                        break;
                    case 4:
                        System.out.println("Cảm ơn bạn đã sử dụng dịch vụ. Tạm biệt!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                        break;
                }
            }
        }
    }

    public static boolean isUserLoggedIn() {
        File file = new File(LOGIN_STATUS_PATH);
        if (file.exists() && !file.isDirectory()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String email = reader.readLine();
                if (email != null && !email.isEmpty()) {
                    IOFile<User> userIO = new IOFile<>();
                    List<User> users = userIO.readFromFile(USER_DATA_PATH);
                    for (User user : users) {
                        if (user.getEmail().equals(email)) {
                            setUserLoggedIn(user);
                            return true;
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Lỗi khi đọc trạng thái đăng nhập. Chi tiết: " + e.getMessage());
            }
        }
        return false;
    }




    public static void clearUserLoggedIn() throws IOException {
        File file = new File(LOGIN_STATUS_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    //----------REGISTER USER-------------
    public static void register() {
        IOFile<User> userIO = new IOFile<>();
        List<User> users;

        try {
            users = userIO.readFromFile(USER_DATA_PATH);
            if (users == null) {
                users = new ArrayList<>();
            }
            String name;
            String email;
            String password;
            String phone;
            String address;

            while (true) {
                System.out.println("---------- Register ----------");
                System.out.print("Nhập tên: ");
                name = sc.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Tên không được để trống.");
                    continue;
                }

                System.out.print("Nhập email: ");
                email = sc.nextLine().trim();
                if (email.isEmpty() || !isValidEmail(email)) {
                    System.out.println("Email không hợp lệ hoặc để trống. Vui lòng nhập lại.");
                    continue;
                }

                String finalEmail = email;
                if (users.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(finalEmail))) {
                    System.out.println("Email đã tồn tại. Vui lòng chọn email khác.");
                    continue;
                }

                System.out.print("Nhập mật khẩu: ");
                password = sc.nextLine().trim();
                if (password.isEmpty()) {
                    System.out.println("Mật khẩu không được để trống.");
                    continue;
                }

                System.out.print("Nhập số điện thoại: ");
                phone = sc.nextLine().trim();
                if (phone.isEmpty()) {
                    System.out.println("Số điện thoại không được để trống.");
                    continue;
                }

                System.out.print("Nhập địa chỉ: ");
                address = sc.nextLine().trim();
                if (address.isEmpty()) {
                    System.out.println("Địa chỉ không được để trống.");
                    continue;
                }

                break;
            }
            User newUser = new User(name, email, password, phone, address, true);
            users.add(newUser);

            userIO.writeToFile(USER_DATA_PATH, users);
            System.out.println("Đăng ký thành công!");

        } catch (IOException e) {
            System.err.println("Không thể ghi người dùng vào tập tin. Chi tiết: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi lớp không tìm thấy. Chi tiết: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // Phương thức kiểm tra tính hợp lệ của email
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    public static void setUserLoggedIn(User user) {
        currentUser = user;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_STATUS_PATH))) {
            writer.write(user.getEmail());  // Lưu email người dùng vào tệp login_status.txt
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu trạng thái đăng nhập. Chi tiết: " + e.getMessage());
        }
        System.out.println("Current user set to: " + currentUser.getName());
    }

    public static void login() {
        try {
            System.out.println("---------- Login ----------");
            System.out.print("Tên tài khoản: ");
            String email = sc.nextLine().trim();
            System.out.print("Mật khẩu: ");
            String password = sc.nextLine().trim();

            if (email.isEmpty() || password.isEmpty()) {
                System.err.println("Tên tài khoản hoặc mật khẩu không hợp lệ.");
                return;
            }
            IOFile<User> userIO = new IOFile<>();
            List<User> users = userIO.readFromFile(USER_DATA_PATH);

            if (users == null || users.isEmpty()) {
                System.err.println("Danh sách người dùng không thể tải từ tập tin hoặc không có người dùng nào được lưu.");
                return;
            }
            boolean loggedIn = false;
            for (User user : users) {
                if (user != null && user.getEmail() != null && user.getPassword() != null) {
                    if (user.getEmail().trim().equalsIgnoreCase(email) && user.getPassword().trim().equals(password)) {
                        if (user.isActive()) {
                            loggedIn = true;
                            System.out.println("Đăng nhập thành công!");
                            setUserLoggedIn(user);
                            UserInfo.setUserLoggedIn(user);
                            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                                System.out.println("Chào mừng Admin!");
                                adminMenu.adminMenu();
                            } else {
                                System.out.println("Chào mừng User!");
                                userMenu.userMenu();
                            }
                            break;
                        } else {
                            System.err.println("Tài khoản của bạn đã bị khóa. Vui lòng liên hệ với quản trị viên.");
                            return;
                        }
                    }
                }
            }
            if (!loggedIn) {
                System.err.println("Email hoặc mật khẩu không hợp lệ. Vui lòng thử lại.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Đã xảy ra lỗi trong quá trình đăng nhập. Chi tiết: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
        }
    }


public static void adminLogin() {
    System.out.println("----------Welcome Admin----------");
    System.out.print("Username: ");
    String adminEmail = sc.nextLine().trim();
    System.out.print("Password: ");
    String adminPassword = sc.nextLine().trim();

    IOFile<User> userIO = new IOFile<>();
    List<User> users;

    try {
        // Hardcoded admin credentials
        if (adminEmail.equals("admin") && adminPassword.equals("admin")) {
            System.out.println("Login successful!");
            adminMenu.adminMenu();
            return; // Exit the method since admin login is successful
        }

        // Check against users list from file
        users = userIO.readFromFile(USER_DATA_PATH);
        if (users != null) {
            boolean isAdmin = users.stream()
                    .anyMatch(user -> user.getEmail().equalsIgnoreCase(adminEmail)
                            && user.getPassword().equals(adminPassword)
                            && user.getRole().equalsIgnoreCase("ADMIN"));

            if (isAdmin) {
                System.out.println("Login successful!");
                adminMenu.adminMenu();
            } else {
                System.out.println("Login failed. Please try again.");
            }
        } else {
            System.out.println("No user data found.");
        }
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}


}
