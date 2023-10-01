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

public class QuanLySanPhamViewController {

    @FXML
    private VBox ListSanPham; // Tham chiếu đến VBox trong QuanLySanPham.fxml

    @FXML
    private TextField searchField;
    @FXML
    private ItemListSanPhamController itemListController;

    @FXML
    private Pane mainPane;
    @FXML
    private Pane subPane;


    String[] productNames = {"Áo khoác nam", "Áo thun nam", "Quần jean nam", "Quần jean nam", "Quần jean nam", "Quần jean nam", "Quần jean nam", "Quần jean nam", "Quần jean nam", "Quần jean nam", "Quần jean nam", "Quần jean nam"};
    String[] prices = {"245,000 vnđ", "150,000 vnđ", "350,000 vnđ", "350,000 vnđ", "350,000 vnđ", "350,000 vnđ", "350,000 vnđ", "350,000 vnđ", "350,000 vnđ", "350,000 vnđ", "350,000 vnđ", "350,000 vnđ"};
    int[] quantities = {10, 15, 5, 5, 5, 5, 5, 8, 9, 10, 11, 12};

    @FXML


    private void initialize() {
        // Mảng chứa danh sách dữ liệu mẫu, bạn có thể thay thế bằng dữ liệu thực tế


        try {
            // Vòng lặp để nạp và thêm từng mục vào VBox
            for (int i = 0; i < productNames.length; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemListSanPham.fxml"));
                HBox item = loader.load(); // Nạp ItemListSanPham.fxml

                // Cài đặt dữ liệu cho mục
                ItemListSanPhamController itemController = loader.getController();
                itemController.setQuanLySanPhamController(this);
                itemController.setProductData(String.valueOf("SP00" + i), productNames[i], prices[i], prices[i], String.valueOf(quantities[i]), String.valueOf(i + 3));

                ListSanPham.getChildren().add(item); // Thêm vào VBox
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleIconClick(String id, String typeButton) {
        if (typeButton.equals("edit")) {
            loadScreen("ChiTietSanPham.fxml", subPane, typeButton);
            subPane.toFront();

        } else if (typeButton.equals("view")) {
            loadScreen("ChiTietSanPham.fxml", subPane, typeButton);
            subPane.toFront();
        } else if (typeButton.equals("delete")) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Xác nhận");
            confirmationAlert.setHeaderText("Xác nhận xóa tệp?");
            confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa tệp này?");
            ButtonType buttonTypeOK = new ButtonType("Xác nhận");
            ButtonType buttonTypeCancel = new ButtonType("Hủy");
            confirmationAlert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

            // Hiển thị Alert và xử lý tương tác người dùng
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOK) {
                    // Xử lý xóa tệp
                    System.out.println("Tệp đã được xóa.");
                } else {
                    // Hủy xóa tệp
                    System.out.println("Xóa tệp đã bị hủy.");
                }
            });
        } else if (typeButton.equals("print")) {

        }

    }
    @FXML
   void handleBtnThemClick(){
        loadScreen("ChiTietSanPham.fxml", subPane, "themSanPham");
        subPane.toFront();
    }

    public void handleChiTietSanPhamClick(String typeButton) {
        if (typeButton.equals("BtnQuayLai")) {
            subPane.getChildren().clear();
            mainPane.toFront();
        }
    }


    private void loadScreen(String fxmlFileName, Pane container, String TypeButton) {
        try {
            // Tải màn hình con từ tệp FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane screen = loader.load();
            ChiTietSanPhamController itemController = loader.getController();
            itemController.setQuanLySanPhamController(this);

            if (TypeButton.equals("edit")) {
                itemController.setTextButtonThem("Lưu", "submitEdit");
                itemController.setDataSanPham(String.valueOf("SP00"), productNames[0], prices[0], prices[0], "quần", "coolmate", String.valueOf(quantities[0]), String.valueOf(0 + 3));
            } else if (TypeButton.equals("view")) {
                itemController.setTextButtonThem("Sửa sản phẩm", "view");
                itemController.disableTextFieldEditing();
                itemController.setDataSanPham(String.valueOf("SP00"), productNames[0], prices[0], prices[0], "quần", "coolmate", String.valueOf(quantities[0]), String.valueOf(0 + 3));
            }
            // Xóa nội dung cũ và đặt nội dung mới vào container (Pane)
            container.getChildren().clear();
            container.getChildren().add(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
