package functions;

import config.DriverSetup;
import constants.XPath;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private static final Logger logger = LogManager.getLogger();
    private static List<Integer> numbers = new ArrayList(Arrays.asList(
            KeyEvent.VK_0,
            KeyEvent.VK_1,
            KeyEvent.VK_2,
            KeyEvent.VK_3,
            KeyEvent.VK_4,
            KeyEvent.VK_5,
            KeyEvent.VK_6,
            KeyEvent.VK_7,
            KeyEvent.VK_8,
            KeyEvent.VK_9
    ));

    /**
     * Compute the given array of values.
     *
     * @param operator The operator to use for the computation.
     * @param val      The array of values to compute.
     */
    private static Double compute(String operator, String... val) throws Exception {
        List<String> operands = new ArrayList(Arrays.asList(val));
        if (!operands.isEmpty()) {
            // Loop all the given values.
            for (int i = 0; i < operands.size(); i++) {
                // Get the character length of the value (e.g., 10.21 equals 5 characters)
                int charSize = operands.get(i).length();
                for (int j = 0; j < charSize; j++) {
                    // Each character must be individually pressed on the calculator.
                    press(Character.toString(operands.get(i).charAt(j)));
                    DriverSetup.pause(1);
                }
                // Press the operator symbol only if the last value is not entered.
                // The equal sign must be clicked to get the total value.
                if ((i + 1) < operands.size()) {
                    press(operator);
                }
            }
            press("="); // Compute by clicking the equals symbol.
            return getTotal(operator, val);
        }
        return null;
    }

    /**
     * Add all the given values
     *
     * @param val The values to add.
     * @throws Exception
     */
    public static Double add(String... val) throws Exception {
        return compute("+", val);
    }

    /**
     * Subtract all the given values
     *
     * @param val The values to subtract.
     * @throws Exception
     */
    public static Double subtract(String... val) throws Exception {
        return compute("-", val);
    }

    /**
     * Multiply all the given values
     *
     * @param val The values to subtract.
     * @throws Exception
     */
    public static Double multiply(String... val) throws Exception {
        return compute("*", val);
    }

    /**
     * Divide all the given values
     *
     * @param val The values to subtract.
     * @throws Exception
     */
    public static Double divide(String... val) throws Exception {
        return compute("/", val);
    }

    /**
     * Returns the integer code of the clear action.
     */
    public static void clear() throws Exception {
        press("C");
    }

    /**
     * Capture and get the total value displayed on the display panel of the calculator.
     * @return The computed value as displayed on the calculator.
     * @throws Exception
     */
    public static double getResult() throws Exception {
        // Tesseract API to read text values from an image.
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("tessdata\\");
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setTessVariable("tessedit_char_blacklist", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

        File canvasImg = new File("target/screenshots/" + DateUtil.getCurrentDate() + "_Canvas.png");

        // Take a screenshot of the calculator canvas
        FileUtils.copyFile(DriverSetup.driver.findElement(By.xpath(XPath.CANVAS)).getScreenshotAs(OutputType.FILE),
                canvasImg);

        BufferedImage buffImg  = ImageIO.read(canvasImg);

        int imgWidth = buffImg.getWidth();
        int imgHeight = buffImg.getHeight();

        // Divide the current height by 6 to get the results only
        int expectedHeight = imgHeight / 6;

        // Crop the image to get the display panel of the calculator. The Tesseract API can better convert the image
        // to text by minimizing the data on the image through cropping.
        BufferedImage croppedImg = buffImg.getSubimage(0, 0, imgWidth, expectedHeight);
        File croppedImgFile = new File("target/screenshots/" + DateUtil.getCurrentDate() + "_TotalResult.png");
        ImageIO.write(croppedImg,"png", croppedImgFile);

        // Replace all new line breaks and spaces.
        String output = tesseract.doOCR(croppedImgFile).replaceAll("[\\r\\n]+", "").replace(" ", "");
        logger.info("Calculator Output: {}", output); // For debugging purposes
        Matcher matcher = Pattern.compile("[+-]?\\b\\d+(?:\\.\\d+)?\\b").matcher(output);

        output = matcher.find() ? matcher.group(0) : null;
        return Double.parseDouble(output);
    }

    /**
     * Presses the given command on the calculator.
     *
     * @param s Accepts 0-9, +, -, /, *, C, ., and =.
     * @return The corresponding integer code.
     */
    public static void press(String s) throws Exception {
        Robot robot = new Robot();
        Integer event = 0;

        if (isNumeric(s)) {
            event = numbers.get(Integer.parseInt(s));
        } else {
            // Common operators and options
            switch (s.toUpperCase()) {
                case "/":
                    event = KeyEvent.VK_DIVIDE;
                    break;
                case "*":
                    event = KeyEvent.VK_ASTERISK;
                    break;
                case "+":
                    event = KeyEvent.VK_ADD;
                    break;
                case "-":
                    event = KeyEvent.VK_MINUS;
                    break;
                case "=":
                    event = KeyEvent.VK_EQUALS;
                    break;
                case ".":
                    event = KeyEvent.VK_PERIOD;
                    break;
                case "C":
                    event = KeyEvent.VK_C;
                    break;
            }
        }
        // If invalid, should throw an exception.
        robot.keyPress(event);
    }

    private static Double getTotal(String operator, String... val) {
        List<String> operands = new ArrayList(Arrays.asList(val));
        Double total = Double.parseDouble(operands.get(0));
        for (int i = 1; i < operands.size(); i++) {
            switch (operator) {
                case "/":
                    total /= Double.parseDouble(operands.get(i));
                    break;
                case "*":
                    total *= Double.parseDouble(operands.get(i));
                    break;
                case "+":
                    total += Double.parseDouble(operands.get(i));
                    break;
                case "-":
                    total -= Double.parseDouble(operands.get(i));
                    break;
            }
        }
        return total;
    }

    private static boolean isNumeric(String s) {
        return s.matches("^[0-9]*$");
    }

}
