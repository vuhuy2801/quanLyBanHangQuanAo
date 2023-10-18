package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;

public class QuanLySanPhamViewController {

    @FXML
    private VBox ListSanPham; // Tham chiếu đến VBox trong QuanLySanPham.fxml

    @FXML
    private TextField inputTimKiemSanPham;
    @FXML
    private ItemListSanPhamController itemListController;

    @FXML
    private Pane mainPane;
    @FXML
    private Pane subPane;

    private SanPham sanPham;

    public static String dinhDangTien(BigDecimal soTien) {
        DecimalFormat MONEY_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        MONEY_FORMATTER.applyPattern("#,##0.### ₫");
        return MONEY_FORMATTER.format(soTien);
    }
    @FXML
    private void initialize() {
        sanPham = new SanPham();
        inputTimKiemSanPham.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ListSanPham.getChildren().clear();
                loadSanPham(sanPham.TimKiem(newValue));
                System.out.println("Dữ liệu đã thay đổi thành: " + newValue);
                // Đây bạn có thể thực hiện xử lý dựa trên dữ liệu đã nhập
            }
        });

        loadSanPham(sanPham.DanhSach());
    }



    void loadSanPham(List<SanPham> danhSachSanPham){
        try {
            // Vòng lặp để nạp và thêm từng mục vào VBox
            for (int i = 0; i < danhSachSanPham.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemListSanPham.fxml"));
                HBox item = loader.load(); // Nạp ItemListSanPh am.fxml
                // Cài đặt dữ liệu cho mục
                ItemListSanPhamController itemController = loader.getController();
                itemController.setQuanLySanPhamController(this);
                itemController.setProductData(danhSachSanPham.get(i).getMaHang(), danhSachSanPham.get(i).getTenHang(), dinhDangTien(danhSachSanPham.get(i).getGiaBan()), dinhDangTien(danhSachSanPham.get(i).getGiaVon()),String.valueOf(danhSachSanPham.get(i).getTonKho()), String.valueOf(danhSachSanPham.get(i).getSoLuongDaBan()));

                ListSanPham.getChildren().add(item); // Thêm vào VBox
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleIconClick(String id, String typeButton) {
        if (typeButton.equals("edit")) {
            SanPham sanPham = new SanPham();
            loadScreen("ChiTietSanPham.fxml", subPane, typeButton, sanPham.ChiTiet(id));
            subPane.toFront();

        } else if (typeButton.equals("view")) {
            SanPham sanPham = new SanPham();
            loadScreen("ChiTietSanPham.fxml", subPane, typeButton, sanPham.ChiTiet(id));
            subPane.toFront();

        } else if (typeButton.equals("delete")) {
//            SanPham sanPham = new SanPham();
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Xác nhận");
            confirmationAlert.setHeaderText("Xác nhận xóa tệp?");
            confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa tệp này?");
            ButtonType buttonTypeOK = new ButtonType("Xác nhận");
            ButtonType buttonTypeCancel = new ButtonType("Hủy");
            confirmationAlert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOK) {
                    sanPham.Xoa(id);
                    ListSanPham.getChildren().clear();
                    loadSanPham(sanPham.DanhSach());
                    System.out.println("Xóa tệp thành công");

                } else {
                    System.out.println("Xóa tệp đã bị hủy.");
                }
            });
        } else if (typeButton.equals("print")) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("BarCodeSanPham.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BarCodeSanPhamController itemController = loader.getController();
            itemController.setData(id);
//            itemController.setDataHoaDon(donHang, listChiTietHD);
            Stage newStage = new Stage();
            newStage.setTitle("Preview in mã vạch sản phẩm:");
            newStage.setScene(new Scene(root));
            newStage.show();

        }

    }
    @FXML
   void handleBtnThemClick(){
        loadScreen("ChiTietSanPham.fxml", subPane, "themSanPham",null);
        subPane.toFront();
    }

    public void handleChiTietSanPhamClick(String typeButton) {
        if (typeButton.equals("BtnQuayLai")) {

            subPane.getChildren().clear();
            ListSanPham.getChildren().clear();
            loadSanPham(sanPham.DanhSach());
            mainPane.toFront();
        }
    }


    private void loadScreen(String fxmlFileName, Pane container, String TypeButton, SanPham sanpham) {

        try {
            // Tải màn hình con từ tệp FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane screen = loader.load();
            ChiTietSanPhamController itemController = loader.getController();
            itemController.setQuanLySanPhamController(this);

            if (TypeButton.equals("edit")) {
                itemController.setTextButtonThem("Lưu", "submitEdit");

                itemController.setDataSanPham(sanpham.getMaHang(), sanpham.getTenHang(), dinhDangTien(sanpham.getGiaBan()),  dinhDangTien(sanpham.getGiaVon()),  sanpham.getNhomHang(), sanpham.getThuongHieu(), String.valueOf(sanpham.getTonKho()), String.valueOf(sanpham.getTrongLuong()), sanpham.getAnh());

            } else if (TypeButton.equals("view")) {
                itemController.setTextButtonThem("Sửa sản phẩm", "view");
                itemController.disableTextFieldEditing();
                //set data
                itemController.setDataSanPham(sanpham.getMaHang(), sanpham.getTenHang(), dinhDangTien(sanpham.getGiaBan()),  dinhDangTien(sanpham.getGiaVon()),  sanpham.getNhomHang(), sanpham.getThuongHieu(), String.valueOf(sanpham.getTonKho()), String.valueOf(sanpham.getTrongLuong()), sanpham.getAnh());
            }
            container.getChildren().clear();
            container.getChildren().add(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
