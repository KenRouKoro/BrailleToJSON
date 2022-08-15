package cn.korostudio.brailletojson.main;

import cn.korostudio.brailletojson.view.main.MainApplication;
import com.formdev.flatlaf.FlatLightLaf;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {


    public static void main(String[] args) {
        log.info("System Loading.");

        log.info("Init Flatlaf.");
        FlatInit();

        log.info("Load JavaFX Application.");
        Application.launch(MainApplication.class,args);


    }

    public static void FlatInit(){
        FlatLightLaf.setup();
    }
}
