package smokeTest.generic;
/**
 * Created by ford.arnett on 11/30/15.
 */

import assertions.AssertionLibrary;
import assertions.AssertionLogger;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import operations.OperationsException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.VideoUtils;

import java.util.Calendar;

public class VideoPlayerFunctions extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() throws OperationsException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(1, 1);
        AutomationOperations.instance().navOp.shows.playFromActiveSeason(1);
    }

    @Test
    public void testVideoFunctions(){
        //Replace this with pull up video UI
        //AutomationOperations.instance().userOp.pauseVideo();

        //driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        //AutomationOperations.instance().userOp.playPauseVideo();
        //Give the video extra time to load
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        AssertionLibrary.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 10000);

        AutomationOperations.instance().userOp.scrubVideo(0.6);
        AutomationOperations.instance().userOp.closedCaptionsToggle();
        AutomationOperations.instance().userOp.closedCaptionsToggle();
        //Allow user to see what just happened, mainly for demo purposes
        driverWrapper.waitLogErr(5000);

        assertionLogger.logMessages();
    }


    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }
}