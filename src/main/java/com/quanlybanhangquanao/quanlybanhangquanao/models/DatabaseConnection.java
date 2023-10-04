package com.quanlybanhangquanao.quanlybanhangquanao.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1434;databaseName=QUAN_LY_BAN_HANG_QUAN_AO;Encrypt=false";
    private static final String USERNAME = "TienAnh";
    private static final String PASSWORD = "Tienanh@123";


    // Phương thức này trả về một kết nối đến cơ sở dữ liệu SQL Server
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return connection;
    }

    // Phương thức này đóng kết nối đã được tạo
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
