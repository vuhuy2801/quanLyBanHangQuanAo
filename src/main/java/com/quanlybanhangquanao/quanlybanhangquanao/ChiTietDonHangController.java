package com.quanlybanhangquanao.quanlybanhangquanao;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChiTietDonHangController implements Initializable {

    @FXML
    private Button btnQuayLai;

    @FXML
    private Label valueMaHoaDon;

    @FXML
    private Label valueThoiGian;

    @FXML
    private Label valueKhachHang;

    @FXML
    private Label valueNguoiBan;

    @FXML
    private Button BtnSuaHoaDon;

    @FXML
    private TableView<YourDataModel> tableViewChiTietDonHang; // Thay YourDataModel bằng lớp dữ liệu của bạn

    @FXML
    private Label valueTongSoLuong;

    @FXML
    private Label valueGiamGia;

    @FXML
    private Label valueTongTienHang;
    private QuanLyDonHangController quanLyDonHangController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Khởi tạo các giá trị mẫu khi khởi động controller
        setData("HD123", "2023-10-05", "Khách hàng A", "Nhân viên B", createSampleDataModel(), "100", "10", "900");
    }

    public void setQuanLyDonHangController(QuanLyDonHangController quanLyDonHangController) {
        this.quanLyDonHangController = quanLyDonHangController;
    }

    // Phương thức để tạo dữ liệu mẫu cho TableView
    private YourDataModel createSampleDataModel() {
        YourDataModel dataModel = new YourDataModel();
        dataModel.setMaHang("MH001");
        dataModel.setTenHang("Sản phẩm 1");
        dataModel.setSoLuong(5);
        dataModel.setDonGia(200);
        dataModel.setGiamGia(20);
        dataModel.setThanhTien(900);
        return dataModel;
    }

    // Phương thức để đổ dữ liệu vào tất cả các thành phần cùng một lúc
    public void setData(String maHoaDon, String thoiGian, String khachHang, String nguoiBan, YourDataModel dataModel, String tongSoLuong, String giamGia, String tongTienHang) {
        valueMaHoaDon.setText(maHoaDon);
        valueThoiGian.setText(thoiGian);
        valueKhachHang.setText(khachHang);
        valueNguoiBan.setText(nguoiBan);

        // Đổ dữ liệu vào TableView
        tableViewChiTietDonHang.getItems().addAll(dataModel);

        valueTongSoLuong.setText(tongSoLuong);
        valueGiamGia.setText(giamGia);
        valueTongTienHang.setText(tongTienHang);
    }
    @FXML
    void handleBtnQuayLaiClick() {
        quanLyDonHangController.handleChiTietDonHangClick("BtnQuayLai");
    }
    @FXML
    void handleBtnThemClick() {

    }
}
