
CREATE DATABASE QUAN_LY_BAN_HANG_QUAN_AO
GO
    USE QUAN_LY_BAN_HANG_QUAN_AO
CREATE TABLE NGUOI(
                      NguoiID char(6) NOT NULL PRIMARY KEY,
                      hoTen nvarchar(255),
                      gioiTinh bit,
                      ngaySinh date,
                      diaChi nvarchar(255),
                      SDT nvarchar(20)
);
GO
CREATE TABLE NHANVIEN(
                         NV_NguoiID char(6) PRIMARY KEY,
                         chucVu nchar(255) NOT NULL,
                         FOREIGN KEY (NV_NguoiID) REFERENCES NGUOI(NguoiID)
);
go

CREATE TABLE KHACHHANG(
                          KH_NguoiID char(6) PRIMARY KEY,
                          diem int,
                          nguoiTao char(6),
                          ngayTao datetime,
                          FOREIGN KEY (KH_NguoiID) REFERENCES NGUOI(NguoiID),
                          FOREIGN KEY (nguoiTao) REFERENCES NHANVIEN(NV_NguoiID)
);

GO
CREATE TABLE SANPHAM (
                         maHang CHAR(10) PRIMARY KEY,
                         tenHang nvarchar(255) NOT NULL,
                         nhomHang nvarchar(255),
                         thuongHieu nvarchar(255),
                         giaVon DECIMAL(18) NOT NULL,
                         giaBan DECIMAL(18) NOT NULL,
                         tonKho INT,
                         trongLuong DECIMAL(18,2),
                         anh VARBINARY(MAX)
);

GO
CREATE TABLE HOADON(
                       maHD char(6) NOT NULL PRIMARY KEY,
                       NV_NguoiID char(6) NOT NULL,
                       ngayLap datetime NOT NULL,
                       KH_NguoiID char(6) NOT NULL,
                       FOREIGN KEY (NV_NguoiID) REFERENCES NHANVIEN(NV_NguoiID),
                       FOREIGN KEY (KH_NguoiID) REFERENCES KHACHHANG(KH_NguoiID)
);
GO
CREATE TABLE ChiTietHD (
                           maHD CHAR(6),
                           maHang CHAR(10) NOT NULL,
                           SoLuong INT NOT NULL,
                           GiamGia DECIMAL(18),
                           FOREIGN KEY (maHD) REFERENCES HOADON(maHD),
                           FOREIGN KEY (maHang) REFERENCES SANPHAM(maHang)
);
GO
CREATE TABLE PHIEUNHAP (
                           maPhieuNhap CHAR(5) PRIMARY KEY,
                           thoiGian DATETIME NOT NULL,
                           soLuong INT,
                           trangThai NVARCHAR(25)
);
GO
CREATE TABLE PHIEUXUAT(
                          maPhieuXuat CHAR(5) PRIMARY KEY,
                          thoiGian DATETIME NOT NULL,
                          soLuong INT,
                          trangThai NVARCHAR(25)
);
GO
CREATE TABLE CHITIETPHIEUNHAP (
                                  maPhieuNhap CHAR(5),
                                  maHang CHAR(10) NOT NULL,
                                  soLuong INT NOT NULL,
                                  donGia DECIMAL(18,2) NOT NULL,
                                  FOREIGN KEY (maPhieuNhap) REFERENCES PHIEUNHAP(maPhieuNhap),
                                  FOREIGN KEY (maHang) REFERENCES SANPHAM(maHang)
);
GO
CREATE TABLE CHITIETPHIEUXUAT (
                                  maPhieuXuat CHAR(5),
                                  maHang CHAR(10) NOT NULL,
                                  soLuong INT NOT NULL,
                                  donGia DECIMAL(18,2) NOT NULL,
                                  giamGia INT,
                                  moTa Nvarchar(25),
                                  FOREIGN KEY (maPhieuXuat) REFERENCES PHIEUXUAT(maPhieuXuat),
                                  FOREIGN KEY (maHang) REFERENCES SANPHAM(maHang)
);
GO
CREATE TABLE MUA(
                    maHD char(6) NOT NULL,
                    maHang char(10) NOT NULL,
                    KH_NguoiID char(6) NOT NULL,
                    soLuong int NOT NULL,
                    CONSTRAINT pk_muasp PRIMARY KEY (KH_NguoiID, maHD, maHang),
                    FOREIGN KEY (KH_NguoiID) REFERENCES KHACHHANG(KH_NguoiID),
                    FOREIGN KEY (maHD) REFERENCES HOADON(maHD),
                    FOREIGN KEY (maHang) REFERENCES SANPHAM(maHang)
);
GO
CREATE TABLE Login (
                       Username VARCHAR(50) NOT NULL PRIMARY KEY,
                       Password VARCHAR(50) NOT NULL
);
GO

