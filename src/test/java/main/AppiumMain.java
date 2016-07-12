package main;

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
    private String suiteName;
    //This is the offset to store files for this run of the suite
    private String uniqueFolderOffset;
    private Calendar startTime = Calendar.getInstance();

    /**
     * This seems to run after the other setup
     * @param ctx
     */
    @BeforeClass
    public void setUpEveryMain(ITestContext ctx){
        suiteName = ctx.getCurrentXmlTest().getSuite().getName();
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

        //TODO refactor to get rid of this uglyness and move to config,see todo in method
        AutomationConfigProperties.screenshotsDirectory = setScreenshotConfigValue();

        //Normally no operations should be done in this class, however, if the app ever launches with the picker
        //then this needs to be run to get to the home page. Since it is possible at anytime to launch with picker,
        //this must be run at each launch.

        try {
            AutomationOperations.instance().userOp.chooseFeedIfNeeded(ResourceLocator.device.AWE_BRAND_NAMES_USA, ResourceLocator.device.AWE_RC_LIVE, ResourceLocator.device.AWE_PICKFEED_SERVERURL_ID);
        }
        catch (Exception ex) {
            Logger.log("feed picker failed");
            String fileName = "failure_feed_picker_" + System.currentTimeMillis();
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, fileName);
        }

    }

    private void initAutomationOperations() {
        AutomationOperations automationOperations = AutomationOperations.instance();
        automationOperations.userOp.init(driverWrapper);
        automationOperations.navOp.init(driverWrapper);
    }

    @AfterMethod ( alwaysRun = true )
    public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Logger.log(testResult.getMethod().getMethodName());
            String fileName = "failure_" + testResult.getMethod().getMethodName() + "_" + System.currentTimeMillis();
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, fileName);
        }

    }

    /** Run after each suite **/
    @AfterClass
    public void tearDownMain() throws Exception{
        // I tried adding this functionality through changing the output directory in custom reporters
        // but I could never get all of the reports to go to the new folder. This may not be the best solution but it works for now
        FileUtils.copyDirectory(new File("test-output"), new File(AutomationConfigProperties.testNGOutputDirectory + defaultUniqueFolderOffset()));

        if (driverWrapper.notNull())
            driverWrapper.quit();
    }

    /**
     *  Create a directory offset to store reports and screenshots in the same folder structure.
     *  Subsequent calls get the same timestamp.
     *
     *  The offset is suite name + current date
     */
    private String defaultUniqueFolderOffset(){
        if(uniqueFolderOffset == null || uniqueFolderOffset.equals("")) {
            DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy hh_mm_ss a");
            Calendar cal = Calendar.getInstance();
            uniqueFolderOffset =  dateFormat.format(cal.getTime()) + " " + suiteName + " build_" + AutomationConfigProperties.buildNumber;
        }
        return uniqueFolderOffset;
    }

    private String setScreenshotConfigValue(){
        String gradleValue = System.getProperty("UNIQUE_FOLDER") + "/mobile_screenshots/";
        //TODO look into if this can be cleaned up, only use one config file
        //Check if this program run from gradle task. If so we would like to use values from the gradle build file. If this property does
        //not exist or if it is not true, values will instead be taken from the appconfig file. Note this only applies to values
        //which are both in the gradle folder and the appconfig file. This was originally created because the gradle output folder must be given
        //to gradle and it cannot cleanly come from the appconfig files. Because of this, values are coming from two different places.
        boolean useGradleValues = Boolean.getBoolean("USE_GRADLE_VALUES");

        if(useGradleValues){
            Logger.log("Using gradle property for screenshot folder " + gradleValue);
            return gradleValue;
        }
        else{
            return defaultUniqueFolderOffset() + "/mobile_screenshots/";
        }
    }

}
