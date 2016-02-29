package smokeTest.generic;

import assertions.AssertionLogger;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Works for iOS and Android
 *
 * Created by ford.arnett on 10/12/15.
 */
public class Navigate extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup(){

    }

    @Test
    public void navigateTabs() {
        for (ResourceLocator.DrawerNavigationItem item : ResourceLocator.DrawerNavigationItem.values()) {
            String navItemName = item.toString();
            AutomationOperations.instance().navOp.navigateUsingDrawer(item);
        }
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }
}
