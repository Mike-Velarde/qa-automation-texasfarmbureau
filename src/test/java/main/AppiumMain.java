package main;

import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.utils.Logger;
import com.bottlerocket.webdriver.WebDriverWrapper;
import com.bottlerocket.webdriver.WebDriverWrapperAndroid;
import com.bottlerocket.webdriver.WebDriverWrapperIos;
import config.*;
import operations.AutomationOperations;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;


/**
 * Created by ford.arnett on 10/2/15.
 */
public class AppiumMain{
    protected WebDriverWrapper driverWrapper;

    private String sessionId;

    /** Keep the same date prefix to identify job sets. **/
    private static Date date = new Date();

    /** Run before each test **/
    @BeforeClass
    public void setUp() throws Exception {
        DeviceAutomationComponents device;
        //Set iOS or Android here
        if(isAndroid()){
            device = new AndroidAutomationComponents();
        }
        else{
            device = new IosAutomationComponents();
        }

        AutomationOperations.instance().initOperations(device);
        AutomationOperations.instance().config.loadConfigVariables();
        //Set configurations
        DesiredCapabilities capabilities = AutomationOperations.instance().config.setCapabilities();

        URL serverAddress;

        serverAddress = new URL("http://127.0.0.1:4723/wd/hub");
        if (isAndroid()) {
            driverWrapper = new WebDriverWrapperAndroid(serverAddress, capabilities, AppDefaults.globalWait);
        } else {
            driverWrapper = new WebDriverWrapperIos(serverAddress, capabilities, AppDefaults.globalWait);
        }

        //this must be after driver wrapper is initialized
        initAutomationOperations();

        Logger.log("Running test method: " );

        //Normally no operations should be done in this class, however, if the app ever launches with the picker
        //then this needs to be run to get to the home page. Since it is possible at anytime to launch with picker,
        //this must be run at each launch.
        AutomationOperations.instance().userOp.chooseFeedIfNeeded(ResourceLocator.device.AWE_BRAND_NAMES_USA, ResourceLocator.device.ANDROID_FW_DEV_LIVE, ResourceLocator.device.AWE_PICKFEED_SERVERURL_ID);

    }

    private boolean isAndroid() {
        Properties prop = null;
        try {
            prop = AutomationConfigurations.loadConfig();
        } catch (Exception ex) {
            ErrorHandler.printErr("Properties file not found", ex);
        }

        return prop == null || prop.getProperty("PLATFORM_NAME").equals("Android");

    }

    private void initAutomationOperations() {
        AutomationOperations automationOperations = AutomationOperations.instance();
        automationOperations.userOp.init(driverWrapper);
        automationOperations.navOp.init(driverWrapper);
    }


    /** Run after each test **/
    @AfterClass
    public void tearDown() throws Exception {
        if (driverWrapper.notNull())
            driverWrapper.quit();
    }

    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Logger.log(testResult.getMethod().getMethodName());
            String fileName = "failure_" + testResult.getMethod().getMethodName() + "_" + System.currentTimeMillis();
            driverWrapper.takeScreenshot(AppDefaults.screenshots, fileName);
        }

    }

}
