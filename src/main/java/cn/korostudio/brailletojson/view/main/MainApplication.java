package cn.korostudio.brailletojson.view.main;

import cn.korostudio.brailletojson.util.FXUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.Style;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        FXUtil.SetMetro(scene, Style.LIGHT);

        stage.setTitle("点阵字转BE模型");
        stage.setScene(scene);

        stage.show();
    }
}
