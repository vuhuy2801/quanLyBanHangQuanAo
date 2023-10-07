package com.quanlybanhangquanao.quanlybanhangquanao.models;

import java.util.Date;
import java.util.List;

public class NhanVien extends Nguoi {
    private String maNhanVien;
    private String nhiemVu;

    // Constructors
    public NhanVien() {
        super(); // Call the constructor of the parent class (Nguoi)
    }

    // Constructor with parameters
    public NhanVien(String maNhanVien, String nhiemVu, String maNguoi, String hoTen, boolean gioiTinh,
                    Date ngaySinh, String diaChi, String SDT) {
        super(maNguoi, hoTen, gioiTinh, ngaySinh, diaChi, SDT); // Call the constructor of the parent class (Nguoi)
        this.maNhanVien = maNhanVien;
        this.nhiemVu = nhiemVu;
    }

    // Getters and setters for properties specific to NhanVien
    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getNhiemVu() {
        return nhiemVu;
    }

    public void setNhiemVu(String nhiemVu) {
        this.nhiemVu = nhiemVu;
    }

    // Additional methods specific to NhanVien
    public KhachHang ChiTiet(String maNhanVienBanHang) {
        // Implement the logic to retrieve details of a Nguoi based on the maNhanVienBanHang
        return null;
    }

    @Override
    public boolean Them(Nguoi n) {
        // Implement the logic for adding a Nguoi
        return false;
    }

    @Override
    public boolean Sua(Nguoi n) {
        // Implement the logic for updating a Nguoi
        return false;
    }

    @Override
    public boolean Xoa(String id) {
        // Implement the logic for deleting a Nguoi based on the ID
        return false;
    }

    @Override
    public List<KhachHang> TimKiem(String key) {
        // Implement the logic for searching Nguoi
        return null;
    }

    @Override
    public List<KhachHang> DanhSach() {
        // Implement the logic for listing Nguoi
        return null;
    }
}
