package operations;


import automationtests.assertions.AssertionLibrary;
import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.config.AutomationConfigurations;
import com.bottlerocket.reporters.AutomationReporter;
import com.bottlerocket.reporters.ExtentReporter;
import com.bottlerocket.utils.Logger;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import com.bottlerocket.webdriverwrapper.WebDriverWrapperAndroid;
import com.bottlerocket.webdriverwrapper.WebDriverWrapperIos;
import config.AndroidAutomationComponents;
import config.DeviceAutomationComponents;
import config.IosAutomationComponents;
import operations.navops.NavigationOperations;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

/**
 * Head object for all operations that a test can use
 *
 * Created by ford.arnett on 9/3/15.
 */
public class AutomationOperations {
    public DeviceAutomationComponents deviceAutomationComponents;
    private AutomationOperations(){}
    private static class SingletonHelper{
        private static final AutomationOperations INSTANCE = new AutomationOperations();
    }
    public static AutomationOperations instance(){
        return SingletonHelper.INSTANCE;
    }
    public UserOperations userOp;
    public NavigationOperations navOp;
    public AutomationConfigurations config;
    public AssertionLibrary assertions;
    public AutomationReporter reporter;
    /**
     * Starts up all components of the automation system and gets them ready for use. This must be done before any of the system objects are to be used.
     *
     * @return the newly created WebDriverWrapper
     * @throws Exception if an error occurred
     */
    public static WebDriverWrapper initializeAutomationSystem() throws Exception {
        WebDriverWrapper driverWrapper;
        DesiredCapabilities capabilities = setupFirst();
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
        setupLast(driverWrapper);
        return driverWrapper;
    }
    /**
     * Setup methods that must be run first before other setup methods can take place.
     *
     * @throws Exception
     */
    private static DesiredCapabilities setupFirst() throws Exception {
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
        return AutomationOperations.instance().config.setCapabilities();
    }
    /**
     * These must be run last since they need an initialized driver wrapper and that is one of the last things ready.
     *
     * @param driverWrapper
     */
    private static void setupLast(WebDriverWrapper driverWrapper) {
        AutomationOperations automationOperations = AutomationOperations.instance();
        automationOperations.userOp.init(driverWrapper);
        automationOperations.navOp.init(driverWrapper);
        AutomationOperations.instance().selectReporter(driverWrapper);
    }
    public void initOperations(DeviceAutomationComponents deviceAutomationComponents){
        this.deviceAutomationComponents = deviceAutomationComponents;
        deviceAutomationComponents.initResourceLocator();
        config = deviceAutomationComponents.getAutomationConfigurations();
        navOp = deviceAutomationComponents.getNavigationOperations();
        userOp = deviceAutomationComponents.getUserOperations();
        assertions = deviceAutomationComponents.getAssertions();
    }
    private void selectReporter(WebDriverWrapper driverWrapper) {
        if (AutomationConfigProperties.reporter.equalsIgnoreCase("extent")) {
            String fileName = AutomationConfigProperties.reportOutputDirectory + "report";
            AutomationOperations.instance().reporter = new ExtentReporter(fileName);
            Logger.log("Reports are being logged with the Extent reporter at " + fileName);
        }
        else if(AutomationConfigProperties.reporter.equalsIgnoreCase("default")) {
            //not yet sure what it will look like to use the regular reporter. May just be an empty implementation that does nothing
            AutomationOperations.instance().reporter = null;
        }
        AutomationOperations.instance().reporter.initializeReporter();
        driverWrapper.setAutomationReporter(reporter);
    }
}