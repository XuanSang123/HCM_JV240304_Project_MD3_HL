package ra.menu.admin;

import ra.models.Order;
import ra.util.IOFile;

import java.io.*;
import java.util.*;

public class OrderManagement {
    static Scanner sc = new Scanner(System.in);

    public static void showOrder() {
        while (true) {
            System.out.println("1. Hiển thị đơn hàng chưa xác nhận");
            System.out.println("2. Hiển thị đơn hàng đã xác nhận");
            System.out.println("3. Hiển thị đơn hàng đã giao");
            System.out.println("4. Huỷ đơn hàng");
            System.out.println("5. Cập nhật trạng thái đơn hàng");
            System.out.println("6. Thoát");
            System.out.print("Hãy nhập lựa chọn: ");
            byte choice = Byte.parseByte(sc.nextLine());
            switch (choice) {
                case 1:
                    unconfirmedOrders();
                    break;
                case 2:
                    confirmedOrders();
                    break;
                case 3:
                    deliveredOrders();
                    break;
                case 4:
                    cancelOrder();
                    break;
                case 5:
                    updateOrderStatus();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Nhập sai vui lòng nhập lại");
            }
        }
    }

    public static void unconfirmedOrders() {
        IOFile<Order> orderIO = new IOFile<>();
        try {
            List<Order> orders = orderIO.readFromFile(IOFile.ORDER_PATH);
            boolean hasOrders = false;
            for (Order order : orders) {
                if (!order.isStatus()) {
                    System.out.println(order);
                    hasOrders = true;
                }
            }
            if (!hasOrders) {
                System.out.println("Không có đơn hàng chưa xác nhận.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi hiển thị đơn hàng chưa xác nhận: " + e.getMessage());
        }
    }

    public static void confirmedOrders() {
        IOFile<Order> orderIO = new IOFile<>();
        try {
            List<Order> orders = orderIO.readFromFile(IOFile.ORDER_PATH);
            boolean hasOrders = false;
            for (Order order : orders) {
                if (order.isStatus() && !order.isDelivered()) {
                    System.out.println(order);
                    hasOrders = true;
                }
            }
            if (!hasOrders) {
                System.out.println("Không có đơn hàng đã xác nhận.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi hiển thị đơn hàng đã xác nhận: " + e.getMessage());
        }
    }


    public static void deliveredOrders() {
        IOFile<Order> orderIO = new IOFile<>();
        try {
            List<Order> orders = orderIO.readFromFile(IOFile.ORDER_PATH);
            boolean hasOrders = false;
            for (Order order : orders) {
                if (order.isDelivered()) {
                    System.out.println(order);
                    hasOrders = true;
                }
            }
            if (!hasOrders) {
                System.out.println("Không có đơn hàng đã giao.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi hiển thị đơn hàng đã giao: " + e.getMessage());
        }
    }

    public static void cancelOrder() {
        IOFile<Order> orderIO = new IOFile<>();
        try {
            List<Order> orders = orderIO.readFromFile(IOFile.ORDER_PATH);
            if (orders.isEmpty()) {
                System.out.println("Danh sách đơn hàng trống.");
                return;
            }

            System.out.print("Nhập mã đơn hàng cần huỷ: ");
            String id = sc.nextLine().trim();
            Order orderToCancel = null;
            for (Order order : orders) {
                if (order.getOrderId().equals(id)) {
                    orderToCancel = order;
                    break;
                }
            }

            if (orderToCancel == null) {
                System.out.println("Không tìm thấy đơn hàng với mã: " + id);
                return;
            }

            orders.remove(orderToCancel);
            orderIO.writeToFile(IOFile.ORDER_PATH, orders);
            System.out.println("Đơn hàng đã được huỷ thành công!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi huỷ đơn hàng: " + e.getMessage());
        }
    }

    public static void updateOrderStatus() {
        IOFile<Order> orderIO = new IOFile<>();
        try {
            List<Order> orders = orderIO.readFromFile(IOFile.ORDER_PATH);
            if (orders.isEmpty()) {
                System.out.println("Danh sách đơn hàng trống.");
                return;
            }

            System.out.print("Nhập mã đơn hàng cần cập nhật: ");
            String id = sc.nextLine().trim();
            Order orderToUpdate = null;
            for (Order order : orders) {
                if (order.getOrderId().equals(id)) {
                    orderToUpdate = order;
                    break;
                }
            }

            if (orderToUpdate == null) {
                System.out.println("Không tìm thấy đơn hàng với mã: " + id);
                return;
            }

            System.out.print("Nhập trạng thái mới (1 cho đã xác nhận, 2 cho đã giao): ");
            int status = Integer.parseInt(sc.nextLine().trim());
            if (status == 1) {
                orderToUpdate.setStatus(true); // Confirmed
            } else if (status == 2) {
                orderToUpdate.setStatus(true); // Delivered
            } else {
                System.out.println("Trạng thái không hợp lệ.");
                return;
            }

            orderIO.writeToFile(IOFile.ORDER_PATH, orders);
            System.out.println("Trạng thái đơn hàng đã được cập nhật thành công!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage());
        }
    }
}
