package automationtests.assertions;

import com.relevantcodes.extentreports.LogStatus;
import operations.AutomationOperations;

/**
 * Created by ford.arnett on 4/11/16.
 */
public abstract class AssertionLibrary {

    public void generalAssertion(AssertionPayload payload) {
        if(payload.assertSuccessful) {
            AutomationOperations.instance().reporter.logTest(LogStatus.PASS, payload.successMessage);
            if(payload.takeScreenshotSuccess) {
                String screenshotName = !payload.screenShotSuccessFilename.equals("") ? payload.screenShotSuccessFilename : "assertion_failed_" + System.currentTimeMillis();
                AutomationOperations.instance().userOp.takeScreenshot(screenshotName);
            }
        } else {
            AutomationOperations.instance().reporter.logTest(LogStatus.FAIL, new Throwable(payload.failureMessage));
            if(payload.takeScreenshotFailure) {
                String screenshotName = !payload.screenShotFailFilename.equals("") ? payload.screenShotFailFilename : "assertion_failed_" +  + System.currentTimeMillis();
                AutomationOperations.instance().userOp.takeScreenshot(screenshotName);
            }
        }
    }

}
