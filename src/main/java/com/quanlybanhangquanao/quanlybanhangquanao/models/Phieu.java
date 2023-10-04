package com.quanlybanhangquanao.quanlybanhangquanao.models;
import com.quanlybanhangquanao.quanlybanhangquanao.models.services.PhieuService;

import java.util.Date;
import java.util.List;

public class Phieu implements PhieuService {
    private String maPhieu;
    private Date thoiGian;
    private String trangThai;

    public Phieu() {
        // Constructor
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTrangThai() {
        return trangThai;
    }


    public List<Phieu> DanhSach() {
        // Implementation to get a list of Phieu objects
        // You would typically retrieve this list from a database or other data source
        return null;
    }


    public List<Phieu> Timkiem(String key) {
        // Implementation to search for Phieu objects by a keyword
        // You would typically query the data source for matching Phieu objects
        return null;
    }

    @Override
    public boolean Xoa(String maPhieu) {
        // Implementation to delete a Phieu by ID
        // You would typically remove the Phieu from the data source
        return false;
    }

    @Override
    public boolean Sua(Phieu phieu) {
        // Implementation to update a Phieu
        // You would typically update the Phieu in the data source
        return false;
    }

    @Override
    public boolean Them(Phieu phieu) {
        // Implementation to add a Phieu
        // You would typically insert the Phieu into the data source
        return false;
    }
}
