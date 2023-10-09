package com.quanlybanhangquanao.quanlybanhangquanao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class QuanLyQuanAoApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QuanLyQuanAoApplication.class.getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Hello!");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}