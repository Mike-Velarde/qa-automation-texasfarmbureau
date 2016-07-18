package automationtests.smokeTest.generic;

/**
 * Created by ford.arnett on 11/19/15.
 */

import appium.AppiumMain;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SplashScreen extends AppiumMain {

    @BeforeClass
    public void setup() {

    }

    @Test (enabled = false)
    /**
     * This currently doesn't work, it seems to run after first page is loaded
     */
    public void testSponsorLogos(){
        System.out.print("");
        //Assert.assertTrue(AutomationOperations.instance().navOp.brandAndSponsorVisible());
    }

}