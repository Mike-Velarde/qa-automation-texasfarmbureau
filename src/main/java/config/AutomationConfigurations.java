package config;



import com.bottlerocket.utils.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * It may not be necessary for loadConfigVariables and setCapabilities to be abstract, this needs to be reevaluated.
 * It seems like the subclasses may not have enough difference to need different methods, especially when the configurations themselves are passed in.
 *
 * Created by ford.arnett on 10/9/15.
 */
public abstract class AutomationConfigurations {
    private static String platform;
    protected static Properties automationProperties = new Properties();

    /**
     * Load properties from the properties file
     * @return
     * @throws Exception
     */
    public static Properties loadConfig() throws Exception{
        Properties platformProperty = new Properties();

        //load the operating system type
        InputStream operatingSystemStream = new FileInputStream(AutomationConfigProperties.OPERATING_SYSTEM_PROPERTY_FILE);
        platformProperty.load(operatingSystemStream);

        platform = platformProperty.getProperty("PLATFORM_NAME");

        InputStream propertiesStream;
        if(isAndroid()){
            propertiesStream = new FileInputStream(AutomationConfigProperties.AUTOMATION_CONFIG_ANDROID_PROPERTIES_FILE);
        }
        else if(isIos()){
            propertiesStream = new FileInputStream(AutomationConfigProperties.AUTOMATION_CONFIG_IOS_PROPERTIES_FILE);
        }
        else {
            throw new Exception("No recognized operating system specified in " + AutomationConfigProperties.OPERATING_SYSTEM_PROPERTY_FILE);
        }

        automationProperties.load(propertiesStream);

        return automationProperties;
    }

    public static boolean isIos() {
        return platform.equalsIgnoreCase("IOS");
    }

    public static boolean isAndroid(){
        return platform.equalsIgnoreCase("Android");
    }

    protected int getIntSafe(String s, int defaultValue){
        try{
            return Integer.parseInt(s);
        }
        catch (NumberFormatException ex){
            return defaultValue;
        }
    }

    protected boolean getAsBoolean(Properties prop, String property, boolean defaultValue){
        String booleanProp = prop.getProperty(property);
        if(booleanProp == null)
            return defaultValue;
        return Boolean.parseBoolean(booleanProp);
    }

    /**
     * Set the variables which are needed to run the application. These are read in from a properties file.
     * Common properties are set here, and subclasses may set additional properties if needed
     *
     */
    public void loadConfigVariables(){
        if(automationProperties == null) {
            Logger.log(AutomationConfigProperties.PROPERTIES_DIRECTORY + "files not found, using default values");
            return;
        }

        /**
         * App configurations
         */
        AutomationConfigProperties.name = automationProperties.getProperty("TESTS_NAME");
        AutomationConfigProperties.buildNumber = automationProperties.getProperty("BUILD_NUMBER");
        AutomationConfigProperties.screenshotsDirectory = automationProperties.getProperty("SCREEN_SHOTS", AutomationConfigProperties.screenshotsDirectory);
        AutomationConfigProperties.testNGOutputDirectory = automationProperties.getProperty("TEST_OUTPUT_DIRECTORY", AutomationConfigProperties.testNGOutputDirectory);
        AutomationConfigProperties.globalWait = getIntSafe(automationProperties.getProperty("GLOBAL_WAIT"), AutomationConfigProperties.globalWait);

        /**
         * Capabilities
         */
        AutomationConfigProperties.appiumVersion = automationProperties.getProperty("APPIUM_VERSION");
        AutomationConfigProperties.platformName = automationProperties.getProperty("PLATFORM_NAME");
        AutomationConfigProperties.platformVersion = automationProperties.getProperty("PLATFORM_VERSION");
        AutomationConfigProperties.deviceName = automationProperties.getProperty("DEVICE_NAME");
        AutomationConfigProperties.appiumURL = automationProperties.getProperty("APPIUM_URL", AutomationConfigProperties.appiumURL);
        AutomationConfigProperties.noReset = getAsBoolean(automationProperties, "NO_RESET", AutomationConfigProperties.noReset);
    }

    public DesiredCapabilities setCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", AutomationConfigProperties.appPath);
        capabilities.setCapability("appium-version", AutomationConfigProperties.appiumVersion);
        capabilities.setCapability("platformName", AutomationConfigProperties.platformName);
        capabilities.setCapability("platformVersion", AutomationConfigProperties.platformVersion);
        capabilities.setCapability("deviceName", AutomationConfigProperties.deviceName);
        capabilities.setCapability("name", AutomationConfigProperties.name);
        capabilities.setCapability("noReset", AutomationConfigProperties.noReset);
        return capabilities;
    }

}