----------------------------------------------------------------- tạo các thủ tục---------------------------------------------


go
CREATE PROCEDURE sp_TraHang
    @NgayLap DATE
AS
BEGIN

    DECLARE @SoLuongTraHang INT
    SET @SoLuongTraHang = 0
    DECLARE @HoaDonID CHAR(6)
    DECLARE @HangID CHAR(10)
    DECLARE @SoLuong INT

    DECLARE TraHangCursor CURSOR FOR
SELECT HD.maHD, M.maHang, M.soLuong
FROM HOADON HD
         INNER JOIN MUA M ON HD.maHD = M.maHD
WHERE CAST(HD.ngayLap AS DATE) = @NgayLap

    OPEN TraHangCursor
    FETCH NEXT FROM TraHangCursor INTO @HoaDonID, @HangID, @SoLuong

    -- Lặp qua danh sách các hóa đơn và trả hàng
    WHILE @@FETCH_STATUS = 0
BEGIN

UPDATE SANPHAM
SET tonKho = tonKho + @SoLuong
WHERE maHang = @HangID
DELETE FROM MUA
WHERE maHD = @HoaDonID AND maHang = @HangID
    SET @SoLuongTraHang = @SoLuongTraHang + 1

        FETCH NEXT FROM TraHangCursor INTO @HoaDonID, @HangID, @SoLuong
END

CLOSE TraHangCursor
    DEALLOCATE TraHangCursor

SELECT @SoLuongTraHang AS SoLuongTraHang
END

go

go
-- thủ tục thống kê
CREATE PROCEDURE tk_ThongKeLoiNhuanTheoTuan
    AS
BEGIN
DECLARE @startOfWeek DATE, @endOfWeek DATE;
SET @startOfWeek = DATEADD(wk, DATEDIFF(wk, 0, GETDATE()), 0); -- Lấy ngày bắt đầu của tuần hiện tại
SET @endOfWeek = DATEADD(day, 6, @startOfWeek); -- Lấy ngày kết thúc của tuần hiện tại

SELECT
    DATEPART(week, dbo.hoadon.ngayLap) AS [thoiGian],
  SUM(dbo.chitietHD.soLuong * dbo.sanpham.giaBan) AS [tongTienHang],
  SUM(dbo.chitietHD.giamGia) AS [giamGia],
  SUM(dbo.chitietHD.soLuong * dbo.sanpham.giaBan) - SUM(dbo.chitietHD.giamGia) AS [doanhThu],
  SUM(dbo.chitietHD.soLuong * dbo.sanpham.giaVon) AS [tongGiaVon],
  SUM(dbo.chitietHD.soLuong * dbo.sanpham.giaBan) - SUM(dbo.chitietHD.soLuong * dbo.sanpham.giaVon) AS [loiNhuan]
FROM dbo.hoadon
    INNER JOIN dbo.chitietHD
ON dbo.hoadon.maHD = dbo.chitietHD.maHD
    INNER JOIN dbo.sanpham
    ON dbo.chitietHD.maHang = dbo.sanpham.maHang
WHERE dbo.hoadon.ngayLap >= @startOfWeek AND dbo.hoadon.ngayLap <= @endOfWeek -- chỉ lấy các hóa đơn trong tuần hiện tại
GROUP BY DATEPART(week, dbo.hoadon.ngayLap)
ORDER BY DATEPART(week, dbo.hoadon.ngayLap);
END

GO
CREATE PROCEDURE sp_ThongKeDoanhThuTrongNgay
    @NgayLap DATE
AS
BEGIN
SELECT COUNT(DISTINCT HD.maHD) AS 'SoLuongDonHang',
        SUM(CT.SoLuong * SP.giaBan - CT.GiamGia) AS 'DoanhThu'
FROM HOADON HD
         INNER JOIN ChiTietHD CT ON HD.maHD = CT.maHD
         INNER JOIN SANPHAM SP ON CT.maHang = SP.maHang
