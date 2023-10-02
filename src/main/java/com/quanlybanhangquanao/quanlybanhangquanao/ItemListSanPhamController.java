package com.quanlybanhangquanao.quanlybanhangquanao;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ItemListSanPhamController {

    private QuanLySanPhamViewController quanLySanPhamController;

    public void setQuanLySanPhamController(QuanLySanPhamViewController quanLySanPhamController) {
        this.quanLySanPhamController = quanLySanPhamController;
    }

    @FXML
    private HBox itemC;

    @FXML
    private Label maSanPham;

    @FXML
    private Label ten;

    @FXML
    private Label giaBan;

    @FXML
    private Label giaGoc;

    @FXML
    private Label tonKho;

    @FXML
    private Label soLuongDaBan;

    @FXML
    private ImageView penIcon;

    @FXML
    private ImageView eyeIcon;

    @FXML
    private ImageView trashIcon;

    @FXML
    private ImageView printIcon;

    public void setProductData(String sttText, String tenText, String giaBanText, String giaGocText, String tonKhoText, String soLuongDaBanText) {
        maSanPham.setText(sttText);
        ten.setText(tenText);
        giaBan.setText(giaBanText);
        giaGoc.setText(giaGocText);
        tonKho.setText(tonKhoText);
        soLuongDaBan.setText(soLuongDaBanText);
    }

    @FXML
    private void handlePenIconClick() {
        // Gọi callback và truyền id (stt) vào QuanLySanPhamController
        quanLySanPhamController.handleIconClick(maSanPham.getText(), "edit");
    }

    @FXML
    private void handleTrashIconClick() {
        // Gọi callback và truyền id (stt) vào QuanLySanPhamController
        quanLySanPhamController.handleIconClick(maSanPham.getText(), "delete");
    }

    @FXML
    private void handleEyeIconClick() {
        // Gọi callback và truyền id (stt) vào QuanLySanPhamController
        quanLySanPhamController.handleIconClick(maSanPham.getText(),"view");
    }
    @FXML
    private void handlePrintIconClick() {
        // Gọi callback và truyền id (stt) vào QuanLySanPhamController
        quanLySanPhamController.handleIconClick(maSanPham.getText(), "print");
    }

}
