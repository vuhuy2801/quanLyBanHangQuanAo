package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.ChiTietDonHang;
import com.quanlybanhangquanao.quanlybanhangquanao.models.DonHang;
import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuanLyDonHangController {

    @FXML
    public VBox ListDonHang;

    @FXML
    private Button btnThem;

    @FXML
    private TextField inputTimKiemDonHang;

    @FXML
    private ItemListDonHangController itemListController;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane subPane;

    public static String dinhDangTien(BigDecimal soTien) {
        DecimalFormat MONEY_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        MONEY_FORMATTER.applyPattern("#,##0.### ₫");
        return MONEY_FORMATTER.format(soTien);
    }
    private DonHang donHang;

    @FXML
    private void initialize() {
        donHang = new DonHang();

        inputTimKiemDonHang.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ListDonHang.getChildren().clear();
                loadDonHang(donHang.TimKiem(newValue));
                System.out.println("Dữ liệu đã thay đổi thành: " + newValue);
            }
        });
        loadDonHang(donHang.DanhSach());
    }

    public static LocalDate formatDate(String inputDate, String inputFormat) throws ParseException {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat);

        Date date = inputDateFormat.parse(inputDate);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    void loadDonHang(List<DonHang> danhSachDonHang) {
        try {
            for (int i = 0; i < danhSachDonHang.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemListDonHang.fxml"));
                Pane item = loader.load();

                ItemListDonHangController itemController = loader.getController();
                itemController.setQuanLyDonHangController(this);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = formatDate(String.valueOf(danhSachDonHang.get(i).getNgayLap()), "yyyy-MM-dd")
                        .format(formatter);
                itemController.setOrderData(danhSachDonHang.get(i).getMaDonHang(),
                        formattedDate,
                        danhSachDonHang.get(i).getHoTenKhachHang(),
                        dinhDangTien(danhSachDonHang.get(i).getTongTienHang()),
                        dinhDangTien(danhSachDonHang.get(i).getGiamGia()));

                ListDonHang.getChildren().add(item);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void handleIconClick(String id, String typeButton) throws IOException {
        if (typeButton.equals("edit")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemDonHang.fxml"));
            Parent root = loader.load();
            ThemDonHangController itemController = loader.getController();
            itemController.setDataEditDonHang(id);
            Stage newStage = new Stage();
            newStage.setTitle("Sửa đơn hàng");
            newStage.setScene(new Scene(root));
            newStage.show();
        } else if (typeButton.equals("view")) {
            loadScreen("ChiTietDonHang.fxml", subPane, typeButton, id);
            subPane.toFront();
        } else if (typeButton.equals("delete")) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Xác nhận");
            confirmationAlert.setHeaderText("Xác nhận xóa đơn hàng?");
            confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa đơn hàng này?");
            ButtonType buttonTypeOK = new ButtonType("Xác nhận");
            ButtonType buttonTypeCancel = new ButtonType("Hủy");
            confirmationAlert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOK) {
                    donHang.Xoa(id);
                    ListDonHang.getChildren().clear();
                    loadDonHang(donHang.DanhSach());
                    System.out.println("Xóa Đơn hàng thành công");
                } else {
                    System.out.println("Xóa đơn hàng đã bị hủy.");
                }
            });
        } else if (typeButton.equals("print")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hoaDon.fxml"));
            Parent root = loader.load();

            DonHang Donhang = new DonHang();
            Donhang = Donhang.ChiTiet(id);
            ChiTietDonHang chiTietHD = new ChiTietDonHang();
            chiTietHD = chiTietHD.ChiTiet(id);
            List<ChiTietDonHang> listChiTietHD = chiTietHD.LayDanhSachSanPhamCuaHoaDon(id);
            hoaDonController itemController = loader.getController();
            itemController.setDataHoaDon1(Donhang.ChiTiet(id), listChiTietHD);
            Stage newStage = new Stage();
            newStage.setTitle("Preview in hóa đơn:" + Donhang.getMaDonHang() );
            newStage.setScene(new Scene(root));
            newStage.show();
        }
    }

    @FXML
    void handleBtnThemClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemDonHang.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        ThemDonHangController itemController = loader.getController();
        itemController.quanLyDonHangController = this;
        newStage.setTitle("Thêm Đơn hàng");
        newStage.setScene(new Scene(root));
        newStage.show();
        // subPane.toFront();
    }


    public void handleChiTietDonHangClick(String typeButton) {
        if (typeButton.equals("BtnQuayLai")) {
            subPane.getChildren().clear();
            mainPane.toFront();
        }
    }

    private void loadScreen(String fxmlFileName, Pane container, String TypeButton, String id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane screen = loader.load();

            ChiTietDonHangController itemController = loader.getController();
            itemController.setQuanLyDonHangController(this);
            itemController.setDataEditDonHang(id);
            container.getChildren().clear();
            container.getChildren().add(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
