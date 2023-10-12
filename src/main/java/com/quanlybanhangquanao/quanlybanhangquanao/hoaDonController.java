package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.ChiTietDonHang;
import com.quanlybanhangquanao.quanlybanhangquanao.models.DonHang;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class hoaDonController  implements Initializable {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane headerPane;

    @FXML
    private Label lbNgayIn;

    @FXML
    private Label lbMaHoaDon;

    @FXML
    private Label lbNgayLap;

    @FXML
    private Label lbKhachHang;

    @FXML
    private Label lbNhanVien;

    @FXML
    private HBox headerHBox;

    @FXML
    private HBox tableHeaderHBox;

    @FXML
    private VBox vBoxListSanPham;

    @FXML
    private Pane footerPane;

    @FXML
    private Label lbTongSoLuong;

    @FXML
    private Label lbGiamGia;

    @FXML
    private Label lbTongTienHang;

    @FXML
    private Label lbCamOn;

    private void inDonHang(){
        ScrollPane item = new ScrollPane(scrollPane);
        item.getTransforms().add(new Scale(0.75, 0.75));
        PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(
                Paper.A4, PageOrientation.PORTRAIT, 0, 0, 0, 0
        );
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null) {

            printerJob.getJobSettings().setPageLayout(pageLayout);

            // Print the scaled content
            boolean success = printerJob.printPage(item);
            if (success) {
                System.out.println("Printing successful.");
                printerJob.endJob();
            } else {
                System.out.println("Printing failed.");
            }
    }
        }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            inDonHang();
        });
    }



    public void setDataHoaDon( DonHang donHang, List<ThemDonHangController .ItemListHoaDon> listChiTietHD){

        lbMaHoaDon.setText(donHang.getMaDonHang());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lbNgayLap.setText(sdf.format(donHang.getNgayLap()));
        lbKhachHang.setText(donHang.getHoTenKhachHang());
        lbNhanVien.setText(donHang.getHoTenNhanVien());
        lbNgayIn.setText(sdf.format(new Date()));
        int demSoLuong = 0;
        int sumGiamGia = 0;
        try {
            // Vòng lặp để nạp và thêm từng mục vào VBox
            for (int i = 0; i < listChiTietHD.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemListSpHoaDon.fxml"));
                HBox item = loader.load();
                // Cài đặt dữ liệu cho mục
                ItemSpHoaDonController itemController = loader.getController();
                itemController.setDataSpHoaDon(listChiTietHD.get(i).getTenHang(),listChiTietHD.get(i).getSoLuong(),listChiTietHD.get(i).getDonGia(), listChiTietHD.get(i).getGiamGia(),listChiTietHD.get(i).getThanhTien());
                vBoxListSanPham.getChildren().add(item); // Thêm vào VBox
                demSoLuong += Integer.parseInt(listChiTietHD.get(i).getSoLuong());
                sumGiamGia += Integer.parseInt(listChiTietHD.get(i).getGiamGia());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lbTongSoLuong.setText(String.valueOf(demSoLuong));
        lbGiamGia.setText(String.valueOf(sumGiamGia) +" đ");
        BigDecimal total = calculateTotal(listChiTietHD);
        lbTongTienHang.setText(dinhDangTien(total));

    }

    public void setDataHoaDon1( DonHang donHang, List<ChiTietDonHang> listChiTietHD){

        lbMaHoaDon.setText(donHang.getMaDonHang());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lbNgayLap.setText(sdf.format(donHang.getNgayLap()));
        lbKhachHang.setText(donHang.getHoTenKhachHang());
        lbNhanVien.setText(donHang.getHoTenNhanVien());
        lbNgayIn.setText(sdf.format(new Date()));
        int demSoLuong = 0;
        BigDecimal sumGiamGia = new BigDecimal(0);
        try {
            // Vòng lặp để nạp và thêm từng mục vào VBox
            for (int i = 0; i < listChiTietHD.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemListSpHoaDon.fxml"));
                HBox item = loader.load();
                // Cài đặt dữ liệu cho mục
                ItemSpHoaDonController itemController = loader.getController();
                itemController.setDataSpHoaDon(listChiTietHD.get(i).getTenHang(),String.valueOf(listChiTietHD.get(i).getSoLuong()),dinhDangTien(listChiTietHD.get(i).getGiaBan()), dinhDangTien(listChiTietHD.get(i).getGiamGia()),String.valueOf(listChiTietHD.get(i).getThanhTien()));
                vBoxListSanPham.getChildren().add(item); // Thêm vào VBox
                demSoLuong += (listChiTietHD.get(i).getSoLuong());
                sumGiamGia =sumGiamGia.add(listChiTietHD.get(i).getGiamGia());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lbTongSoLuong.setText(String.valueOf(demSoLuong));
        lbGiamGia.setText(String.valueOf(sumGiamGia) +" đ");
        BigDecimal total = calculateTotal2(listChiTietHD);
        lbTongTienHang.setText(dinhDangTien(total));

    }


    public static String dinhDangTien(BigDecimal soTien) {
        DecimalFormat MONEY_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        MONEY_FORMATTER.applyPattern("#,##0.### ₫");
        return MONEY_FORMATTER.format(soTien);
    }

    private BigDecimal calculateTotal(List<ThemDonHangController.ItemListHoaDon> listChiTietHD) {
        BigDecimal total = BigDecimal.ZERO; // Initialize total to zero
        for (ThemDonHangController.ItemListHoaDon item : listChiTietHD) {
            BigDecimal donGia = chuyenChuoiTienSangBigDecimal(item.getDonGia());
            int soLuong = Integer.parseInt(item.getSoLuong());

            total = total.add(donGia.multiply(new BigDecimal(soLuong)));
        }
        return total;
    }

    private BigDecimal calculateTotal2(List<ChiTietDonHang> listChiTietHD) {
        BigDecimal total = BigDecimal.ZERO; // Initialize total to zero
        for (ChiTietDonHang item : listChiTietHD) {
            BigDecimal donGia = item.getGiaBan();
            int soLuong = item.getSoLuong();

            total = total.add(donGia.multiply(new BigDecimal(soLuong)));
        }
        return total;
    }

    // Chuyển đổi chuỗi định dạng số tiền thành BigDecimal
    public static BigDecimal chuyenChuoiTienSangBigDecimal(String chuoiTien ) {
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



}
