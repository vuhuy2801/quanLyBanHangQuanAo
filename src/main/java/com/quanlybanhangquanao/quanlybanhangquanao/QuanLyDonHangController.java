package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.DonHang;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class QuanLyDonHangController {

    @FXML
    private VBox ListDonHang;

    @FXML
    private Button btnThem;

    @FXML
    private TextField searchField;

    @FXML
    private ItemListDonHangController itemListController;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane subPane;

    String[] orderCodes = {"DH001", "DH002", "DH003", "DH004", "DH005"};
    String[] orderTimes = {"2023-01-15 10:30", "2023-01-16 14:45", "2023-01-17 09:15", "2023-01-18 16:20", "2023-01-19 11:55"};
    String[] customers = {"Khách hàng 1", "Khách hàng 2", "Khách hàng 3", "Khách hàng 4", "Khách hàng 5"};
    String[] totalPrices = {"500,000 vnđ", "750,000 vnđ", "320,000 vnđ", "980,000 vnđ", "250,000 vnđ"};
    String[] discounts = {"50,000 vnđ", "0 vnđ", "30,000 vnđ", "100,000 vnđ", "0 vnđ"};

    @FXML
    private void initialize() {
        try {
            for (int i = 0 ; i < orderCodes.length; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemListDonHang.fxml"));
                Pane item = loader.load();

                ItemListDonHangController itemController = loader.getController();
                itemController.setQuanLyDonHangController(this);
                itemController.setOrderData(orderCodes[i], orderTimes[i], customers[i], totalPrices[i], discounts[i]);

                ListDonHang.getChildren().add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleIconClick(String id, String typeButton) {
        if (typeButton.equals("edit")) {
//            loadScreen("ChiTietDonHang.fxml", subPane, typeButton);
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
