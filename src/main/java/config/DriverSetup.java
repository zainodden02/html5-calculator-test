package config;

import functions.DateUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;

public abstract class DriverSetup {
    public final Logger logger = LogManager.getLogger(getClass());
    public static WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void close() {
        driver.close();
    }

    /**
     * Take a screenshot of the current viewport. If a file with the same name exists, it will be
     * overridden with the new one.
     *
     * @param description The description (filename) of the screenshot.
     * @return The generated filename.
     * @throws IOException
     */
    public static String takeScreenshot(String description) throws IOException {
        String filename = description + ".png";
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("target/screenshots/" + filename));
        return filename;
    }

    /**
     * Take a screenshot of the current viewport.
     *
     * @return The generated filename.
     * @throws IOException
     */
    public static String takeScreenshot() throws IOException {
       return takeScreenshot(DateUtil.getCurrentDate());
    }

    /**
     * Pause the execution.
     *
     * @param seconds The number of seconds to pause.
     * @throws InterruptedException
     */
    public static void pause(int seconds) throws InterruptedException {
        Thread.sleep(1000 * seconds);
    }
}
