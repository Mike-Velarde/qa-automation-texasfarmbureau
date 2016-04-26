package regressiontest;
/**
 * Created by ford.arnett on 3/9/16.
 */

import assertions.AssertionLibrary;
import assertions.AssertionLogger;
import com.bottlerocket.config.AutomationConfigProperties;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import utils.VideoUtils;

import java.util.Calendar;

public class VideoRegression extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testVideo(){
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.play);
        AutomationOperations.instance().userOp.videoDetailsPlayVideo();

        //Wait for the video to begin playing
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);

        //how to rotate http://stackoverflow.com/questions/25864385/changing-android-device-orientation-with-adb

        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_i_am_full_screen_" + System.currentTimeMillis());
        assertionLogger.setTestType("Check run time is of the format xx:xx:xx and is left of the total run time");
        Calendar calBefore = AutomationOperations.instance().userOp.getVideoCurrentRunTime(true);
        Calendar calAfter = AutomationOperations.instance().userOp.getVideoCurrentRunTime(false);
        assertionLogger.addMessage(VideoUtils.getVideoTimeFromCalendar(calBefore) + "/" + VideoUtils.getVideoTimeFromCalendar(calAfter));
        assertionLogger.assertTrue(calBefore.before(calAfter));

        AssertionLibrary.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 10000);

        driverWrapper.takeScreenshotSuppressError("verify_progress_bar_is_moving_appropriately_before");
        //Wait long enough to see a noticeable difference in the scrubber
        driverWrapper.waitLogErr(30000);
        driverWrapper.takeScreenshotSuppressError("verify_progress_bar_is_moving_appropriately_after");

        assertionLogger.setTestType("Check if video has been paused");
        AutomationOperations.instance().userOp.playPauseVideo();
        assertionLogger.assertTrue(AutomationOperations.instance().userOp.isVideoPaused());
        AutomationOperations.instance().userOp.playPauseVideo();

        //We check to see that the play button is in the play state, and that the video run time is changing
        assertionLogger.setTestType("Check if video play has been resumed");
        AutomationOperations.instance().userOp.playPauseVideo();
        assertionLogger.assertTrue(AutomationOperations.instance().userOp.isVideoPaused());
        AutomationOperations.instance().userOp.playPauseVideo();
        AssertionLibrary.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 10000);


    }


    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }

}