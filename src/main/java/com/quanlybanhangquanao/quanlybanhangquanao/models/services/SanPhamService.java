package com.quanlybanhangquanao.quanlybanhangquanao.models.services;
import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import java.util.List;

public interface SanPhamService {
    boolean Them(SanPham n);

    boolean Sua(SanPham n);

    boolean Xoa(String maHang);

    List<SanPham> TimKiem(String maHang);

    List<SanPham> DanhSach();

    SanPham ChiTiet(String key);
}

