package com.quanlybanhangquanao.quanlybanhangquanao;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ItemListDonHangController {

    private QuanLyDonHangController quanLyDonHangController;

    public void setQuanLyDonHangController(QuanLyDonHangController quanLyDonHangController) {
        this.quanLyDonHangController = quanLyDonHangController;
    }

    @FXML
    private HBox itemC;


    @FXML
    private Label maDonHang;

    @FXML
    private Label thoiGian;

    @FXML
    private Label khachHang;

    @FXML
    private Label tongTien;

    @FXML
    private Label giamGia;

    @FXML
    private ImageView penIcon;

    @FXML
    private ImageView eyeIcon;

    @FXML
    private ImageView trashIcon;

    @FXML
    private ImageView printIcon;

    public void setOrderData( String maDonHangText, String thoiGianText, String khachHangText, String tongTienText, String giamGiaText) {
        maDonHang.setText(maDonHangText);
        thoiGian.setText(thoiGianText);
        khachHang.setText(khachHangText);
        tongTien.setText(tongTienText);
        giamGia.setText(giamGiaText);
    }

    @FXML
    private void handlePenIconClick() throws IOException {
        // Gọi callback và truyền id (maDonHang) vào QuanLyDonHangController
        quanLyDonHangController.handleIconClick(maDonHang.getText(), "edit");
    }

    @FXML
    private void handleTrashIconClick() throws IOException {
        // Gọi callback và truyền id (maDonHang) vào QuanLyDonHangController
        quanLyDonHangController.handleIconClick(maDonHang.getText(), "delete");
    }

    @FXML
    private void handleEyeIconClick() throws IOException {
        // Gọi callback và truyền id (maDonHang) vào QuanLyDonHangController
        quanLyDonHangController.handleIconClick(maDonHang.getText(),"view");
    }

    @FXML
    private void handlePrintIconClick() throws IOException {
        // Gọi callback và truyền id (maDonHang) vào QuanLyDonHangController
        quanLyDonHangController.handleIconClick(maDonHang.getText(), "print");
    }

}
