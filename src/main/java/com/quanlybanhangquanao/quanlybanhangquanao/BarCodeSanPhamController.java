package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class BarCodeSanPhamController implements Initializable {
    @FXML
    private GridPane girbListItem;
    @FXML
    private ScrollPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            inDonHang();
        });
    }


    private void inDonHang(){
        ScrollPane item = new ScrollPane(girbListItem);
        item.getTransforms().add(new Scale(0.70, 0.70));
        PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(
                Paper.A4, PageOrientation.PORTRAIT, 0, 0, 0, 0
        );
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null) {
            printerJob.getJobSettings().setJobName("in ma san pham");
            printerJob.getJobSettings().setPrintQuality(PrintQuality.HIGH);
            printerJob.getJobSettings().setPageLayout(pageLayout);
            // Print the scaled content
            boolean success = printerJob.printPage(item);
            if (success) {
                System.out.println("Printing successful.");
                printerJob.endJob();
                closeWindow();
            } else {
                System.out.println("Printing failed.");
            }
        }
    }

    private void closeWindow() {
        // Đóng cửa sổ hoặc thực hiện các hành động khác sau khi hoàn thành
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }


    public static String dinhDangTien(BigDecimal soTien) {
        DecimalFormat MONEY_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        MONEY_FORMATTER.applyPattern("#,##0.### ₫");
        return MONEY_FORMATTER.format(soTien);
    }

    // Chuyển đổi chuỗi định dạng số tiền thành BigDecimal
    public static BigDecimal chuyenChuoiTienSangBigDecimal(String chuoiTien) {
        try {
            DecimalFormat MONEY_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
            MONEY_FORMATTER.applyPattern("#,##0.### ₫");

            // Loại bỏ các ký tự không phải là số, dấu phẩy và dấu chấm
            String chuoiTienDaChuanHoa = chuoiTien
                    .replaceAll("[^0-9.,]", "")
                    .replace(",", "");

            return new BigDecimal(chuoiTienDaChuanHoa);
        } catch (NumberFormatException e) {
            // Xử lý nếu có lỗi khi chuyển đổi
            e.printStackTrace();
            return BigDecimal.ZERO; // Hoặc có thể trả về giá trị mặc định khác
        }
    }
    public void setData(String maSanPham){
        SanPham sanPham = new SanPham();
        sanPham = sanPham.ChiTiet(maSanPham);
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 3; col++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemMaVachSanPham.fxml"));
                Pane item = null; // Nạp ItemListSanPh am.fxml
                try {
                    item = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Cài đặt dữ liệu cho mục
                ItemMaVachSanPhamController itemController = loader.getController();
                itemController.setDataMaVachSanPham(sanPham.getMaHang(),("Giá: "+dinhDangTien(sanPham.getGiaBan())),sanPham.getTenHang());
                girbListItem.add(item, col, row);
            }
        }


    }

}
