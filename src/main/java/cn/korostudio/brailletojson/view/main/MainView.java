package cn.korostudio.brailletojson.view.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONObject;
import cn.korostudio.brailletojson.json.BEModel;
import cn.korostudio.brailletojson.json.Bone;
import cn.korostudio.brailletojson.json.Description;
import cn.korostudio.brailletojson.util.FXUtil;
import cn.korostudio.brailletojson.view.font.FontChoicer;
import cn.korostudio.brailletojson.view.font.FontDig;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Slf4j
public class MainView {
    protected Font font =null;
    protected BufferedImage nowImage = null;
    protected FontDig dialog=new FontDig();

    @FXML
    protected ImageView viewImageView;
    @FXML
    protected ImageView showImageView;
    @FXML
    protected TextField showTextField;
    @FXML
    protected TextField idTextField;
    @FXML
    protected TextField thicknessTextField;
    @FXML
    public void clickChoiceFontButton(){
        Optional<Font> result = dialog.showAndWait();
        result.ifPresent(f -> {
            font = f;
            log.info(f.toString());
        });
        BufferedImage bufferedImage = new BufferedImage(272,200,BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics g = bufferedImage.getGraphics();

        FontMetrics fm=g.getFontMetrics(font);  //创建一个FontMetrics对象
        int textWidth=fm.stringWidth("测");
        int fontSize=fm.getHeight(); //字高=leading+ascent+descent
        int leading=fm.getLeading();
        int ascent=fm.getAscent();
        int descent=fm.getDescent();//bottom->baseline的高度

        Graphics2D g2d= (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString("测试文本abc123",0,leading+ascent);
        viewImageView.setImage(SwingFXUtils.toFXImage(bufferedImage,null));
    }
    @FXML
    public void clickShowButton(){
        if (font==null){
            return;
        }
        String showStr = showTextField.getText();
        char[] chars_showStr = showStr.toCharArray();
        int b = 0,q = 0;
        for (char c : chars_showStr) {
            String temp = String.valueOf(c);
            // 判断是全角字符
            if (temp.matches("[^\\x00-\\xff]")) {
                q++;
            }
            // 判断是半角字符
            else {
                b++;
            }
        }
        int rawW = q*font.getSize()+b*font.getSize()/2+ font.getSize();
        int rawH = font.getSize();


        BufferedImage bufferedImage = new BufferedImage(rawW,rawH,BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics g = bufferedImage.getGraphics();

        FontMetrics fm=g.getFontMetrics(font);  //创建一个FontMetrics对象
        int textWidth=fm.stringWidth(showStr); //字宽
        int fontSize=fm.getHeight(); //字高=leading+ascent+descent
        int leading=fm.getLeading();
        int ascent=fm.getAscent();
        int descent=fm.getDescent();//bottom->baseline的高度

         bufferedImage = new BufferedImage(textWidth,fontSize,BufferedImage.TYPE_INT_ARGB_PRE);
         g = bufferedImage.getGraphics();


        Graphics2D g2d= (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(showStr,0,leading+ascent);

        BufferedImage bigBufferedImage = new BufferedImage(textWidth*10,fontSize*10,BufferedImage.TYPE_INT_ARGB_PRE);
        g = bigBufferedImage.getGraphics();
        g2d= (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.drawImage(bufferedImage,0,0,textWidth*10,fontSize*10,null);

        nowImage = bufferedImage;

        g2d.setColor(Color.black);
        g2d.drawLine(10,10,15,10);
        g2d.drawLine(10,110,15,110);
        g2d.drawLine(10,110,10,10);
        g2d.drawString("10px",20,65);

        for(int i=1;i<=9;i++){
            g2d.drawLine(10,10+i*10,13,10+i*10);
        }


        showImageView.setFitWidth(rawW*10);
        showImageView.setFitHeight(fontSize*10);
        showImageView.setImage(SwingFXUtils.toFXImage(bigBufferedImage,null));

    }

    @FXML
    public void clickOutput(){

        if (nowImage == null){
            return;
        }

        FileWriter writer = new FileWriter(FileUtil.touch("D:/test.json"));

        JSONObject model = new JSONObject();
        model.putOnce("format_version","1.12.0");

        BEModel model1 = new BEModel();
        Description description = new Description();
        description.setIdentifier(idTextField.getText());

        model1.setDescription(description);

        Bone main = new Bone();
        main.setName("main");

        double thickness = 1;

        try{
            thickness = Double.parseDouble(thicknessTextField.getText());
        }catch (Exception ignored){}

        main.setCubes(FXUtil.Translation(nowImage,thickness));



        model1.getBones().add(main);
        List<BEModel> models = new ArrayList<>();
        models.add(model1);

        model.putOnce("minecraft:geometry",models);

        writer.write(model.toString());
        log.info("Output Fin.");
    }

}
