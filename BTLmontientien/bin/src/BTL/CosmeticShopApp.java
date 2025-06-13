package BTL;

import java.util.List;
import java.util.Scanner;

//import BTL.NguoiDungDAOImpl.DAOFactory;

public class CosmeticShopApp {
    private static AuthService authService = new AuthServiceProxy();
    private static CommentService commentService = new CommentServiceAdapter();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("CHÀO MỪNG ĐẾN CỬA HÀNG MỸ PHẨM");
        
        while (true) {
            if (!authService.isLoggedIn()) {
                hienThiMenuChuaDangNhap();
            } else {
                hienThiMenuDaDangNhap();
            }
        }
    }
    
    private static void hienThiMenuChuaDangNhap() {
        System.out.println("\n1. Đăng ký");
        System.out.println("2. Đăng nhập");
        System.out.println("3. Thoát");
        System.out.print("Chọn: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                dangKy();
                break;
            case 2:
                dangNhap();
                break;
            case 3:
                System.out.println("Cảm ơn bạn đã sử dụng dịch vụ!");
                System.exit(0);
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }
    
    private static void hienThiMenuDaDangNhap() {
        System.out.println("\nXin chào, " + authService.getCurrentUser().getHoTen());
        System.out.println("1. Xem thông tin cá nhân");
        System.out.println("2. Thêm bình luận");
        System.out.println("3. Xem bình luận");
        System.out.println("4. Đăng xuất");
        System.out.print("Chọn: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                xemThongTinCaNhan();
                break;
            case 2:
                themBinhLuan();
                break;
            case 3:
                xemBinhLuan();
                break;
            case 4:
                authService.dangXuat();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }
    
    private static void dangKy() {
        System.out.println("\n=== ĐĂNG KÝ TÀI KHOẢN ===");
        System.out.print("Tên đăng nhập: ");
        String tenDangNhap = scanner.nextLine();
        
        System.out.print("Mật khẩu: ");
        String matKhau = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Họ tên: ");
        String hoTen = scanner.nextLine();
        
        System.out.print("Địa chỉ: ");
        String diaChi = scanner.nextLine();
        
        System.out.print("Số điện thoại: ");
        String soDienThoai = scanner.nextLine();
        
        NguoiDung nguoiDung = new NguoiDung.Builder(tenDangNhap, matKhau, email)
                .hoTen(hoTen)
                .diaChi(diaChi)
                .soDienThoai(soDienThoai)
                .build();
        
        NguoiDungDAO dao = DAOFactory.getNguoiDungDAO();
        if (dao.dangKy(nguoiDung)) {
            System.out.println("Đăng ký thành công!");
        } else {
            System.out.println("Đăng ký thất bại!");
        }
    }
    
    private static void dangNhap() {
        System.out.println("\n=== ĐĂNG NHẬP ===");
        System.out.print("Tên đăng nhập: ");
        String tenDangNhap = scanner.nextLine();
        
        System.out.print("Mật khẩu: ");
        String matKhau = scanner.nextLine();
        
        NguoiDung nguoiDung = authService.dangNhap(tenDangNhap, matKhau);
        if (nguoiDung == null) {
            System.out.println("Sai tên đăng nhập hoặc mật khẩu!");
        }
    }
    
    private static void xemThongTinCaNhan() {
        NguoiDung currentUser = authService.getCurrentUser();
        System.out.println("\n=== THÔNG TIN CÁ NHÂN ===");
        System.out.println("Tên đăng nhập: " + currentUser.getTenDangNhap());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Họ tên: " + currentUser.getHoTen());
        System.out.println("Địa chỉ: " + currentUser.getDiaChi());
        System.out.println("Số điện thoại: " + currentUser.getSoDienThoai());
    }
    
    private static void themBinhLuan() {
        System.out.println("\n=== THÊM BÌNH LUẬN ===");
        System.out.print("Nhập ID sản phẩm: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Nhập nội dung bình luận: ");
        String noiDung = scanner.nextLine();
        
        System.out.print("Nhập đánh giá (1-5): ");
        int danhGia = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        commentService.themBinhLuan(authService.getCurrentUser().getId(), productId, noiDung, danhGia);
    }
    
    private static void xemBinhLuan() {
        System.out.println("\n=== XEM BÌNH LUẬN ===");
        System.out.print("Nhập ID sản phẩm: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        List<String> comments = commentService.xemBinhLuan(productId);
        System.out.println("\n=== DANH SÁCH BÌNH LUẬN ===");
        for (String comment : comments) {
            System.out.println(comment);
        }
    }
}