WHERE CAST(HD.ngayLap AS DATE) = CAST(@NgayLap AS DATE)
GROUP BY CAST(HD.ngayLap AS DATE)
END

GO

go
-- thống kê số hàng bán trong 1 ngày
CREATE PROCEDURE sp_SoLuongHangBanTrongNgay
    @ngay DATE
AS
BEGIN
SELECT ISNULL(SUM(ct.SoLuong), 0) AS SoLuongHangBan
FROM HOADON hd
         INNER JOIN ChiTietHD ct ON hd.maHD = ct.maHD
WHERE CAST(hd.ngayLap AS DATE) = CAST(@ngay AS DATE)
END




GO
-- thủ tục thêm sửa xóa cho bảng sản phẩm
CREATE PROCEDURE sp_viewChiTietSanPham
    @maHang CHAR(10)
AS
BEGIN
SELECT *
FROM SANPHAM
WHERE maHang = @maHang
END

GO
CREATE PROCEDURE viewSanPham
    AS
BEGIN
SELECT maHang, tenHang, giaBan, giaVon, tonKho,
       (SELECT ISNULL(SUM(soLuong), 0) FROM ChiTietHD WHERE maHang = SANPHAM.maHang) AS soLuongDaBan
FROM SANPHAM
END
GO

CREATE PROCEDURE sp_layDataTableBarCode
    @maHang CHAR(10)
AS
BEGIN
SELECT maHang, tenHang, giaBan
FROM SANPHAM
WHERE maHang = @maHang
END


GO

CREATE PROCEDURE sp_ThemSanPham
    @maHang CHAR(10),
@tenHang NVARCHAR(255),
@nhomHang NVARCHAR(50),
@thuongHieu NVARCHAR(50),
@giaVon DECIMAL(18),
@giaBan DECIMAL(18),
@tonKho INT,
@trongLuong DECIMAL(18),
@anh VARBINARY(MAX) = NULL
AS
BEGIN
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES (@maHang, N'' + @tenHang, N'' + @nhomHang, N'' + @thuongHieu, @giaVon, @giaBan, @tonKho, @trongLuong, @anh)
END


GO

CREATE PROCEDURE sp_SuaSanPham
    @maHang CHAR(10),
@tenHang NVARCHAR(255),
@nhomHang NVARCHAR(50),
@thuongHieu NVARCHAR(50),
@giaVon DECIMAL(18),
@giaBan DECIMAL(18),
@tonKho INT,
@trongLuong DECIMAL(18),
@anh VARBINARY(MAX) = NULL
AS
BEGIN
UPDATE SANPHAM
SET tenHang = N'' + @tenHang,
    nhomHang = N'' + @nhomHang,
    thuongHieu = N'' + @thuongHieu,
    giaVon = @giaVon,
    giaBan = @giaBan,
    tonKho = @tonKho,
    trongLuong = @trongLuong,
    anh = @anh
WHERE maHang = @maHang
END
GO

CREATE PROCEDURE sp_XoaSanPham
    @maHang CHAR(10)
AS
BEGIN
    -- kiểm tra xem sản phẩm có tồn tại trong bảng SANPHAM không
    IF EXISTS(SELECT * FROM SANPHAM WHERE maHang = @maHang)
BEGIN
        -- xóa các bản ghi liên quan trong bảng ChiTietHD trước
DELETE FROM ChiTietHD WHERE maHang = @maHang
-- sau đó xóa sản phẩm trong bảng SANPHAM
DELETE FROM SANPHAM WHERE maHang = @maHang
SELECT 'true'
END
ELSE
BEGIN
SELECT 'false'
END
END
GO
CREATE PROCEDURE sp_TimKiemSanPham
    @keyword NVARCHAR(255)
AS
BEGIN
SELECT sp.*, (SELECT ISNULL(SUM(soLuong), 0) FROM ChiTietHD WHERE maHang = sp.maHang) AS soLuongDaBan
FROM (
         SELECT maHang,tenHang,giaVon,giaBan,tonKho FROM SANPHAM
         WHERE maHang LIKE '%' + @keyword + '%'
            OR tenHang LIKE '%' + @keyword + '%'
            OR nhomHang LIKE '%' + @keyword + '%'
            OR thuongHieu LIKE '%' + @keyword + '%'
            OR CAST(giaVon AS NVARCHAR(255)) LIKE '%' + @keyword + '%'
            OR CAST(giaBan AS NVARCHAR(255)) LIKE '%' + @keyword + '%'
            OR CAST(tonKho AS NVARCHAR(255)) LIKE '%' + @keyword + '%'
            OR CAST(trongLuong AS NVARCHAR(255)) LIKE '%' + @keyword + '%'
     ) AS sp
