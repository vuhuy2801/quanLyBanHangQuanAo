package com.quanlybanhangquanao.quanlybanhangquanao;

public class YourDataModel {
    private String maHang;
    private String tenHang;
    private int soLuong;
    private double donGia;
    private double giamGia;
    private double thanhTien;

    public YourDataModel() {
        // Khởi tạo mặc định
    }

    public YourDataModel(String maHang, String tenHang, int soLuong, double donGia, double giamGia) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.giamGia = giamGia;
        this.thanhTien = (soLuong * donGia) - giamGia;
    }

    // Getter và setter cho các trường dữ liệu
    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
