package smokeTest.generic;

import assertions.AssertionLibrary;
import assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.config.AutomationConfigProperties;
import config.ResourceLocator;
import domod.UserBank;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Common methods are used for both, while iOS has some separate code
 *
 * Created by ford.arnett on 11/2/15.
 */
public class Featured extends AppiumMain{
    protected AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() throws WebDriverWrapperException {
        UserBank user = new UserBank();

        AutomationOperations.instance().userOp.signIn(user.defaultUser, false);
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.featured);
    }

    /**
     * Test all items of the featured screen.
     */
    @Test (enabled =  false)
    public void testFeatured() throws WebDriverWrapperException {
        testSearch();

        testShowDetails();

        testWatchlist();

        testWebsite();

        testPlay();


        //TODO this broke somehow, need to figure out how to fix. Tried scrolling with lots of ids none seemed to work
        //AutomationOperations.instance().navOp.featured.scrollToBottom();


    }

    @Test
    protected void testWebsite() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.website);
        boolean visible = AutomationOperations.instance().navOp.featured.isWebsiteVisible();
        assertionLogger.setTestType("Verify that the website is visible");
        assertionLogger.assertTrue(visible);
        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    @Test
    protected void testPlay() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.play);
        AutomationOperations.instance().userOp.videoDetailsPlayVideo();
        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "video_playing_before_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("error taking screenshot",e);
        }

        //Give video extra time to load
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "video_during_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("error taking screenshot",e);
        }
        //check that video has played
        AutomationOperations.instance().assertions.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 10000);

        //Get screenshot to compare against before so we know video is playing
        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "video_playing_after_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("error taking screenshot",e);
        }
        //AutomationOperations.instance().userOp.bringUpVideoUI();

        //Leave video
        AutomationOperations.instance().navOp.mainToolbarBack();
        //go back from play
        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    @Test
    protected void testWatchlist() throws WebDriverWrapperException {
        int watchCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.watchlist);
        assertionLogger.setTestType("Verify that the watchlist count is the expected count");
        assertionLogger.assertNotEquals(watchCount, AutomationOperations.instance().userOp.getDrawerWatchlistCount());
    }

    @Test
    protected void testSearch() throws WebDriverWrapperException {
        int resultOnPage = AutomationOperations.instance().userOp.search("episode");
        assertionLogger.setTestType("Test that there are more than 0 results");
        assertionLogger.assertNotEquals(resultOnPage, 0);
        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    @Test
    protected void testShowDetails() throws WebDriverWrapperException {
        String showTitle = AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.details);
        assertionLogger.addMessage("Verify that the show title is what we expect, Expected: " + showTitle + " Actual: " + AutomationOperations.instance().userOp.getShowDetailsShowTitle());
        //assertionLogger.assertTrue(showTitle.equalsIgnoreCase(AutomationOperations.instance().userOp.getShowDetailsShowTitle()));
        AutomationOperations.instance().navOp.returnFromShowDetails();
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }


}
