-- T?o ng??i dùng (schema) QUAN_LY_BAN_HANG_QUAN_AO trong Oracle

-- T?o b?ng NGUOI
CREATE TABLE NGUOI (
    NguoiID CHAR(6) PRIMARY KEY,
    hoTen NVARCHAR2(255),
    gioiTinh NUMBER(1,0),
    ngaySinh DATE,
    diaChi NVARCHAR2(255),
    SDT NVARCHAR2(20)
);

-- T?o b?ng NHANVIEN
CREATE TABLE NHANVIEN (
    NV_NguoiID CHAR(6) PRIMARY KEY,
    chucVu NCHAR(255) NOT NULL,
    FOREIGN KEY (NV_NguoiID) REFERENCES NGUOI (NguoiID)
);

-- T?o b?ng KHACHHANG
CREATE TABLE KHACHHANG (
    KH_NguoiID CHAR(6) PRIMARY KEY,
    diem NUMBER,
    nguoiTao CHAR(6),
    ngayTao TIMESTAMP,
    FOREIGN KEY (KH_NguoiID) REFERENCES NGUOI (NguoiID),
    FOREIGN KEY (nguoiTao) REFERENCES NHANVIEN (NV_NguoiID)
);

-- T?o b?ng SANPHAM
CREATE TABLE SANPHAM (
    maHang CHAR(10) PRIMARY KEY,
    tenHang NVARCHAR2(255) NOT NULL,
    nhomHang NVARCHAR2(255),
    thuongHieu NVARCHAR2(255),
    giaVon NUMBER(18,2) NOT NULL,
    giaBan NUMBER(18,2) NOT NULL,
    tonKho NUMBER,
    trongLuong NUMBER(18,2),
    anh BLOB
);

-- T?o b?ng HOADON
CREATE TABLE HOADON (
    maHD CHAR(6) PRIMARY KEY,
    NV_NguoiID CHAR(6) NOT NULL,
    ngayLap TIMESTAMP NOT NULL,
    KH_NguoiID CHAR(6) NOT NULL,
    FOREIGN KEY (NV_NguoiID) REFERENCES NHANVIEN (NV_NguoiID),
    FOREIGN KEY (KH_NguoiID) REFERENCES KHACHHANG (KH_NguoiID)
);

-- T?o b?ng ChiTietHD
CREATE TABLE ChiTietHD (
    maHD CHAR(6),
    maHang CHAR(10) NOT NULL,
    SoLuong NUMBER NOT NULL,
    GiamGia NUMBER(18,2),
    FOREIGN KEY (maHD) REFERENCES HOADON (maHD),
    FOREIGN KEY (maHang) REFERENCES SANPHAM (maHang)
);

-- T?o b?ng PHIEUNHAP
CREATE TABLE PHIEUNHAP (
    maPhieuNhap CHAR(5) PRIMARY KEY,
    thoiGian TIMESTAMP NOT NULL,
    soLuong NUMBER,
    trangThai NVARCHAR2(25)
);

-- T?o b?ng PHIEUXUAT
CREATE TABLE PHIEUXUAT (
    maPhieuXuat CHAR(5) PRIMARY KEY,
    thoiGian TIMESTAMP NOT NULL,
    soLuong NUMBER,
    trangThai NVARCHAR2(25)
);

-- T?o b?ng CHITIETPHIEUNHAP
CREATE TABLE CHITIETPHIEUNHAP (
    maPhieuNhap CHAR(5),
    maHang CHAR(10) NOT NULL,
    soLuong NUMBER NOT NULL,
    donGia NUMBER(18,2) NOT NULL,
    FOREIGN KEY (maPhieuNhap) REFERENCES PHIEUNHAP (maPhieuNhap),
    FOREIGN KEY (maHang) REFERENCES SANPHAM (maHang)
);

