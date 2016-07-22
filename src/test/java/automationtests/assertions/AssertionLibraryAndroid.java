package automationtests.assertions;

import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import operations.AutomationOperations;
import utils.VideoUtils;

import java.util.Calendar;

/**
 * Created by ford.arnett on 6/7/16.
 */
public class AssertionLibraryAndroid extends AssertionLibrary {
    public void assertVideoRuntimeChanged(AssertionLogger assertionLogger, WebDriverWrapper driverWrapper, int wait) {
        Calendar startTime = AutomationOperations.instance().userOp.getVideoCurrentRunTime(true);
        driverWrapper.waitLogErr(wait);
        assertionLogger.setTestType("Verify that the video time has progressed, meaning the video has played.");
        Calendar timeAfterPlay = AutomationOperations.instance().userOp.getVideoCurrentRunTime(true);
        assertionLogger.assertNotEquals(VideoUtils.getVideoTimeFromCalendar(startTime), VideoUtils.getVideoTimeFromCalendar(timeAfterPlay));
    }
}