END

go
-----------Thủ tục Quản lý khách hàng-------------


GO
CREATE PROCEDURE kh_layDanhSachKhachHang
    AS
BEGIN
SELECT KHACHHANG.KH_NguoiID, NGUOI.hoTen, NGUOI.SDT, COALESCE(SUM(ChiTietHD.SoLuong * SANPHAM.giaBan - ChiTietHD.GiamGia), 0) AS TongTien, KHACHHANG.diem
FROM KHACHHANG
         LEFT JOIN HOADON ON KHACHHANG.KH_NguoiID = HOADON.KH_NguoiID
         LEFT JOIN ChiTietHD ON HOADON.maHD = ChiTietHD.maHD
         LEFT JOIN SANPHAM ON ChiTietHD.maHang = SANPHAM.maHang
         INNER JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
GROUP BY KHACHHANG.KH_NguoiID, NGUOI.hoTen, NGUOI.SDT, KHACHHANG.diem;
END
go



GO
CREATE PROCEDURE kh_layThongTinKhachHang
    @maKhachHang char(6)
AS
BEGIN
    SET NOCOUNT ON;

SELECT KHACHHANG.KH_NguoiID, NGUOI.hoTen, NGUOI.SDT, NGUOI.ngaySinh, NGUOI.gioiTinh, NGUOI.diaChi, KHACHHANG.diem, NGUOI_TAO.hoTen as nguoiTao, KHACHHANG.ngayTao
FROM KHACHHANG
         INNER JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
         INNER JOIN NHANVIEN ON KHACHHANG.nguoiTao = NHANVIEN.NV_NguoiID
         INNER JOIN NGUOI NGUOI_TAO ON NHANVIEN.NV_NguoiID = NGUOI_TAO.NguoiID
WHERE KHACHHANG.KH_NguoiID = @maKhachHang;
END

GO
CREATE PROCEDURE kh_suaThongTinKhachHang
    @khNguoiID char(6),
@hoTen nvarchar(255),
@gioiTinh bit,
@ngaySinh date,
@diaChi nvarchar(255),
@SDT nvarchar(20)
AS
BEGIN
UPDATE NGUOI
SET hoTen = N'' + @hoTen, gioiTinh = @gioiTinh, ngaySinh = @ngaySinh, diaChi = N'' + @diaChi, SDT = @SDT
WHERE NguoiID = @khNguoiID;
END;

DROP PROCEDURE kh_suaThongTinKhachHang;


GO

CREATE PROCEDURE kh_timKiemKhachHang
    @keyword NVARCHAR(255)
AS
BEGIN
SELECT KHACHHANG.KH_NguoiID, NGUOI.hoTen, NGUOI.SDT, COALESCE(SUM(ChiTietHD.SoLuong * SANPHAM.giaBan - ChiTietHD.GiamGia),0) AS TongTien, KHACHHANG.diem
FROM KHACHHANG
         INNER JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
         LEFT JOIN HOADON ON KHACHHANG.KH_NguoiID = HOADON.KH_NguoiID
         LEFT JOIN ChiTietHD ON HOADON.maHD = ChiTietHD.maHD
         LEFT JOIN SANPHAM ON ChiTietHD.maHang = SANPHAM.maHang
WHERE KHACHHANG.KH_NguoiID LIKE '%' + @keyword + '%'
   OR NGUOI.hoTen LIKE '%' + @keyword + '%'
   OR NGUOI.SDT LIKE '%' + @keyword + '%'
   OR KHACHHANG.diem LIKE '%' + @keyword + '%'
GROUP BY KHACHHANG.KH_NguoiID, NGUOI.hoTen, NGUOI.SDT, KHACHHANG.diem;
END

GO
CREATE PROCEDURE kh_xoaKhachHang
    @maKhachHang CHAR(6)
AS
BEGIN

DELETE FROM ChiTietHD WHERE maHD IN (
    SELECT maHD FROM HOADON WHERE KH_NguoiID = @maKhachHang
);
DELETE FROM HOADON WHERE KH_NguoiID = @maKhachHang;
DELETE FROM KHACHHANG WHERE KH_NguoiID = @maKhachHang;
DELETE FROM NGUOI WHERE NguoiID = @maKhachHang;
END

