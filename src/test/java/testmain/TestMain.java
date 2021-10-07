package testmain;

import automationtestinstance.AutomationTestInitializer;
import automationtestinstance.AutomationTestManager;
import com.aventstack.extentreports.Status;
import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.utils.Logger;
import config.TestDataManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;
import retryutils.RetryAnalyzer;

import java.lang.reflect.Method;


/**
 * Created by ford.arnett on 10/2/15.
 */
public class TestMain {
    protected AutomationTestManager am;
    String suiteName;
    //Note: Store run data in TDM that you can pass to your tests(Users, search params, orders, etc.). This makes it easy to manage, switch with params, and manage state.
    protected TestDataManager testDataManager;


    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        RetryAnalyzer retryAnalyzer = new RetryAnalyzer();
        for (ITestNGMethod method : context.getAllTestMethods()) {
            method.setRetryAnalyzerClass(retryAnalyzer.getClass());
        }
    }

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
        am.reporter.startTest(method.getName());
        if (AutomationConfigProperties.screenRecord) {
            am.driverWrapper.startScreenRecording();
        }
    }

    /** Run before each class **/
    @BeforeClass
    public void setUpMain() throws Exception {
        testDataManager = new TestDataManager(TestDataManager.CLIENT_ENV, TestDataManager.LOCALE_US);
        AutomationTestInitializer initializer = new AutomationTestInitializer();
        am = initializer.initializeAutomationSystem(testDataManager);
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult testResult) {
        IRetryAnalyzer retryAnalyzer = testResult.getMethod().getRetryAnalyzer(testResult);
        int retryCount = ((RetryAnalyzer) retryAnalyzer).getRetryCount();

        if (testResult.getStatus() == ITestResult.FAILURE || testResult.getStatus() == ITestResult.SKIP) {
            if (retryCount < ((RetryAnalyzer) retryAnalyzer).getMaxRetryCount()) {
                am.reporter.getTest().skip(testResult.getMethod().getMethodName());
                am.reporter.logTest(Status.SKIP, testResult.getThrowable());
            } else {
                am.reporter.getTest().fail(testResult.getMethod().getMethodName());
                am.reporter.logTest(Status.FAIL, testResult.getThrowable());
            }
        } else {
            am.reporter.getTest().pass(testResult.getMethod().getMethodName());
        }

        String screenshotName = "After Method_" + System.currentTimeMillis();
        am.userOp.takeScreenshot(screenshotName);

    }

    @AfterClass
    public void after() {
        if (am.driverWrapper.notNull()) {
            Logger.log("Shutting down driver wrapper.");
            am.driverWrapper.quit();
        }
        if (am.appiumService != null && am.appiumService.isRunning()) {
            am.appiumService.stop();
        }
    }

    @AfterSuite
    public void tearDownFinal(){
        am.reporter.close();
    }
}