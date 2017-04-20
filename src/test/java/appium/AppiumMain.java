package appium;

import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.utils.Logger;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import com.relevantcodes.extentreports.LogStatus;
import operations.AutomationOperations;
import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;


/**
 * Created by ford.arnett on 10/2/15.
 */
public class AppiumMain{
    protected WebDriverWrapper driverWrapper;
    private String suiteName;
    protected AutomationOperations ops = AutomationOperations.instance();

    /**
     * This seems to run after the other setup
     * @param ctx
     */
    @BeforeClass
    public void setUpMain(ITestContext ctx){
        suiteName = ctx.getCurrentXmlTest().getSuite().getName();
    }

    @BeforeMethod
    public void setupEvery(Method method) {
        AutomationOperations.instance().reporter.startTest(method.getName());
    }

    /** Run before each class **/
    @BeforeClass
    public void setUpMain() throws Exception {
        driverWrapper = AutomationOperations.initializeAutomationSystem();

    }

    @AfterMethod(alwaysRun = true)
    public void takeScreenShotOnFailure(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            AutomationOperations.instance().reporter.logTest(LogStatus.FAIL, testResult.getThrowable());
            Logger.log(testResult.getMethod().getMethodName());
            String fileName = "failure_" + testResult.getMethod().getMethodName() + "_" + System.currentTimeMillis();
            try {
                driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, fileName);
            } catch (Exception ex) {
                Logger.log("Error occurred when taking screenshot. Attempted to save screenshot to " + AutomationConfigProperties.screenshotsDirectory + fileName);
            }
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            AutomationOperations.instance().reporter.logTest(LogStatus.SKIP, "Test skipped " + testResult.getThrowable());
        } else {
            AutomationOperations.instance().reporter.logTest(LogStatus.PASS, "Test passed");
        }

        AutomationOperations.instance().reporter.endTest();

    }

    /** Run after each suite **/
    @AfterClass(alwaysRun = true)
    public void tearDownMain(ITestContext ctx) throws Exception{
        //If driverWrapper is never initialized Appium most likely never started so we don't want to keep records
        if(driverWrapper == null) {
            return;
        }

        AutomationOperations.instance().reporter.write();

        // I tried adding this functionality through changing the output directory in custom reporters
        // but I could never get all of the reports to go to the new folder. This may not be the best solution but it works for now
        if (!AutomationConfigProperties.useGradleValues && AutomationConfigProperties.reporter.equalsIgnoreCase("default")) {
            FileUtils.copyDirectory(new File("test-output"), new File(AutomationConfigProperties.reportOutputDirectory));
        }

        if (driverWrapper.notNull())
            driverWrapper.quit();
    }

    @AfterSuite
    public void tearDownFinal() {
        AutomationOperations.instance().reporter.close();
    }

}
