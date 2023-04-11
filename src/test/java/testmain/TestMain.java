package testmain;

import automationtestinstance.AutomationTestInitializer;
import automationtestinstance.AutomationTestManager;
import com.aventstack.extentreports.Status;
import com.bottlerocket.bash.*;
import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.remote.*;
import com.bottlerocket.utils.*;
import config.TestDataManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;
import retryutils.RetryAnalyzer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.Arrays.asList;


/**
 * Created by ford.arnett on 10/2/15.
 */
public class TestMain {
    protected AutomationTestManager am;
    String suiteName;
    //Note: Store run data in TDM that you can pass to your tests(Users, search params, orders, etc.). This makes it easy to manage, switch with params, and manage state.
    protected TestDataManager testDataManager;

    private FlickVideoRunner flickVideoRunner;


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
    public void setupEvery(Method method, Object[] testData) {
        try {
            AutomationTestInitializer initializer = new AutomationTestInitializer();
            am = initializer.initializeAutomationSystem();

            //sets test name to include data-provider data if available
            startTest(method, testData);
            //adds any Groups and Description to top of report if available
            updateReportedGroupsAndDescription(method);
            SauceExecutor sauceExecutor = new SauceExecutor(am.config.remote);
            sauceExecutor.setJobName(am.driverWrapper, method.getName());

            Logger.log(method.getName() + " <-- Method Name***");
        } catch (Exception e) {
            String message = "***FAILED IN @BEFOREMethod*** in **TEST MAIN** Exception == " + e;
            Logger.log(method.getName() + message + " - Exception e is: " + e);
            Logger.log(method.getName() + message + " - Exception e is: " + e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult testResult) throws InterruptedException {
        SauceExecutor executor = new SauceExecutor(am.config.remote);
        IRetryAnalyzer retryAnalyzer = testResult.getMethod().getRetryAnalyzer();
        int retryCount = ((RetryAnalyzer) retryAnalyzer).getRetryCount();

        if(testResult.getStatus() != ITestResult.SUCCESS) {
            if(retryCount < ((RetryAnalyzer) retryAnalyzer).getMaxRetryCount()) {
                am.reporter.getTest().skip(testResult.getMethod().getMethodName());
                am.reporter.logTest(Status.SKIP, testResult.getThrowable());
            }
            else {
                executor.testFailed(am.driverWrapper);
                am.reporter.getTest().fail(testResult.getMethod().getMethodName());
                am.reporter.logTest(Status.FAIL, testResult.getThrowable());
            }
        }
        else {
            am.reporter.getTest().pass(testResult.getMethod().getMethodName());
            executor.testPassed(am.driverWrapper);
        }

        am.reporter.write();

        String screenshotName =  "After Method_" + System.currentTimeMillis();
        am.userOp.takeScreenshot(screenshotName);

        if (am.driverWrapper.notNull()) {
            Logger.log("Shutting down driver wrapper.");
            am.driverWrapper.quit();
        }

    }

//    @AfterClass
//    public void after() {
//        if (am.driverWrapper.notNull()) {
//            Logger.log("Shutting down driver wrapper.");
//            am.driverWrapper.quit();
//        }
//        if (am.appiumService != null && am.appiumService.isRunning()) {
//            am.appiumService.stop();
//        }
//    }

    @AfterSuite
    public void tearDownFinal() throws InterruptedException {
        if (am.config.screenRecord) {
            try {
                flickVideoRunner.stopVideo(am.config);
            } catch (IOException e) {
                ErrorHandler.printErr("error occurred when attempted to stop video ", e);
            }
        }
        am.reporter.writeTestCoverageList(new File("testCoverageOut/testCoverageListOut_" + System.currentTimeMillis() + ".txt"));
        am.reporter.close();
    }

    //Gets all Groups and Description that belong to this method and add them to top of Extent Report
    private void updateReportedGroupsAndDescription(Method method) {
        String BOLD_TEXT = "<b>%s</b>";
        String TEST_DESCRIPTION = "<b>DESCRIPTION: </b>";
        String testDescription = method.getAnnotation((Test.class)).description();
        List<String> groups = asList(method.getAnnotation((Test.class)).groups());
        if(!groups.isEmpty()) {
            for ( String group : groups ) {
                am.reporter.addInfoToReport(String.format(BOLD_TEXT, group));
            }
        }
        if(!testDescription.isEmpty()) {
            am.reporter.addInfoToReport(TEST_DESCRIPTION + testDescription);
        }
    }

    //starts test with name and dataprovider data if applicable
    private void startTest(Method method, Object[] testData) {
        if(testData.length > 0) {
            am.reporter.startTest(method.getName() + "[" + testData[0] + "]");
        } else {
            am.reporter.startTest(method.getName());
        }
    }
}