package com.quanlybanhangquanao.quanlybanhangquanao.models.services;
import com.quanlybanhangquanao.quanlybanhangquanao.models.KhachHang;
import com.quanlybanhangquanao.quanlybanhangquanao.models.Nguoi;

import java.util.List;

public interface NguoiService {
    boolean Them(Nguoi n);

    boolean Sua(Nguoi n);

    boolean Xoa(String key);

    List<KhachHang> TimKiem(String key);

    List<KhachHang> DanhSach();

    Nguoi ChiTiet(String key);
}
