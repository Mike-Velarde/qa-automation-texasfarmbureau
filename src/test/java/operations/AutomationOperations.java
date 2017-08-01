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
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import operations.navops.NavigationOperations;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;

/**
 * Head object for all operations that a test can use
 *
 * Created by ford.arnett on 9/3/15.
 */
public class AutomationOperations {
    public DeviceAutomationComponents deviceAutomationComponents;

    private AutomationOperations() {
    }

    private static class SingletonHelper {
        private static final AutomationOperations INSTANCE = new AutomationOperations();
    }

    public static AutomationOperations instance() {
        return SingletonHelper.INSTANCE;
    }

    public UserOperations userOp;
    public NavigationOperations navOp;
    public AutomationConfigurations config;
    public AssertionLibrary assertions;
    public AutomationReporter reporter;
    public AppiumDriverLocalService appiumService;

    /**
     * Starts up all components of the automation system and gets them ready for use. This must be done before any of the system objects are to be used.
     *
     * @return the newly created WebDriverWrapper
     * @throws Exception if an error occurred
     */
    public static WebDriverWrapper initializeAutomationSystem() throws Exception {
        WebDriverWrapper driverWrapper;
        DesiredCapabilities capabilities = setupFirst();

        URL serverAddress;
        if(AutomationConfigProperties.customAppiumInstance) {
            serverAddress = createAppiumSession();
        } else {
            serverAddress = new URL(AutomationConfigProperties.appiumURL);
        }

        Logger.log("Appium URL is " + serverAddress);
        if (AutomationConfigurations.isAndroid()) {
            driverWrapper = new WebDriverWrapperAndroid(serverAddress, capabilities, AutomationConfigProperties.globalWait);
        } else if (AutomationConfigurations.isIos()) {
            driverWrapper = new WebDriverWrapperIos(serverAddress, capabilities, AutomationConfigProperties.globalWait);
        } else {
            throw new Error("Operating system not recognized, check config files");
        }
        setupLast(driverWrapper);
        return driverWrapper;
    }

    private static URL createAppiumSession() {
        File logPathDir = new File(AutomationConfigProperties.reportOutputDirectory);
        //noinspection ResultOfMethodCallIgnored
        logPathDir.mkdirs();
        File logPathFile = new File(logPathDir + "/appium_out.txt");
        Logger.log("Appium logs file " + logPathFile.getAbsolutePath());

        AppiumServiceBuilder builder = new AppiumServiceBuilder().usingAnyFreePort().withLogFile(logPathFile);
        AutomationOperations.instance().appiumService = AppiumDriverLocalService.buildService(builder);
        AutomationOperations.instance().appiumService.start();

        silenceAppiumConsoleLogging();

        return AutomationOperations.instance().appiumService.getUrl();
    }

    /**
     * This will remove the appium logs from System out. From what I can tell this should have been added in a recent update by default but I'm still seeing the appium output in the console.
     * I would love to get rid of this entire method once the default behavior changes.
     * https://github.com/appium/java-client/pull/483
     */
    private static void silenceAppiumConsoleLogging() {
        Field streamField = null;
        Field streamsField = null;
        try {
            streamField = AppiumDriverLocalService.class.getDeclaredField("stream");
            streamField.setAccessible(true);
            streamsField = Class.forName("io.appium.java_client.service.local.ListOutputStream")
                    .getDeclaredField("streams");
            streamsField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            ((ArrayList<OutputStream>) streamsField.get(streamField.get(AutomationOperations.instance().appiumService))).clear(); // remove System.out logging
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
        if (AutomationConfigurations.isAndroid()) {
            device = new AndroidAutomationComponents();
        } else {
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

    public void initOperations(DeviceAutomationComponents deviceAutomationComponents) {
        this.deviceAutomationComponents = deviceAutomationComponents;
        deviceAutomationComponents.initResourceLocator();
        config = deviceAutomationComponents.getAutomationConfigurations();
        navOp = deviceAutomationComponents.getNavigationOperations();
        userOp = deviceAutomationComponents.getUserOperations();
        assertions = deviceAutomationComponents.getAssertions();
    }

    /**
     * Select the reporter to be used in the system. Currently there's only one we would want to use, however we can just use an if statement looking at {@link #reporter} to support selecting one at runtime.
     *
     * @param driverWrapper
     */
    private void selectReporter(WebDriverWrapper driverWrapper) {
        String fileName = AutomationConfigProperties.reportOutputDirectory + "/report";
        AutomationOperations.instance().reporter = new ExtentReporter(fileName);
        Logger.log("Reports are being logged with the Extent reporter at " + fileName + ".html");

        AutomationOperations.instance().reporter.initializeReporter();
        driverWrapper.setAutomationReporter(reporter);
        setSystemInfo(reporter);

    }

    private void setSystemInfo(AutomationReporter reporter) {
        reporter.addSystemInfo("Project Name", AutomationConfigProperties.PROJECT_NAME);
        reporter.addSystemInfo("Device Name", AutomationConfigProperties.deviceName);
        reporter.addSystemInfo("Udid", AutomationConfigProperties.udid);
        reporter.addSystemInfo("Device Version", AutomationConfigProperties.platformVersion);
        reporter.addSystemInfo("Build Number", AutomationConfigProperties.buildNumber);
        reporter.addSystemInfo("Global Wait", String.valueOf(AutomationConfigProperties.globalWait));
    }
}