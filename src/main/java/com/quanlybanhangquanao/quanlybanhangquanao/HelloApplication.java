package com.quanlybanhangquanao.quanlybanhangquanao;
import com.quanlybanhangquanao.quanlybanhangquanao.controlles.QuanLySanPhamController;
import com.quanlybanhangquanao.quanlybanhangquanao.models.DatabaseConnection;

import com.quanlybanhangquanao.quanlybanhangquanao.models.SanPham;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Hello!");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}