package com.quanlybanhangquanao.quanlybanhangquanao.models;

public class ChiTietPhieuNhapHang extends Phieu {
    private SanPham sanPham;
    private float donGia;
    private int soLuong;

    public ChiTietPhieuNhapHang() {
        // Constructor
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public boolean Them(Phieu phieu) {
        // Implementation to add a ChiTietPhieuNhapHang to a Phieu
        // Return true if successful, false otherwise
        return false;
    }

    @Override
    public boolean Sua(Phieu phieu) {
        // Implementation to update a ChiTietPhieuNhapHang in a Phieu
        // Return true if successful, false otherwise
        return false;
    }

    @Override
    public boolean Xoa(String maPhieu) {
        // Implementation to delete a ChiTietPhieuNhapHang from a Phieu by ID
        // Return true if successful, false otherwise
        return false;
    }

    public float ThanhTien() {
        // Implementation to calculate the total cost for the ChiTietPhieuNhapHang
        float thanhTien = soLuong * donGia;
        return thanhTien;
    }
}
