package appium;

import com.bottlerocket.bash.FlickVideoRunner;
import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.utils.Logger;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import com.relevantcodes.extentreports.LogStatus;
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
    private WebDriverWrapper driverWrapper;
    private String suiteName;
    protected AutomationOperations ops = AutomationOperations.instance();
    private FlickVideoRunner runner;

    /**
     * This seems to run after the other setup
     * @param ctx
     */
    @BeforeClass
    public void setUpMain(ITestContext ctx){
        suiteName = ctx.getCurrentXmlTest().getSuite().getName();
    }

    @BeforeMethod(alwaysRun = true)
    public void setupEvery(Method method) {
        AutomationOperations.instance().reporter.startTest(method.getName());
    }

    /** Run before each class **/
    @BeforeClass
    public void setUpMain() throws Exception {
        driverWrapper = AutomationOperations.initializeAutomationSystem();

        if(AutomationConfigProperties.screenRecord) {
            runner = new FlickVideoRunner(new File(AutomationConfigProperties.reportOutputDirectory));
            try {
                runner.startVideo();
            } catch (IOException e) {
                ErrorHandler.printErr("error occurred when attempting to start video. ", e);
            }
        }

    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            AutomationOperations.instance().reporter.logTest(LogStatus.FAIL, testResult.getThrowable());
            Logger.log(testResult.getMethod().getMethodName());
            String fileName = "failure_" + testResult.getMethod().getMethodName() + "_" + System.currentTimeMillis();
            try {
                driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, fileName);
            } catch (Exception ex) {
                Logger.log("Error occurred when taking screenshot. Attempted to save screenshot to " + AutomationConfigProperties.screenshotsDirectory + fileName);
            }
            Logger.log("Failed test " + testResult.getName());
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            AutomationOperations.instance().reporter.logTest(LogStatus.SKIP, "Test skipped " + testResult.getThrowable());
            Logger.log("Skipped test " + testResult.getName());
        } else {
            Logger.log("Passed test " + testResult.getName());
        }

        AutomationOperations.instance().reporter.endTest();

    }

    /** Run after each suite **/
    @AfterClass(alwaysRun = true)
    public void tearDownMain(ITestContext ctx) {
        //If driverWrapper is never initialized Appium most likely never started so we don't want to keep records
        if(driverWrapper == null) {
            return;
        }

        AutomationOperations.instance().reporter.writeTestCoverageList(new File("testCoverageListOut.txt"));
        AutomationOperations.instance().reporter.write();

        if (driverWrapper.notNull()) {
            Logger.log("Shutting down driver wrapper.");
            driverWrapper.quit();
        }
        if(AutomationOperations.instance().appiumService != null && AutomationOperations.instance().appiumService.isRunning()) {
            AutomationOperations.instance().appiumService.stop();
        }
    }

    @AfterSuite
    public void tearDownFinal() throws InterruptedException {
        if(AutomationConfigProperties.screenRecord) {
            try {
                runner.stopVideo();
            } catch (IOException e) {
                ErrorHandler.printErr("error occured when attempted to stop video ", e);
            }
        }

        AutomationOperations.instance().reporter.close();
    }

}