go

GO

CREATE PROCEDURE kh_themKhachHang
    @khNguoiID CHAR(6),
@hoTen NVARCHAR(255),
@gioiTinh BIT,
@ngaySinh DATE,
@diaChi NVARCHAR(255),
@SDT NVARCHAR(20),
@nguoiTao CHAR(6),
@ngayTao DATETIME
AS
BEGIN
INSERT INTO NGUOI (NguoiID, hoTen, gioiTinh, ngaySinh, diaChi, SDT)
VALUES (@khNguoiID, N'' + @hoTen, @gioiTinh, @ngaySinh, N'' + @diaChi, @SDT)
    INSERT INTO KHACHHANG (KH_NguoiID, diem, nguoiTao, ngayTao)
VALUES (@khNguoiID, 0, @nguoiTao, @ngayTao)
END


---------------- thủ tục quản lý hóa đơn------------------------------------
GO
CREATE PROCEDURE hd_layDanhSachHoaDon
    AS
BEGIN
SELECT HOADON.maHD, HOADON.ngayLap, NGUOI.hoTen AS tenKH, SUM(SANPHAM.giaBan * ChiTietHD.SoLuong) AS TongTienHang, SUM(ChiTietHD.GiamGia) AS GiamGia
FROM HOADON
         JOIN ChiTietHD ON HOADON.maHD = ChiTietHD.maHD
         JOIN SANPHAM ON ChiTietHD.maHang = SANPHAM.maHang
         JOIN KHACHHANG ON HOADON.KH_NguoiID = KHACHHANG.KH_NguoiID
         JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
GROUP BY HOADON.maHD, HOADON.ngayLap, NGUOI.hoTen;
END

GO

CREATE PROCEDURE hd_DanhSach
    as
BEGIN
SELECT maHD, NV_NguoiID, ngayLap, KH_NguoiID
FROM HOADON;
END
go
CREATE PROCEDURE hd_suaHoaDon
    @maHD char(6),
    @NV_NguoiID char(6),
    @ngayLap datetime,
    @KH_NguoiID char(6)
AS
BEGIN
    IF EXISTS (SELECT 1 FROM HOADON WHERE maHD = @maHD)
BEGIN
UPDATE HOADON
SET NV_NguoiID = @NV_NguoiID,
    ngayLap = @ngayLap,
    KH_NguoiID = @KH_NguoiID
WHERE maHD = @maHD;
SELECT 1 AS result;
END
ELSE
BEGIN
SELECT 0 AS result;
END
END
go
CREATE PROCEDURE hd_themChiTietHoaDon
    @maHoaDon CHAR(6),
    @maHang CHAR(10),
    @SoLuong INT,
    @GiamGia DECIMAL(18)
AS
BEGIN
INSERT INTO ChiTietHD(maHD, maHang, SoLuong, GiamGia)
VALUES (@maHoaDon, @maHang, @SoLuong, @GiamGia)
END

go


Go
CREATE PROCEDURE hd_layDanhSachSanPhamCuaHoaDon
    @maHoaDon CHAR(6)
AS
BEGIN
SELECT
    CTHD.maHang,
    SANPHAM.tenHang,
    CTHD.SoLuong,
    SANPHAM.giaBan,
    CTHD.GiamGia,
    (CTHD.SoLuong * SANPHAM.giaBan - CTHD.GiamGia) as ThanhTien
FROM
    HOADON
        JOIN ChiTietHD as CTHD ON HOADON.maHD = CTHD.maHD
        JOIN SANPHAM ON CTHD.maHang = SANPHAM.maHang
WHERE
        HOADON.maHD = @maHoaDon
END

GO
CREATE PROCEDURE hd_layThongTinHoaDonChiTiet
    @maHoaDon CHAR(6)
AS
BEGIN
SELECT HD.maHD, HD.ngayLap, NGUOI.hoTen AS 'TenKhachHang', NHANVIEN_NGUOI.hoTen AS 'TenNhanVien'
FROM HOADON HD
         INNER JOIN KHACHHANG ON HD.KH_NguoiID = KHACHHANG.KH_NguoiID
         INNER JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
         INNER JOIN NHANVIEN ON HD.NV_NguoiID = NHANVIEN.NV_NguoiID
         INNER JOIN NGUOI NHANVIEN_NGUOI ON NHANVIEN.NV_NguoiID = NHANVIEN_NGUOI.NguoiID
