package com.quanlybanhangquanao.quanlybanhangquanao;

import com.quanlybanhangquanao.quanlybanhangquanao.models.DonHang;
import com.quanlybanhangquanao.quanlybanhangquanao.models.KhachHang;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class ThemDonHangController {

    @FXML
    private TableView<ItemListHoaDon> tableViewChiTietDonHang;

    @FXML
    private TableColumn<ItemListHoaDon, String> stt;

    @FXML
    private TableColumn<ItemListHoaDon, String> maHang;
    @FXML
    private TableColumn<ItemListHoaDon, String> tenHang;
    @FXML
    private TableColumn<ItemListHoaDon, String> soLuongColumn;
    @FXML
    private TableColumn<ItemListHoaDon, String> donGia;
    @FXML
    private TableColumn<ItemListHoaDon, String> giamGia;
    @FXML
    private TableColumn<ItemListHoaDon, String> thanhTien;
    @FXML
    private TableColumn<ItemListHoaDon, String> xoaColumn;

    @FXML
    private TableView<ItemListSanPham> tableViewListSanPham;
    @FXML
    private TableColumn<ItemListSanPham, String> maSP;
    @FXML
    private TableColumn<ItemListSanPham, String> tenSP;
    @FXML
    private TableColumn<ItemListSanPham, String> tonKho;
    @FXML
    private TableColumn<ItemListSanPham, String> giaSP;
    @FXML
    private TableColumn<ItemListSanPham, String> them;


    @FXML
    private Button btnTaoDonHang;

    @FXML
    private TextField inputTimKiemSanPham;

//    @FXML
//    private ImageView searchIcon;



    @FXML
    private ChoiceBox<String> selectKhachHang;

    @FXML
    private Button btnThemKhachHang;

    @FXML
    private Label valueThoiGianLapHoaDon;


    @FXML
    private Label valueMaHoaDon;

    @FXML
    private Label valueSoLuong;

    @FXML
    private Label valueGiamGia;

    @FXML
    private Label valueTongTienHang;

    @FXML
    private void initialize() {

        inputTimKiemSanPham.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tableViewListSanPham.getItems().clear();
                ObservableList<ItemListSanPham> dataSanPhamSearch = FXCollections.observableArrayList(
                        handleTimKiemSanPham(newValue)
                );
                tableViewListSanPham.setItems(dataSanPhamSearch);
                System.out.println("Dữ liệu đã thay đổi thành: " + newValue);
                // Đây bạn có thể thực hiện xử lý dựa trên dữ liệu đã nhập
            }
        });

        ObservableList<String> options = FXCollections.observableArrayList(
                loadDataKhachHang()
        );
        selectKhachHang.setItems(options);
        selectKhachHang.getSelectionModel().select(0);

        // Đặt các cell value factory cho các cột
        maSP.setCellValueFactory(param -> param.getValue().maSPgProperty());
        tenSP.setCellValueFactory(param -> param.getValue().tenSPProperty());
        tonKho.setCellValueFactory(param -> param.getValue().tonKhoProperty());
        giaSP.setCellValueFactory(param -> param.getValue().giaSPProperty());

//        xoaColumn.setCellValueFactory(param -> param.getValue().xoaProperty());
        them.setCellFactory(param -> new TableCell<>() {
            private final Button themButton = new Button("+");

            @Override
            protected void updateItem(String ItemListSanPham, boolean empty) {
                super.updateItem(ItemListSanPham, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(themButton);
                    themButton.setOnAction(event -> handleThemSanPham(getIndex()));
                }
            }
        });

        // Đặt các cell value factory cho các cột
        stt.setCellValueFactory(param -> param.getValue().sttProperty());
        maHang.setCellValueFactory(param -> param.getValue().maHangProperty());
        tenHang.setCellValueFactory(param -> param.getValue().tenHangProperty());
        soLuongColumn.setCellValueFactory(param -> param.getValue().soLuongProperty());
        donGia.setCellValueFactory(param -> param.getValue().donGiaProperty());
        giamGia.setCellValueFactory(param -> param.getValue().giamGiaProperty());
        thanhTien.setCellValueFactory(param -> param.getValue().thanhTienProperty());
