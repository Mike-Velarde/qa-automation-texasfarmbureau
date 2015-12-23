package smokeTest;

import assertions.AssertionLogger;
import com.bottlerocket.utils.ErrorHandler;
import config.AppDefaults;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Calendar;

/**
 * Created by ford.arnett on 11/2/15.
 */
public class Featured extends AppiumMain{
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup(){

    }

    @Test
    public void testFeatured(){
        int resultOnPage = AutomationOperations.instance().userOp.search("episode");
        assertionLogger.setTestType("Test that there are more than 0 results");
        assertionLogger.assertNotEquals(resultOnPage, 0);
        AutomationOperations.instance().navOp.mainToolbarBack();

        String showTitle = AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.details);
        assertionLogger.setTestType("Verify that the show title is what we expect");
        assertionLogger.assertTrue(showTitle.equalsIgnoreCase(AutomationOperations.instance().userOp.getShowDetailsShowTitle()));
        AutomationOperations.instance().navOp.mainToolbarBack();

        int watchCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.watchlist);
      //  Assert.assertTrue(watchCount + 1 == AutomationOperations.instance().userOp.getDrawerWatchlistCount());
        assertionLogger.setTestType("Verify that the watchlist count is the expected count");
        assertionLogger.assertNotEquals(watchCount, AutomationOperations.instance().userOp.getDrawerWatchlistCount());

        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.play);
        AutomationOperations.instance().userOp.videoDetailsPlayVideo();
        try {
            driverWrapper.takeScreenshot(AppDefaults.screenshotsDirectory, "video_playing_before_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("error taking screenshot",e);
        }

        //Give video extra time to load
        driverWrapper.waitLogErr(10000);
        //check that video has played
        Calendar runTime = AutomationOperations.instance().userOp.getVideoCurrentRunTime(true);
        //AutomationOperations.instance().userOp.playPauseVideo();
        driverWrapper.waitLogErr(10000);
        assertionLogger.setTestType("Verify that the video time has progressed, meaning the video has played.");
        assertionLogger.assertFalse(runTime.equals(AutomationOperations.instance().userOp.getVideoCurrentRunTime(true)));

        //Get screenshot to compare against before so we know video is playing
        try {
            driverWrapper.takeScreenshot(AppDefaults.screenshotsDirectory, "video_playing_after_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("error taking screenshot",e);
        }
        //AutomationOperations.instance().userOp.bringUpVideoUI();

        //Leave video
        AutomationOperations.instance().navOp.mainToolbarBack();
        //go back from play
        AutomationOperations.instance().navOp.mainToolbarBack();


        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.website);
        boolean visible = AutomationOperations.instance().navOp.featured.isWebsiteVisible();
        assertionLogger.setTestType("Verify that the website is visible.");
        assertionLogger.assertTrue(visible);
        AutomationOperations.instance().navOp.mainToolbarBack();

        //TODO this broke somehow, need to figure out how to fix. Tried scrolling with lots of ids none seemed to work
        //AutomationOperations.instance().navOp.featured.scrollToBottom();


    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }


}
