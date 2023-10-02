package com.quanlybanhangquanao.quanlybanhangquanao;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class QuanLyKhachHangController {

    @FXML
    private VBox ListKhachHang;

    @FXML
    private TextField searchField;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane subPane;

    String[] customerIDs = {"KH001", "KH002", "KH003", "KH004", "KH005"};
    String[] customerNames = {"Khách hàng 1", "Khách hàng 2", "Khách hàng 3", "Khách hàng 4", "Khách hàng 5"};
    String[] phoneNumbers = {"1234567890", "0987654321", "1357924680", "9876543210", "0123456789"};
    String[] totalPurchases = {"1,000,000 vnđ", "2,500,000 vnđ", "800,000 vnđ", "3,200,000 vnđ", "500,000 vnđ"};
    String[] loyaltyPoints = {"100", "250", "80", "320", "50"};

    @FXML
    private void initialize() {
        try {
            for (int i = 0; i < customerIDs.length; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemListKhachHang.fxml"));
                HBox item = loader.load();

                ItemListKhachHangController itemController = loader.getController();
                itemController.setQuanLyKhachHangController(this);
                itemController.setCustomerData(customerIDs[i], customerNames[i], phoneNumbers[i], totalPurchases[i], loyaltyPoints[i]);

                ListKhachHang.getChildren().add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleIconClick(String id, String typeButton) {
        if (typeButton.equals("edit")) {
            loadScreen("ChiTietKhachHang.fxml", subPane, typeButton);
            subPane.toFront();
        } else if (typeButton.equals("view")) {
            loadScreen("ChiTietKhachHang.fxml", subPane, typeButton);
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
                    System.out.println("Khách hàng đã được xóa.");
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
        loadScreen("ChiTietKhachHang.fxml", subPane, "themKhachHang");
        subPane.toFront();
    }

    public void handleChiTietKhachHangClick(String typeButton) {
        if (typeButton.equals("BtnQuayLai")) {
            subPane.getChildren().clear();
            mainPane.toFront();
        }
    }

    private void loadScreen(String fxmlFileName, Pane container, String TypeButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane screen = loader.load();
            ChiTietKhachHangController itemController = loader.getController();
            itemController.setQuanLyKhachHangController(this);

            if (TypeButton.equals("edit")) {
                itemController.setTextButtonThem("Lưu", "submitEdit");
                itemController.setDataKhachHang("KH001", "Khách hàng 1", "1234567890", "28/01/2003", "Nam","yên bái");
            } else if (TypeButton.equals("view")) {
                itemController.setTextButtonThem("Sửa khách hàng", "view");
                itemController.disableTextFieldEditing();
                itemController.setDataKhachHang("KH001", "Khách hàng 1", "1234567890", "28/01/2003", "Nam","yên bái");
            }

            container.getChildren().clear();
            container.getChildren().add(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
