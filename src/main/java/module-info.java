module com.quanlybanhangquanao.quanlybanhangquanao {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.quanlybanhangquanao.quanlybanhangquanao to javafx.fxml;
    exports com.quanlybanhangquanao.quanlybanhangquanao;

}