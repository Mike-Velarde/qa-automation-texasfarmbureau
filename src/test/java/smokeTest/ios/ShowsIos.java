package smokeTest.ios;

import assertions.AssertionLibrary;
import assertions.AssertionLogger;
import com.bottlerocket.errorhandling.OperationsException;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.config.AutomationConfigProperties;
import config.ResourceLocator;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import smokeTest.generic.Shows;

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
        AutomationOperations.instance().navOp.shows.selectShow(1);

        //For now I am making the assumption that if we can get the show title then we have made it to the show details screen
        assertionLogger.setTestType("Test if the show title is non empty");
        assertionLogger.assertNotEquals(AutomationOperations.instance().userOp.getShowDetailsShowTitle(), "");

        AutomationOperations.instance().navOp.addShowToWatchlist();

        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "verify_season_change_before_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("Error taking screenshot ", e);
        }
        AutomationOperations.instance().navOp.shows.showDetailSelectSeason(1);
        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "verify_season_change_after_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("Error taking screenshot ", e);
        }
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

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }

}
