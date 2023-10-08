package com.quanlybanhangquanao.quanlybanhangquanao;

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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuanLyDonHangController {

    @FXML
    private VBox ListDonHang;

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

    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
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
    void loadDonHang(List<DonHang> danhSachDonHang){
        try {
            for (int i = 0 ; i < danhSachDonHang.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemListDonHang.fxml"));
                Pane item = loader.load();

                ItemListDonHangController itemController = loader.getController();
                itemController.setQuanLyDonHangController(this);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = formatDate(String.valueOf(danhSachDonHang.get(i).getNgayLap()), "yyyy-MM-dd").format(formatter);
                itemController.setOrderData(danhSachDonHang.get(i).getMaDonHang(),
                        formattedDate,
                        danhSachDonHang.get(i).getHoTenKhachHang(),
                        decimalFormat.format(danhSachDonHang.get(i).getTongTienHang()),
                        decimalFormat.format(danhSachDonHang.get(i).getGiamGia()));

                ListDonHang.getChildren().add(item);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void handleIconClick(String id, String typeButton) {
        if (typeButton.equals("edit")) {
            loadScreen("ChiTietDonHang.fxml", subPane, typeButton);
            subPane.toFront();
        } else if (typeButton.equals("view")) {
            loadScreen("ChiTietDonHang.fxml", subPane, typeButton);
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
                    System.out.println("Đơn hàng đã được xóa.");
                } else {
                    System.out.println("Xóa đơn hàng đã bị hủy.");
                }
            });
        } else if (typeButton.equals("print")) {
            // Xử lý in đơn hàng
        }
    }

    @FXML
    void handleBtnThemClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemDonHang.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Cửa sổ mới");
        newStage.setScene(new Scene(root));
        newStage.show();
//        subPane.toFront();
    }

    public void handleChiTietDonHangClick(String typeButton) {
        if (typeButton.equals("BtnQuayLai")) {
            subPane.getChildren().clear();
            mainPane.toFront();
        }
    }

    private void loadScreen(String fxmlFileName, Pane container, String TypeButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane screen = loader.load();

            ChiTietDonHangController itemController = loader.getController();
            itemController.setQuanLyDonHangController(this);

            container.getChildren().clear();
            container.getChildren().add(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
