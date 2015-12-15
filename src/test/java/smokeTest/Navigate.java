package smokeTest;

import assertions.AssertionLogger;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by ford.arnett on 10/12/15.
 */
public class Navigate extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup(){

    }

    @Test
    public void navigateTabs(){
        for(ResourceLocator.DrawerNavigationItem item : ResourceLocator.DrawerNavigationItem.values()) {
            AutomationOperations.instance().navOp.navigateUsingDrawer(item);
        }
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }
}
