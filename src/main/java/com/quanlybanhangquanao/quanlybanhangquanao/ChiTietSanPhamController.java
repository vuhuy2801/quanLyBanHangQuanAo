package com.quanlybanhangquanao.quanlybanhangquanao;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.fxml.Initializable;

public class ChiTietSanPhamController {
    private QuanLySanPhamViewController quanLySanPhamController;

    @FXML
    private Button btnQuayLai;

    @FXML
    private TextField inputMaSanPham;

    @FXML
    private TextField inputTenSanPham;

    @FXML
    private TextField inputNhomSanPham;

    @FXML
    private TextField inputThuongHieu;

    @FXML
    private TextField inputGiaVon;

    @FXML
    private TextField inputGiaBan;

    @FXML
    private TextField inputTonKho;

    @FXML
    private TextField inputTrongLuong;

    @FXML
    private Button BtnThem;

    @FXML
    private VBox imageContainer;

    @FXML
    private ImageView imageView;

    public void setQuanLySanPhamController(QuanLySanPhamViewController quanLySanPhamController) {
        this.quanLySanPhamController = quanLySanPhamController;
    }


    public void setDataSanPham(String sttText, String tenText, String giaBanText, String giaGocText, String nhomSanPham, String thuongHieu, String tonKhoText, String soLuongDaBanText) {
        inputMaSanPham.setText(sttText);
        inputTenSanPham.setText(tenText);
        inputGiaBan.setText(giaBanText);
        inputGiaVon.setText(giaGocText);
        inputTonKho.setText(tonKhoText);
        inputTrongLuong.setText(soLuongDaBanText);
        inputNhomSanPham.setText(nhomSanPham);
        inputThuongHieu.setText(thuongHieu);
    }

    public void setTextButtonThem(String text) {
        BtnThem.setText(text);
    }

    public void disableTextFieldEditing() {
        inputMaSanPham.setEditable(false);
        inputTenSanPham.setEditable(false);
        inputGiaBan.setEditable(false);
        inputGiaVon.setEditable(false);
        inputTonKho.setEditable(false);
        inputTrongLuong.setEditable(false);
        inputNhomSanPham.setEditable(false);
        inputThuongHieu.setEditable(false);

    }

    // Method to enable editing of the TextField if needed
    public void enableTextFieldEditing() {
        inputMaSanPham.setEditable(true);
        inputTenSanPham.setEditable(true);
        inputGiaBan.setEditable(true);
        inputGiaVon.setEditable(true);
        inputTonKho.setEditable(true);
        inputTrongLuong.setEditable(true);
        inputNhomSanPham.setEditable(true);
        inputThuongHieu.setEditable(true);
    }



    @FXML
    void handleBtnQuayLaiClick() {
        quanLySanPhamController.handleChiTietSanPhamClick("BtnQuayLai");
    }

    @FXML
    void handleBtnThemClick() {
        quanLySanPhamController.handleChiTietSanPhamClick("BtnAdd");
    }


}
