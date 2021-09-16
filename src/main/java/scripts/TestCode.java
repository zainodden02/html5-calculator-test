package scripts;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import net.sourceforge.tess4j.util.ImageIOHelper;
import org.apache.xmlgraphics.image.writer.imageio.ImageIOImageWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class TestCode {

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\PersonalRepository\\xendit-qa-exam\\target\\screenshots\\results.png";
        File file = new File(filePath);
        ITesseract tesseract = new Tesseract();


        tesseract.setDatapath("C:\\PersonalRepository\\xendit-qa-exam\\tessdata");
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setTessVariable("tessedit_char_blacklist", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        tesseract.setPageSegMode(3);
        String texts = tesseract.doOCR(file);
        System.out.println(texts);
    }
}
