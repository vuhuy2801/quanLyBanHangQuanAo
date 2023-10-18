package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.KhachHang;
import com.quanlybanhangquanao.quanlybanhangquanao.models.Nguoi;
import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class QuanLyKhachHangController {

    @FXML
    private VBox ListKhachHang;

    @FXML
    private TextField inputTimKiemKhachHang;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane subPane;

    private KhachHang khachHang;

    @FXML
    private void initialize() {
        khachHang = new KhachHang();
        inputTimKiemKhachHang.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ListKhachHang.getChildren().clear();
                loadKhachHang(khachHang.TimKiem(newValue));
            }
        });

        loadKhachHang(khachHang.DanhSach());
    }



    private void loadKhachHang(List<KhachHang> danhSachKhachHang){
        try {
            for (int i = 0; i < danhSachKhachHang.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemListKhachHang.fxml"));
                HBox item = loader.load();

                ItemListKhachHangController itemController = loader.getController();
                itemController.setQuanLyKhachHangController(this);
                itemController.setCustomerData(danhSachKhachHang.get(i).getMaKhachHang(), danhSachKhachHang.get(i).getHoTen(), String.valueOf(danhSachKhachHang.get(i).getSDT()), formatCurrency(danhSachKhachHang.get(i).getTongTien(),"đ"), String.valueOf(danhSachKhachHang.get(i).getDiemTichLuy()));

                ListKhachHang.getChildren().add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String formatCurrency(float number, String currencySymbol) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setCurrencySymbol( currencySymbol); // Đặt ký tự đại diện cho tiền tệ
        DecimalFormat decimalFormat = new DecimalFormat("#,###.## ¤", symbols); // ¤ là ký hiệu cho tiền tệ
        return decimalFormat.format(number);
    }



    public void handleIconClick(String id, String typeButton) {
        if (typeButton.equals("edit")) {
            KhachHang nguoi = new KhachHang();
            loadScreen("ChiTietKhachHang.fxml", subPane, typeButton, nguoi.ChiTiet(id));
            subPane.toFront();
        } else if (typeButton.equals("view")) {
            KhachHang nguoi = new KhachHang();
            loadScreen("ChiTietKhachHang.fxml", subPane, typeButton, nguoi.ChiTiet(id));
            subPane.toFront();
        } else if (typeButton.equals("delete")) {

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Xác nhận");
            confirmationAlert.setHeaderText("Xác nhận xóa khách hàng?");
            confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa khách hàng này?");
            ButtonType buttonTypeOK = new ButtonType("Xác nhận");
            ButtonType buttonTypeCancel = new ButtonType("Hủy");
            confirmationAlert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOK) {
//                    KhachHang nguoi = new KhachHang();
                    khachHang.Xoa(id);
                    ListKhachHang.getChildren().clear();
//                    mainPane.getChildren().clear();
                    System.out.println("Khách hàng đã được xóa.");
                    loadKhachHang(khachHang.DanhSach());
                } else {
                    System.out.println("Xóa khách hàng đã bị hủy.");
                }
            });
        } else if (typeButton.equals("print")) {
            // Xử lý in thông tin khách hàng
        }
    }

    @FXML
    void handleBtnThemClick() {
        loadScreen("ChiTietKhachHang.fxml", subPane, "themKhachHang", null);
        subPane.toFront();
    }

    public void handleChiTietKhachHangClick(String typeButton) {
        if (typeButton.equals("BtnQuayLai")) {
            subPane.getChildren().clear();
            ListKhachHang.getChildren().clear();
            loadKhachHang(khachHang.DanhSach());
            mainPane.toFront();
        }
    }

    public static LocalDate formatDate(String inputDate, String inputFormat) throws ParseException {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat);

        Date date = inputDateFormat.parse(inputDate);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    private void loadScreen(String fxmlFileName, Pane container, String TypeButton, KhachHang nguoi) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane screen = loader.load();
            ChiTietKhachHangController itemController = loader.getController();
            itemController.setQuanLyKhachHangController(this);

            if (TypeButton.equals("edit")) {
                itemController.setTextButtonThem("Lưu", "submitEdit");
                itemController.setDataKhachHang(nguoi.getMaNguoi(), nguoi.getHoTen(),  nguoi.getSDT(), formatDate(String.valueOf(nguoi.getNgaySinh()), "yyyy-MM-dd"), nguoi.getGioiTinh() ,nguoi.getDiaChi());
            } else if (TypeButton.equals("view")) {
                itemController.setTextButtonThem("Sửa khách hàng", "view");
                itemController.disableTextFieldEditing();
                itemController.setDataKhachHang(nguoi.getMaNguoi(), nguoi.getHoTen(),  nguoi.getSDT(), formatDate(String.valueOf(nguoi.getNgaySinh()), "yyyy-MM-dd"), nguoi.getGioiTinh(),nguoi.getDiaChi());

            }

            container.getChildren().clear();
            container.getChildren().add(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