-- T?o b?ng CHITIETPHIEUXUAT
CREATE TABLE CHITIETPHIEUXUAT (
    maPhieuXuat CHAR(5),
    maHang CHAR(10) NOT NULL,
    soLuong NUMBER NOT NULL,
    donGia NUMBER(18,2) NOT NULL,
    giamGia NUMBER,
    moTa NVARCHAR2(25),
    FOREIGN KEY (maPhieuXuat) REFERENCES PHIEUXUAT (maPhieuXuat),
    FOREIGN KEY (maHang) REFERENCES SANPHAM (maHang)
);

-- T?o b?ng MUA
CREATE TABLE MUA (
    maHD CHAR(6) NOT NULL,
    maHang CHAR(10) NOT NULL,
    KH_NguoiID CHAR(6) NOT NULL,
    soLuong NUMBER NOT NULL,
    CONSTRAINT pk_muasp PRIMARY KEY (KH_NguoiID, maHD, maHang),
    FOREIGN KEY (KH_NguoiID) REFERENCES KHACHHANG (KH_NguoiID),
    FOREIGN KEY (maHD) REFERENCES HOADON (maHD),
    FOREIGN KEY (maHang) REFERENCES SANPHAM (maHang)
);

-- T?o b?ng Login
CREATE TABLE Login (
    Username VARCHAR2(50) PRIMARY KEY,
    Password VARCHAR2(50) NOT NULL
);


-----------Thu tuc Quan ly san pham-------------


CREATE OR REPLACE PROCEDURE sp_TraHang (
    p_NgayLap DATE,
    p_Cursor OUT SYS_REFCURSOR
) AS
   v_SoLuongTraHang NUMBER := 0;
   v_HoaDonID CHAR(6);
   v_HangID CHAR(10);
   v_SoLuong NUMBER;
   rec HOADON%ROWTYPE; -- Sử dụng HOADON%ROWTYPE cho biến rec

BEGIN
    OPEN p_Cursor FOR
        SELECT HD.maHD, M.maHang, M.soLuong
        FROM HOADON HD
        INNER JOIN MUA M ON HD.maHD = M.maHD
        WHERE TRUNC(HD.ngayLap) = TRUNC(p_NgayLap);

    FOR rec IN (SELECT HD.maHD, M.maHang, M.soLuong FROM HOADON HD
                INNER JOIN MUA M ON HD.maHD = M.maHD
                WHERE TRUNC(HD.ngayLap) = TRUNC(p_NgayLap)) LOOP
        v_HoaDonID := rec.maHD;
        v_HangID := rec.maHang;
        v_SoLuong := rec.soLuong;

        UPDATE SANPHAM
        SET tonKho = tonKho + v_SoLuong
        WHERE maHang = v_HangID;

        DELETE FROM MUA
        WHERE maHD = v_HoaDonID AND maHang = v_HangID;

        v_SoLuongTraHang := v_SoLuongTraHang + 1;
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('SoLuongTraHang: ' || v_SoLuongTraHang);
END;
/





