package config;

import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.utils.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Properties;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class AutomationConfigurationsAndroid extends AutomationConfigurations {

    /**
     * Set the variables which are needed to run the application. These are read in from a properties file.
     *
     */
    @Override
    public void loadConfigVariables(){

        if(automationProperties == null) {
            Logger.log(AppDefaults.PROPERTIES_DIRECTORY + "files not found, using default values");
            return;
        }
        /**
         * Capabilites
         */
        AppDefaults.apkLocation = automationProperties.getProperty("APK_LOCATION", AppDefaults.apkLocation);
        AppDefaults.appiumVersion = automationProperties.getProperty("APPIUM_VERSION", AppDefaults.appiumVersion);
        AppDefaults.platformName = automationProperties.getProperty("PLATFORM_NAME", AppDefaults.platformName);
        AppDefaults.platformVersion = automationProperties.getProperty("PLATFORM_VERSION", AppDefaults.platformVersion);
        AppDefaults.deviceName = automationProperties.getProperty("DEVICE_NAME", AppDefaults.deviceName);
        AppDefaults.appiumURL = automationProperties.getProperty("APPIUM_URL", AppDefaults.appiumURL);
        AppDefaults.noReset = getAsBoolean(automationProperties, "NO_RESET", AppDefaults.noReset);

        /**
         * App configurations
         */
        AppDefaults.screenshotsDirectory = automationProperties.getProperty("SCREEN_SHOTS", AppDefaults.screenshotsDirectory);
        AppDefaults.name = automationProperties.getProperty("TESTS_NAME", AppDefaults.name);
        AppDefaults.globalWait = getIntSafe(automationProperties.getProperty("GLOBAL_WAIT"), AppDefaults.globalWait);
        AppDefaults.testNGOutputDirectory = automationProperties.getProperty("TEST_OUTPUT_DIRECTORY", AppDefaults.testNGOutputDirectory);
        AppDefaults.buildNumber = automationProperties.getProperty("BUILD_NUMBER", AppDefaults.buildNumber);

    }

    @Override
    public DesiredCapabilities setCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", AppDefaults.apkLocation);
        capabilities.setCapability("appium-version", AppDefaults.appiumVersion);
        capabilities.setCapability("platformName", AppDefaults.platformName);
        capabilities.setCapability("platformVersion", AppDefaults.platformVersion);
        capabilities.setCapability("deviceName", AppDefaults.deviceName);
        capabilities.setCapability("name", AppDefaults.name);
        capabilities.setCapability("noReset", AppDefaults.noReset);
        return capabilities;
    }
}
