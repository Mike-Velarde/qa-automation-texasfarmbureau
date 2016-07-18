package automationtests.smokeTest.ios;

import assertions.AssertionLibrary;
import assertions.AssertionLogger;
import com.bottlerocket.errorhandling.OperationsException;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.config.AutomationConfigProperties;
import config.ResourceLocator;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import automationtests.smokeTest.generic.Shows;

/**
 * Created by ford.arnett on 2/17/16.
 */
public class ShowsIos extends Shows {

    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testShows() throws WebDriverWrapperException, OperationsException {

        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(1, -1);

        //For now I am making the assumption that if we can get the show title then we have made it to the show details screen
        assertionLogger.setTestType("Test if the show title is non empty");
        assertionLogger.assertNotEquals(AutomationOperations.instance().userOp.getShowDetailsShowTitle(), "");

        AutomationOperations.instance().navOp.addShowToWatchlist();


        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_season_change_before_" + System.currentTimeMillis());
        AutomationOperations.instance().navOp.shows.showDetailSelectSeason(1);
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_season_change_after_" + System.currentTimeMillis());

        AutomationOperations.instance().navOp.shows.playFromActiveSeason(2);

        //Wait for ads
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        driverWrapper.takeScreenshotSuppressError("run_time_before");
        driverWrapper.waitLogErr(10000);
        driverWrapper.takeScreenshotSuppressError("run_time_after");

        AutomationOperations.instance().navOp.closeVideoPlayer();
        AutomationOperations.instance().navOp.mainToolbarBack();

        AutomationOperations.instance().navOp.shows.scrollToBottom();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_season_change_after_" + System.currentTimeMillis());
        driverWrapper.waitLogErr(3000);

        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    @Test
    public void testShareFacebook() throws OperationsException, WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(1, -1);
        AutomationOperations.instance().userOp.shareShowFacebook();
        //The timing may be off on this, it's not an easy thing to capture
        driverWrapper.takeScreenshotSuppressError("verify_facebook_post_successful_" + System.currentTimeMillis());
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }

}
