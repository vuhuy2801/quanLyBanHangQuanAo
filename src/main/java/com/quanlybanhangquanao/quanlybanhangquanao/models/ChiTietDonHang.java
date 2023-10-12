package com.quanlybanhangquanao.quanlybanhangquanao.models;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHang extends DonHang {
    private int soLuong;

    private NhanVien nhanVien;

    private KhachHang khachHang;

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    private String tenHang;

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    private BigDecimal giaBan;
    @Override
    public BigDecimal getGiamGia() {
        return giamGia;
    }

    @Override
    public void setGiamGia(BigDecimal giamGia) {
        this.giamGia = giamGia;
    }

    private BigDecimal giamGia;
    private SanPham sanPham;

    public String getMaHang() {
        return sanPham.getMaHang();

    }

    public void setMaHang(String maHang) {
        sanPham.setMaHang(maHang);
    }

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public String getHoTenNhanVien() {
        return nhanVien.getHoTen();

    }
    @Override
    public void setHoTenNhanVien(String hoTen) {
        nhanVien.setHoTen(hoTen);
    }
    @Override
    public String getHoTenKhachHang() {
        return khachHang.getHoTen();
    }
    @Override
    public void setHoTenKhachHang(String hoTen) {
        khachHang.setHoTen(hoTen);
    }

    public String getMaHangSanPham() {
        return maHangSanPham;
    }

    public void setMaHangSanPham(String maHangSanPham) {
        this.maHangSanPham = maHangSanPham;
    }

    private String maHangSanPham;
    private float thanhTien;
    public ChiTietDonHang() {
        sanPham = new SanPham();
        khachHang = new KhachHang();
        nhanVien = new NhanVien();
    }



    public ChiTietDonHang(String maHangSanPham, String tenHang, int soLuong, BigDecimal giaBan, BigDecimal giamGia, float thanhTien){
        this.maHangSanPham = maHangSanPham;
        this.tenHang = tenHang;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giamGia = giamGia;
        this.thanhTien = thanhTien;
    }


    public ChiTietDonHang(String maHD, String maHang, int soLuong,BigDecimal giamGia){
        sanPham = new SanPham();
        setMaDonHang(maHD);
     this.maHangSanPham = maHang;
        this.soLuong = soLuong;
        this.giamGia = giamGia;
    }



    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoLuong() {
        return soLuong;
    }




    public boolean Them(List<ChiTietDonHang> listDonHangChiTiet) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false); // Tắt tự động commit

            try {
                for (ChiTietDonHang chiTietDonHang1 : listDonHangChiTiet) {
                    String storedProcedure = "{call dbo.hd_themChiTietHoaDon(?, ?, ?, ?)}";

                    try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                        callableStatement.setString(1, chiTietDonHang1.getMaDonHang());
                        callableStatement.setString(2, chiTietDonHang1.getMaHangSanPham());
                        callableStatement.setInt(3, chiTietDonHang1.getSoLuong());
                        callableStatement.setBigDecimal(4, chiTietDonHang1.getGiamGia());
                        int rowsUpdated = callableStatement.executeUpdate();
                        if (rowsUpdated == 0) {
                            // Không tìm thấy bản ghi nào để cập nhật
                            connection.rollback(); // Rollback nếu có lỗi
                            return false;
                        }
                    }
                }

                connection.commit(); // Commit nếu không có lỗi
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback(); // Rollback nếu có lỗi
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<ChiTietDonHang> LayDanhSachSanPhamCuaHoaDon(String maDonHang) {
        List<ChiTietDonHang> danhSachSanPham = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "{call hd_layDanhSachSanPhamCuaHoaDon(?)}";
            CallableStatement statement = connection.prepareCall(sql);
            statement.setString(1, maDonHang);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maHangSanPham = resultSet.getString("maHang");
                String tenHang = resultSet.getString("tenHang");
                int soLuong = resultSet.getInt("SoLuong");
                BigDecimal giaBan = resultSet.getBigDecimal("giaBan");
                BigDecimal giamGia = resultSet.getBigDecimal("GiamGia");
                float thanhTien = resultSet.getFloat("ThanhTien");

                ChiTietDonHang chiTiet = new ChiTietDonHang(maHangSanPham, tenHang, soLuong, giaBan, giamGia, thanhTien);
                danhSachSanPham.add(chiTiet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachSanPham;
    }


    @Override
    public ChiTietDonHang ChiTiet(String maDonHang) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.hd_layThongTinHoaDonChiTiet(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maDonHang); // Truyền mã đơn hàng

                ResultSet resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
                    chiTietDonHang.setMaDonHang(resultSet.getString("maHD"));
                    chiTietDonHang.setNgayLap(resultSet.getTimestamp("ngayLap"));
                    chiTietDonHang.setHoTenKhachHang(resultSet.getString("TenKhachHang"));
                    chiTietDonHang.setHoTenNhanVien(resultSet.getString("TenNhanVien"));
                    return chiTietDonHang;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean Sua(List<ChiTietDonHang> listDonHangChiTiet) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false); // Tắt tự động commit

            try {
                for (ChiTietDonHang chiTietDonHang1 : listDonHangChiTiet) {
                    String storedProcedure = "{call dbo.hd_suaChiTietHoaDon(?, ?, ?, ?)}";

                    try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                        callableStatement.setString(1, chiTietDonHang1.getMaDonHang());
                        callableStatement.setString(2, chiTietDonHang1.getMaHangSanPham());
                        callableStatement.setInt(3, chiTietDonHang1.getSoLuong());
                        callableStatement.setBigDecimal(4, chiTietDonHang1.getGiamGia());
                        int rowsUpdated = callableStatement.executeUpdate();
                        if (rowsUpdated == 0) {
                            // Không tìm thấy bản ghi nào để cập nhật
                            connection.rollback(); // Rollback nếu có lỗi
                            return false;
                        }
                    }
                }

                connection.commit(); // Commit nếu không có lỗi
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback(); // Rollback nếu có lỗi
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public List<DonHang> TimKiem(String key) {
        // Implementation to search for ChiTietDonHang in a DonHang by a keyword
        // Return a list of matching DonHang objects
        return null;
    }




}
