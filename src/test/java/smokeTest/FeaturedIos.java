package smokeTest;

import assertions.AssertionLogger;
import com.bottlerocket.utils.ErrorHandler;
import config.AutomationConfigProperties;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by ford.arnett on 2/10/16.
 */
public class FeaturedIos extends Featured {

    @BeforeClass
    public void setup(){

    }

    /**
     * Test the items of the featured screen.
     */
    @Test
    public void testFeatured(){
        testSearch();

        testShowDetails();

        testWatchlist();

        testWebsite();

        testPlay();

        //TODO this broke somehow, need to figure out how to fix. Tried scrolling with lots of ids none seemed to work
        //AutomationOperations.instance().navOp.featured.scrollToBottom();


    }

    @Override
    protected void testPlay() {
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
        AutomationOperations.instance().userOp.assertVideoRuntimeChanged(assertionLogger, 10000);

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

    @Override
    protected void testWatchlist() {
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.watchlist);
        //TODO reevaluate how to verify on iOS
        //assertionLogger.setTestType("Verify that the watchlist count is the expected count");
        //assertionLogger.assertNotEquals(watchCount, AutomationOperations.instance().userOp.getDrawerWatchlistCount());
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }


}
