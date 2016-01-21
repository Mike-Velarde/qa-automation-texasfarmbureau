package main;

import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.utils.Logger;
import com.bottlerocket.webdriver.WebDriverWrapper;
import com.bottlerocket.webdriver.WebDriverWrapperAndroid;
import com.bottlerocket.webdriver.WebDriverWrapperIos;
import config.*;
import operations.AutomationOperations;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


/**
 * Created by ford.arnett on 10/2/15.
 */
public class AppiumMain{
    protected WebDriverWrapper driverWrapper;
    private String suiteName;
    //This is the offset to store files for this run of the suite
    private String uniqueFolderOffset;

    /**
     * This seems to run after the other setup
     * @param ctx
     */
    @BeforeClass
    public void setUpEveryMain(ITestContext ctx){
        suiteName = ctx.getCurrentXmlTest().getSuite().getName();
    }

    /** Run before each test **/
    @BeforeClass
    public void setUpMain() throws Exception {
        DeviceAutomationComponents device;

        AutomationConfigurations.loadConfig();
        //Set iOS or Android here
        if(AutomationConfigurations.isAndroid()){
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
        if (AutomationConfigurations.isAndroid()) {
            driverWrapper = new WebDriverWrapperAndroid(serverAddress, capabilities, AppDefaults.globalWait);
        } else if (AutomationConfigurations.isIos()){
            driverWrapper = new WebDriverWrapperIos(serverAddress, capabilities, AppDefaults.globalWait);
        }
        else {
            throw new Error("Operating system not recognized, check config files");
        }

        //this must be after driver wrapper is initialized
        initAutomationOperations();

        //This is really ugly way to do this here but I can't think of a one
        AppDefaults.screenshotsDirectory += getUniqueFolderOffset() + "/mobile_screenshots/";

        Logger.log("Running test method: " );

        //Normally no operations should be done in this class, however, if the app ever launches with the picker
        //then this needs to be run to get to the home page. Since it is possible at anytime to launch with picker,
        //this must be run at each launch.
        //TODO fix to use for iOS too
        // AutomationOperations.instance().userOp.chooseFeedIfNeeded(ResourceLocator.device.AWE_BRAND_NAMES_USA, ResourceLocator.device.ANDROID_FW_DEV_LIVE, ResourceLocator.device.AWE_PICKFEED_SERVERURL_ID);

    }

    private void initAutomationOperations() {
        AutomationOperations automationOperations = AutomationOperations.instance();
        automationOperations.userOp.init(driverWrapper);
        automationOperations.navOp.init(driverWrapper);
    }

    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Logger.log(testResult.getMethod().getMethodName());
            String fileName = "failure_" + testResult.getMethod().getMethodName() + "_" + System.currentTimeMillis();
            driverWrapper.takeScreenshot(AppDefaults.screenshotsDirectory, fileName);
        }

    }

    /** Run after each suite **/
    @AfterClass
    public void tearDownMain() throws Exception{
        // I tried adding this functionality through changing the output directory in custom reporters
        // but I could never get all of the reports to go to the new folder. This may not be the best solution but it works for now
        FileUtils.copyDirectory(new File("test-output"), new File(AppDefaults.testNGOutputDirectory + getUniqueFolderOffset()));

        if (driverWrapper.notNull())
            driverWrapper.quit();
    }

    /**
     *  Create a directory offset to store reports and screenshots in the same folder structure.
     *
     *  The offset is suite name + current date
     */
    public String getUniqueFolderOffset(){
        if(uniqueFolderOffset == null || uniqueFolderOffset.equals("")) {
            DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy hh_mm_ss a");
            Calendar cal = Calendar.getInstance();
            uniqueFolderOffset =  dateFormat.format(cal.getTime()) + " " + suiteName + " build_" + AppDefaults.buildNumber;
        }
        return uniqueFolderOffset;
    }

}
