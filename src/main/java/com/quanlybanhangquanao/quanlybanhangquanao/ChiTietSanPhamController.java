package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
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


    public void setDataSanPham(String maSanPham, String tenText, String giaBanText, String giaGocText, String nhomSanPham, String thuongHieu, String tonKhoText, String trongLuong) {
        inputMaSanPham.setText(maSanPham);
        inputTenSanPham.setText(tenText);
        inputGiaBan.setText(giaBanText);
        inputGiaVon.setText(giaGocText);
        inputTonKho.setText(tonKhoText);
        inputTrongLuong.setText(trongLuong);
        inputNhomSanPham.setText(nhomSanPham);
        inputThuongHieu.setText(thuongHieu);
    }

    public String[] getDataSanPham() {
        return new String[]{inputMaSanPham.getText(),
                inputTenSanPham.getText(),
                inputGiaBan.getText(),
                inputGiaVon.getText(),
                inputTonKho.getText(),
                inputTrongLuong.getText(),
                inputNhomSanPham.getText(),
                inputThuongHieu.getText(),
        };
    }

    public void setTextButtonThem(String text, String id) {
        BtnThem.setText(text);
        BtnThem.setId(id);
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
        String id = BtnThem.getId();
        if (id.equals("submitEdit")) {
            if (handleUpdateSanPham(true)) {

                // bắn đến sửa sanpham.sua
                setTextButtonThem("Sửa sản phẩm", "view");
                disableTextFieldEditing();
                thongBao("Thành công", "Sản phẩm đã được cập nhật thành công");
            }

        } else if (id.equals("view")) {
            enableTextFieldEditing();
            setTextButtonThem("Lưu", "submitEdit");

        } else if (id.equals("themSanPham")) {
            if (handleThemSanPham()) {

            //bắn đến thêm
                thongBao("Thành công", "Sản phẩm đã được thêm thành công");
                quanLySanPhamController.handleChiTietSanPhamClick("BtnQuayLai");
            }

        }
    }

    void thongBao(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();

    }

    boolean handleThemSanPham() {
        String[] data = getDataSanPham();
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("")) {
                thongBao("lỗi", "dữ liệu không đc để trống");
                return false;
            } else {
                System.out.println(data[i]);
            }
        }
        return true;
    }



    boolean handleUpdateSanPham(boolean isSuccess) {
        if (isSuccess) {
            String[] data = getDataSanPham();
            for (int i = 0; i < data.length; i++) {
                System.out.println(data[i]);
            }
            return true;
        }
        return false;
    }


}
