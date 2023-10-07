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
    private float thanhTien;
    public ChiTietDonHang() {
        sanPham = new SanPham();
        khachHang = new KhachHang();
        nhanVien = new NhanVien();
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public SanPham getSanPham() {
        return sanPham;
    }


    public boolean Them(ChiTietDonHang chiTietDonHang) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call hd_themChiTietHoaDon(?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, getMaDonHang()); // Truyền mã hóa đơn
                callableStatement.setString(2, chiTietDonHang.getMaHang()); // Truyền mã sản phẩm
                callableStatement.setInt(3, chiTietDonHang.getSoLuong()); // Truyền số lượng
                callableStatement.setBigDecimal(4, chiTietDonHang.getGiamGia()); // Truyền giảm giá
                callableStatement.execute();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
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


    public boolean Sua(ChiTietDonHang chiTietDonHang) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.hd_suaChiTietHoaDon(?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, chiTietDonHang.getMaDonHang());
                callableStatement.setString(2, chiTietDonHang.getMaHang());
                callableStatement.setInt(3, chiTietDonHang.getSoLuong());
                callableStatement.setBigDecimal(4, chiTietDonHang.getGiamGia());
                int rowsUpdated = callableStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    return true;
                }
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
