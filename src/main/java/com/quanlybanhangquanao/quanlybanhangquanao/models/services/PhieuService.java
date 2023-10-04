package com.quanlybanhangquanao.quanlybanhangquanao.models.services;
import com.quanlybanhangquanao.quanlybanhangquanao.models.Phieu;

public interface PhieuService {
    boolean Them(Phieu phieu);
    boolean Sua(Phieu phieu);
    boolean Xoa(String key);
}
