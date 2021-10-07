package automationtestinstance;

/*
  Created by ford.arnett on 10/6/21
 */

import com.bottlerocket.config.*;
import com.bottlerocket.driverwrapper.AppiumDriverWrapperAndroid;
import com.bottlerocket.driverwrapper.AppiumDriverWrapperIos;
import com.bottlerocket.driverwrapper.WebDriverWrapperGeneric;
import com.bottlerocket.reporters.*;
import com.bottlerocket.utils.*;
import config.*;
import io.appium.java_client.service.local.*;
import org.apache.commons.lang3.*;
import org.openqa.selenium.remote.*;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;


/**
 * initializes driverWrapper, reporter, operations, and configs for the test run
 *
 */
public class AutomationTestInitializer {
    public DeviceAutomationComponents deviceAutomationComponents;
    AutomationTestManager am;

    /**
     * Starts up all components of the automation system and gets them ready for use. This must be done before any of the system objects are to be used.
     *
     *
     * @return the newly created WebDriverWrapper
     * @throws Exception if an error occurred
     */
    public AutomationTestManager initializeAutomationSystem(TestDataManager testDataManager) throws Exception{
//        DeviceAutomationComponents device;
        AutomationConfigurations.loadConfig();


        AutomationTestManager am = new AutomationTestManager();
        DesiredCapabilities capabilities = setupFirst(am);
        //Example to set a capability based on parameters used to start the test
        //testDataManager.setEnvCapabilities(capabilities, testDataManager.getCurrentEnvType(), testDataManager.getCurrentLocale());

        if (AutomationConfigurations.isAndroid()) {
            initializeAndroidSystem(am, capabilities);
        } else if (AutomationConfigurations.isIos()) {
            initializeIosSystem(am, capabilities);
        } else if (AutomationConfigurations.isWeb()) {
            initializeSeleniumSystem(am, capabilities);
        } else {
            throw new Error("Operating system not recognized, check config files");
        }

        selectReporter(am);
        am.initializeComponents();

        return am;
    }

    private AutomationTestManager initializeAndroidSystem(AutomationTestManager testManager, DesiredCapabilities capabilities) throws Exception {
        URL serverAddress;
        if (AutomationConfigProperties.customAppiumInstance) {
            serverAddress = createAppiumSession();
        } else {
            serverAddress = new URL(AutomationConfigProperties.appiumURL);
        }

        testManager.driverWrapper = new AppiumDriverWrapperAndroid(serverAddress, capabilities, AutomationConfigProperties.globalWait);

        return testManager;

    }

    private void initializeIosSystem(AutomationTestManager am, DesiredCapabilities capabilities) throws Exception{
        URL serverAddress;
        if (AutomationConfigProperties.customAppiumInstance) {
            serverAddress = createAppiumSession();
        } else {
            serverAddress = new URL(AutomationConfigProperties.appiumURL);
        }

        am.driverWrapper = new AppiumDriverWrapperIos(serverAddress, capabilities, AutomationConfigProperties.globalWait);
    }

    private AutomationTestManager initializeSeleniumSystem(AutomationTestManager am, DesiredCapabilities capabilities) throws Exception {
        am.driverWrapper = new WebDriverWrapperGeneric(capabilities, AutomationConfigProperties.globalWait, AutomationConfigProperties.browserName);


        return am;
    }

    public AutomationTestManager initializeAPITestingSuite() {
        throw new NotImplementedException("do this later");
    }

    private URL createAppiumSession() {
        File logPathDir = new File(AutomationConfigProperties.reportOutputDirectory);
        //noinspection ResultOfMethodCallIgnored
        logPathDir.mkdirs();
        File logPathFile = new File(logPathDir + "/appium_out.txt");
        Logger.log("Appium logs file " + logPathFile.getAbsolutePath());

        AppiumServiceBuilder builder = new AppiumServiceBuilder().usingAnyFreePort().withLogFile(logPathFile);
        am.appiumService = AppiumDriverLocalService.buildService(builder);
        am.appiumService.start();

        silenceAppiumConsoleLogging();

        return am.appiumService.getUrl();
    }

    /**
     * This will remove the appium logs from System out. From what I can tell this should have been added in a recent update by default but I'm still seeing the appium output in the console.
     * I would love to get rid of this entire method once the default behavior changes.
     * https://github.com/appium/java-client/pull/483
     */
    private void silenceAppiumConsoleLogging() {
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
            ((ArrayList<OutputStream>) streamsField.get(streamField.get(am.appiumService))).clear(); // remove System.out logging
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup methods that must be run first before other setup methods can take place.
     *
     */
    private DesiredCapabilities setupFirst(AutomationTestManager testManager) throws Exception {
        DeviceAutomationComponents device;
        //Set iOS or Android here
        if (AutomationConfigurations.isAndroid()) {
            device = new AndroidAutomationComponents();
        } else if (AutomationConfigurations.isIos()) {
            device = new IosAutomationComponents();
        } else {
            throw new Exception("Platform not recognized");
        }
        //Set configurations
        setComponentsInManager(device, testManager);
        testManager.config.loadConfigVariables();

        return testManager.config.setCapabilities();
    }

    public void setComponentsInManager(DeviceAutomationComponents deviceAutomationComponents, AutomationTestManager automationTestManager) {
        this.deviceAutomationComponents = deviceAutomationComponents;
        deviceAutomationComponents.initResourceLocator();
        automationTestManager.config = deviceAutomationComponents.getAutomationConfigurations();
        automationTestManager.navOp = deviceAutomationComponents.getNavigationOperations();
        automationTestManager.userOp = deviceAutomationComponents.getUserOperations();
        automationTestManager.assertions = deviceAutomationComponents.getAssertions();
    }

    /**
     * Select the reporter to be used in the system. Currently there's only one we would want to use, however we can just use an if statement looking at @see       * Select the reporter to be used in the system. Currently there's only one we would want to use, however we can just use an if statement looking at {@link AutomationTestManager#reporter} to support selecting one at runtime. to support selecting one at runtime.
     *
     * @param automationTestManager The test manager to set the reporter of
     */
    private void selectReporter(AutomationTestManager automationTestManager) {
        String fileName = AutomationConfigProperties.reportOutputDirectory + "/report";
        if (automationTestManager.reporter == null) {
            automationTestManager.reporter = new ExtentReporter(fileName);
        }
        Logger.log("Reports are being logged with the Extent reporter at " + fileName + ".html");

        automationTestManager.reporter.initializeReporter(false);
        automationTestManager.driverWrapper.setAutomationReporter(automationTestManager.reporter);
        setSystemInfo(automationTestManager.reporter);
    }


    private static void setSystemInfo(AutomationReporter reporter) {
        if (reporter.getTest() == null) {
            reporter.addSystemInfo("Project Name", AutomationConfigProperties.projectName);
            reporter.addSystemInfo("Device Name", AutomationConfigProperties.deviceName);
            reporter.addSystemInfo("Udid", AutomationConfigProperties.udid);
            reporter.addSystemInfo("Device Version", AutomationConfigProperties.platformVersion);
            reporter.addSystemInfo("Build Number", AutomationConfigProperties.buildNumber);
            reporter.addSystemInfo("Global Wait", String.valueOf(AutomationConfigProperties.globalWait));
        }
    }

}

