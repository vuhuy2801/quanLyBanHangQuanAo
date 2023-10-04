package com.quanlybanhangquanao.quanlybanhangquanao.controlles;
import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;

import java.util.List;

public class QuanLySanPhamController {
    private SanPham sanPham;

    public void layDanhSach(){
        sanPham = new SanPham();
        List<SanPham> danhSachSanPham = sanPham.DanhSach();
        for (int i = 0; i < danhSachSanPham.size(); i++) {
          System.out.println((danhSachSanPham.get(i)).getTenHang());
        }
    }
}
