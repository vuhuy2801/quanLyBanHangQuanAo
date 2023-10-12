package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.ChiTietDonHang;
import com.quanlybanhangquanao.quanlybanhangquanao.models.DonHang;
import com.quanlybanhangquanao.quanlybanhangquanao.models.KhachHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChiTietDonHangController implements Initializable {

    @FXML
    private Button btnQuayLai;

    @FXML
    private Label valueMaHoaDon;

    @FXML
    private Label valueThoiGian;

    @FXML
    private Label valueKhachHang;

    @FXML
    private Label valueNguoiBan;

    @FXML
    private Button BtnSuaHoaDon;

    @FXML
    private TableView<ThemDonHangController.ItemListHoaDon> tableViewChiTietDonHang; // Thay YourDataModel bằng lớp dữ liệu của bạn

    @FXML
    private Label valueTongSoLuong;

    @FXML
    private Label valueGiamGia;

    @FXML
    private Label valueTongTienHang;
    private QuanLyDonHangController quanLyDonHangController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Khởi tạo các giá trị mẫu khi khởi động controller
//        setData("HD123", "2023-10-05", "Khách hàng A", "Nhân viên B", createSampleDataModel(), "100", "10", "900");

    }

    public void setQuanLyDonHangController(QuanLyDonHangController quanLyDonHangController) {
        this.quanLyDonHangController = quanLyDonHangController;
    }

    // Phương thức để tạo dữ liệu mẫu cho TableView

    // Phương thức để đổ dữ liệu vào tất cả các thành phần cùng một lúc
   public void  setDataEditDonHang(String maDonHang){
        int indexKH= 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        DonHang donHang = new DonHang();
        DonHang thongtinChiTiet = donHang.ChiTiet(maDonHang);
        ChiTietDonHang chiTietDonHang = new ChiTietDonHang();

        String formattedTime = dateFormat.format(thongtinChiTiet.getNgayLap());
        valueThoiGian.setText(formattedTime);
        valueMaHoaDon.setText(thongtinChiTiet.getMaDonHang());
        valueNguoiBan.setText(thongtinChiTiet.getHoTenNhanVien());
        valueKhachHang.setText(thongtinChiTiet.getHoTenKhachHang());


        chiTietDonHang = chiTietDonHang.ChiTiet(maDonHang);
        ArrayList<ThemDonHangController.ItemListHoaDon> itemList =  new ArrayList<>() ;
        List<ChiTietDonHang> danhSachSanPham = chiTietDonHang.LayDanhSachSanPhamCuaHoaDon(maDonHang);
       handleUpdateThanhTien( chiTietDonHang.LayDanhSachSanPhamCuaHoaDon(maDonHang));
        for (int index = 0; index < danhSachSanPham.size(); index++) {
            ChiTietDonHang item = danhSachSanPham.get(index);
            itemList.add( new ThemDonHangController.ItemListHoaDon(String.valueOf(index +1), item.getMaHangSanPham(), item.getTenHang(), String.valueOf(item.getSoLuong()), String.valueOf(item.getGiaBan()), String.valueOf(item.getGiamGia()), String.valueOf(item.getThanhTien()), "Xóa"));
        }
        ObservableList<ThemDonHangController.ItemListHoaDon> sampleData = FXCollections.observableArrayList(
                itemList
        );

        tableViewChiTietDonHang.setItems(sampleData);

    }


    private void handleUpdateThanhTien(List<ChiTietDonHang> danhSachSanPham) {
        int sumSoLuong = 0;
        BigDecimal sumGiamGia = new BigDecimal(0);
        BigDecimal sumthanhTien = new BigDecimal(0);
        for (int index = 0; index < danhSachSanPham.size(); index++) {
            ChiTietDonHang item = danhSachSanPham.get(index);
            sumSoLuong += item.getSoLuong();
            sumGiamGia = sumGiamGia.add(item.getGiamGia());
            sumthanhTien = sumthanhTien.add(item.getGiaBan().multiply(BigDecimal.valueOf(item.getSoLuong())).subtract(item.getGiamGia()));
        }
        valueTongTienHang.setText(dinhDangTien(sumthanhTien));
        valueGiamGia.setText(String.valueOf(sumGiamGia));
        valueTongSoLuong.setText(String.valueOf(sumSoLuong));
    }

    public static String dinhDangTien(BigDecimal soTien) {
        DecimalFormat MONEY_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        MONEY_FORMATTER.applyPattern("#,##0.### ₫");
        return MONEY_FORMATTER.format(soTien);
    }
    @FXML
    void handleBtnQuayLaiClick() {
        quanLyDonHangController.handleChiTietDonHangClick("BtnQuayLai");
    }
    @FXML
    void handleBtnThemClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemDonHang.fxml"));
        Parent root = loader.load();
        ThemDonHangController itemController = loader.getController();
        itemController.setDataEditDonHang(valueMaHoaDon.getText());
        Stage newStage = new Stage();
        newStage.setTitle("Sửa đơn hàng");
        newStage.setScene(new Scene(root));
        newStage.show();
        quanLyDonHangController.handleChiTietDonHangClick("BtnQuayLai");
    }
}
