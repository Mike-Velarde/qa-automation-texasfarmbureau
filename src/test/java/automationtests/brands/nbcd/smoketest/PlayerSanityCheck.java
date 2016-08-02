package automationtests.brands.nbcd.smoketest;

import appium.AppiumMain;
import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import domod.UserBank;
import operations.AutomationOperations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * The point of this class is to have a test that can run quickly and often to check and see if the player is working correctly.
 */
public class PlayerSanityCheck extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() throws WebDriverWrapperException {
        UserBank userBank = new UserBank();
        AutomationOperations.instance().userOp.signIn(userBank.defaultUser, false);
    }

    @Test
    public void livePlayer() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.featured);
    }


    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }
}