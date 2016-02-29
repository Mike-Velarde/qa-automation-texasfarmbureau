package smokeTest.ios;

import assertions.AssertionLogger;
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
    public void testShows(){

        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(2);

        //For now I am making the assumption that if we can get the show title then we have made it to the show details screen
        assertionLogger.setTestType("Test if the show title is non empty");
        assertionLogger.assertNotEquals(AutomationOperations.instance().userOp.getShowDetailsShowTitle(), "");

        //TODO think about this, on iOS there is no way to know right now if this button is adding or removing from list
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
        //TODO finish this method (there's multiple episodes stacked in UI viewer, so need to handle odd index issue ie 0 is 1 1 is 3 2 is 5 etc.
        AutomationOperations.instance().navOp.shows.playFromActiveSeason(2);

        //Wait for ads
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        AutomationOperations.instance().userOp.assertVideoRuntimeChanged(assertionLogger, 10000);

        AutomationOperations.instance().navOp.mainToolbarBack();
        AutomationOperations.instance().navOp.mainToolbarBack();

        //This needs more work
        AutomationOperations.instance().navOp.shows.showDetailSelectSeason(1);
        //This isn't working right now
        AutomationOperations.instance().navOp.shows.scrollToBottom();

        AutomationOperations.instance().navOp.mainToolbarBack();

        assertionLogger.logMessages();
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }

}
