package com.quanlybanhangquanao.quanlybanhangquanao;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ItemListKhachHangController {

    private QuanLyKhachHangController quanLyKhachHangController;

    public void setQuanLyKhachHangController(QuanLyKhachHangController quanLyKhachHangController) {
        this.quanLyKhachHangController = quanLyKhachHangController;
    }

    @FXML
    private HBox itemC;


    @FXML
    private Label maKhachHang;

    @FXML
    private Label tenKhachHang;

    @FXML
    private Label dienThoai;

    @FXML
    private Label tongTienDaMua;

    @FXML
    private Label diemTichLuy;

    @FXML
    private ImageView penIcon;

    @FXML
    private ImageView eyeIcon;

    @FXML
    private ImageView trashIcon;

    @FXML
    private ImageView printIcon;

    public void setCustomerData(String maKhachHangText, String tenKhachHangText, String dienThoaiText, String tongTienDaMuaText, String diemTichLuyText) {
        maKhachHang.setText(maKhachHangText);
        tenKhachHang.setText(tenKhachHangText);
        dienThoai.setText(dienThoaiText);
        tongTienDaMua.setText(tongTienDaMuaText);
        diemTichLuy.setText(diemTichLuyText);
    }

    @FXML
    private void handlePenIconClick() {
        // Gọi callback và truyền id (maKhachHang) vào QuanLyKhachHangController
        quanLyKhachHangController.handleIconClick(maKhachHang.getText(), "edit");
    }

    @FXML
    private void handleTrashIconClick() {
        // Gọi callback và truyền id (maKhachHang) vào QuanLyKhachHangController
        quanLyKhachHangController.handleIconClick(maKhachHang.getText(), "delete");
    }

    @FXML
    private void handleEyeIconClick() {
        // Gọi callback và truyền id (maKhachHang) vào QuanLyKhachHangController
        quanLyKhachHangController.handleIconClick(maKhachHang.getText(),"view");
    }

    @FXML
    private void handlePrintIconClick() {
        // Gọi callback và truyền id (maKhachHang) vào QuanLyKhachHangController
        quanLyKhachHangController.handleIconClick(maKhachHang.getText(), "print");
    }
}
