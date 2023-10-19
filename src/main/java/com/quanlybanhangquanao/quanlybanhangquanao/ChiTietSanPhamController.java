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
        inputMaSanPham.setEditable(false);
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
        String maSanPham = data[0];
        String tenHang = data[1];
        String giaVonText = data[2];
        String giaBanText = data[3];
        String tonKhoText = data[4];
        String trongLuongText = data[5];

        if (!maSanPham.startsWith("SP")) {
            thongBao("Lỗi", "Mã sản phẩm phải bắt đầu bằng 'SP'");
            return false;
        }


        if (maSanPham.length() > 5) {
            thongBao("Lỗi", "Mã sản phẩm có độ dài tối đa là 5 ký tự");
            return false;
        }


        if (tenHang.matches(".*\\d+.*")) {
            thongBao("Lỗi", "Tên hàng không được chứa số");
            return false;
        }
        if (!giaVonText.matches("^\\d+(\\.\\d+)?$") || !giaBanText.matches("^\\d+(\\.\\d+)?$") || !tonKhoText.matches("^\\d+$") || !trongLuongText.matches("^\\d+(\\.\\d+)?$")) {
            thongBao("Lỗi", "Giá vốn, giá bán, số lượng và trọng lượng không hợp lệ");
            return false;
        }
        try {
            BigDecimal giaVon = new BigDecimal(giaVonText.replace(",", "."));
            BigDecimal giaBan = new BigDecimal(giaBanText.replace(",", "."));
            int tonKho = Integer.parseInt(tonKhoText);
            BigDecimal trongLuong = new BigDecimal(trongLuongText);

            if (giaVon.compareTo(BigDecimal.ZERO) < 0 || giaBan.compareTo(BigDecimal.ZERO) < 0 || tonKho < 0 || trongLuong.compareTo(BigDecimal.ZERO) < 0) {
                thongBao("Lỗi", "Giá vốn, giá bán, số lượng và trọng lượng không được âm");
                return false;
            }
        } catch (NumberFormatException e) {
            thongBao("Lỗi", "Dữ liệu không hợp lệ");
            return false;
        }

        if (this.sanPham != null && this.sanPham.getAnh() != null) {
            sanPham = new SanPham(data[0], data[1], data[6], data[7],  new BigDecimal(data[2]), new BigDecimal(data[3]), Integer.parseInt(data[4]), new BigDecimal(data[5]), this.sanPham.getAnh());
        } else {
            sanPham = new SanPham(data[0], data[1], data[6], data[7],  new BigDecimal(data[2]), new BigDecimal(data[3]), Integer.parseInt(data[4]), new BigDecimal(data[5]), null);
        }

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
        String tenHang = data[1];

        for (int i = 0; i < data.length; i++) {
            if (data[i].isEmpty()) {
                thongBao("Lỗi", "Dữ liệu không được để trống");
                return false;
            }
        }

        String maSanPham = data[0];
        if (!maSanPham.toLowerCase().startsWith("sp")) {
            thongBao("Lỗi", "Mã sản phẩm phải bắt đầu bằng 'SP'");
            return false;
        }

        if (tenHang.matches(".*\\d+.*")) {
            thongBao("Lỗi", "Tên hàng không được chứa số");
            return false;
        }

        try {
            int tonKho = Integer.parseInt(data[4]);
            if (tonKho <= 0) {
                thongBao("Lỗi", "Số lượng tồn kho phải là số nguyên dương");
                return false;
            }
        } catch (NumberFormatException e) {
            thongBao("Lỗi", "Số lượng tồn kho không hợp lệ");
            return false;
        }

        try {
            BigDecimal trongLuong = new BigDecimal(data[5]);
            if (trongLuong.compareTo(BigDecimal.ZERO) < 0) {
                thongBao("Lỗi", "Trọng lượng phải là số không âm");
                return false;
            }
        } catch (NumberFormatException e) {
            thongBao("Lỗi", "Trọng lượng không hợp lệ");
            return false;
        }

        try {
            giaBanText = giaBanText.replace(",", ".");
            giaVonText = giaVonText.replace(",", ".");
            BigDecimal giaBan = new BigDecimal(giaBanText);
            BigDecimal giaVon = new BigDecimal(giaVonText);

            // Kiểm tra giá bán và giá vốn phải lớn hơn 0
            if (giaBan.compareTo(BigDecimal.ZERO) <= 0 || giaVon.compareTo(BigDecimal.ZERO) <= 0) {
                thongBao("Lỗi", "Giá bán và giá vốn phải lớn hơn 0");
                return false;
            }
            System.out.println(data[2]);

            if (this.sanPham != null && this.sanPham.getAnh() != null) {
                sanPham = new SanPham(data[0], data[1], data[6], data[7], giaBan, giaVon, Integer.parseInt(data[4]), new BigDecimal(data[5]), this.sanPham.getAnh());
            } else {
                sanPham = new SanPham(data[0], data[1], data[6], data[7], giaBan, giaVon, Integer.parseInt(data[4]), new BigDecimal(data[5]), null);
            }

            boolean isSuccess = sanPham.Sua(sanPham);

            if (isSuccess) {
                thongBao("Thành công", "Sản phẩm đã được cập nhật thành công");
                quanLySanPhamController.handleChiTietSanPhamClick("BtnQuayLai");
            } else {
                thongBao("Lỗi", "Có lỗi xảy ra khi cập nhật sản phẩm");
            }

            return isSuccess;
        } catch (NumberFormatException e) {
            thongBao("Lỗi", "Giá bán và giá vốn không hợp lệ");
            return false;
        }



    }




}
