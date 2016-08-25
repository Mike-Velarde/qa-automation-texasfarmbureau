package automationtests.smokeTest.ios;
/**
 * Created by stephen.farmer on 8/23/16.
 */

import appium.AppiumMain;
import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import operations.AutomationOperations;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ShowDetailIos extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.details);
    }

    @Test
    public void testShowDetailItems() {
        assertionLogger.setTestType("Testing the Show Details title");
        assertionLogger.assertFalse(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_TITLE)));
        assertionLogger.setTestType("Testing the Show Details subtitle");
        assertionLogger.assertFalse(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_SUBTITLE)));

        // Test sponsored
        assertionLogger.setTestType("Verify sponsor label is present");
        assertionLogger.assertTrue(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_SPONSOR_LABEL)));

        assertionLogger.setTestType("Verify sponsor text says SPONSORED BY");
        assertionLogger.assertEquals(driverWrapper.getElementValue(driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SPONSOR_LABEL)), "SPONSORED BY");
    }

    @Test
    public void testBackButton() {
        driverWrapper.getElementByName(ResourceLocator.device.AWE_SHOW_DETAILS_BACK_BUTTON).click();
        assertionLogger.setTestType("Testing the Show Details back button");
        assertionLogger.assertFalse(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_SHOW_DETAILS_BACK_BUTTON)));
    }

    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }
}