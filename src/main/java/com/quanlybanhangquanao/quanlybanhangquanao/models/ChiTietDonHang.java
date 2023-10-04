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
    private float giamGia;
    private SanPham sanPham;

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }

    private float thanhTien;
    public ChiTietDonHang() {
        // Constructor
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setGiamGia(float giamGia) {
        this.giamGia = giamGia;
    }

    public float getGiamGia() {
        return giamGia;
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
                callableStatement.setString(2, chiTietDonHang.getSanPham().getMaHang()); // Truyền mã sản phẩm
                callableStatement.setInt(3, chiTietDonHang.getSoLuong()); // Truyền số lượng
                callableStatement.setBigDecimal(4, BigDecimal.valueOf(chiTietDonHang.getGiamGia())); // Truyền giảm giá
                callableStatement.execute();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }



    @Override
    public boolean Sua(DonHang n) {
        // Implementation to update a ChiTietDonHang in a DonHang
        // Return true if successful, false otherwise
        return false;
    }

    @Override
    public boolean Xoa(String maDonHang) {
        // Implementation to delete a ChiTietDonHang from a DonHang by ID
        // Return true if successful, false otherwise
        return false;
    }

    @Override
    public List<DonHang> TimKiem(String key) {
        // Implementation to search for ChiTietDonHang in a DonHang by a keyword
        // Return a list of matching DonHang objects
        return null;
    }




}
