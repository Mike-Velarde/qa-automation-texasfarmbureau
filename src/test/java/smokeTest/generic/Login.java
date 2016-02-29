package smokeTest.generic;

import assertions.AssertionLogger;
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
    public void testLogin(){
        UserBank user = new UserBank();

        AutomationOperations.instance().userOp.signIn(user.defaultUser);
        assertionLogger.setTestType("Check that the user is logged in");
        assertionLogger.assertTrue(AutomationOperations.instance().userOp.isUserLoggedIn());
    }

    @Test(priority = 1)
    public void testLogout(){
        if(AutomationOperations.instance().userOp.isUserLoggedIn()) {
            UserBank user = new UserBank();
            AutomationOperations.instance().userOp.signIn(user.defaultUser);
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