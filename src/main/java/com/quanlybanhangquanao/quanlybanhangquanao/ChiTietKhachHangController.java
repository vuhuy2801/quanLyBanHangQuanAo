package com.quanlybanhangquanao.quanlybanhangquanao;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ChiTietKhachHangController {
    private QuanLyKhachHangController quanLyKhachHangController;

    @FXML
    private Button btnQuayLai;

    @FXML
    private TextField inputMaKhachHang;

    @FXML
    private TextField inputTenKhachHang;

    @FXML
    private TextField inputDienThoai;

    @FXML
    private TextField inputNgaySinh;

    @FXML
    private TextField inputGioiTinh;

    @FXML
    private TextField inputDiaChi;

    @FXML
    private Button btnThem;

    @FXML
    private VBox imageContainer;

    @FXML
    private ImageView imageView;

    public void setQuanLyKhachHangController(QuanLyKhachHangController quanLyKhachHangController) {
        this.quanLyKhachHangController = quanLyKhachHangController;
    }

    public void setDataKhachHang(String maKhachHang, String tenKhachHang, String dienThoai, String ngaySinh, String gioiTinh, String diaChi) {
        inputMaKhachHang.setText(maKhachHang);
        inputTenKhachHang.setText(tenKhachHang);
        inputDienThoai.setText(dienThoai);
        inputNgaySinh.setText(ngaySinh);
        inputGioiTinh.setText(gioiTinh);
        inputDiaChi.setText(diaChi);
    }

    public String[] getDataKhachHang() {
        return new String[]{
                inputMaKhachHang.getText(),
                inputTenKhachHang.getText(),
                inputDienThoai.getText(),
                inputNgaySinh.getText(),
                inputGioiTinh.getText(),
                inputDiaChi.getText()
        };
    }

    public void setTextButtonThem(String text, String id) {
        btnThem.setText(text);
        btnThem.setId(id);
    }

    public void disableTextFieldEditing() {
        inputMaKhachHang.setEditable(false);
        inputTenKhachHang.setEditable(false);
        inputDienThoai.setEditable(false);
        inputNgaySinh.setEditable(false);
        inputGioiTinh.setEditable(false);
        inputDiaChi.setEditable(false);
    }

    // Method to enable editing of the TextField if needed
    public void enableTextFieldEditing() {
        inputMaKhachHang.setEditable(true);
        inputTenKhachHang.setEditable(true);
        inputDienThoai.setEditable(true);
        inputNgaySinh.setEditable(true);
        inputGioiTinh.setEditable(true);
        inputDiaChi.setEditable(true);
    }

    @FXML
    void handleBtnQuayLaiClick() {
        quanLyKhachHangController.handleChiTietKhachHangClick("BtnQuayLai");
    }

    @FXML
    void handleBtnThemClick() {
        String id = btnThem.getId();
        if (id.equals("submitEdit")) {
            if (handleUpdateKhachHang(true)) {
                setTextButtonThem("Sửa khách hàng", "view");
                disableTextFieldEditing();
                thongBao("Thành công", "Khách hàng đã được cập nhật thành công");
            }
        } else if (id.equals("view")) {
            enableTextFieldEditing();
            setTextButtonThem("Lưu", "submitEdit");
        } else if (id.equals("themKhachHang")) {
            if (handleThemKhachHang()) {
                thongBao("Thành công", "Khách hàng đã được thêm thành công");
                quanLyKhachHangController.handleChiTietKhachHangClick("BtnQuayLai");
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

    boolean handleThemKhachHang() {
        String[] data = getDataKhachHang();
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("")) {
                thongBao("Lỗi", "Dữ liệu không được để trống");
                return false;
            } else {
                System.out.println(data[i]);
            }
        }
        return true;
    }

    boolean handleUpdateKhachHang(boolean isSuccess) {
        if (isSuccess) {
            String[] data = getDataKhachHang();
            for (int i = 0; i < data.length; i++) {
                System.out.println(data[i]);
            }
            return true;
        }
        return false;
    }
}
