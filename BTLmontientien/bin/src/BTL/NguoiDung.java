package BTL;
public class NguoiDung {
    private int id;
    private String tenDangNhap;
    private String matKhau;
    private String email;
    private String hoTen;
    private String diaChi;
    private String soDienThoai;
    
    // Constructor riêng tư
    private NguoiDung(Builder builder) {
        this.id = builder.id;
        this.tenDangNhap = builder.tenDangNhap;
        this.matKhau = builder.matKhau;
        this.email = builder.email;
        this.hoTen = builder.hoTen;
        this.diaChi = builder.diaChi;
        this.soDienThoai = builder.soDienThoai;
    }
    
    // Lớp Builder
    public static class Builder {
        private int id;
        private String tenDangNhap;
        private String matKhau;
        private String email;
        private String hoTen;
        private String diaChi;
        private String soDienThoai;
        
        public Builder(String tenDangNhap, String matKhau, String email) {
            this.tenDangNhap = tenDangNhap;
            this.matKhau = matKhau;
            this.email = email;
        }
        
        public Builder id(int id) {
            this.id = id;
            return this;
        }
        
        public Builder hoTen(String hoTen) {
            this.hoTen = hoTen;
            return this;
        }
        
        public Builder diaChi(String diaChi) {
            this.diaChi = diaChi;
            return this;
        }
        
        public Builder soDienThoai(String soDienThoai) {
            this.soDienThoai = soDienThoai;
            return this;
        }
        
        public NguoiDung build() {
            return new NguoiDung(this);
        }
    }
    
    // Getter methods
    public int getId() { return id; }
    public String getTenDangNhap() { return tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public String getEmail() { return email; }
    public String getHoTen() { return hoTen; }
    public String getDiaChi() { return diaChi; }
    public String getSoDienThoai() { return soDienThoai; }
}