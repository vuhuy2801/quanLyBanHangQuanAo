package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ItemMaVachSanPhamController {
    @FXML
    private Label lbMaSanPham;
    @FXML
    private Label lbGiaSanPham;
    @FXML
    private Label lbTenSanPham;
    @FXML
    private Label lbSubMaSanPham;



    void setDataMaVachSanPham(String maSanPham, String GiaSanPham, String TenSanPham){
        lbMaSanPham.setText("*"+maSanPham.replace(" ", "")+"*");
        lbMaSanPham.setStyle("-fx-font-family: 'Libre Barcode 39'; -fx-font-size: 44;");
        lbTenSanPham.setText(TenSanPham);
        lbGiaSanPham.setText(GiaSanPham);
        lbSubMaSanPham.setText(maSanPham.replace(" ", ""));

    }

}
