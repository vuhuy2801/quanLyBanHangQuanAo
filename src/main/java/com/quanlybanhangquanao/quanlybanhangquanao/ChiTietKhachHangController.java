package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.KhachHang;
import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
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


    @FXML
    private void initialize() {
        LocalDate currentTime = LocalDate.now(Clock.systemDefaultZone());
        inputNgaySinh.setValue(currentTime); // Đặt ngày cho DatePicker
        inputGioiTinh.setValue("nam"); // Đặt giá trị cho ChoiceBox
    }



    public void setDataKhachHang(String maKhachHang, String tenKhachHang, String dienThoai, LocalDate ngaySinh, boolean gioiTinh, String diaChi) {
        String textGioiTinh = gioiTinh ? "nam" : "nữ";

        inputMaKhachHang.setText(maKhachHang);
        inputMaKhachHang.setEditable(false);

        inputTenKhachHang.setText(tenKhachHang);
        inputDienThoai.setText(dienThoai);
        inputNgaySinh.setValue(ngaySinh); // Đặt ngày cho DatePicker
        inputGioiTinh.setValue(textGioiTinh); // Đặt giá trị cho ChoiceBox
        inputDiaChi.setText(diaChi);
    }

    public String[] getDataKhachHang() {
        String gioiTinhValue = inputGioiTinh.getValue();
        boolean gioiTinh = (gioiTinhValue != null && gioiTinhValue.equalsIgnoreCase("nam")) ? true : false;
        // Lấy giá trị từ ChoiceBox
        LocalDate ngaySinhValue = inputNgaySinh.getValue();
        String ngaySinh = (ngaySinhValue != null) ? ngaySinhValue.toString() : null;

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
            if (data[i] == null || data[i].equals("")) {
                thongBao("Lỗi", "Dữ liệu không được để trống");
                return false;
            }

        }

        String tenKhachHang = data[1];
        String SDT = data[2];


        String maKhachHang = data[0];
        if ( !maKhachHang.startsWith("KH") ) {
            thongBao("Lỗi", "Mã khách hàng phải bắt đầu bằng 'KH'");
            return false;
        }
        if (maKhachHang.length() > 5) {
            thongBao("Lỗi", "Mã khách hàng có độ dài tối đa là 5 ký tự");
            return false;
        }

        if (tenKhachHang.matches(".*\\d+.*")) {
            thongBao("Lỗi", "Tên khách hàng không được chứa số");
            return false;
        }

        if (!SDT.matches("^\\d{10}$")) {
            thongBao("Lỗi", "Số điện thoại không hợp lệ");
            return false;
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
            if (data[i] == null || data[i].equals("")) {
                thongBao("Lỗi", "Dữ liệu không được để trống");
                return false;
            }

        }
        String tenKhachHang = data[1];
        String SDT = data[2];


        String maKhachHang = data[0];
        if (!maKhachHang.toLowerCase().startsWith("kh")) {
            thongBao("Lỗi", "Mã sản phẩm phải bắt đầu bằng 'KH'");
            return false;
        }

        if (tenKhachHang.matches(".*\\d+.*")) {
            thongBao("Lỗi", "Tên khách hàng không được chứa số");
            return false;
        }

        if (!SDT.matches("^\\d{10}$")) {
            thongBao("Lỗi", "Số điện thoại không hợp lệ");
            return false;
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
