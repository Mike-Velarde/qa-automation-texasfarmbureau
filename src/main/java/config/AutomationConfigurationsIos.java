package config;

import com.bottlerocket.utils.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Properties;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class AutomationConfigurationsIos extends AutomationConfigurations {

    /**
     * Set the variables which are needed to run the application. These are read in from a properties file.
     *
     */
    @Override
    public void loadConfigVariables(){
        Properties autoConfig = null;
        try {
            autoConfig = loadConfig();
        }
        catch (Exception ex){
            //ErrorHandler.printErr("Error occured while loading automation configurations. ", ex);
        }

        if(autoConfig == null) {
            Logger.log(AppDefaults.CONFIG_FILE_NAME + " not found, using default values");
            return;
        }
        /**
         * Capabilites
         */
        AppDefaults.apkLocation = autoConfig.getProperty("APK_LOCATION", AppDefaults.apkLocation);
        AppDefaults.appiumVersion = autoConfig.getProperty("APPIUM_VERSION", AppDefaults.appiumVersion);
        AppDefaults.platformName = autoConfig.getProperty("PLATFORM_NAME", AppDefaults.platformName);
        AppDefaults.platformVersion = autoConfig.getProperty("PLATFORM_VERSION", AppDefaults.platformVersion);
        AppDefaults.deviceName = autoConfig.getProperty("DEVICE_NAME", AppDefaults.deviceName);
        AppDefaults.appiumURL = autoConfig.getProperty("APPIUM_URL", AppDefaults.appiumURL);
        AppDefaults.noReset = getAsBoolean(autoConfig, "NO_RESET", AppDefaults.noReset);

        /**
         * App configurations
         */
        AppDefaults.screenshotsDirectory = autoConfig.getProperty("SCREEN_SHOTS", AppDefaults.screenshotsDirectory);
        AppDefaults.name = autoConfig.getProperty("TESTS_NAME", AppDefaults.name);
        AppDefaults.globalWait = getIntSafe(autoConfig.getProperty("GLOBAL_WAIT"), AppDefaults.globalWait);
        AppDefaults.testNGOutputDirectory = autoConfig.getProperty("TEST_OUTPUT_DIRECTORY", AppDefaults.testNGOutputDirectory);
        AppDefaults.buildNumber = autoConfig.getProperty("BUILD_NUMBER", AppDefaults.buildNumber);

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
