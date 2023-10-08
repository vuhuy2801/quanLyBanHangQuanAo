package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.io.IOException;
import java.util.Base64;

import javafx.stage.FileChooser;

public class ChiTietSanPhamController {
    private QuanLySanPhamViewController quanLySanPhamController;

    @FXML
    private Button btnQuayLai;

    @FXML
    private TextField inputMaSanPham;

    @FXML
    private TextField inputTenSanPham;

    @FXML
    private TextField inputNhomSanPham;

    @FXML
    private TextField inputThuongHieu;

    @FXML
    private TextField inputGiaVon;

    @FXML
    private TextField inputGiaBan;

    @FXML
    private TextField inputTonKho;

    @FXML
    private TextField inputTrongLuong;

    @FXML
    private Button BtnThem;

    @FXML
    private VBox imageContainer;

    @FXML
    private ImageView viewAnh;
    @FXML
    private Button buttonChonAnh;
    SanPham sanPham;
    @FXML
    void handleBtnChonAnhClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Ảnh Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
         sanPham = new SanPham();
        java.io.File selectedFile = fileChooser.showOpenDialog(buttonChonAnh.getScene().getWindow());
        if (selectedFile != null) {
            try {
                FileInputStream fis = new FileInputStream(selectedFile);
                byte[] imageData = new byte[(int) selectedFile.length()];
                fis.read(imageData);
                fis.close(); // Đóng luồng sau khi đọc xong
                sanPham.setAnh(imageData);
                Image image = new Image(new ByteArrayInputStream(imageData));
                viewAnh.setImage(image);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setQuanLySanPhamController(QuanLySanPhamViewController quanLySanPhamController) {
        this.quanLySanPhamController = quanLySanPhamController;
    }


    public void setDataSanPham(String maSanPham, String tenText, String giaBanText, String giaGocText, String nhomSanPham, String thuongHieu, String tonKhoText, String trongLuong, byte[] dataAnh) {
        inputMaSanPham.setText(maSanPham);
        inputTenSanPham.setText(tenText);
        inputGiaBan.setText(giaBanText);
        inputGiaVon.setText(giaGocText);
        inputTonKho.setText(tonKhoText);
        inputTrongLuong.setText(trongLuong);
        inputNhomSanPham.setText(nhomSanPham);
        inputThuongHieu.setText(thuongHieu);

        if (dataAnh != null) {

            // Hiển thị ảnh nếu bạn muốn
            Image image = new Image(new ByteArrayInputStream(dataAnh));
            viewAnh.setImage(image);
        }
    }

    public String[] getDataSanPham() {
        return new String[]{inputMaSanPham.getText(),
                inputTenSanPham.getText(),
                inputGiaBan.getText(),
                inputGiaVon.getText(),
                inputTonKho.getText(),
                inputTrongLuong.getText(),
                inputNhomSanPham.getText(),
                inputThuongHieu.getText(),
        };
    }

    public void setTextButtonThem(String text, String id) {
        BtnThem.setText(text);
        BtnThem.setId(id);
    }

    public void disableTextFieldEditing() {
        inputMaSanPham.setEditable(false);
        inputTenSanPham.setEditable(false);
        inputGiaBan.setEditable(false);
        inputGiaVon.setEditable(false);
        inputTonKho.setEditable(false);
        inputTrongLuong.setEditable(false);
        inputNhomSanPham.setEditable(false);
        inputThuongHieu.setEditable(false);

    }

    // Method to enable editing of the TextField if needed
    public void enableTextFieldEditing() {
        inputMaSanPham.setEditable(true);
        inputTenSanPham.setEditable(true);
        inputGiaBan.setEditable(true);
        inputGiaVon.setEditable(true);
        inputTonKho.setEditable(true);
        inputTrongLuong.setEditable(true);
        inputNhomSanPham.setEditable(true);
        inputThuongHieu.setEditable(true);
    }



    @FXML
    void handleBtnQuayLaiClick() {
        quanLySanPhamController.handleChiTietSanPhamClick("BtnQuayLai");
    }

    @FXML
    void handleBtnThemClick() {
        try {
        String id = BtnThem.getId();
        if (id.equals("submitEdit")) {
            boolean isSuccess = handleUpdateSanPham();
            if (isSuccess) {
                setTextButtonThem("Sửa sản phẩm", "view");
                disableTextFieldEditing();
            }

        } else if (id.equals("view")) {
            enableTextFieldEditing();
            setTextButtonThem("Lưu", "submitEdit");

        } else if (id.equals("themSanPham")) {
            if (handleThemSanPham()) {
                quanLySanPhamController.handleChiTietSanPhamClick("BtnQuayLai");
            }

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void thongBao(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();

    }

    boolean handleThemSanPham() {
        String[] data = getDataSanPham();
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("")) {
                thongBao("Lỗi", "Dữ liệu không được để trống");
                return false;
            }
        }

        SanPham sanPham = new SanPham(data[0], data[1], data[6], data[7], new BigDecimal(data[2]), new BigDecimal(data[3]), Integer.parseInt(data[4]), new BigDecimal(data[5]), null);
        sanPham.setAnh(this.sanPham.getAnh());
        System.out.println(sanPham.getTenHang());
        boolean isSuccess = ThemSanPham(sanPham);
        if (isSuccess) {
            thongBao("Thành công", "Sản phẩm đã được thêm thành công");
            quanLySanPhamController.handleChiTietSanPhamClick("BtnQuayLai");
        }

        return isSuccess;
    }

    private boolean ThemSanPham(SanPham sanPham) {
        return sanPham.Them(sanPham);
    }


    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    boolean handleUpdateSanPham() {
        String[] data = getDataSanPham();
        String giaBanText = data[2];
        String giaVonText = data[3];

        giaBanText = giaBanText.replace(",", ".");
        giaVonText = giaVonText.replace(",", ".");
        BigDecimal giaBan = new BigDecimal(giaBanText);
        BigDecimal giaVon = new BigDecimal(giaVonText);

        for (int i = 0; i < data.length; i++) {
            if (data[i].isEmpty()) {

                thongBao("Lỗi", "Dữ liệu không được để trống");
                return false;
            }
        }
        System.out.println(data[2]);
        SanPham sanPham = new SanPham(data[0], data[1], data[6], data[7], giaBan, giaVon, Integer.parseInt(data[4]), new BigDecimal(data[5]), null);
        sanPham.setAnh(this.sanPham.getAnh());
        boolean isSuccess = sanPham.Sua(sanPham);

        if (isSuccess) {
            thongBao("Thành công", "Sản phẩm đã được cập nhật thành công");
            quanLySanPhamController.handleChiTietSanPhamClick("BtnQuayLai");
        } else {
            thongBao("Lỗi", "Có lỗi xảy ra khi cập nhật sản phẩm");
        }

        return isSuccess;
    }




}
