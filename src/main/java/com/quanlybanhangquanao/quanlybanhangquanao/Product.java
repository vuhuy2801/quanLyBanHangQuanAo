package com.quanlybanhangquanao.quanlybanhangquanao;
public class Product {
    private String maHang;
    private String tenHang;
    private double giaBan;
    private double giaVon;
    private int tonKho;
    private int soLuongDaBan;

    public Product(String maHang, String tenHang, double giaBan, double giaVon, int tonKho, int soLuongDaBan) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.giaBan = giaBan;
        this.giaVon = giaVon;
        this.tonKho = tonKho;
        this.soLuongDaBan = soLuongDaBan;
    }

    // Getter và Setter cho các thuộc tính
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

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public double getGiaVon() {
        return giaVon;
    }

    public void setGiaVon(double giaVon) {
        this.giaVon = giaVon;
    }

    public int getTonKho() {
        return tonKho;
    }

    public void setTonKho(int tonKho) {
        this.tonKho = tonKho;
    }

    public int getSoLuongDaBan() {
        return soLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan) {
        this.soLuongDaBan = soLuongDaBan;
    }
}
