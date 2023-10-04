package com.quanlybanhangquanao.quanlybanhangquanao.models;

public class BaoCaoThongKeThang extends BaoCaoThongKe {
    public BaoCaoThongKeThang() {
        // Constructor
    }

    @Override
    public double tinhToanThongKe() {
        // Implementation to calculate monthly statistics
        // You can add logic here to calculate specific monthly statistics
        return 0.0; // Replace with your actual calculation
    }

    @Override
    public void hienThi() {
        // Implementation to display or present monthly statistics
        System.out.println("Bao Cao Thong Ke Thang:");
        System.out.println("Ten Bao Cao: " + getTenBaoCao());
        System.out.println("So Don Hang: " + getSoHoaDon());
        System.out.println("Doanh Thu: " + getDoanhThu());
        System.out.println("So Luong Hang Ban: " + getSoLuongHangBan());
        System.out.println("Thong Ke Thang Result: " + tinhToanThongKe());
    }
}

