package smokeTest.generic;

import assertions.AssertionLogger;
import com.bottlerocket.webdriverwrapper.WebDriverWrapperException;
import domod.UserBank;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Works for iOS and Android
 *
 * Created by ford.arnett on 10/23/15.
 */
public class Login extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup(){

    }

    @Test(priority = 2)
    public void testLogin() throws WebDriverWrapperException {
        UserBank user = new UserBank();

        AutomationOperations.instance().userOp.signIn(user.defaultUser, true);
        assertionLogger.setTestType("Check that the user is logged in");
        assertionLogger.assertTrue(AutomationOperations.instance().userOp.isUserLoggedIn());
    }

    @Test(priority = 1)
    public void testLogout() throws WebDriverWrapperException {
        if(AutomationOperations.instance().userOp.isUserLoggedIn()) {
            UserBank user = new UserBank();
            AutomationOperations.instance().userOp.signIn(user.defaultUser, false);
        }
        AutomationOperations.instance().userOp.signOut();
        assertionLogger.setTestType("Check that the user is logged out");
        assertionLogger.assertFalse(AutomationOperations.instance().userOp.isUserLoggedIn());

    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }

}
