package scripts;

import config.DriverSetup;
import functions.Calculator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.testng.annotations.Test;

import java.io.File;

public class TC001_CheckTheSumOfTwoWholeNumbers extends DriverSetup {

    @Test
    public void TC001() throws Exception {
        driver.get("https://www.online-calculator.com/full-screen-calculator/");
        Thread.sleep(2000);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='fullframe']")));
        logger.info("Switched frame");
        double total = Calculator.add("10066", "1564", "631.02");
        logger.info("Total value is {}", total);
        pause(5);
        logger.info("Screenshot [{}] has been captured.", takeScreenshot());
        FileUtils.copyFile(driver.findElement(By.xpath("//canvas[@id='canvas']")).getScreenshotAs(OutputType.FILE), new File("target/screenshots/results.png"));
    }

}
