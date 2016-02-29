package smokeTest.generic;

/**
 * Created by ford.arnett on 11/19/15.
 */

import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SplashScreen extends AppiumMain {

    @BeforeClass
    public void setup() {

    }

    @Test
    /**
     * This currently doesn't work, it seems to run after first page is loaded
     */
    public void testSponsorLogos(){
        System.out.print("");
        //Assert.assertTrue(AutomationOperations.instance().navOp.brandAndSponsorVisible());
    }

}