package com.quanlybanhangquanao.quanlybanhangquanao.models.services;
import com.quanlybanhangquanao.quanlybanhangquanao.models.DonHang;

import java.util.List;

public interface DonHangService {
    boolean Them(DonHang n);

    boolean Sua(DonHang n);

    boolean Xoa(String key);

    List<DonHang> TimKiem(String key);

    List<DonHang> DanhSach();

    DonHang ChiTiet(String key);

    float ThanhTien();
}
