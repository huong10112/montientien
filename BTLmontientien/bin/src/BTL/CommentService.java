package BTL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CommentService {
    void themBinhLuan(int userId, int productId, String noiDung, int danhGia);
    List<String> xemBinhLuan(int productId);
}

class CommentServiceImpl implements CommentService {
    private Connection connection;
    
    public CommentServiceImpl() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    @Override
    public void themBinhLuan(int userId, int productId, String noiDung, int danhGia) {
        String sql = "INSERT INTO binh_luan (id_nguoi_dung, id_san_pham, noi_dung, danh_gia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.setString(3, noiDung);
            stmt.setInt(4, danhGia);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<String> xemBinhLuan(int productId) {
        List<String> comments = new ArrayList<>();
        String sql = "SELECT n.ho_ten, b.noi_dung, b.danh_gia FROM binh_luan b " +
                   "JOIN nguoi_dung n ON b.id_nguoi_dung = n.id " +
                   "WHERE b.id_san_pham = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String comment = rs.getString("ho_ten") + ": " + 
                               rs.getString("noi_dung") + " (Đánh giá: " + 
                               rs.getInt("danh_gia") + "/5)";
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
}

class CommentServiceAdapter implements CommentService {
    private CommentService commentService;
    
    public CommentServiceAdapter() {
        this.commentService = new CommentServiceImpl();
    }
    
    @Override
    public void themBinhLuan(int userId, int productId, String noiDung, int danhGia) {
        System.out.println("Đang thêm bình luận cho sản phẩm ID: " + productId);
        commentService.themBinhLuan(userId, productId, noiDung, danhGia);
        System.out.println("Đã thêm bình luận thành công!");
    }
    
    @Override
    public List<String> xemBinhLuan(int productId) {
        System.out.println("Đang lấy bình luận cho sản phẩm ID: " + productId);
        List<String> comments = commentService.xemBinhLuan(productId);
        System.out.println("Tìm thấy " + comments.size() + " bình luận");
        return comments;
    }
}