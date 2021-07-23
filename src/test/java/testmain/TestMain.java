package testmain;

import com.aventstack.extentreports.Status;
import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.driverwrapper.DriverWrapper;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.utils.Logger;
import config.TestDataManager;
import operations.AutomationOperations;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;


/**
 * Created by ford.arnett on 10/2/15.
 */
public class TestMain {
    private DriverWrapper driverWrapper;
    private String suiteName;
    protected AutomationOperations ops = AutomationOperations.instance();
    TestDataManager testDataManager;

    /**
     * This seems to run after the other setup
     *
     * @param ctx the test context
     */
    @BeforeClass
    public void setUpMain(ITestContext ctx) {
        suiteName = ctx.getCurrentXmlTest().getSuite().getName();
    }

    @BeforeMethod(alwaysRun = true)
    public void setupEvery(Method method) {
        ops.reporter.startTest(method.getName());
    }

    /**
     * Run before each class
     **/
    @BeforeClass
    public void setUpMain() throws Exception {
        testDataManager = new TestDataManager(TestDataManager.DEV_ENV, TestDataManager.US_LOCALE);
        driverWrapper = AutomationOperations.initializeAutomationSystem();
    }


    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Logger.log(testResult.getMethod().getMethodName());
            String fileName = "failure_" + testResult.getMethod().getMethodName() + "_" + System.currentTimeMillis();
            try {
                ops.userOp.takeScreenshot(fileName);
            } catch (Exception ex) {
                Logger.log("Error occurred when taking screenshot. Attempted to save screenshot to " + AutomationConfigProperties.screenshotsDirectory + fileName);
            }
            try {
                ops.reporter.logTest(Status.FAIL, testResult.getThrowable(), fileName);
            } catch (IOException ex) {
                ErrorHandler.printErr("Error attaching screenshot to report", ex);
            }
            Logger.log("Failed test " + testResult.getName());
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            //I'm not sure if this still happens. Do we still have cases where test is null on skip?
            if(ops.reporter.getTest() == null) {
                ops.reporter.startTest(testResult.getName());
            }

            String fileName = "test_skipped_" + testResult.getMethod().getMethodName() + "_" + System.currentTimeMillis();
            try {
                ops.userOp.takeScreenshot(fileName);

            } catch (Exception ex) {
                Logger.log("Error occurred when taking screenshot. Attempted to save screenshot to " + AutomationConfigProperties.screenshotsDirectory + fileName);
            }

            String throwable = testResult.getThrowable() == null ? "" : testResult.getThrowable().getMessage();
            ops.reporter.logTest(Status.SKIP, "Skipped test " + testResult.getMethod().getMethodName() + " " + throwable);
            Logger.log("Skipped test " + testResult.getName());
            ops.reporter.getTest().skip("Test Skipped " + testResult.getName());
        } else {
            Logger.log("Passed test " + testResult.getName());
            ops.reporter.getTest().pass("Test Passed " + testResult.getName());
        }

        //If driverWrapper is never initialized Appium most likely never started so we don't want to keep records
        if (driverWrapper == null) {
            return;
        } else {
            if (AutomationConfigProperties.screenRecord) {
                driverWrapper.stopScreenRecording(testResult.getMethod().getMethodName());
            }
        }

        ops.reporter.writeTestCoverageList(new File("testCoverageListOut.txt"));
        ops.reporter.write();
    }

    @AfterClass
    public void after() {
        if (driverWrapper.notNull()) {
            Logger.log("Shutting down driver wrapper.");
            driverWrapper.quit();
        }
        if (ops.appiumService != null && ops.appiumService.isRunning()) {
            ops.appiumService.stop();
        }
    }

    @AfterSuite
    public void tearDownFinal(){
        ops.reporter.close();
    }
}