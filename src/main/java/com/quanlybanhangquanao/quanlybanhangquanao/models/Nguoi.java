package com.quanlybanhangquanao.quanlybanhangquanao.models;
import com.quanlybanhangquanao.quanlybanhangquanao.models.services.NguoiService;

import java.util.Date;
import java.util.List;

public class Nguoi implements NguoiService {
    private String maNguoi;
    private String hoTen;
    private boolean gioiTinh;
    private Date ngaySinh;
    private String diaChi;
    private String SDT;

    // Constructors
    public Nguoi() {
        // Default constructor
    }

    // Constructor with parameters
    public Nguoi(String maNguoi, String hoTen, boolean gioiTinh, Date ngaySinh, String diaChi, String SDT) {
        this.maNguoi = maNguoi;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.SDT = SDT;
    }

    // Getters and setters for properties
    public String getMaNguoi() {
        return maNguoi;
    }

    public void setMaNguoi(String maNguoi) {
        this.maNguoi = maNguoi;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    // Implementing methods from NguoiService interface
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

    @Override
    public Nguoi ChiTiet(String id) {
        // Implement the logic for retrieving details of a Nguoi based on the ID
        return null;
    }
}

