package ra.menu.user;

import ra.models.User;
import ra.services.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Scanner;

public class UserInfo {
    private static Scanner sc = new Scanner(System.in);
    private static UserServiceImpl userService = new UserServiceImpl();
    public static User currentUser;

    public static void UserInfo() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("1. Đổi mật khẩu");
            System.out.println("2. Hiển thị thông tin");
            System.out.println("3. Thay đổi thông tin");
            System.out.println("4. Trở về");
            System.out.print("Hãy nhập lựa chọn: ");
            byte choice = Byte.parseByte(sc.nextLine());
            switch (choice) {
                case 1:
                    changePassword();
                    break;
                case 2:
                    showInfo();
                    break;
                case 3:
                    editInfo();
                    break;
                case 4:
                    System.out.println("Trở về trang chủ.");
                    return;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại.");
            }
        }
    }

    public static void showInfo() {
        if (currentUser == null) {
            System.out.println("Bạn chưa đăng nhập hoặc người dùng không được thiết lập.");
            return;
        }

        System.out.println("Thông tin người dùng:");
        System.out.println("Tên: " + currentUser.getName());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Số điện thoại: " + currentUser.getPhone());
        System.out.println("Địa chỉ: " + currentUser.getAddress());
        System.out.println("Trạng thái: " + (currentUser.isActive() ? "Hoạt động" : "Không hoạt động"));
    }

    public static void setUserLoggedIn(User user) {
        currentUser = user;
        System.out.println("Current user set to: " + currentUser.getName());
    }


    private static void changePassword() throws IOException, ClassNotFoundException {
        if (currentUser == null) {
            System.out.println("Bạn chưa đăng nhập hoặc người dùng không được thiết lập.");
            return;
        }

        System.out.print("Nhập mật khẩu cũ: ");
        String oldPassword = sc.nextLine().trim();

        System.out.print("Nhập mật khẩu mới: ");
        String newPassword = sc.nextLine().trim();

        if (userService.isValidPassword(currentUser.getEmail(), oldPassword)) {
            if (userService.updatePassword(currentUser.getEmail(), newPassword)) {
                System.out.println("Mật khẩu đã được thay đổi thành công.");
            } else {
                System.out.println("Đã xảy ra lỗi khi thay đổi mật khẩu.");
            }
        } else {
            System.out.println("Mật khẩu cũ không đúng.");
        }
    }




    private static void editInfo() {
        if (currentUser == null) {
            System.out.println("Bạn chưa đăng nhập hoặc người dùng không được thiết lập.");
            return;
        }

        System.out.println("Nhập thông tin mới của bạn (nhấn Enter để giữ nguyên):");

        System.out.print("Tên: ");
        String newName = sc.nextLine();
        if (!newName.trim().isEmpty()) {
            currentUser.setName(newName);
        }

        System.out.print("Số điện thoại: ");
        String newPhone = sc.nextLine();
        if (!newPhone.trim().isEmpty()) {
            currentUser.setPhone(newPhone);
        }

        System.out.print("Địa chỉ: ");
        String newAddress = sc.nextLine();
        if (!newAddress.trim().isEmpty()) {
            currentUser.setAddress(newAddress);
        }

        // Cập nhật thông tin người dùng
        userService.update(currentUser);
        System.out.println("Thông tin đã được cập nhật thành công.");
    }
}
