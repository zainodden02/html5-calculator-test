package scripts;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TestImage {

    public static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\PersonalRepository\\xendit-qa-exam\\tessdata");
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setTessVariable("tessedit_char_blacklist", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        tesseract.setPageSegMode(3);

        String filePath = "C:\\PersonalRepository\\xendit-qa-exam\\target\\screenshots\\results.png";
        File imageFile = new File(filePath);

        BufferedImage buffImg  = ImageIO.read(imageFile);

        int imgWidth = buffImg.getWidth();
        int imgHeight = buffImg.getHeight();

        logger.info("Image Width: {}", imgWidth);
        logger.info("Image Height: {}", imgHeight);

        // Divide the current height by 5 to get the results only
        int expectedHeight = imgHeight / 5;
        logger.info("Expected Height: {}", expectedHeight);

        BufferedImage croppedImg = buffImg.getSubimage(0, 0, imgWidth, expectedHeight);
        File croppedImgFile = new File("C:\\PersonalRepository\\xendit-qa-exam\\target\\screenshots\\results1.png");
        ImageIO.write(croppedImg,"png", croppedImgFile);

        String texts = tesseract.doOCR(croppedImgFile);

        System.out.println(texts);
    }

}
