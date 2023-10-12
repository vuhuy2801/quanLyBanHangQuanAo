package com.quanlybanhangquanao.quanlybanhangquanao.models;

import com.quanlybanhangquanao.quanlybanhangquanao.models.services.SanPhamService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class SanPham implements SanPhamService {
    private String maHang;
    private String tenHang;

    public String getNhomHang() {
        return nhomHang;
    }

    public void setNhomHang(String nhomHang) {
        this.nhomHang = nhomHang;
    }

    private String nhomHang;
    private String thuongHieu;

    public BigDecimal getGiaVon() {
        return giaVon;
    }

    public void setGiaVon(BigDecimal giaVon) {
        this.giaVon = giaVon;
    }

    private BigDecimal giaVon;

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    private BigDecimal giaBan;
    private int tonKho;
    private BigDecimal trongLuong;
    private byte[] anh;

    public int getSoLuongDaBan() {
        return soLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan) {
        this.soLuongDaBan = soLuongDaBan;
    }

    private int soLuongDaBan;

    // Constructors
    public SanPham() {
        // Default constructor
    }

    // Constructor with parameters
    public SanPham(String maHang, String tenHang, String nhomHang, String thuongHieu, BigDecimal giaVon,
            BigDecimal giaBan, int tonKho, BigDecimal trongLuong, byte[] anh) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.nhomHang = nhomHang;
        this.thuongHieu = thuongHieu;
        this.giaVon = giaVon;
        this.giaBan = giaBan;
        this.tonKho = tonKho;
        this.trongLuong = trongLuong;
        this.anh = anh;
    }

    // Getters and setters for properties
    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public int getTonKho() {
        return tonKho;
    }

    public void setTonKho(int tonKho) {
        this.tonKho = tonKho;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    // Implementing methods from SanPhamService interface
    @Override
    public boolean Them(SanPham sanPham) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.sp_ThemSanPham(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, sanPham.getMaHang());
                callableStatement.setString(2, sanPham.getTenHang());
                callableStatement.setString(3, sanPham.getNhomHang());
                callableStatement.setString(4, sanPham.getThuongHieu());
                callableStatement.setBigDecimal(5, sanPham.getGiaVon());
                callableStatement.setBigDecimal(6, sanPham.getGiaBan());
                callableStatement.setInt(7, sanPham.getTonKho());
                callableStatement.setBigDecimal(8, sanPham.getTrongLuong());
                callableStatement.setBytes(9, sanPham.getAnh());
                callableStatement.execute();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean Sua(SanPham n) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.sp_SuaSanPham(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, n.getMaHang());
                callableStatement.setString(2, n.getTenHang());
                callableStatement.setString(3, n.getNhomHang());
                callableStatement.setString(4, n.getThuongHieu());
                callableStatement.setBigDecimal(5, n.getGiaVon());
                callableStatement.setBigDecimal(6, n.getGiaBan());
                callableStatement.setInt(7, n.getTonKho());
                callableStatement.setBigDecimal(8, n.getTrongLuong());
                callableStatement.setBytes(9, n.getAnh());

                callableStatement.execute();

                return true; // Sửa sản phẩm thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean Xoa(String maHang) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.sp_XoaSanPham(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maHang);
                int rowsAffected = callableStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SanPham> TimKiem(String key) {
        List<SanPham> danhSachTimKiem = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.sp_TimKiemSanPham(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, key);

                // Thực hiện stored procedure
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    // Đọc dữ liệu từ ResultSet và tạo đối tượng SanPham tương ứng
                    SanPham sanPham = new SanPham();
                    sanPham.setMaHang(resultSet.getString("maHang"));
                    sanPham.setTenHang(resultSet.getString("tenHang"));
                    sanPham.setGiaVon(resultSet.getBigDecimal("giaVon"));
                    sanPham.setGiaBan(resultSet.getBigDecimal("giaBan"));
                    sanPham.setTonKho(resultSet.getInt("tonKho"));
                    sanPham.setSoLuongDaBan(resultSet.getInt("soLuongDaBan"));

                    danhSachTimKiem.add(sanPham);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }

        return danhSachTimKiem;
    }

    @Override
    public List<SanPham> DanhSach() {
        List<SanPham> danhSachSanPham = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.viewSanPham}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                // Thực hiện stored procedure
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    // Đọc dữ liệu từ ResultSet và tạo đối tượng SanPham tương ứng
                    SanPham sanPham = new SanPham();
                    sanPham.setMaHang(resultSet.getString("maHang"));
                    sanPham.setTenHang(resultSet.getString("tenHang"));
                    sanPham.setGiaVon(resultSet.getBigDecimal("giaVon"));
                    sanPham.setGiaBan(resultSet.getBigDecimal("giaBan"));
                    sanPham.setTonKho(resultSet.getInt("tonKho"));
                    sanPham.setSoLuongDaBan(resultSet.getInt("soLuongDaBan"));

                    danhSachSanPham.add(sanPham);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }

        return danhSachSanPham;
    }

    @Override
    public SanPham ChiTiet(String maHang) {
        SanPham chiTietSanPham = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call dbo.sp_viewChiTietSanPham(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maHang);

                // Thực hiện stored procedure
                ResultSet resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    // Đọc dữ liệu từ ResultSet và tạo đối tượng SanPham tương ứng
                    chiTietSanPham = new SanPham();
                    chiTietSanPham.setMaHang(resultSet.getString("maHang"));
                    chiTietSanPham.setTenHang(resultSet.getString("tenHang"));
                    chiTietSanPham.setNhomHang(resultSet.getString("nhomHang"));
                    chiTietSanPham.setThuongHieu(resultSet.getString("thuongHieu"));
                    chiTietSanPham.setGiaVon(resultSet.getBigDecimal("giaVon"));
                    chiTietSanPham.setGiaBan(resultSet.getBigDecimal("giaBan"));
                    chiTietSanPham.setTonKho(resultSet.getInt("tonKho"));
                    chiTietSanPham.setTrongLuong(resultSet.getBigDecimal("trongLuong"));
                    chiTietSanPham.setAnh(resultSet.getBytes("anh"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        }

        return chiTietSanPham;
    }

    public BigDecimal getTrongLuong() {
        return trongLuong;
    }

    public void setTrongLuong(BigDecimal trongLuong) {
        this.trongLuong = trongLuong;
    }
}
