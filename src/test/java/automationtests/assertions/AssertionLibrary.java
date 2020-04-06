package automationtests.assertions;

import com.aventstack.extentreports.Status;
import com.bottlerocket.errorhandling.AssertionLibraryException;
import operations.AutomationOperations;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * Created by ford.arnett on 4/11/16.
 */
public abstract class AssertionLibrary {

    public void generalAssertion(AssertionPayload payload) {
        if(payload.category != null) {
            AutomationOperations.instance().reporter.addToTestCoverageList(payload.category.toString(), payload.testDescription);
        }
        if(payload.assertSuccessful) {
            AutomationOperations.instance().reporter.logTest(Status.PASS, payload.successMessage);
            if(payload.takeScreenshotSuccess) {
                String screenshotName = !payload.screenShotSuccessFilename.equals("") ? payload.screenShotSuccessFilename : "assertion_success_" + System.currentTimeMillis();
                AutomationOperations.instance().userOp.takeScreenshot(screenshotName);
            }
        } else {
            //Let testNG know we have a fail during this test.
            AssertionLibraryException assertionLibraryException = new AssertionLibraryException("One or more tests in this group has failed");
            assertionLibraryException.setStackTrace(new StackTraceElement[0]);
            Reporter.getCurrentTestResult().setThrowable(assertionLibraryException);
            Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);

            AutomationOperations.instance().reporter.logTest(Status.FAIL, new Throwable(payload.failureMessage));
            if(payload.takeScreenshotFailure) {
                String screenshotName = !payload.screenShotFailFilename.equals("") ? payload.screenShotFailFilename : "assertion_failed_" +  + System.currentTimeMillis();
                AutomationOperations.instance().userOp.takeScreenshot(screenshotName);
            }
        }
    }

}
