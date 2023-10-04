package com.quanlybanhangquanao.quanlybanhangquanao.models;

public class BaoCaoThongKeNgay extends BaoCaoThongKe {
    public BaoCaoThongKeNgay() {
        // Constructor
    }

    @Override
    public double tinhToanThongKe() {
        // Implementation to calculate statistics for a daily report
        // You can provide specific logic for daily calculations here
        return 0.0; // Replace with your actual calculation
    }

    @Override
    public void hienThi() {
        // Implementation to display or present the daily report
        // You can format and display daily statistics here
        System.out.println("Bao Cao Thong Ke Ngay:");
        System.out.println("Ten Bao Cao: " + getTenBaoCao());
        System.out.println("So Don Hang: " + getSoHoaDon());
        System.out.println("Doanh Thu: " + getDoanhThu());
        System.out.println("So Luong Hang Ban: " + getSoLuongHangBan());
        System.out.println("Thong Ke Result (Ngay): " + tinhToanThongKe());
    }
}

