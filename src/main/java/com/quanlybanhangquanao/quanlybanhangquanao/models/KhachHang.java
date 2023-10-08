package com.quanlybanhangquanao.quanlybanhangquanao.models;

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
            String storedProcedure = "{call dbo.kh_layThongTinKhachHang(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maKhachHang);

                // Thực hiện stored procedure
                ResultSet resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {

                    // Lấy thông tin từ ResultSet
                    String maNguoi = resultSet.getString("KH_NguoiID");
                    String hoTen = resultSet.getString("hoTen");
                    boolean gioiTinh = resultSet.getBoolean("gioiTinh");
                    Date ngaySinh = resultSet.getDate("ngaySinh");
                    String diaChi = resultSet.getString("diaChi");
                    String sdt = resultSet.getString("SDT");

                    // Tạo đối tượng KhachHang với thông tin lấy từ ResultSet
                    khachHang = new KhachHang(maNguoi, hoTen, gioiTinh, ngaySinh, diaChi,sdt);
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
            String storedProcedure = "{call dbo.kh_themKhachHang(?, ?, ?, ?, ?, ?, ?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, n.getMaNguoi()); // Đảm bảo bạn đã thêm getter cho maNguoi trong lớp cha Nguoi
                callableStatement.setString(2, n.getHoTen());
                callableStatement.setBoolean(3, n.getGioiTinh());
                Date ngaySinhUtil = n.getNgaySinh();
                java.sql.Date ngaySinhSql = new java.sql.Date(ngaySinhUtil.getTime());
                callableStatement.setDate(4, ngaySinhSql);
                callableStatement.setString(5, n.getDiaChi());
                callableStatement.setString(6, n.getSDT()); // Chuyển SDT sang kiểu String
                callableStatement.setString(7, "NV001"); // Thay thế bằng nguoiTao thích hợp

                callableStatement.setTimestamp(8, null);
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
            String storedProcedure = "{call dbo.kh_suaThongTinKhachHang(?, ?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, n.getMaNguoi()); // Đảm bảo bạn đã thêm getter cho maNguoi trong lớp cha Nguoi
                callableStatement.setString(2, n.getHoTen());
                callableStatement.setBoolean(3, n.getGioiTinh());
                Date ngaySinhUtil = n.getNgaySinh();
                java.sql.Date ngaySinhSql = new java.sql.Date(ngaySinhUtil.getTime());
                callableStatement.setDate(4, ngaySinhSql);
                callableStatement.setString(5, n.getDiaChi());
                callableStatement.setString(6, n.getSDT()); // Chuyển SDT sang kiểu String
                callableStatement.execute();

                    return true; // Cập nhật thông tin thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean Xoa(String id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.kh_xoaKhachHang(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, id); // Truyền mã khách hàng cần xóa
                int rowsAffected = callableStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }
        return false;
    }


    @Override
    public List<KhachHang> TimKiem(String key) {
        List<KhachHang> danhSachNguoi = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.kh_timKiemKhachHang(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, key);  // Truyền từ khóa cần tìm kiếm

                // Thực hiện stored procedure và lấy kết quả
                boolean hasResults = callableStatement.execute();

                // Xử lý kết quả tìm kiếm và trả về danh sách người (khách hàng) tìm thấy
                while (hasResults) {
                    ResultSet resultSet = callableStatement.getResultSet();
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
                    hasResults = callableStatement.getMoreResults(); // Kiểm tra xem còn kết quả nào nữa không
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
            String storedProcedure = "{call dbo.kh_layDanhSachKhachHang}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                // Thực hiện stored procedure
                callableStatement.execute();

                // Xử lý kết quả và trả về danh sách khách hàng
                ResultSet resultSet = callableStatement.getResultSet();

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

