package assertions;

import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import operations.AutomationOperations;
import utils.VideoUtils;

import java.util.Calendar;

/**
 * Created by ford.arnett on 4/11/16.
 */
public class AssertionLibrary {
    /**
     * Assert that the video runtime has changed
     *
     * @param wait time to wait for video during play, this should be the difference in between the two observed times
     */
    public static void assertVideoRuntimeChanged(AssertionLogger assertionLogger, WebDriverWrapper driverWrapper, int wait) {
        Calendar startTime = AutomationOperations.instance().userOp.getVideoCurrentRunTime(true);
        driverWrapper.waitLogErr(wait);
        assertionLogger.setTestType("Verify that the video time has progressed, meaning the video has played.");
        Calendar timeAfterPlay = AutomationOperations.instance().userOp.getVideoCurrentRunTime(true);
        assertionLogger.assertNotEquals(VideoUtils.getVideoTimeFromCalendar(startTime), VideoUtils.getVideoTimeFromCalendar(timeAfterPlay));
    }

}
