package appium;

import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.config.AutomationConfigurations;
import com.bottlerocket.utils.Logger;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import com.bottlerocket.webdriverwrapper.WebDriverWrapperAndroid;
import com.bottlerocket.webdriverwrapper.WebDriverWrapperIos;
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


/**
 * Created by ford.arnett on 10/2/15.
 */
public class AppiumMain{
    protected WebDriverWrapper driverWrapper;

    /**
     * This seems to run after the other setup
     * @param ctx
     */
    @BeforeClass
    public void setUpMain(ITestContext ctx){

    }


    /** Run before all tests **/
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

        URL serverAddress = new URL(AutomationConfigProperties.appiumURL);
        Logger.log("Appium URL is " + serverAddress);
        if (AutomationConfigurations.isAndroid()) {
            driverWrapper = new WebDriverWrapperAndroid(serverAddress, capabilities, AutomationConfigProperties.globalWait);
        } else if (AutomationConfigurations.isIos()){
            driverWrapper = new WebDriverWrapperIos(serverAddress, capabilities, AutomationConfigProperties.globalWait);
        }
        else {
            throw new Error("Operating system not recognized, check config files");
        }

        //this must be after driver wrapper is initialized
        initAutomationOperations();

    }

    private void initAutomationOperations() {
        AutomationOperations automationOperations = AutomationOperations.instance();
        automationOperations.userOp.init(driverWrapper);
        automationOperations.navOp.init(driverWrapper);
    }

    @AfterMethod(alwaysRun = true)
    public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Logger.log(testResult.getMethod().getMethodName());
            String fileName = "failure_" + testResult.getMethod().getMethodName() + "_" + System.currentTimeMillis();
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, fileName);
        }

    }

    /** Run after each suite **/
    @AfterClass(alwaysRun = true)
    public void tearDownMain(ITestContext ctx) throws Exception{
        //If driverWrapper is never initialized Appium most likely never started so we don't want to keep records
        if(driverWrapper == null) {
            return;
        }

        // I tried adding this functionality through changing the output directory in custom reporters
        // but I could never get all of the reports to go to the new folder. This may not be the best solution but it works for now
        FileUtils.copyDirectory(new File("test-output"), new File(AutomationConfigProperties.testNGOutputDirectory + AutomationConfigProperties.uniqueFolderOffset));

        if (driverWrapper.notNull())
            driverWrapper.quit();
    }

}
