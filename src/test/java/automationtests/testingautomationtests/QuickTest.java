package automationtests.testingautomationtests;
/**
 * Created by ford.arnett on 4/1/16.
 */

import automationtests.assertions.AssertionLogger;
import appium.AppiumMain;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class QuickTest extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void quickTest(){
        assertionLogger.addMessage("This is a quick and pointless test, used to test the automation suite is working as intended");
        assertionLogger.setTestType("Pointless test");
        assertionLogger.assertEquals("42", "42");
    }


    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }
}