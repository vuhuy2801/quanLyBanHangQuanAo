package com.quanlybanhangquanao.quanlybanhangquanao.models;
import com.quanlybanhangquanao.quanlybanhangquanao.models.services.ThongKeService;

import java.sql.Date;

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
    public void thucHienBaoCaoDoanhThu(Date ngayBaoCao) {}
    @Override
    public void thucHienBaoCaoTraHang(Date ngayBaoCao) {}
    @Override
    public void thucHienBaoCaoSoLuong(Date ngayBaoCao) {}

}

