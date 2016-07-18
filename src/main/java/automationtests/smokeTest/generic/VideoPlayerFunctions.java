package automationtests.smokeTest.generic;
/**
 * Created by ford.arnett on 11/30/15.
 */

import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.OperationsException;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import appium.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VideoPlayerFunctions extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() throws OperationsException, WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(1, 0);
        AutomationOperations.instance().navOp.shows.playFromActiveSeason(1);
        //Give the video extra time to load
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
    }

    public void pause(){
        AutomationOperations.instance().userOp.pauseVideo();
        assertionLogger.setTestType("Test that the pause button has changed to play. This does not guarantee pausing");
        assertionLogger.assertTrue(AutomationOperations.instance().userOp.isVideoPaused());
        //Just because the button has changed doesn't mean with 100% certainty the video has paused. This allows us to verify for sure.
        driverWrapper.takeScreenshotSuppressError("verify_video_is_paused_before_" + System.currentTimeMillis());
        driverWrapper.waitLogErr(5000);
        driverWrapper.takeScreenshotSuppressError("verify_video_is_paused_after_" + System.currentTimeMillis());
    }

    public void play(){
        AutomationOperations.instance().userOp.playVideo();
        assertionLogger.setTestType("Test that the pause button has changed to pause. This does not guarantee playing");
        assertionLogger.assertTrue(AutomationOperations.instance().userOp.isVideoPlaying());
        AutomationOperations.instance().assertions.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 10000);
    }

    public void closedCaption(){
        AutomationOperations.instance().userOp.closedCaptionsOn();
        //Try to get a good amount of screenshots to miss times where there are no captions
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());
        driverWrapper.waitLogErr(5000);
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());

        //Check and make sure captions are off
        AutomationOperations.instance().userOp.closedCaptionsOff();
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());
        driverWrapper.waitLogErr(5000);
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());
        driverWrapper.takeScreenshotSuppressError("verify_closed_captions_are_on_screen_" + System.currentTimeMillis());

    }


    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }
}