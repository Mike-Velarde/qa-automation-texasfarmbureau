package smokeTest;

import assertions.AssertionLogger;
import config.ResourceLocator;
import domod.UserBank;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by ford.arnett on 10/23/15.
 */
public class Login extends AppiumMain {
    AssertionLogger logger = new AssertionLogger();

    @BeforeClass
    public void setup(){

    }

    @Test(priority = 2)
    public void testLogin(){
        UserBank user = new UserBank();

        AutomationOperations.instance().userOp.signIn(user.defaultUser);
        logger.setTestType("Check that the user is logged in");
        logger.assertTrue(AutomationOperations.instance().userOp.isUserLoggedIn());
    }

    @Test(priority = 1)
    public void testLogout(){
        if(AutomationOperations.instance().userOp.isUserLoggedIn()) {
            UserBank user = new UserBank();
            AutomationOperations.instance().userOp.signIn(user.defaultUser);
        }
        AutomationOperations.instance().userOp.signOut();
        logger.setTestType("Check that the user is logged out");
        logger.assertFalse(AutomationOperations.instance().userOp.isUserLoggedIn());

    }

    @AfterClass
    public void tearDown(){
        logger.logMessages();
    }

}
