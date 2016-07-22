package automationtests.smoketest.android;

import automationtests.smoketest.general.Featured;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import operations.AutomationOperations;
import org.testng.annotations.Test;

/**
 * Created by ford.arnett on 7/13/16.
 */
public class FeaturedAndroid extends Featured {

    @Override
    protected void testWatchlist() throws WebDriverWrapperException {
        int watchCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.watchlist);
        assertionLogger.setTestType("Verify that the watchlist count is the expected count");
        assertionLogger.assertNotEquals(watchCount, AutomationOperations.instance().userOp.getDrawerWatchlistCount());
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

}
