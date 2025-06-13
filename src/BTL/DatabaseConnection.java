package BTL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    // Constructor riêng tư để ngăn việc tạo instance từ bên ngoài
    private DatabaseConnection() {
        try {
        	String url = "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS02;databaseName=shop_my_pham;encrypt=true;trustServerCertificate=true";
        	String username = "sa"; // 
        	String password = "123456";
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối database thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối database: " + e.getMessage());
        }
    }
    
    // Phương thức static để lấy instance duy nhất
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    // Lấy kết nối
    public Connection getConnection() {
        return connection;
    }
    
    // Đóng kết nối
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Đã đóng kết nối database");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
        }
    }
}