WHERE HD.maHD = @maHoaDon;
END
GO



go
CREATE PROCEDURE hd_timKiemHoaDon
    @keyword NVARCHAR(255)
AS
BEGIN
SELECT HOADON.maHD, HOADON.ngayLap, NGUOI.hoTen AS tenKH, SUM(SANPHAM.giaBan * ChiTietHD.SoLuong) AS TongTienHang, SUM(ChiTietHD.GiamGia) AS GiamGia
FROM HOADON
         JOIN ChiTietHD ON HOADON.maHD = ChiTietHD.maHD
         JOIN SANPHAM ON ChiTietHD.maHang = SANPHAM.maHang
         JOIN KHACHHANG ON HOADON.KH_NguoiID = KHACHHANG.KH_NguoiID
         JOIN NGUOI ON KHACHHANG.KH_NguoiID = NGUOI.NguoiID
WHERE HOADON.maHD LIKE '%' + @keyword + '%'
   OR HOADON.ngayLap LIKE '%' + @keyword + '%'
   OR NGUOI.hoTen LIKE N'%' + @keyword + N'%'
GROUP BY HOADON.maHD, HOADON.ngayLap, NGUOI.hoTen;
END
GO
CREATE PROCEDURE hd_themHoaDon
    @maHoaDon CHAR(6),
    @NV_NguoiID CHAR(6),
    @ngayLap DATETIME,
    @KH_NguoiID CHAR(6)
AS
BEGIN
INSERT INTO HOADON (maHD, NV_NguoiID, ngayLap, KH_NguoiID)
VALUES (@maHoaDon, @NV_NguoiID, @ngayLap, @KH_NguoiID)
END
GO
CREATE PROCEDURE hd_suaChiTietHoaDon
    @maHD CHAR(6),
    @maHang CHAR(10),
    @SoLuong INT,
    @GiamGia DECIMAL(18)
AS
BEGIN
    -- Kiểm tra xem sản phẩm có tồn tại trong chi tiết hóa đơn không
    IF NOT EXISTS (SELECT 1 FROM ChiTietHD WHERE maHD = @maHD AND maHang = @maHang)
BEGIN
        -- Nếu sản phẩm chưa tồn tại, thêm sản phẩm mới vào chi tiết hóa đơn
INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia)
VALUES (@maHD, @maHang, @SoLuong, @GiamGia);
END
ELSE
BEGIN
        -- Nếu sản phẩm đã tồn tại, cập nhật thông tin sản phẩm
UPDATE ChiTietHD
SET SoLuong = @SoLuong,
    GiamGia = @GiamGia
WHERE maHD = @maHD AND maHang = @maHang;
END
END

GO
CREATE PROCEDURE hd_layHoaDonCuoi
    AS
BEGIN
SELECT TOP 1 * FROM HOADON ORDER BY maHD DESC
END

GO
CREATE PROCEDURE hd_xoaHoaDon
    @maHoaDon CHAR(6)
AS
BEGIN
BEGIN TRANSACTION
BEGIN TRY
UPDATE SANPHAM
SET tonKho = tonKho + cthd.SoLuong
    FROM SANPHAM sp
    INNER JOIN ChiTietHD cthd ON sp.maHang = cthd.maHang
WHERE cthd.maHD = @maHoaDon
-- Xóa tất cả các chi tiết hóa đơn liên quan đến mã hóa đơn cần xóa
DELETE FROM ChiTietHD WHERE maHD = @maHoaDon
-- Cập nhật lại số lượng tồn kho của các sản phẩm có trong chi tiết hóa đơn

-- Xóa hóa đơn
DELETE FROM HoaDon WHERE maHD = @maHoaDon

    COMMIT TRANSACTION
END TRY
BEGIN CATCH
ROLLBACK TRANSACTION
    THROW
END CATCH
END

GO

--------------------- thủ tục kiểm tra đăng nhập------------------
CREATE PROCEDURE VerifyUser
    @username VARCHAR(50),
    @password VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (SELECT * FROM Login WHERE Username = @username AND Password = @password)
BEGIN
SELECT 1 AS Result;
END
ELSE
BEGIN
SELECT 0 AS Result;
END
END



GO


INSERT INTO Login(Username, Password)
VALUES
('admin','admin');
GO

