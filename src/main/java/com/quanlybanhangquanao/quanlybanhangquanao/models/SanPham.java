package com.quanlybanhangquanao.quanlybanhangquanao.models;

import com.quanlybanhangquanao.quanlybanhangquanao.models.services.SanPhamService;
import oracle.jdbc.internal.OracleTypes;
import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
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
            String storedProcedure = "{call sp_ThemSanPham(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, sanPham.getMaHang());
                callableStatement.setString(2, sanPham.getTenHang());
                callableStatement.setString(3, sanPham.getNhomHang());
                callableStatement.setString(4, sanPham.getThuongHieu());
                callableStatement.setBigDecimal(5, sanPham.getGiaVon());
                callableStatement.setBigDecimal(6, sanPham.getGiaBan());
                callableStatement.setInt(7, sanPham.getTonKho());
                callableStatement.setBigDecimal(8, sanPham.getTrongLuong());

                if (sanPham.getAnh() != null) {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(sanPham.getAnh());
                    callableStatement.setBinaryStream(9, inputStream);
                } else {
                    callableStatement.setNull(9, Types.BLOB);
                }

                callableStatement.execute();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean Sua(SanPham sanPham) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call sp_SuaSanPham(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, sanPham.getMaHang());
                callableStatement.setString(2, sanPham.getTenHang());
                callableStatement.setString(3, sanPham.getNhomHang());
                callableStatement.setString(4, sanPham.getThuongHieu());
                callableStatement.setBigDecimal(5, sanPham.getGiaVon());
                callableStatement.setBigDecimal(6, sanPham.getGiaBan());
                callableStatement.setInt(7, sanPham.getTonKho());
                callableStatement.setBigDecimal(8, sanPham.getTrongLuong());

                if (sanPham.getAnh() != null) {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(sanPham.getAnh());
                    callableStatement.setBinaryStream(9, inputStream);
                } else {
                    callableStatement.setNull(9, Types.BLOB);
                }

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
            String storedProcedure = "{call sp_XoaSanPham(?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maHang.replace(" ", ""));
                callableStatement.registerOutParameter(2, Types.VARCHAR); // Đăng ký tham số OUT

                callableStatement.execute();

                // Lấy giá trị trả về từ thủ tục PL/SQL
                String result = callableStatement.getString(2);

                if ("true".equals(result)) {
                    return true; // Xóa sản phẩm thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public List<SanPham> TimKiem(String keyword) {
        List<SanPham> danhSachTimKiem = new ArrayList();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call sp_TimKiemSanPham(?, ?)}"; // Loại bỏ prefix dbo.

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, keyword);
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // Đăng ký OUT parameter

                callableStatement.execute();

                // Nhận kết quả từ OUT parameter
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

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
            String storedProcedure = "{call viewSanPham(?)}"; // Thêm dấu ? cho OUT parameter

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                // Đăng ký OUT parameter
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);

                // Thực hiện stored procedure
                callableStatement.execute();

                // Nhận kết quả từ OUT parameter
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

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


    public SanPham ChiTiet(String maHang) {
        SanPham chiTietSanPham = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String storedProcedure = "{call sp_viewChiTietSanPham(?, ?)}"; // Loại bỏ prefix dbo.
            maHang = maHang.replace(" ","");
            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, maHang);
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // Đăng ký OUT parameter

                callableStatement.execute();

                // Nhận kết quả từ OUT parameter
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

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
