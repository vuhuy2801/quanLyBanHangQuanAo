package com.quanlybanhangquanao.quanlybanhangquanao;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemSpHoaDonController implements Initializable {

    @FXML
    private Label lbTenSanPham;

    @FXML
    private Label lbSoLuong;

    @FXML
    private Label lbDonGia;

    @FXML
    private Label lbGiamGia;

    @FXML
    private Label lbThanhTien;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Thực hiện các thao tác khởi tạo ở đây
    }

    public void setDataSpHoaDon(String tenSanPham, String soLuong, String donGia, String giamGia, String thanhTien) {
        lbTenSanPham.setText(tenSanPham);
        lbSoLuong.setText(soLuong);
        lbDonGia.setText(donGia);
        lbGiamGia.setText(giamGia);
        lbThanhTien.setText(thanhTien);
    }
}
