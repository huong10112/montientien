package BTL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface NguoiDungDAO {
    // Đăng ký tài khoản mới
    boolean dangKy(NguoiDung nguoiDung);
    
    // Đăng nhập vào hệ thống
    NguoiDung dangNhap(String tenDangNhap, String matKhau);
    
    // Cập nhật thông tin cá nhân
    boolean capNhatThongTin(NguoiDung nguoiDung);
    
    // Lấy thông tin người dùng theo ID
    NguoiDung layThongTin(int id);
    
    // Đổi mật khẩu
    boolean doiMatKhau(int userId, String matKhauMoi);
    
    // Kiểm tra tên đăng nhập đã tồn tại chưa
    boolean kiemTraTenDangNhapTonTai(String tenDangNhap);
    
    // Kiểm tra email đã tồn tại chưa
    boolean kiemTraEmailTonTai(String email);
}

class NguoiDungDAOImpl implements NguoiDungDAO {
    private Connection connection;
    
    public NguoiDungDAOImpl() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public boolean dangKy(NguoiDung nguoiDung) {
        String sql = "INSERT INTO nguoi_dung (ten_dang_nhap, mat_khau, email, ho_ten, dia_chi, so_dien_thoai) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nguoiDung.getTenDangNhap());
            stmt.setString(2, nguoiDung.getMatKhau());
            stmt.setString(3, nguoiDung.getEmail());
            stmt.setString(4, nguoiDung.getHoTen());
            stmt.setString(5, nguoiDung.getDiaChi());
            stmt.setString(6, nguoiDung.getSoDienThoai());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public NguoiDung dangNhap(String tenDangNhap, String matKhau) {
        String sql = "SELECT * FROM nguoi_dung WHERE ten_dang_nhap = ? AND mat_khau = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tenDangNhap);
            stmt.setString(2, matKhau);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NguoiDung.Builder(tenDangNhap, matKhau, rs.getString("email"))
                        .id(rs.getInt("id"))
                        .hoTen(rs.getString("ho_ten"))
                        .diaChi(rs.getString("dia_chi"))
                        .soDienThoai(rs.getString("so_dien_thoai"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean capNhatThongTin(NguoiDung nguoiDung) {
        String sql = "UPDATE nguoi_dung SET email = ?, ho_ten = ?, dia_chi = ?, so_dien_thoai = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nguoiDung.getEmail());
            stmt.setString(2, nguoiDung.getHoTen());
            stmt.setString(3, nguoiDung.getDiaChi());
            stmt.setString(4, nguoiDung.getSoDienThoai());
            stmt.setInt(5, nguoiDung.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public NguoiDung layThongTin(int id) {
        String sql = "SELECT * FROM nguoi_dung WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NguoiDung.Builder(
                        rs.getString("ten_dang_nhap"),
                        rs.getString("mat_khau"),
                        rs.getString("email"))
                    .id(rs.getInt("id"))
                    .hoTen(rs.getString("ho_ten"))
                    .diaChi(rs.getString("dia_chi"))
                    .soDienThoai(rs.getString("so_dien_thoai"))
                    .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean doiMatKhau(int userId, String matKhauMoi) {
        String sql = "UPDATE nguoi_dung SET mat_khau = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, matKhauMoi);
            stmt.setInt(2, userId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean kiemTraTenDangNhapTonTai(String tenDangNhap) {
        String sql = "SELECT COUNT(*) FROM nguoi_dung WHERE ten_dang_nhap = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tenDangNhap);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean kiemTraEmailTonTai(String email) {
        String sql = "SELECT COUNT(*) FROM nguoi_dung WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

class DAOFactory {
    public static NguoiDungDAO getNguoiDungDAO() {
        return new NguoiDungDAOImpl();
    }
}