INSERT INTO NGUOI (NguoiID, hoTen, gioiTinh, ngaySinh, diaChi, SDT)
VALUES
('KH001', N'Khách Lẻ', 0, '1990-01-01', N'123 Đường A, Q1, TP.HCM', '0901234567'),
('NV001', N'Vũ Huy', 1, '1995-02-02', N'456 Đường B, Q2, TP.HCM', '0907654321'),
('KH002', N'Lê Văn C', 0, '1985-03-03', N'789 Đường C, Q3, TP.HCM', '0912345678');

GO
INSERT INTO NHANVIEN (NV_NguoiID, chucVu)
VALUES
('NV001', N'Nhân viên bán hàng');
GO
INSERT INTO KHACHHANG (KH_NguoiID, diem, nguoiTao, ngayTao)
VALUES
('KH001', 100, 'NV001', '2023-04-17 12:00:00'),
('KH002', 50, 'NV001', '2023-04-17 12:30:00');

--INSERT INTO CHUCVU (chucVu, luong)
--VALUES
--(N'Nhân viên bán hàng', 5000000),
--(N'Quản lý', 10000000),
--(N'Kế toán', 8000000);


GO
INSERT INTO SANPHAM (maHang, tenHang, nhomHang, thuongHieu, giaVon, giaBan, tonKho, trongLuong, anh)
VALUES
('SP001', N'Áo khoác nam', 'Áo khoác', 'Zara', 500000, 700000, 10, 0.5, NULL),
('SP002', N'Quần jean nam', 'Quần jean', 'Levis', 350000, 450000, 20, 0.3, NULL),
('SP003', N'Áo thun coolmate', 'Áo thun', 'Coolmate', 125000, 150000, 5, 0.25, NULL),
('SP004', N'váy đầm nữ', 'Váy đầm', 'H&M', 250000, 350000, 15, 0.4, NULL),
('SP005', N'Áo sơ mi nữ', 'Áo sơ mi', 'Uniqlo', 200000, 250000, 30, 0.3, NULL),
('SP006', N'Áo khoác nữ', 'Áo khoác', 'H&M', 450000, 600000, 8, 0.4, NULL),
('SP007', N'Quần tây nam', 'Quần tây', 'Zara', 600000, 800000, 12, 0.35, NULL),
('SP008', N'Áo phông nữ', 'Áo phông', 'Adidas', 300000, 400000, 25, 0.3, NULL),
('SP009', N'Áo len nam', 'Áo len', 'Uniqlo', 400000, 550000, 18, 0.4, NULL),
('SP010', N'Chân váy nữ', 'Chân váy', 'Levis', 280000, 380000, 20, 0.35, NULL),
('SP011', N'Giày thể thao nam', 'Giày thể thao', 'Nike', 800000, 1200000, 5, 0.8, NULL),
('SP012', N'Áo khoác phao nữ', 'Áo khoác', 'The North Face', 1200000, 1500000, 6, 1.2, NULL),
('SP013', N'Quần legging nữ', 'Quần legging', 'Fabletics', 350000, 450000, 15, 0.25, NULL),
('SP014', N'Áo len nữ', 'Áo len', 'Mango', 450000, 650000, 12, 0.35, NULL),
('SP015', N'Giày búp bê nữ', 'Giày búp bê', 'H&M', 250000, 320000, 25, 0.4, NULL);
GO




INSERT INTO HOADON (maHD, NV_NguoiID, ngayLap, KH_NguoiID)
VALUES
    ('HD001', 'NV001', '2022-03-01 12:30:00', 'KH001'),
    ('HD002', 'NV001', '2022-03-02 12:50:00', 'KH002'),
    ('HD003', 'NV001', '2022-03-03 12:20:00', 'KH002'),
    ('HD004', 'NV001', '2022-03-04 13:00:00', 'KH002'),
    ('HD005', 'NV001', '2022-03-05 14:00:00', 'KH001');
GO
	INSERT INTO ChiTietHD (maHD, maHang, SoLuong, GiamGia)
VALUES
    ('HD001', 'SP003', 2, 80000),
	('HD001', 'SP002', 2, 20000),
    ('HD002', 'SP002', 1, 0),
    ('HD002', 'SP003', 3, 0),
    ('HD003', 'SP002', 2, 0),
    ('HD004', 'SP005', 4, 0),
    ('HD005', 'SP004', 1, 0),
    ('HD005', 'SP001', 3, 0);





