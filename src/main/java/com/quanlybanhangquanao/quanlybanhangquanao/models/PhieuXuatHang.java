package com.quanlybanhangquanao.quanlybanhangquanao.models;

import java.util.List;

public class PhieuXuatHang extends Phieu {
    public PhieuXuatHang() {
        // Constructor
    }

    @Override
    public List<Phieu> DanhSach() {
        // Implementation to get a list of PhieuXuatHang objects
        // You would typically retrieve this list from a database or other data source
        return null;
    }

    @Override
    public boolean Them(Phieu phieu) {
        // Implementation to add a PhieuXuatHang
        // You would typically insert the PhieuXuatHang into the data source
        return false;
    }

    @Override
    public boolean Sua(Phieu phieu) {
        // Implementation to update a PhieuXuatHang
        // You would typically update the PhieuXuatHang in the data source
        return false;
    }

    @Override
    public boolean Xoa(String maPhieu) {
        // Implementation to delete a PhieuXuatHang by ID
        // You would typically remove the PhieuXuatHang from the data source
        return false;
    }

    @Override
    public List<Phieu> Timkiem(String key) {
        // Implementation to search for PhieuXuatHang objects by a keyword
        // You would typically query the data source for matching PhieuXuatHang objects
        return null;
    }
}

