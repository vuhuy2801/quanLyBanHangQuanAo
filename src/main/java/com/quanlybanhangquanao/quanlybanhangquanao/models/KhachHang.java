package com.quanlybanhangquanao.quanlybanhangquanao.models;

import oracle.jdbc.internal.OracleTypes;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class KhachHang extends Nguoi {
    private String maKhachHang;
    private int diemTichLuy;
    private  float tongTien;


    public float getTongTien() {
        return tongTien;
    }


    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }



    // Constructors

    public KhachHang() {
        super();
    }

    public KhachHang(String maNguoi, String hoTen, boolean gioiTinh, Date ngaySinh, String diaChi, String sdt) {
        super(maNguoi, hoTen, gioiTinh, ngaySinh, diaChi, sdt);
    }

    public KhachHang(String maKhachHang, int diemTichLuy, float tongTien, String hoTen, String SDT) {
        // Gọi constructor của lớp cha với thông tin cơ bản
        super(null, hoTen, false, null, null, SDT);

        this.maKhachHang = maKhachHang;
        this.diemTichLuy = diemTichLuy;
        this.tongTien = tongTien;
    }


    // Getters and setters for properties specific to KhachHang
    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    // Additional methods specific to KhachHang
    @Override
    public KhachHang ChiTiet(String maKhachHang) {
        KhachHang khachHang = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call kh_layThongTinKhachHang(?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maKhachHang);
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // Đăng ký tham số OUT

                // Thực hiện stored procedure
                callableStatement.execute();

                // Lấy giá trị trả về từ tham số OUT
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                if (resultSet.next()) {
                    // Lấy thông tin từ ResultSet
                    String maNguoi = resultSet.getString("KH_NguoiID");
                    String hoTen = resultSet.getString("hoTen");
                    boolean gioiTinh = resultSet.getBoolean("gioiTinh");
                    Date ngaySinh = resultSet.getDate("ngaySinh");
                    String diaChi = resultSet.getString("diaChi");
                    String sdt = resultSet.getString("SDT");

                    // Tạo đối tượng KhachHang với thông tin lấy từ ResultSet
                    khachHang = new KhachHang(maNguoi, hoTen, gioiTinh, ngaySinh, diaChi, sdt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }

        return khachHang;
    }





    @Override
    public boolean Them(Nguoi n) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call kh_themKhachHang(?, ?, ?, ?, ?, ?, ?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, n.getMaNguoi());
                callableStatement.setString(2, n.getHoTen());
                callableStatement.setInt(3, n.getGioiTinh() ? 1 : 0); // 1 cho true, 0 cho false
                Date ngaySinhUtil = n.getNgaySinh();
                java.sql.Date ngaySinhSql = new java.sql.Date(ngaySinhUtil.getTime());
                callableStatement.setDate(4, ngaySinhSql);
                callableStatement.setString(5, n.getDiaChi());
                callableStatement.setString(6, n.getSDT());
                callableStatement.setString(7, "NV001"); // Thay thế bằng nguoiTao thích hợp
                callableStatement.setNull(8, Types.DATE); // Chúng ta không set ngayTao ở đây

                callableStatement.execute();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean Sua(Nguoi n) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call kh_suaThongTinKhachHang(?, ?, ?, ?, ?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, n.getMaNguoi());
                callableStatement.setString(2, n.getHoTen());
                callableStatement.setInt(3, n.getGioiTinh() ? 1 : 0); // 1 cho true, 0 cho false
                Date ngaySinhUtil = n.getNgaySinh();
                java.sql.Date ngaySinhSql = new java.sql.Date(ngaySinhUtil.getTime());
                callableStatement.setDate(4, ngaySinhSql);
                callableStatement.setString(5, n.getDiaChi());
                callableStatement.setString(6, n.getSDT());
                callableStatement.execute();
                return true; // Cập nhật thông tin thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean Xoa(String maKhachHang) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call kh_xoaKhachHang(?, ?)}"; // Thêm tham số kết quả p_Result OUT VARCHAR2

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maKhachHang); // Truyền mã khách hàng cần xóa
                callableStatement.registerOutParameter(2, Types.VARCHAR); // Đăng ký tham số OUT

                callableStatement.execute();

                String result = callableStatement.getString(2); // Lấy giá trị của tham số OUT

                if ("true".equals(result)) {
                    return true; // Xóa khách hàng thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }
        return false;
    }



    @Override
    public List<KhachHang> TimKiem(String keyword) {
        List<KhachHang> danhSachNguoi = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call kh_timKiemKhachHang(?, ?)}"; // Thêm tham số p_Cursor OUT SYS_REFCURSOR

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, keyword);  // Truyền từ khóa cần tìm kiếm
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // Đăng ký tham số OUT

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2); // Lấy giá trị của tham số OUT

                while (resultSet.next()) {
                    String maKhachHang = resultSet.getString("KH_NguoiID");
                    String hoTen = resultSet.getString("hoTen");
                    String sdt = resultSet.getString("SDT");
                    float tongTien = resultSet.getFloat("TongTien");
                    int diemTichLuy = resultSet.getInt("diem");

                    // Tạo đối tượng KhachHang với thông tin tìm thấy
                    KhachHang khachHang = new KhachHang(maKhachHang, diemTichLuy, tongTien, hoTen, sdt);
                    danhSachNguoi.add(khachHang);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }

        return danhSachNguoi;
    }



    @Override
    public List<KhachHang> DanhSach() {
        List<KhachHang> danhSachKhachHang = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call kh_layDanhSachKhachHang(?)}"; // Thêm tham số p_Cursor OUT

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR); // Đăng ký tham số OUT

                // Thực hiện stored procedure
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1); // Lấy giá trị của tham số OUT

                while (resultSet.next()) {
                    String maKhachHang = resultSet.getString("KH_NguoiID");
                    String hoTen = resultSet.getString("hoTen");
                    String sdt = resultSet.getString("SDT");
                    float tongTien = resultSet.getFloat("TongTien");
                    int diemTichLuy = resultSet.getInt("diem");

                    // Tạo đối tượng KhachHang với thông tin từ danh sách
                    KhachHang khachHang = new KhachHang(maKhachHang, diemTichLuy, tongTien, hoTen, sdt);
                    danhSachKhachHang.add(khachHang);
                }

                return danhSachKhachHang;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }
        return null;
    }



}

