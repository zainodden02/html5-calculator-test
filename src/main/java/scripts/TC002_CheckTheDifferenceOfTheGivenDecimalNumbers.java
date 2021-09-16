package scripts;

import config.DriverSetup;
import constants.IFrames;
import constants.WebURL;
import functions.Calculator;
import functions.FileUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TC002_CheckTheDifferenceOfTheGivenDecimalNumbers extends DriverSetup {

    private final JSONObject data = FileUtil.loadJSON("TC-002.json");

    @Test
    public void TC002() throws Exception {
        // Go to the Online Calculator URL
        driver.get(WebURL.ONLINE_CALCULATOR);
        pause(2); // To see the UI

        // Switch frame
        driver.switchTo().frame(driver.findElement(By.xpath(IFrames.ONLINE_CALCULATOR)));

        // Get 'values' Json array
        JSONArray array = data.optJSONArray("values");
        String[] values = new String[array.length()];
        for (int i = 0; i < array.length(); i++) {
            values[i] = array.getString(i);
        }

        // Compute (subtract) the given values. Get the runtime computation of the given values.
        // EXPECTED TOTAL VALUE IS: -1162.03
        double total = Calculator.subtract(values);
        logger.info("The difference of the given values is {}.", total);
        pause(5); // To see the UI

        // Get the computed value as displayed on the display panel of the calculator.
        double computedValue = Calculator.getResult();

        // Assert that the values are equal.
        assertThat(total).describedAs("The computed value [%s] on the calculator is NOT correct.", computedValue).isEqualTo(computedValue);
        logger.info("The computed value [{}] on the calculator is correct.", total);

        // Take a screenshot of the entire viewport
        logger.info("Web screenshot [{}] has been captured.", takeScreenshot());
    }
}
