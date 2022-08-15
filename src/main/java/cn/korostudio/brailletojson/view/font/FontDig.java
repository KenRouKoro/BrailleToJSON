package cn.korostudio.brailletojson.view.font;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import cn.korostudio.brailletojson.util.FXUtil;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import jfxtras.styles.jmetro.Style;

public class FontDig extends Dialog<Font>{
    FontChoicer c;

    public FontDig(){
        setTitle("选择字体");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FontChoicer.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        try {
            Parent root = fxmlLoader.load();//如果使用 Parent root = FXMLLoader.load(...) 静态读取方法，无法获取到Controller的实例对象
            getDialogPane().setContent(root);
            c = fxmlLoader.getController();
        } catch (IOException ex) {
            Logger.getLogger(FontDig.class.getName()).log(Level.SEVERE, null, ex);
        }

        //FXUtil.SetMetro(getDialogPane().getScene(), Style.LIGHT);

        ButtonType loginButtonType1 = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
        ButtonType loginButtonType2 = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(loginButtonType1, loginButtonType2);

        setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType1) {
                return c.getAWTFont();
            }
            return null;
        });
    }
}