package com.quanlybanhangquanao.quanlybanhangquanao.models;
import com.quanlybanhangquanao.quanlybanhangquanao.models.services.ThongKeService;

public class BaoCaoThongKe implements ThongKeService {
    private String tenBaoCao;
    private int soDonHang;
    private float doanhThu;
    private int soLuongHangBan;

    public BaoCaoThongKe() {
        // Constructor
    }

    public void setTenBaoCao(String tenBaoCao) {
        this.tenBaoCao = tenBaoCao;
    }

    public String getTenBaoCao() {
        return tenBaoCao;
    }

    public void setSoHoaDon(int soDonHang) {
        this.soDonHang = soDonHang;
    }

    public int getSoHoaDon() {
        return soDonHang;
    }

    public void setDoanhThu(float doanhThu) {
        this.doanhThu = doanhThu;
    }

    public float getDoanhThu() {
        return doanhThu;
    }

    public void setSoLuongHangBan(int soLuongHangBan) {
        this.soLuongHangBan = soLuongHangBan;
    }

    public int getSoLuongHangBan() {
        return soLuongHangBan;
    }

    @Override
    public double tinhToanThongKe() {
        // Implementation to calculate some statistical result
        // You can use the fields such as soDonHang, doanhThu, soLuongHangBan
        // to perform the calculation
        // For example, you can calculate an average or a ratio here
        return 0.0; // Replace with your actual calculation
    }

    @Override
    public void hienThi() {
        // Implementation to display or present the results of the statistical calculations
        System.out.println("Bao Cao Thong Ke:");
        System.out.println("Ten Bao Cao: " + tenBaoCao);
        System.out.println("So Don Hang: " + soDonHang);
        System.out.println("Doanh Thu: " + doanhThu);
        System.out.println("So Luong Hang Ban: " + soLuongHangBan);
        System.out.println("Thong Ke Result: " + tinhToanThongKe());
    }
}

