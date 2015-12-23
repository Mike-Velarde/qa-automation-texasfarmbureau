package smokeTest;
/**
 * Created by ford.arnett on 11/30/15.
 */

import assertions.AssertionLogger;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Calendar;

public class VideoPlayerFunctions extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {
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
        driverWrapper.waitLogErr(10000);
        Calendar startTime = AutomationOperations.instance().userOp.getVideoCurrentRunTime(true);
        //AutomationOperations.instance().userOp.playPauseVideo();
        driverWrapper.waitLogErr(10000);
        Calendar timeAfterPlay = AutomationOperations.instance().userOp.getVideoCurrentRunTime(true);
        assertionLogger.setTestType("Test if the video has played");
        assertionLogger.assertNotEquals(getVideoTimeFromCalendar(startTime), getVideoTimeFromCalendar(timeAfterPlay));

        AutomationOperations.instance().userOp.scrubVideo(0.6);
        AutomationOperations.instance().userOp.closedCaptionsToggle();
        AutomationOperations.instance().userOp.closedCaptionsToggle();
        //Allow user to see what just happened, mainly for demo purposes
        driverWrapper.waitLogErr(5000);

        assertionLogger.logMessages();
    }

    private String getVideoTimeFromCalendar(Calendar cal){
        String result;
        result = cal.get(Calendar.HOUR) == 0 ? "00:" : cal.get(Calendar.HOUR) + ":";
        result += cal.get(Calendar.MINUTE) == 0 ? "00:" : cal.get(Calendar.MINUTE) + ":";
        result += cal.get(Calendar.SECOND) == 0 ? "00" : cal.get(Calendar.SECOND) + "";

        return result;
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }
}