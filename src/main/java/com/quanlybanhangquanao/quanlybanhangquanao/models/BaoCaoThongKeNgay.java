package com.quanlybanhangquanao.quanlybanhangquanao.models;

import java.sql.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaoCaoThongKeNgay extends BaoCaoThongKe {
    private float doanhThu;
    private int soHoaDon;
    private int soLuongHoaDonNgay;
    private int soLuongTraHang;
    public int getSoLuongTraHang() {
        return soLuongTraHang;
    }

    public void setSoLuongTraHang(int soLuongTraHang) {
        this.soLuongTraHang = soLuongTraHang;
    }


    public int getSoLuongHoaDonNgay() {
        return soLuongHoaDonNgay;
    }

    public void setSoLuongHoaDonNgay(int soLuongHoaDonNgay) {
        this.soLuongHoaDonNgay = soLuongHoaDonNgay;
    }

    @Override
    public int getSoHoaDon() {
        return soHoaDon;
    }
    @Override
    public void setSoHoaDon(int soHoaDon) {
        this.soHoaDon = soHoaDon;
    }
    @Override
    public float getDoanhThu() {
        return doanhThu;
    }
    @Override
    public void setDoanhThu(float doanhThu) {
        this.doanhThu = doanhThu;
    }

    public BaoCaoThongKeNgay() {
        // Constructor
    }
    @Override
    public void thucHienBaoCaoDoanhThu(Date ngayBaoCao) {
        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call sp_ThongKeDoanhThuTrongNgay(?)}")) {

            callableStatement.setDate(1, ngayBaoCao);
            callableStatement.execute();

            try (ResultSet resultSet = callableStatement.getResultSet()) {
                if (resultSet.next()) {
                    setSoHoaDon(resultSet.getInt("SoLuongDonHang"));
                    setDoanhThu(resultSet.getFloat("DoanhThu"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void thucHienBaoCaoSoLuong(Date ngayBaoCao) {
        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call sp_SoLuongHangBanTrongNgay(?)}")) {

            callableStatement.setDate(1, ngayBaoCao);
            callableStatement.execute();

            try (ResultSet resultSet = callableStatement.getResultSet()) {
                if (resultSet.next()) {
                    setSoLuongHoaDonNgay(resultSet.getInt("SoLuongHangBan"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void thucHienBaoCaoTraHang(Date ngayBaoCao) {
        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call sp_TraHang(?)}")) {

            callableStatement.setDate(1, ngayBaoCao);
            callableStatement.execute();

            try (ResultSet resultSet = callableStatement.getResultSet()) {
                if (resultSet.next()) {
                    setSoLuongTraHang(resultSet.getInt("SoLuongTraHang"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
