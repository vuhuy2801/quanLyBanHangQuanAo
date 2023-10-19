package com.quanlybanhangquanao.quanlybanhangquanao.models;
import com.quanlybanhangquanao.quanlybanhangquanao.models.services.DonHangService;
import oracle.jdbc.internal.OracleTypes;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonHang implements DonHangService {
    private String maDonHang;
    private NhanVien nhanVien;
    private BigDecimal tongTienHang;
    private BigDecimal giamGia;
    public BigDecimal getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(BigDecimal giamGia) {
        this.giamGia = giamGia;
    }


    private KhachHang khachHang;
    private Date ngayLap;



    public BigDecimal getTongTienHang() {
        return tongTienHang;
    }

    public void setTongTienHang(BigDecimal tongTienHang) {
        this.tongTienHang = tongTienHang;
    }


    public String getMaNhanVien() {
            return nhanVien.getMaNhanVien();
    }

    public void setMaNhanVien(String maNhanVien) {
        nhanVien.setMaNhanVien(maNhanVien);
    }

    public String getHoTenNhanVien() {
            return nhanVien.getHoTen();

    }

    public void setHoTenNhanVien(String hoTen) {
        nhanVien.setHoTen(hoTen);
    }

    public String getHoTenKhachHang() {
            return khachHang.getHoTen();
    }

    public void setHoTenKhachHang(String hoTen) {
        khachHang.setHoTen(hoTen);
    }

    public String getMaKhachHang() {
            return khachHang.getMaKhachHang();
    }

    public void setMaKhachHang(String maKhachHang) {
        khachHang.setMaKhachHang(maKhachHang);
    }


    public DonHang() {
        khachHang = new KhachHang();
        nhanVien = new NhanVien();
    }

    // Constructor with parameters
    public DonHang(String maDonHang, NhanVien nhanVien, KhachHang khachHang, Date ngayLap) {
        this.maDonHang = maDonHang;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
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
            String storedProcedure = "{call hd_themHoaDon(?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, donHang.getMaDonHang()); // Truyền mã đơn hàng
                callableStatement.setString(2, donHang.getMaNhanVien()); // Truyền mã nhân viên
                java.sql.Date ngayLapSql = new java.sql.Date(donHang.getNgayLap().getTime());
                callableStatement.setDate(3, ngayLapSql);
                callableStatement.setString(4, donHang.getMaKhachHang());

                callableStatement.execute();

                return true; // Thêm hóa đơn thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean Sua(DonHang donHang) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call hd_suaHoaDon(?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, donHang.getMaDonHang()); // Truyền mã đơn hàng
                callableStatement.setString(2, donHang.getMaNhanVien()); // Truyền mã nhân viên
                java.sql.Date ngayLapSql = new java.sql.Date(donHang.getNgayLap().getTime());
                callableStatement.setDate(3, ngayLapSql);
                callableStatement.setString(4, donHang.getMaKhachHang()); // Truyền mã khách hàng

                callableStatement.registerOutParameter(5, Types.NUMERIC); // Đăng ký tham số đầu ra p_Result

                callableStatement.execute();

                int result = callableStatement.getInt(5); // Lấy giá trị của p_Result

                return result == 1; // Nếu giá trị trả về bằng 1 thì cập nhật thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean Xoa(String maHoaDon) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call hd_xoaHoaDon(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maHoaDon); // Truyền mã hóa đơn cần xóa

                callableStatement.execute();

                return true; // Nếu không có ngoại lệ nào xảy ra, việc xóa được coi là thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public List<DonHang> TimKiem(String key) {
        List<DonHang> danhSachDonHang = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call hd_timKiemHoaDon(?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, key); // Truyền từ khóa cần tìm kiếm
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // Đăng ký tham số OUT cho Cursor

                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2); // Lấy giá trị Cursor từ tham số OUT

                while (resultSet.next()) {
                    DonHang donHang = new DonHang();
                    donHang.setMaDonHang(resultSet.getString("maHD"));
                    donHang.setNgayLap(resultSet.getTimestamp("ngayLap"));
                    donHang.setHoTenKhachHang(resultSet.getString("tenKH"));
                    donHang.setTongTienHang(resultSet.getBigDecimal("TongTienHang"));
                    donHang.setGiamGia(resultSet.getBigDecimal("GiamGia"));
                    danhSachDonHang.add(donHang);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachDonHang;
    }


    @Override
    public List<DonHang> DanhSach() {
        List<DonHang> danhSachDonHang = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call hd_layDanhSachHoaDon(?)}";
            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR); // Đăng ký tham số OUT cho Cursor

                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1); // Lấy giá trị Cursor từ tham số OUT

                while (resultSet.next()) {
                    DonHang donHang = new DonHang();
                    donHang.setMaDonHang(resultSet.getString("maHD"));
                    donHang.setNgayLap(resultSet.getTimestamp("ngayLap"));
                    donHang.setHoTenKhachHang(resultSet.getString("tenKH"));
                    donHang.setTongTienHang(resultSet.getBigDecimal("TongTienHang"));
                    donHang.setGiamGia(resultSet.getBigDecimal("GiamGia"));
                    danhSachDonHang.add(donHang);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachDonHang;
    }




    @Override
    public DonHang ChiTiet(String maDonHang) {
        DonHang donHang = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call hd_layThongTinHoaDonChiTiet(?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maDonHang); // Truyền mã hóa đơn vào stored procedure
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // Đăng ký tham số OUT cho Cursor

                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2); // Lấy giá trị Cursor từ tham số OUT

                if (resultSet.next()) {
                    donHang = new DonHang();
                    donHang.setMaDonHang(resultSet.getString("maHD"));
                    donHang.setNgayLap(resultSet.getTimestamp("ngayLap"));
                    donHang.setHoTenKhachHang(resultSet.getString("TenKhachHang"));
                    donHang.setHoTenNhanVien(resultSet.getString("TenNhanVien"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return donHang;
    }


    public DonHang DonHangCuoi() {
        DonHang donHangCuoi = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call hd_layHoaDonCuoi(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR); // Đăng ký tham số OUT cho Cursor

                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1); // Lấy giá trị Cursor từ tham số OUT

                if (resultSet.next()) {
                    donHangCuoi = new DonHang();
                    donHangCuoi.setMaDonHang(resultSet.getString("maHD"));
                    donHangCuoi.setNgayLap(resultSet.getTimestamp("ngayLap"));
                    donHangCuoi.setMaNhanVien(resultSet.getString("NV_NguoiID"));
                    donHangCuoi.setMaKhachHang(resultSet.getString("KH_NguoiID"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return donHangCuoi;
    }



    @Override
    public float ThanhTien() {

        return 0.0f;
    }
}
