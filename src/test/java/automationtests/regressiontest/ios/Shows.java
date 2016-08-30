package automationtests.regressiontest.ios;
/**
 * Created by stephen.farmer on 8/10/16.
 */

import appium.AppiumMain;
import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Shows extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
    }

    @Test
    public void testAlphabeticOrder() {
        assertionLogger.setTestType("Testing");
    }

    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }
}