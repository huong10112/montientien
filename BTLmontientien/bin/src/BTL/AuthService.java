package BTL;

public interface AuthService {
    NguoiDung dangNhap(String tenDangNhap, String matKhau);
    void dangXuat();
    boolean isLoggedIn();
    NguoiDung getCurrentUser();
}

class AuthServiceImpl implements AuthService {
    private NguoiDung currentUser;
    
    @Override
    public NguoiDung dangNhap(String tenDangNhap, String matKhau) {
        NguoiDungDAO dao = DAOFactory.getNguoiDungDAO();
        currentUser = dao.dangNhap(tenDangNhap, matKhau);
        return currentUser;
    }
    
    @Override
    public void dangXuat() {
        currentUser = null;
    }
    
    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    @Override
    public NguoiDung getCurrentUser() {
        return currentUser;
    }
}

class AuthServiceProxy implements AuthService {
    private AuthService realService;
    
    public AuthServiceProxy() {
        this.realService = new AuthServiceImpl();
    }
    
    @Override
    public NguoiDung dangNhap(String tenDangNhap, String matKhau) {
        System.out.println("Đang thực hiện đăng nhập với tài khoản: " + tenDangNhap);
        NguoiDung user = realService.dangNhap(tenDangNhap, matKhau);
        if (user != null) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
        }
        return user;
    }
    
    @Override
    public void dangXuat() {
        if (realService.isLoggedIn()) {
            System.out.println("Đang đăng xuất tài khoản: " + realService.getCurrentUser().getTenDangNhap());
            realService.dangXuat();
            System.out.println("Đăng xuất thành công!");
        } else {
            System.out.println("Không có ai đăng nhập!");
        }
    }
    
    @Override
    public boolean isLoggedIn() {
        return realService.isLoggedIn();
    }
    
    @Override
    public NguoiDung getCurrentUser() {
        return realService.getCurrentUser();
    }
}