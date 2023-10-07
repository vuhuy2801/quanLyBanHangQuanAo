package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.KhachHang;
import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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
    private DatePicker inputNgaySinh;

    @FXML
    private ChoiceBox<String> inputGioiTinh; // Sử dụng ChoiceBox<String>

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


    private void initialize() {

    }



    public void setDataKhachHang(String maKhachHang, String tenKhachHang, String dienThoai, LocalDate ngaySinh, boolean gioiTinh, String diaChi) {
        String textGioiTinh = gioiTinh ? "nam" : "nữ";

        inputMaKhachHang.setText(maKhachHang);
        inputTenKhachHang.setText(tenKhachHang);
        inputDienThoai.setText(dienThoai);
        inputNgaySinh.setValue(ngaySinh); // Đặt ngày cho DatePicker
        inputGioiTinh.setValue(textGioiTinh); // Đặt giá trị cho ChoiceBox
        inputDiaChi.setText(diaChi);
    }

    public String[] getDataKhachHang() {
        boolean gioiTinh = (inputGioiTinh.getValue()).equalsIgnoreCase("nam") ? true : false; // Lấy giá trị từ ChoiceBox
        String ngaySinh = inputNgaySinh.getValue().toString(); // Lấy giá trị từ DatePicker
        return new String[]{
                inputMaKhachHang.getText(),
                inputTenKhachHang.getText(),
                inputDienThoai.getText(),
                ngaySinh,
                String.valueOf(gioiTinh),
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
        inputGioiTinh.setDisable(false);
        inputDiaChi.setEditable(false);
    }

    // Method to enable editing of the TextField if needed
    public void enableTextFieldEditing() {
        inputMaKhachHang.setEditable(true);
        inputTenKhachHang.setEditable(true);
        inputDienThoai.setEditable(true);
        inputNgaySinh.setEditable(true);
        inputGioiTinh.setDisable(true);
        inputDiaChi.setEditable(true);
    }

    @FXML
    void handleBtnQuayLaiClick() {
        quanLyKhachHangController.handleChiTietKhachHangClick("BtnQuayLai");
    }

    @FXML
    void handleBtnThemClick() throws ParseException {
        String id = btnThem.getId();
        if (id.equals("submitEdit")) {
            boolean isSuccess = handleUpdateKhachHang();
            if (isSuccess) {
                setTextButtonThem("Sửa khách hàng", "view");
                disableTextFieldEditing();
                quanLyKhachHangController.handleChiTietKhachHangClick("BtnQuayLai");
            }

        } else if (id.equals("view")) {
            enableTextFieldEditing();
            setTextButtonThem("Lưu", "submitEdit");
        } else if (id.equals("themKhachHang")) {
            if (handleThemKhachHang()) {

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
    public static Date convertStringToDate(String dateString, String dateFormat) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.parse(dateString);
    }


    boolean handleThemKhachHang() throws ParseException {
        String[] data = getDataKhachHang();
        for (int i = 0; i < data.length; i++) {
            if (data[i].isEmpty()) {
                thongBao("Lỗi", "Dữ liệu không được để trống");
                return false;
            } else {
//                System.out.println(data[i]);
            }
        }
            KhachHang khachHang = new KhachHang(data[0], data[1], (data[4]).equals("true") ?true :false, convertStringToDate(data[3], "yyyy-MM-dd"), data[5], data[2]);
            boolean isSuccess = ThemKhachHang(khachHang);
        if (isSuccess) {
            thongBao("Thành công", "Khách hàng đã được thêm thành công");
        } else {
            thongBao("Lỗi", "Có lỗi xảy ra khi thêm khách hàng");
        }
        return isSuccess;
    }
    private boolean ThemKhachHang(KhachHang khachHang) {
        return khachHang.Them(khachHang);
    }

    boolean handleUpdateKhachHang() throws ParseException {
        String[] data = getDataKhachHang();
        for (int i = 0; i < data.length; i++) {
            if (data[i].isEmpty()) {

                thongBao("Lỗi", "Dữ liệu không được để trống");
                return false;
            }
        }
        KhachHang khachHang = new KhachHang(data[0], data[1], (data[4]).equals("true") ?true :false, convertStringToDate(data[3], "yyyy-MM-dd"), data[5], data[2]);

        boolean isSuccess = khachHang.Sua(khachHang);

        if (isSuccess) {
            thongBao("Thành công", "khách hàng đã được cập nhật thành công");

        } else {
            thongBao("Lỗi", "Có lỗi xảy ra khi cập nhật khách hàng");
        }

        return isSuccess;
    }
}
