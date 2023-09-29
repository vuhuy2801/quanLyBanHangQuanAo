package com.quanlybanhangquanao.quanlybanhangquanao;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import javafx.scene.chart.XYChart;

public class HomeViewController {


    @FXML
    private Button btnOverview;

    @FXML
    private Button btnQuanLySanPham;

    @FXML
    private Button btnQuanLyKhachHang;

    @FXML
    private Button btnQuanLyDonHang;

    @FXML
    private Button btnQuanLyKho;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane mainPanel;

    @FXML
    private BarChart<String, Number> barChart;


    @FXML
    private Pane pnlOverview;

    private Button activeButton = null;





    public void initialize() {
        // Tạo dữ liệu mẫu
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Triệu");
        dataSeries.getData().add(new XYChart.Data<>("Tháng 1", 10000));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 2", 12000));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 3", 9000));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 4", 15000));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 5", 2000));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 6", 3000));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 7", 5000));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 8", 12000));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 9", 113000));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 10", 112000));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 11", 11020));
        dataSeries.getData().add(new XYChart.Data<>("Tháng 12", 110300));


        // Thêm dữ liệu mẫu vào biểu đồ
        barChart.getData().add(dataSeries);
    }

        public void handleClicks(ActionEvent actionEvent) {
            if (activeButton != null) {
                activeButton.setStyle(""); // Đặt lại màu nền của nút trước đó
            }
            Button clickedButton = (Button) actionEvent.getSource();
            clickedButton.setStyle(
                    "-fx-background-color: #000000;" +
                    "-fx-text-fill : #ffffff;");
            activeButton = clickedButton;
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.toFront();
        }
        if (actionEvent.getSource() == btnQuanLyKhachHang) {
            loadScreen("QuanLyKhachHang.fxml",mainPanel);
            mainPanel.toFront();
        }
        if (actionEvent.getSource() == btnQuanLyDonHang) {
            loadScreen("QuanLyDonHang.fxml",mainPanel);
            mainPanel.toFront();
        }
        if (actionEvent.getSource() == btnQuanLyKho) {
            loadScreen("QuanLyKho.fxml",mainPanel);
            mainPanel.toFront();
        }
        if(actionEvent.getSource()== btnQuanLySanPham)
        {
            loadScreen("QuanLySanPham.fxml",mainPanel);
            mainPanel.toFront();
        }
    }


    private void loadScreen(String fxmlFileName, Pane container) {
        try {
            // Tải màn hình con từ tệp FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Pane screen = loader.load();

            // Xóa nội dung cũ và đặt nội dung mới vào container (Pane)
            container.getChildren().clear();
            container.getChildren().add(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

