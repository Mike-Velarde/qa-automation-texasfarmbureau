package automationtests.smokeTest.ios;

import automationtests.assertions.AssertionLogger;
import automationtests.smokeTest.generic.Featured;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by ford.arnett on 2/10/16.
 */
public class FeaturedIos extends Featured {
    protected AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup(){

    }

    @Override
    protected void testWatchlist() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.watchlist);
        //TODO reevaluate how to verify on iOS
        //assertionLogger.setTestType("Verify that the watchlist count is the expected count");
        //assertionLogger.assertNotEquals(watchCount, AutomationOperations.instance().userOp.getDrawerWatchlistCount());
    }

    @Test
    protected void testPlay() throws WebDriverWrapperException {
        super.testPlay();
    }

    @Test
    protected void testSearch() throws WebDriverWrapperException {
        super.testSearch();
    }

    @Test
    protected void testShowDetails() throws WebDriverWrapperException {
        super.testShowDetails();
    }

    @Test
    protected void testWebsite() throws WebDriverWrapperException {
        super.testWebsite();
    }


    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }


}
