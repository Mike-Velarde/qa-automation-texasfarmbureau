package testingautomationtests;
/**
 * Created by ford.arnett on 4/1/16.
 */

import main.AppiumMain;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class QuickTest extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }


    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }
}