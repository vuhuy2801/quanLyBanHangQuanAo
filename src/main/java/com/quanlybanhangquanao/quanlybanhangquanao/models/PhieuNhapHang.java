package com.quanlybanhangquanao.quanlybanhangquanao.models;

import java.util.List;

public class PhieuNhapHang extends Phieu {
    public PhieuNhapHang() {
        // Constructor
    }

    @Override
    public List<Phieu> DanhSach() {
        // Implementation to get a list of PhieuNhapHang objects
        // You would typically retrieve this list from a database or other data source
        return null;
    }

    @Override
    public boolean Them(Phieu phieu) {
        // Implementation to add a PhieuNhapHang
        // You would typically insert the PhieuNhapHang into the data source
        return false;
    }

    @Override
    public boolean Sua(Phieu phieu) {
        // Implementation to update a PhieuNhapHang
        // You would typically update the PhieuNhapHang in the data source
        return false;
    }

    @Override
    public boolean Xoa(String maPhieu) {
        // Implementation to delete a PhieuNhapHang by ID
        // You would typically remove the PhieuNhapHang from the data source
        return false;
    }

    @Override
    public List<Phieu> Timkiem(String key) {
        // Implementation to search for PhieuNhapHang objects by a keyword
        // You would typically query the data source for matching PhieuNhapHang objects
        return null;
    }
}