//        xoaColumn.setCellValueFactory(param -> param.getValue().xoaProperty());
        xoaColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Xóa");

            @Override
            protected void updateItem(String ItemListHoaDon, boolean empty) {
                super.updateItem(ItemListHoaDon, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> handleXoaSanPham(getIndex()));
                }
            }
        });

//        tableViewChiTietDonHang.setItems(sampleData);
        tableViewListSanPham.setItems(sampleDataSanPham);

        editCellHoaDon();
        // Cập nhật thời gian hiện tại
        updateThoiGianLapHoaDon();

        // Cập nhật các giá trị khác dựa trên dữ liệu trong bảng
        updateCacLabelTongHop();
    }


    private void handleXoaSanPham(int index) {
        // Xử lý sự kiện xóa sản phẩm ở đây
        tableViewChiTietDonHang.getItems().remove(index);
        updateCacLabelTongHop();
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


    // Cập nhật thời gian hiện tại
    private void updateThoiGianLapHoaDon() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentTime = new Date();
        String formattedTime = dateFormat.format(currentTime);
        valueThoiGianLapHoaDon.setText(formattedTime);
    }

    // Cập nhật các giá trị SoLuong, GiamGia và TongTienHang
    private void updateCacLabelTongHop() {
        BigDecimal soLuongTong = BigDecimal.ZERO;
        BigDecimal giamGiaTong = BigDecimal.ZERO;
        BigDecimal tongTienHang = BigDecimal.ZERO;

        for (ItemListHoaDon item : tableViewChiTietDonHang.getItems()) {
            BigDecimal soLuong = new BigDecimal(item.getSoLuong());
            BigDecimal giamGia = chuyenChuoiTienSangBigDecimal(item.getGiamGia());
            BigDecimal thanhTien = chuyenChuoiTienSangBigDecimal(item.getThanhTien());

            soLuongTong = soLuongTong.add(soLuong);
            giamGiaTong = giamGiaTong.add(giamGia);
            tongTienHang = tongTienHang.add(thanhTien);
        }

        // Cập nhật các Label với giá trị tính toán
        valueSoLuong.setText(soLuongTong.toString());
        valueGiamGia.setText(giamGiaTong.toString());
        valueTongTienHang.setText(dinhDangTien(tongTienHang)); // Use the formatMoney method to format the BigDecimal
    }


    @FXML
    private void handleThemSanPham(int index) {
        // Lấy dòng được chọn từ bảng tableViewListSanPham
        ItemListSanPham selectedProduct = tableViewListSanPham.getItems().get(index);


        if (selectedProduct != null) {
            // Lấy thông tin sản phẩm từ dòng đã chọn
            String maSP = selectedProduct.getMaHang();
            String tenSP = selectedProduct.getTenHang();
            String donGia = selectedProduct.getGia();
            String tonKho = selectedProduct.getTonKho();

            // Kiểm tra xem số lượng tồn kho có hợp lệ để thêm sản phẩm hay không
            if (Integer.parseInt(tonKho) == 0) {
                showAlert("Hết hàng", "Sản phẩm đã hết hàng trong kho.");
            } else {
                // Kiểm tra xem sản phẩm đã có trong danh sách `tableViewChiTietDonHang` hay chưa
                boolean productExists = false;
                for (ItemListHoaDon item : tableViewChiTietDonHang.getItems()) {
                    if (maSP.equals(item.getMaHang())) {
                        int currentQuantity = Integer.parseInt(item.getSoLuong());
                        if (currentQuantity < Integer.parseInt(tonKho)) {
                            // Tăng số lượng sản phẩm trong danh sách `tableViewChiTietDonHang`
                            item.setSoLuong(String.valueOf(currentQuantity + 1));
                            // Cập nhật thành tiền
                            handleUpdateThanhTien(item);
                        } else {
                            showAlert("Số lượng tối đa", "Sản phẩm đã thêm vào đơn hàng đạt số lượng tối đa trong kho.");
                        }
                        productExists = true;
                        break;
                    }
                }

                if (!productExists) {
                    // Thêm sản phẩm vào danh sách `tableViewChiTietDonHang`
                    tableViewChiTietDonHang.getItems().add(new ItemListHoaDon(
                            String.valueOf(tableViewChiTietDonHang.getItems().size() + 1),
                            maSP, tenSP, "1", donGia, "0", donGia, "Xóa"));
                    // Cập nhật thành tiền
                    handleUpdateThanhTien(tableViewChiTietDonHang.getItems().get(tableViewChiTietDonHang.getItems().size() - 1));
                }
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn sản phẩm để thêm vào đơn hàng.");
        }
    }


//    private void handleThemSanPham(int index) {
//        // Xử lý sự kiện xóa sản phẩm ở đây
//        System.out.println("thêm sp" + index);
////        tableViewChiTietDonHang.getItems().add(index);
//    }

    // ObservableList để theo dõi và cập nhật dữ liệu trong TableView
//    ObservableList<ItemListHoaDon> sampleData = FXCollections.observableArrayList(
//
//            new ItemListHoaDon("1", "MH001", "Máy tính", "5", "1,500,000", "0", "7,500,000", "Xóa"),
//            new ItemListHoaDon("2", "MH002", "Laptop", "3", "2,000,000", "0", "6,000,000", "Xóa"),
//            new ItemListHoaDon("3", "MH003", "Điện thoại", "2", "1,000,000", "0", "2,000,000", "Xóa")
//    );

    ObservableList<ItemListSanPham> sampleDataSanPham = FXCollections.observableArrayList(
            loadListSanPham()
    );

    ArrayList<ItemListSanPham> loadListSanPham(){
        SanPham sanpham = new SanPham();
        ArrayList<ItemListSanPham> listSanPham = new ArrayList<>();
        for (SanPham item : sanpham.DanhSach()) {
            listSanPham.add( new ItemListSanPham(item.getMaHang(), item.getTenHang(), String.valueOf(item.getTonKho()), dinhDangTien(item.getGiaBan()), "+"));
        }
        return listSanPham;
    }

    ArrayList<ItemListSanPham> handleTimKiemSanPham(String keyword){
        SanPham sanpham = new SanPham();
        ArrayList<ItemListSanPham> listSanPham = new ArrayList<>();
        for (SanPham item : sanpham.TimKiem(keyword)) {
            listSanPham.add( new ItemListSanPham(item.getMaHang(), item.getTenHang(), String.valueOf(item.getTonKho()),dinhDangTien(item.getGiaBan()) , "+"));
        }
        return listSanPham;
    }

    ArrayList<String> loadDataKhachHang(){
        KhachHang kh = new KhachHang();
        ArrayList<String> listKhachHang = new ArrayList<>();
        for (KhachHang khachHang: kh.DanhSach()){
            listKhachHang.add(khachHang.getMaKhachHang()+":"+khachHang.getHoTen());
        }
        return listKhachHang;
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void editCellHoaDon() {
        soLuongColumn.setCellFactory(TextFieldTableCell.<ItemListHoaDon>forTableColumn());
        soLuongColumn.setOnEditCommit(event -> {
            ItemListHoaDon item = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();

            // Kiểm tra giá trị mới có hợp lệ không (ví dụ: kiểm tra nếu là số dương)
            if (isValidSoLuong(newValue, item)) {
                item.setSoLuong(newValue);
                handleUpdateThanhTien(item);
            } else {
                // Nếu giá trị không hợp lệ, hiển thị thông báo
                showAlert("Giá trị không hợp lệ", "Vui lòng nhập số dương cho Số lượng. Hoặc nhỏ hơn tồn kho");
                tableViewChiTietDonHang.refresh(); // Làm mới TableView để hiển thị lại giá trị cũ
            }
        });

        giamGia.setCellFactory(TextFieldTableCell.<ItemListHoaDon>forTableColumn());
        giamGia.setOnEditCommit(event -> {
            ItemListHoaDon item = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();

            // Kiểm tra giá trị mới có hợp lệ không (ví dụ: kiểm tra nếu là số không âm)
            if (isValidGiamGia(newValue, item)) {
                item.setGiamGia(newValue);
                handleUpdateThanhTien(item);
            } else {
                // Nếu giá trị không hợp lệ, hiển thị thông báo
                showAlert("Giá trị không hợp lệ", "Vui lòng nhập số không âm cho Giảm giá. hoặc giá trị giảm lớn hơn số lượng * đơn giá");
                tableViewChiTietDonHang.refresh(); // Làm mới TableView để hiển thị lại giá trị cũ
            }
        });
    }


    private boolean isValidSoLuong(String newValue, ItemListHoaDon item) {
        int value = Integer.parseInt(newValue);
        int tonKho = 0;

        boolean isInvalid = false;
        // Kiểm tra nếu giá trị là số dương
        try {
            if(value >= 0){
                for (ItemListSanPham item1 : tableViewListSanPham.getItems()) {
                    if (item.getMaHang().equals(item1.getMaHang())) {
                        tonKho = Integer.parseInt(item1.getTonKho());
                        break;
                    }
                }
                if(tonKho >= value){
                    isInvalid = true;
                }
            }


            return isInvalid;


        } catch (NumberFormatException e) {
            return isInvalid;
        }
    }

    private boolean isValidGiamGia(String newValue, ItemListHoaDon item) {
        try {
            BigDecimal value = new BigDecimal(newValue);

            BigDecimal soLuong = new BigDecimal(item.getSoLuong());
            BigDecimal donGia = chuyenChuoiTienSangBigDecimal(item.getDonGia());
            BigDecimal thanhTien = soLuong.multiply(donGia);

            // Check if the value is non-negative and less than or equal to thanhTien
            return value.compareTo(BigDecimal.ZERO) >= 0 && value.compareTo(thanhTien) <= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    @FXML
    private void btnThanhToan_Click() {
        // edit bool value
        if (false) {
            // Xử lý sửa đơn hàng ở đây
            // Lấy danh sách sản phẩm từ bảng tableViewChiTietDonHang
            List<ItemListHoaDon> listChiTietHD = new ArrayList<>();
            for (ItemListHoaDon item : tableViewChiTietDonHang.getItems()) {
                listChiTietHD.add(item);
            }

            // Tạo đối tượng DonHang để cập nhật thông tin đơn hàng
            DonHang donHang = new DonHang();
            donHang.setMaDonHang(valueMaHoaDon.getText()); // Thiết lập mã đơn hàng
            donHang.setMaNhanVien("NV001"); // Thiết lập mã nhân viên (đổi thành mã nhân viên thích hợp)
            donHang.setNgayLap(new Date()); // Lấy thời gian hiện tại làm ngày lập
            donHang.setMaKhachHang("KH001"); // Lấy mã khách hàng từ ChoiceBox  selectKhachHang.getValue()

            // Cập nhật thông tin đơn hàng và chi tiết đơn hàng donHang.Sua(donHang, listChiTietHD);
            boolean result = true;
            if (result) {
                showAlert("Sửa hóa đơn thành công", Alert.AlertType.INFORMATION);
                // Đóng cửa sổ hoặc thực hiện các hành động khác sau khi sửa đơn hàng thành công
                closeWindow();
            } else {
                showAlert("Sửa hóa đơn thất bại", Alert.AlertType.ERROR);
            }
        } else {
            // Xử lý tạo đơn hàng mới ở đây
            String maKhachHang = (selectKhachHang.getSelectionModel().getSelectedItem()).split(":")[0];
            DonHang donHang = new DonHang();
            donHang.setMaDonHang(valueMaHoaDon.getText()); // Thiết lập mã đơn hàng
            donHang.setMaNhanVien("NV001"); // Thiết lập mã nhân viên (đổi thành mã nhân viên thích hợp)
            donHang.setNgayLap(new Date()); // Lấy thời gian hiện tại làm ngày lập
            donHang.setMaKhachHang(maKhachHang); // Lấy mã khách hàng từ ChoiceBox selectKhachHang.getValue()

            // Lấy danh sách sản phẩm từ bảng tableViewChiTietDonHang
            List<ItemListHoaDon> listChiTietHD = tableViewChiTietDonHang.getItems();

            // Thêm đơn hàng và chi tiết đơn hàng vào cơ sở dữ liệu  donHang.Them(donHang, listChiTietHD);
            printDonHangInfo(donHang, listChiTietHD);
            boolean result = true;
            if (result) {
                showAlert("Tạo hóa đơn thành công", Alert.AlertType.INFORMATION);
                // Hiển thị thông báo xác nhận in hóa đơn
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Xác Nhận");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("Bạn có muốn in hóa đơn không?");
                Optional<ButtonType> resultButtonType = confirmAlert.showAndWait();

                if (resultButtonType.isPresent() && resultButtonType.get() == ButtonType.OK) {
                    // Xử lý việc in hóa đơn ở đây (nếu cần)
                }

                // Đóng cửa sổ hoặc thực hiện các hành động khác sau khi tạo đơn hàng thành công
                closeWindow();
            } else {
                showAlert("Tạo hóa đơn thất bại", Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private BigDecimal calculateTotal(List<ItemListHoaDon> listChiTietHD) {
        BigDecimal total = BigDecimal.ZERO; // Initialize total to zero
        for (ItemListHoaDon item : listChiTietHD) {
            BigDecimal donGia = chuyenChuoiTienSangBigDecimal(item.getDonGia());
            int soLuong = Integer.parseInt(item.getSoLuong());

            total = total.add(donGia.multiply(new BigDecimal(soLuong)));
        }
        return total;
    }



    private void printDonHangInfo(DonHang donHang, List<ItemListHoaDon> listChiTietHD) {
        // In thông tin đơn hàng
        System.out.println("Thông tin đơn hàng:");
        System.out.println("Mã đơn hàng: " + donHang.getMaDonHang());
        System.out.println("Mã nhân viên: " + donHang.getMaNhanVien());
        System.out.println("Ngày lập: " + donHang.getNgayLap());
        System.out.println("Mã khách hàng: " + donHang.getMaKhachHang());

        // In danh sách chi tiết hóa đơn
        System.out.println("Danh sách chi tiết hóa đơn:");
        for (ItemListHoaDon item : listChiTietHD) {
            System.out.println("Mã sản phẩm: " + item.getMaHang());
            System.out.println("Số lượng: " + item.getSoLuong());
            System.out.println("Giảm giá: " + item.getGiamGia());
        }

        // In tổng thanh toán
        BigDecimal total = calculateTotal(listChiTietHD);
        System.out.println("Tổng thanh toán: " + dinhDangTien(total));
    }



    private void closeWindow() {
        // Đóng cửa sổ hoặc thực hiện các hành động khác sau khi hoàn thành
        Stage stage = (Stage) btnTaoDonHang.getScene().getWindow();
        stage.close();
    }


    private void handleUpdateThanhTien(ItemListHoaDon item) {
        // Parse soLuong, donGia, and giamGia as BigDecimal
        BigDecimal soLuong = new BigDecimal(item.getSoLuong());
        BigDecimal donGia = chuyenChuoiTienSangBigDecimal(item.getDonGia());
        BigDecimal giamGia = chuyenChuoiTienSangBigDecimal(item.getGiamGia());

        // Calculate the new value for thanhTien based on soLuong, donGia, and giamGia
        BigDecimal thanhTien = soLuong.multiply(donGia).subtract(giamGia);

        // Format and update the thanhTien property
        item.setThanhTien(dinhDangTien(thanhTien));

        // In ra giá trị thanhTien để kiểm tra
        System.out.println("Thành tiền mới: " + item.getThanhTien());

        // Update tổng tiền
        updateCacLabelTongHop();
        // Cập nhật TableView
        tableViewChiTietDonHang.refresh();
    }



    // Lớp mô hình dữ liệu cho TableView
    public static class ItemListHoaDon {
        private final String stt;
        private final String maHang;
        private final String tenHang;

        public String getTenHang() {
            return tenHang;
        }

        private String soLuongColumn;
        private final String donGia;
        private String giamGia;
        private String thanhTien;

        private final String xoa;


        public String getDonGia() {
            return donGia;
        }

        public String getStt() {
            return stt;
        }

        public String getMaHang() {
            return maHang;
        }

        public ItemListHoaDon(String stt, String maHang, String tenHang, String soLuong, String donGia,
                              String giamGia, String thanhTien, String xoa) {
            this.stt = stt;
            this.maHang = maHang;
            this.tenHang = tenHang;
            this.soLuongColumn = soLuong;
            this.donGia = donGia;
            this.giamGia = giamGia;
            this.thanhTien = thanhTien;
            this.xoa = xoa;
        }


        public String getThanhTien() {
            return thanhTien;
        }

        public void setThanhTien(String thanhTien) {
            this.thanhTien = thanhTien;
        }

        public String getSoLuong() {
            return soLuongColumn;
        }

        public void setSoLuong(String soLuong) {
            this.soLuongColumn = soLuong;
        }

        public String getGiamGia() {
            return giamGia;
        }

        public void setGiamGia(String giamGia) {
            this.giamGia = giamGia;
        }

        public StringProperty sttProperty() {
            return new SimpleStringProperty(stt);
        }

        public StringProperty maHangProperty() {
            return new SimpleStringProperty(maHang);
        }

        public StringProperty tenHangProperty() {
            return new SimpleStringProperty(tenHang);
        }

        public StringProperty soLuongProperty() {
            return new SimpleStringProperty(soLuongColumn);
        }

        public StringProperty donGiaProperty() {
            return new SimpleStringProperty(donGia);
        }

        public StringProperty giamGiaProperty() {
            return new SimpleStringProperty(giamGia);
        }

        public StringProperty thanhTienProperty() {
            return new SimpleStringProperty(thanhTien);
        }

        public StringProperty xoaProperty() {
            return new SimpleStringProperty(xoa);
        }
    }


    public static class ItemListSanPham {
        private final String maHang;
        private final String tenHang;
        private final String tonKho;
        private final String gia;
        private final String them;

        public String getMaHang() {
            return maHang;
        }

        public String getTenHang() {
            return tenHang;
        }

        public String getTonKho() {
            return tonKho;
        }

        public String getGia() {
            return gia;
        }

        public ItemListSanPham(String maHang, String tenHang, String tonKho, String gia, String them) {
            this.maHang = maHang;
            this.tenHang = tenHang;
            this.tonKho = tonKho;
            this.gia = gia;
            this.them = them;
        }


        public StringProperty maSPgProperty() {
            return new SimpleStringProperty(maHang);
        }

        public StringProperty tenSPProperty() {
            return new SimpleStringProperty(tenHang);
        }

        public StringProperty tonKhoProperty() {
            return new SimpleStringProperty(tonKho);
        }

        public StringProperty giaSPProperty() {
            return new SimpleStringProperty(gia);
        }

        public StringProperty themProperty() {
            return new SimpleStringProperty(them);
        }


    }


}



