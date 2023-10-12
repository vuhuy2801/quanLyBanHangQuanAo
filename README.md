# Quản Lý Bán Hàng Shop Quần Áo Coolmate

## Mô Tả Dự Án

Dự án **Quản Lý Bán Hàng Shop Quần Áo Coolmate** giúp sinh viên nắm vững những kiến thức cơ bản về lập trình hướng đối tượng (OOP) trong ngôn ngữ Java. Thông qua đề tài này, sinh viên có thể hiểu rõ hơn về cách áp dụng OOP trong việc thiết kế và phát triển các ứng dụng Java, đặc biệt là trong việc sử dụng lớp (class) và đối tượng (object) để tạo ra các chức năng và tính năng trong ứng dụng.
## Yêu cầu về môi trường

Trước khi bắt đầu, đảm bảo bạn đã có các yêu cầu sau:

- Java 8
- JDK 21
- Microsoft SQL Server
## Video demo sản phẩm

[Tên thay thế]: Vuhuy.xyz


## Cách Cài Đặt

1. Clone dự án về máy tính của bạn.

2. Chạy file `mockup.sql` để tạo cơ sở dữ liệu.

3. Sửa file cấu hình để kết nối đến cơ sở dữ liệu. Mở file `DatabaseConnection.java` tại đường dẫn sau quanLyBanHangQuanAo\src\main\java\com\quanlybanhangquanao\quanlybanhangquanao\models\DatabaseConnection.java và thay đổi thông tin kết nối database:

    ```java
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1434;databaseName=QUAN_LY_BAN_HANG_QUAN_AO;Encrypt=false";
    private static final String USERNAME = "Demo";
    private static final String PASSWORD = "demo@123";
    ```
4. chạy `QuanLyQuanAoApplication.java` tại src\main\java\com\quanlybanhangquanao\quanlybanhangquanao


## Người Đóng Góp

Cảm ơn những người đã đóng góp vào dự án:

- Vũ Huy (Trưởng Nhóm)
- Tiến Anh (Thành Viên)
- Quang vũ (Thành viên)
