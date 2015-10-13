package config;



import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ford.arnett on 10/9/15.
 */
public class AutomationConfigurations {

    /**
     * Load properties from the properties file
     * @return
     * @throws Exception
     */
    public static Properties loadConfig() throws Exception{
        Properties properties = new Properties();

        InputStream inputStream = AutomationConfigurations.class.getClassLoader().getResourceAsStream(AppDefaults.CONFIG_FILE_NAME);
        if(inputStream != null){
            properties.load(inputStream);
        }
        else{
            throw new FileNotFoundException("Properties file " + properties);
        }

        return properties;
    }

    /**
     * Set the variables which are needed to run the application. These are read in from a properties file.
     *
     */
    public void initConfigVariables(){
        Properties autoConfig = null;
        try {
            autoConfig = loadConfig();
        }
        catch (Exception ex){
            //ErrorHandler.printErr("Error occured while loading automation configurations. ", ex);
        }

        if(autoConfig == null) {
            System.out.println(AppDefaults.CONFIG_FILE_NAME + " not found, using default values");
            return;
        }

        AppDefaults.apkLocation = autoConfig.getProperty("APK_LOCATION", AppDefaults.apkLocation);
        AppDefaults.screenshots = autoConfig.getProperty("SCREEN_SHOTS", AppDefaults.screenshots);
        AppDefaults.appiumVersion = autoConfig.getProperty("APPIUM_VERSION", AppDefaults.appiumVersion);
        AppDefaults.platformName = autoConfig.getProperty("PLATFORM_NAME", AppDefaults.platformName);
        AppDefaults.platformVersion = autoConfig.getProperty("PLATFORM_VERSION", AppDefaults.platformVersion);
        AppDefaults.deviceName = autoConfig.getProperty("DEVICE_NAME", AppDefaults.deviceName);
        AppDefaults.name = autoConfig.getProperty("TESTS_NAME", AppDefaults.name);
        AppDefaults.appiumURL = autoConfig.getProperty("APPIUM_URL", AppDefaults.deviceName);
        AppDefaults.globalWait = getIntSafe(autoConfig.getProperty("GLOBAL_WAIT"), AppDefaults.globalWait);
        AppDefaults.resetApp = getAsBoolean(autoConfig, "RESET_APP");

    }

    private int getIntSafe(String s, int defaultValue){
        try{
           return Integer.parseInt(s);
        }
        catch (NumberFormatException ex){
            return defaultValue;
        }
    }

    private boolean getAsBoolean(Properties prop, String property){
        String booleanProp = prop.getProperty(property);
        if(booleanProp == null)
            return AppDefaults.resetApp;
        return Boolean.parseBoolean(booleanProp);
    }
}
