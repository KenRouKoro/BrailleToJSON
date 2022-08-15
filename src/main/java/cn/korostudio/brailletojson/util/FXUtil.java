package cn.korostudio.brailletojson.util;

import cn.hutool.core.util.URLUtil;
import cn.korostudio.brailletojson.json.Cube;
import javafx.scene.Scene;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FXUtil {

    /**
     * 设置Metro主题
     */
    public static void SetMetro(Scene scene,Style style){
        JMetro jMetro = new JMetro(style);
        jMetro.setScene(scene);
    }

    public static String  getResURLStr(String url){
        return URLUtil.getURL(url).toExternalForm();
    }

    public static boolean isPointInRect(int x, int y,double[]PointXs,double[]PointYs) {
        final Point A = new Point((int) PointXs[0],(int)PointYs[0]);
        final Point B = new Point((int) PointXs[1],(int)PointYs[1]);
        final Point C = new Point((int) PointXs[2],(int)PointYs[2]);
        final Point D = new Point((int) PointXs[3],(int)PointYs[3]);
        final int a = (B.x - A.x)*(y - A.y) - (B.y - A.y)*(x - A.x);
        final int b = (C.x - B.x)*(y - B.y) - (C.y - B.y)*(x - B.x);
        final int c = (D.x - C.x)*(y - C.y) - (D.y - C.y)*(x - C.x);
        final int d = (A.x - D.x)*(y - D.y) - (A.y - D.y)*(x - D.x);
        return (a > 0 && b > 0 && c > 0 && d > 0) || (a < 0 && b < 0 && c < 0 && d < 0);
    }
    public static Font FXFontToAWTFont(javafx.scene.text.Font fxFont){
        int size = (int) fxFont.getSize();
        String family = fxFont.getFamily();
        int type=0;
        return null;
    }

    public static List<Cube> Translation(BufferedImage image,double thickness){
        ArrayList<Cube> backList = new ArrayList<>();

        for(int x=0;x<image.getWidth();x++){
            Cube buffer = null;
            for(int y=0;y< image.getHeight();y++){
                int rgb = image.getRGB(x,y);
                if(rgb!=0){
                    if(buffer != null){
                        buffer.getOrigin()[1]--;
                        buffer.getSize()[1]++;
                    }else{
                        buffer = new Cube();
                        buffer.setOrigin(new double[]{ x-(double)image.getWidth()/2, image.getHeight() - y -(double)image.getHeight()/2, 0});
                        buffer.getSize()[2] = thickness;
                    }
                }else if(buffer != null){
                    backList.add(buffer);
                    buffer = null;
                }
            }
            if (buffer != null){
                backList.add(buffer);
            }
        }


        return backList;
    }

}