CREATE OR REPLACE PROCEDURE sp_ThongKeDoanhThuTrongNgay (
    p_NgayLap DATE,
    p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
        SELECT COUNT(DISTINCT HD.maHD) AS SoLuongDonHang,
               SUM(CT.SoLuong * SP.giaBan - CT.GiamGia) AS DoanhThu
        FROM HOADON HD
        INNER JOIN ChiTietHD CT ON HD.maHD = CT.maHD
        INNER JOIN SANPHAM SP ON CT.maHang = SP.maHang
        WHERE TRUNC(HD.ngayLap) = TRUNC(p_NgayLap)
        GROUP BY TRUNC(HD.ngayLap);
END;
/


CREATE OR REPLACE PROCEDURE sp_SoLuongHangBanTrongNgay (
    p_Ngay DATE,
    p_SoLuong OUT NUMBER
) AS
BEGIN
    SELECT NVL(SUM(ct.SoLuong), 0)
    INTO p_SoLuong
    FROM HOADON hd
    INNER JOIN ChiTietHD ct ON hd.maHD = ct.maHD
    WHERE TRUNC(hd.ngayLap) = TRUNC(p_Ngay);
END;
/






CREATE OR REPLACE PROCEDURE sp_viewChiTietSanPham (
    p_maHang CHAR
    , p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT *
    FROM SANPHAM
    WHERE maHang = p_maHang;
END;
/


CREATE OR REPLACE PROCEDURE viewSanPham (
    p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT sp.maHang, sp.tenHang, sp.giaBan, sp.giaVon, sp.tonKho, COALESCE(SUM(ct.SoLuong), 0) AS soLuongDaBan
    FROM SANPHAM sp
    LEFT JOIN ChiTietHD ct ON sp.maHang = ct.maHang
    GROUP BY sp.maHang, sp.tenHang, sp.giaBan, sp.giaVon, sp.tonKho;
END;
/


CREATE OR REPLACE PROCEDURE sp_layDataTableBarCode (
    p_maHang CHAR
    , p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT maHang, tenHang, giaBan
    FROM SANPHAM
    WHERE maHang = p_maHang;
END;
/


CREATE OR REPLACE PROCEDURE sp_ThemSanPham (
    p_maHang CHAR,
    p_tenHang NVARCHAR2,
    p_nhomHang NVARCHAR2,
    p_thuongHieu NVARCHAR2,
    p_giaVon NUMBER,
    p_giaBan NUMBER,
    p_tonKho NUMBER,
    p_trongLuong NUMBER,
    p_anh BLOB DEFAULT NULL
) AS
BEGIN
    INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
    VALUES (p_maHang, p_tenHang, p_nhomHang, p_thuongHieu, p_giaVon, p_giaBan, p_tonKho, p_trongLuong, p_anh);
END;
/



CREATE OR REPLACE PROCEDURE sp_SuaSanPham (
    p_maHang CHAR,
    p_tenHang NVARCHAR2,
    p_nhomHang NVARCHAR2,
    p_thuongHieu NVARCHAR2,
    p_giaVon NUMBER,
    p_giaBan NUMBER,
    p_tonKho NUMBER,
    p_trongLuong NUMBER,
    p_anh BLOB DEFAULT NULL
) AS
BEGIN
    UPDATE SANPHAM
    SET tenHang = p_tenHang,
        nhomHang = p_nhomHang,
        thuongHieu = p_thuongHieu,
        giaVon = p_giaVon,
        giaBan = p_giaBan,
        tonKho = p_tonKho,
        trongLuong = p_trongLuong,
        anh = p_anh
    WHERE maHang = p_maHang;
END;
/


CREATE OR REPLACE PROCEDURE sp_XoaSanPham (
    p_maHang CHAR,
    p_Result OUT VARCHAR2
) AS
   l_count NUMBER; -- Biến để lưu kết quả kiểm tra tồn tại

BEGIN
    p_Result := 'false'; -- Mặc định là 'false'

    -- Kiểm tra xem sản phẩm có tồn tại trong bảng SANPHAM không
    SELECT COUNT(*) INTO l_count FROM SANPHAM WHERE maHang = p_maHang;

    -- Kiểm tra xem có các bản ghi liên quan trong bảng ChiTietHD không
    IF l_count > 0 THEN
        -- Kiểm tra xem có các bản ghi liên quan trong bảng ChiTietHD không
        SELECT COUNT(*) INTO l_count FROM ChiTietHD WHERE maHang = p_maHang;

        IF l_count = 0 THEN
            -- Xóa sản phẩm trong bảng SANPHAM
            DELETE FROM SANPHAM WHERE maHang = p_maHang;
            p_Result := 'true';
        ELSE
            p_Result := 'false'; -- Không thể xóa vì có các bản ghi liên quan
        END IF;
    END IF;
END;







-----------Thu tuc Quan ly khach hang-------------

CREATE OR REPLACE PROCEDURE kh_layDanhSachKhachHang (
    p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT KHACHHANG.KH_NguoiID, NGUOI.hoTen, NGUOI.SDT, COALESCE(SUM(ChiTietHD.SoLuong * SANPHAM.giaBan - ChiTietHD.GiamGia), 0) AS TongTien, KHACHHANG.diem
    FROM KHACHHANG
    LEFT JOIN HOADON ON KHACHHANG.KH_NguoiID = HOADON.KH_NguoiID
    LEFT JOIN ChiTietHD ON HOADON.maHD = ChiTietHD.maHD
    LEFT JOIN SANPHAM ON ChiTietHD.maHang = SANPHAM.maHang
    INNER JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
    GROUP BY KHACHHANG.KH_NguoiID, NGUOI.hoTen, NGUOI.SDT, KHACHHANG.diem;
END;
/


CREATE OR REPLACE PROCEDURE kh_layThongTinKhachHang (
    p_maKhachHang CHAR,
    p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT KHACHHANG.KH_NguoiID, NGUOI.hoTen, NGUOI.SDT, NGUOI.ngaySinh, NGUOI.gioiTinh, NGUOI.diaChi, KHACHHANG.diem, NGUOI_TAO.hoTen as nguoiTao, KHACHHANG.ngayTao
    FROM KHACHHANG
    INNER JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
    INNER JOIN NHANVIEN ON KHACHHANG.nguoiTao = NHANVIEN.NV_NguoiID
    INNER JOIN NGUOI NGUOI_TAO ON NHANVIEN.NV_NguoiID = NGUOI_TAO.NguoiID
    WHERE KHACHHANG.KH_NguoiID = p_maKhachHang;
END;
/




CREATE OR REPLACE PROCEDURE kh_suaThongTinKhachHang (
    p_khNguoiID CHAR,
    p_hoTen NVARCHAR2,
    p_gioiTinh NUMBER,
    p_ngaySinh DATE,
    p_diaChi NVARCHAR2,
    p_SDT NVARCHAR2
) AS
BEGIN
    UPDATE NGUOI
    SET hoTen = N'' || p_hoTen, gioiTinh = p_gioiTinh, ngaySinh = p_ngaySinh, diaChi = N'' || p_diaChi, SDT = p_SDT
    WHERE NguoiID = p_khNguoiID;
END;
/


CREATE OR REPLACE PROCEDURE kh_timKiemKhachHang (
    p_keyword NVARCHAR2,
    p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT KHACHHANG.KH_NguoiID, NGUOI.hoTen, NGUOI.SDT, COALESCE(SUM(ChiTietHD.SoLuong * SANPHAM.giaBan - ChiTietHD.GiamGia), 0) AS TongTien, KHACHHANG.diem
    FROM KHACHHANG
    INNER JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
    LEFT JOIN HOADON ON KHACHHANG.KH_NguoiID = HOADON.KH_NguoiID
    LEFT JOIN ChiTietHD ON HOADON.maHD = ChiTietHD.maHD
    LEFT JOIN SANPHAM ON ChiTietHD.maHang = SANPHAM.maHang
    WHERE KHACHHANG.KH_NguoiID LIKE '%' || p_keyword || '%'
       OR NGUOI.hoTen LIKE '%' || p_keyword || '%'
       OR NGUOI.SDT LIKE '%' || p_keyword || '%'
       OR KHACHHANG.diem LIKE '%' || p_keyword || '%'
    GROUP BY KHACHHANG.KH_NguoiID, NGUOI.hoTen, NGUOI.SDT, KHACHHANG.diem;
END;
/


CREATE OR REPLACE PROCEDURE kh_xoaKhachHang (
    p_maKhachHang CHAR,
    p_Result OUT VARCHAR2
) AS
   l_count NUMBER; -- Biến để lưu kết quả kiểm tra tồn tại

BEGIN
    p_Result := 'false'; -- Mặc định là 'false'

    -- Kiểm tra xem khách hàng có tồn tại trong bảng KHACHHANG không
    SELECT COUNT(*) INTO l_count FROM KHACHHANG WHERE KH_NguoiID = p_maKhachHang;

    IF l_count > 0 THEN
        -- Xóa các bản ghi liên quan trong bảng ChiTietHD trước
        DELETE FROM ChiTietHD WHERE maHD IN (
            SELECT maHD FROM HOADON WHERE KH_NguoiID = p_maKhachHang
        );
        -- Xóa các hóa đơn của khách hàng
        DELETE FROM HOADON WHERE KH_NguoiID = p_maKhachHang;
        -- Xóa khách hàng trong bảng KHACHHANG
        DELETE FROM KHACHHANG WHERE KH_NguoiID = p_maKhachHang;
        -- Xóa khách hàng trong bảng NGUOI
        DELETE FROM NGUOI WHERE NguoiID = p_maKhachHang;
        p_Result := 'true';
    END IF;
END;
/



CREATE OR REPLACE PROCEDURE kh_themKhachHang (
    p_khNguoiID CHAR,
    p_hoTen NVARCHAR2,
    p_gioiTinh NUMBER,
    p_ngaySinh DATE,
    p_diaChi NVARCHAR2,
    p_SDT NVARCHAR2,
    p_nguoiTao CHAR,
    p_ngayTao DATE
) AS
BEGIN
    INSERT INTO NGUOI (NguoiID, hoTen, gioiTinh, ngaySinh, diaChi, SDT)
    VALUES (p_khNguoiID, N'' || p_hoTen, p_gioiTinh, p_ngaySinh, N'' || p_diaChi, p_SDT);
    INSERT INTO KHACHHANG (KH_NguoiID, diem, nguoiTao, ngayTao)
    VALUES (p_khNguoiID, 0, p_nguoiTao, p_ngayTao);
END;
/




--------------------- th? t?c quan ly hoa don ------------------

CREATE OR REPLACE PROCEDURE hd_layDanhSachHoaDon (
    p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT HOADON.maHD, HOADON.ngayLap, NGUOI.hoTen AS tenKH, SUM(SANPHAM.giaBan * ChiTietHD.SoLuong) AS TongTienHang, SUM(ChiTietHD.GiamGia) AS GiamGia
    FROM HOADON
    JOIN ChiTietHD ON HOADON.maHD = ChiTietHD.maHD
    JOIN SANPHAM ON ChiTietHD.maHang = SANPHAM.maHang
    JOIN KHACHHANG ON HOADON.KH_NguoiID = KHACHHANG.KH_NguoiID
    JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
    GROUP BY HOADON.maHD, HOADON.ngayLap, NGUOI.hoTen;
END;
/


CREATE OR REPLACE PROCEDURE hd_layThongTinHoaDonChiTiet (
    p_maHoaDon CHAR,
    p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT HD.maHD, HD.ngayLap, NGUOI.hoTen AS "TenKhachHang", NHANVIEN_NGUOI.hoTen AS "TenNhanVien"
    FROM HOADON HD
    INNER JOIN KHACHHANG ON HD.KH_NguoiID = KHACHHANG.KH_NguoiID
    INNER JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
    INNER JOIN NHANVIEN ON HD.NV_NguoiID = NHANVIEN.NV_NguoiID
    INNER JOIN NGUOI NHANVIEN_NGUOI ON NHANVIEN.NV_NguoiID = NHANVIEN_NGUOI.NguoiID
    WHERE HD.maHD = p_maHoaDon;
END;
/

CREATE OR REPLACE PROCEDURE hd_suaChiTietHoaDon (
    p_maHD CHAR,
    p_maHang CHAR,
    p_SoLuong INT,
    p_GiamGia DECIMAL
) AS
   l_count NUMBER; -- Biến để lưu kết quả kiểm tra tồn tại

BEGIN
    -- Kiểm tra xem sản phẩm có tồn tại trong chi tiết hóa đơn không
    SELECT COUNT(*) INTO l_count FROM ChiTietHD WHERE maHD = p_maHD AND maHang = p_maHang;
    
    IF l_count = 0 THEN
        -- Nếu sản phẩm chưa tồn tại, thêm sản phẩm mới vào chi tiết hóa đơn
        INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia) VALUES (p_maHD, p_maHang, p_SoLuong, p_GiamGia);
    ELSE
        -- Nếu sản phẩm đã tồn tại, cập nhật thông tin sản phẩm
        UPDATE ChiTietHD SET SoLuong = p_SoLuong, GiamGia = p_GiamGia WHERE maHD = p_maHD AND maHang = p_maHang;
    END IF;
END;
/



CREATE OR REPLACE PROCEDURE hd_timKiemHoaDon (
    p_keyword NVARCHAR2,
    p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT HOADON.maHD, HOADON.ngayLap, NGUOI.hoTen AS tenKH, SUM(SANPHAM.giaBan * ChiTietHD.SoLuong) AS TongTienHang, SUM(ChiTietHD.GiamGia) AS GiamGia
    FROM HOADON
    JOIN ChiTietHD ON HOADON.maHD = ChiTietHD.maHD
    JOIN SANPHAM ON ChiTietHD.maHang = SANPHAM.maHang
    JOIN KHACHHANG ON HOADON.KH_NguoiID = KHACHHANG.KH_NguoiID
    JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
    WHERE HOADON.maHD LIKE '%' || p_keyword || '%'
       OR HOADON.ngayLap LIKE '%' || p_keyword || '%'
       OR NGUOI.hoTen LIKE '%' || p_keyword || '%'
    GROUP BY HOADON.maHD, HOADON.ngayLap, NGUOI.hoTen;
END;
/


CREATE OR REPLACE PROCEDURE hd_suaHoaDon (
    p_maHD CHAR,
    p_NV_NguoiID CHAR,
    p_ngayLap DATE,
    p_KH_NguoiID CHAR,
    p_Result OUT NUMBER
) AS
   l_count NUMBER; -- Biến để lưu kết quả kiểm tra tồn tại

BEGIN
    p_Result := 0; -- Mặc định là 0

    -- Kiểm tra xem hóa đơn có tồn tại không
    SELECT COUNT(*) INTO l_count FROM HOADON WHERE maHD = p_maHD;
    
    IF l_count > 0 THEN
        -- Nếu tồn tại, cập nhật thông tin hóa đơn
        UPDATE HOADON SET NV_NguoiID = p_NV_NguoiID, ngayLap = p_ngayLap, KH_NguoiID = p_KH_NguoiID WHERE maHD = p_maHD;
        p_Result := 1; -- Cập nhật thành công
    END IF;
END;
/



CREATE OR REPLACE PROCEDURE hd_themChiTietHoaDon (
    p_maHoaDon CHAR,
    p_maHang CHAR,
    p_SoLuong INT,
    p_GiamGia DECIMAL
) AS
BEGIN
    INSERT INTO ChiTietHD(maHD, maHang, SoLuong, GiamGia) VALUES (p_maHoaDon, p_maHang, p_SoLuong, p_GiamGia);
END;
/

CREATE OR REPLACE PROCEDURE hd_layDanhSachSanPhamCuaHoaDon (
    p_maHoaDon CHAR,
    p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT CTHD.maHang, SANPHAM.tenHang, CTHD.SoLuong, SANPHAM.giaBan, CTHD.GiamGia, (CTHD.SoLuong * SANPHAM.giaBan - CTHD.GiamGia) as ThanhTien
    FROM HOADON
    JOIN ChiTietHD CTHD ON HOADON.maHD = CTHD.maHD
    JOIN SANPHAM ON CTHD.maHang = SANPHAM.maHang
    WHERE HOADON.maHD = p_maHoaDon;
END;
/


CREATE OR REPLACE PROCEDURE hd_layHoaDonCuoi (
    p_Cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Cursor FOR
    SELECT * FROM (SELECT * FROM HOADON ORDER BY maHD DESC) WHERE ROWNUM = 1;
END;
/


CREATE OR REPLACE PROCEDURE hd_xoaHoaDon (
    p_maHoaDon CHAR
) AS
BEGIN
    SAVEPOINT before_delete; -- Tạo điểm lưu trước khi xóa

    -- Lấy danh sách sản phẩm và số lượng tương ứng từ ChiTietHD
    FOR chi_tiet IN (SELECT maHang, SoLuong FROM ChiTietHD WHERE maHD = p_maHoaDon) LOOP
        -- Cập nhật lại số lượng tồn kho của từng sản phẩm
        UPDATE SANPHAM
        SET tonKho = tonKho + chi_tiet.SoLuong
        WHERE maHang = chi_tiet.maHang;
    END LOOP;

    -- Xóa tất cả các chi tiết hóa đơn liên quan đến mã hóa đơn cần xóa
    DELETE FROM ChiTietHD WHERE maHD = p_maHoaDon;

    -- Xóa hóa đơn
    DELETE FROM HOADON WHERE maHD = p_maHoaDon;

    COMMIT; -- Xác nhận xóa
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK TO before_delete; -- Phục hồi trạng thái trước khi xóa
        RAISE; -- Kích hoạt lỗi để được xử lý ở mức cao hơn
END;
/


CREATE OR REPLACE PROCEDURE hd_themHoaDon (
    p_maHoaDon CHAR,
    p_NV_NguoiID CHAR,
    p_ngayLap DATE,
    p_KH_NguoiID CHAR
) AS
BEGIN
    INSERT INTO HOADON (maHD, NV_NguoiID, ngayLap, KH_NguoiID)
    VALUES (p_maHoaDon, p_NV_NguoiID, p_ngayLap, p_KH_NguoiID);
END;
/







-- Thêm tài khoản người dùng
INSERT INTO Login(Username, Password)
VALUES ('admin', 'admin');

-- Thêm thông tin người dùng
INSERT INTO NGUOI (NguoiID, hoTen, gioiTinh, ngaySinh, diaChi, SDT)
VALUES ('KH001', N'Khách Lẻ', 0, TO_DATE('1990-01-01', 'YYYY-MM-DD'), N'123 Đường A, Q1, TP.HCM', '0901234567');
INSERT INTO NGUOI (NguoiID, hoTen, gioiTinh, ngaySinh, diaChi, SDT)
VALUES ('NV001', N'Vũ Huy', 1, TO_DATE('1995-02-02', 'YYYY-MM-DD'), N'456 Đường B, Q2, TP.HCM', '0907654321');
INSERT INTO NGUOI (NguoiID, hoTen, gioiTinh, ngaySinh, diaChi, SDT)
VALUES ('KH002', N'Lê Văn C', 0, TO_DATE('1985-03-03', 'YYYY-MM-DD'), N'789 Đường C, Q3, TP.HCM', '0912345678');

-- Thêm thông tin nhân viên
INSERT INTO NHANVIEN (NV_NguoiID, chucVu)
VALUES ('NV001', N'Nhân viên bán hàng');

-- Thêm thông tin khách hàng
INSERT INTO KHACHHANG (KH_NguoiID, diem, nguoiTao, ngayTao)
VALUES ('KH001', 100, 'NV001', TO_TIMESTAMP('2023-04-17 12:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO KHACHHANG (KH_NguoiID, diem, nguoiTao, ngayTao)
VALUES ('KH002', 50, 'NV001', TO_TIMESTAMP('2023-04-17 12:30:00', 'YYYY-MM-DD HH24:MI:SS'));

-- Thêm thông tin sản phẩm
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP001', N'Áo khoác nam', 'Áo khoác', 'Zara', 500000, 700000, 10, 0.5, NULL);
-- Thêm thông tin sản phẩm (tiếp theo)
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP002', N'Quần jean nam', 'Quần jean', 'Levis', 350000, 450000, 20, 0.3, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP003', N'Áo thun coolmate', 'Áo thun', 'Coolmate', 125000, 150000, 5, 0.25, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP004', N'váy đầm nữ', 'Váy đầm', 'H&M', 250000, 350000, 15, 0.4, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP005', N'Áo sơ mi nữ', 'Áo sơ mi', 'Uniqlo', 200000, 250000, 30, 0.3, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP006', N'Áo khoác nữ', 'Áo khoác', 'H&M', 450000, 600000, 8, 0.4, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP007', N'Quần tây nam', 'Quần tây', 'Zara', 600000, 800000, 12, 0.35, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP008', N'Áo phông nữ', 'Áo phông', 'Adidas', 300000, 400000, 25, 0.3, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP009', N'Áo len nam', 'Áo len', 'Uniqlo', 400000, 550000, 18, 0.4, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP010', N'Chân váy nữ', 'Chân váy', 'Levis', 280000, 380000, 20, 0.35, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP011', N'Giày thể thao nam', 'Giày thể thao', 'Nike', 800000, 1200000, 5, 0.8, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP012', N'Áo khoác phao nữ', 'Áo khoác', 'The North Face', 1200000, 1500000, 6, 1.2, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP013', N'Quần legging nữ', 'Quần legging', 'Fabletics', 350000, 450000, 15, 0.25, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP014', N'Áo len nữ', 'Áo len', 'Mango', 450000, 650000, 12, 0.35, NULL);
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES ('SP015', N'Giày búp bê nữ', 'Giày búp bê', 'H&M', 250000, 320000, 25, 0.4, NULL);


-- Thêm thông tin hóa đơn
INSERT INTO HOADON (maHD, NV_NguoiID, ngayLap, KH_NguoiID)
VALUES ('HD001', 'NV001', TO_TIMESTAMP('2022-03-01 12:30:00', 'YYYY-MM-DD HH24:MI:SS'), 'KH001');
INSERT INTO HOADON (maHD, NV_NguoiID, ngayLap, KH_NguoiID)
VALUES ('HD002', 'NV001', TO_TIMESTAMP('2022-03-02 12:50:00', 'YYYY-MM-DD HH24:MI:SS'), 'KH002');
INSERT INTO HOADON (maHD, NV_NguoiID, ngayLap, KH_NguoiID)
VALUES ('HD003', 'NV001', TO_TIMESTAMP('2022-03-03 12:20:00', 'YYYY-MM-DD HH24:MI:SS'), 'KH002');
INSERT INTO HOADON (maHD, NV_NguoiID, ngayLap, KH_NguoiID)
VALUES ('HD004', 'NV001', TO_TIMESTAMP('2022-03-04 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'KH002');
INSERT INTO HOADON (maHD, NV_NguoiID, ngayLap, KH_NguoiID)
VALUES ('HD005', 'NV001', TO_TIMESTAMP('2022-03-05 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'KH001');

-- Thêm chi tiết hóa đơn
INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia)
VALUES ('HD001', 'SP003', 2, 80000);
INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia)
VALUES ('HD001', 'SP002', 2, 20000);
INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia)
VALUES ('HD002', 'SP002', 1, 0);
INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia)
VALUES ('HD002', 'SP003', 3, 0);
INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia)
VALUES ('HD003', 'SP002', 2, 0);
INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia)
VALUES ('HD004', 'SP005', 4, 0);
INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia)
VALUES ('HD005', 'SP004', 1, 0);
INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia)
VALUES ('HD005', 'SP001', 3, 0);



