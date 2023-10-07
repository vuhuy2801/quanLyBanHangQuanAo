package com.quanlybanhangquanao.quanlybanhangquanao.models;
import com.quanlybanhangquanao.quanlybanhangquanao.models.services.DonHangService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonHang implements DonHangService {
    private String maDonHang;

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    private String maNhanVien;

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    private String maKhachHang;
    private Date ngayLap;


    public DonHang() {
    }

    // Constructor with parameters
    public DonHang(String maDonHang, String maNhanVien, String maKhachHang, Date ngayLap) {
        this.maDonHang = maDonHang;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.ngayLap = ngayLap;
    }

    // Getters and setters for properties
    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    // Other methods
    public void InDonHang() {
        // Print information about the order
        System.out.println("Order details:");
        System.out.println("Order ID: " + maDonHang);
        System.out.println("Order Date: " + ngayLap);
        System.out.println("Total Amount: " + ThanhTien());
    }

    // Implementing methods from DonHangService interface
    @Override
    public boolean Them(DonHang donHang) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.hd_themHoaDon(?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, donHang.getMaDonHang()); // Truyền mã đơn hàng
                callableStatement.setString(2, donHang.getMaNhanVien()); // Truyền mã nhân viên
                callableStatement.setTimestamp(3, new Timestamp(donHang.getNgayLap().getTime())); // Truyền ngày lập
                callableStatement.setString(4, donHang.getMaKhachHang()); // Truyền mã khách hàng

                // Thực hiện stored procedure
                callableStatement.execute();

                // Lấy kết quả trả về từ stored procedure
                int result = callableStatement.getInt(5);

                // Xử lý kết quả theo logic của bạn
                if (result == 1) {
                    return true; // Đã thêm đơn hàng thành công
                } else {
                    return false; // Không thêm đơn hàng
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }
        return false;
    }


    @Override
    public boolean Sua(DonHang donHang) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.hd_suaHoaDon(?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, donHang.getMaDonHang()); // Truyền mã đơn hàng
                callableStatement.setString(2, donHang.getMaNhanVien()); // Truyền mã nhân viên
                callableStatement.setTimestamp(3, new Timestamp(donHang.getNgayLap().getTime())); // Truyền ngày lập
                callableStatement.setString(4, donHang.getMaKhachHang()); // Truyền mã khách hàng

                // Thực hiện stored procedure
                callableStatement.execute();

                // Lấy kết quả trả về từ stored procedure
                int result = callableStatement.getInt(1);

                // Xử lý kết quả theo logic của bạn
                if (result == 1) {
                    return true; // Đã cập nhật đơn hàng thành công
                } else {
                    return false; // Không cập nhật đơn hàng
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }
        return false;
    }


    @Override
    public boolean Xoa(String key) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.hd_xoaHoaDon(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, key); // Đặt giá trị tham số
                callableStatement.execute();
                int result = callableStatement.getInt(1); // 1 là vị trí của cột result trong kết quả
                if (result == 1) {
                    return true; // Xóa thành công
                } else {
                    return false; // Xóa không thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }

        return false; // Xóa không thành công (lỗi hoặc không tìm thấy đơn hàng)
    }


    @Override
    public List<DonHang> TimKiem(String key) {
        // Implement the logic for searching orders
        return null;
    }


    @Override
    public List<DonHang> DanhSach() {
        List<DonHang> danhSachDonHang = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.hd_DanhSach}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                // Thực hiện stored procedure
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    DonHang donHang = new DonHang();
                    donHang.setMaDonHang(resultSet.getString("maHD"));
                    donHang.setNgayLap(resultSet.getTimestamp("ngayLap"));
                    donHang.setMaNhanVien(resultSet.getString("NV_NguoiID"));
                    donHang.setMaKhachHang(resultSet.getString("KH_NguoiID"));
                    danhSachDonHang.add(donHang);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }

        return danhSachDonHang;
    }


    @Override
    public DonHang ChiTiet(String maDonHang) {
        DonHang donHang = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call hd_layThongTinHoaDonChiTiet(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maDonHang); // Truyền mã hóa đơn vào stored procedure

                // Thực hiện stored procedure
                ResultSet resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    donHang = new DonHang();
                    donHang.setMaDonHang(resultSet.getString("maHD"));
                    donHang.setNgayLap(resultSet.getTimestamp("ngayLap"));
                    donHang.setMaKhachHang(resultSet.getString("TenKhachHang"));
                    donHang.setMaNhanVien(resultSet.getString("TenNhanVien"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }

        return donHang;
    }

    @Override
    public float ThanhTien() {
        // Implement the logic to calculate the total cost of the order
        return 0.0f;
    }
}
