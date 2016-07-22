package automationtests.experimental;
/**
 * Created by ford.arnett on 1/12/16.
 */

import appium.AppiumMain;
import automationtests.assertions.AssertionLogger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class iOSGettingStufftoWork extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }


    @Test
    public void testingThings(){
        driverWrapper.getElementByName("nb grid close").click();
        driverWrapper.getElementByName("nb settings").click();
        driverWrapper.getElementByName("nb search").click();
        driverWrapper.getElementByName("nb shows").click();
        driverWrapper.getElementByName("nb schedule").click();
    }

    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }
}