package com.quanlybanhangquanao.quanlybanhangquanao.controlles;



import com.quanlybanhangquanao.quanlybanhangquanao.models.DonHang;

import java.util.List;

public class QuanLyDonHangController {

    private DonHang donHang;

    public void layDanhSach(){
        donHang = new DonHang();
        List<DonHang> dachSachDonHang = donHang.DanhSach();
        for (int i = 0; i < dachSachDonHang.size(); i++) {
            System.out.println((dachSachDonHang.get(i)).getMaNhanVien());
        }
    }
}
