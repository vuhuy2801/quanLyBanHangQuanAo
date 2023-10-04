package com.quanlybanhangquanao.quanlybanhangquanao.models;

public class ChiTietPhieuXuatHang extends Phieu {
    private SanPham sanPham;
    private int soLuong;
    private float donGia;
    private float giamGia;

    public ChiTietPhieuXuatHang() {
        // Constructor
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public float getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(float giamGia) {
        this.giamGia = giamGia;
    }

    public float ThanhTien() {
        // Implementation to calculate the total amount for the ChiTietPhieuXuatHang
        float thanhTien = (soLuong * donGia) - giamGia;
        return thanhTien;
    }

    @Override
    public boolean Them(Phieu phieu) {
        // Implementation to add a ChiTietPhieuXuatHang to a Phieu
        // Return true if successful, false otherwise
        return false;
    }

    @Override
    public boolean Sua(Phieu phieu) {
        // Implementation to update a ChiTietPhieuXuatHang in a Phieu
        // Return true if successful, false otherwise
        return false;
    }

    @Override
    public boolean Xoa(String maPhieu) {
        // Implementation to delete a ChiTietPhieuXuatHang from a Phieu by ID
        // Return true if successful, false otherwise
        return false;
    }
}

