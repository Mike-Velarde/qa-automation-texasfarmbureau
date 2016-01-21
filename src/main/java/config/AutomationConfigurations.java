package config;



import com.bottlerocket.utils.ErrorHandler;
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
        InputStream operatingSystemStream = new FileInputStream(AppDefaults.OPERATING_SYSTEM_PROPERTY_FILE);
        platformProperty.load(operatingSystemStream);

        platform = platformProperty.getProperty("PLATFORM_NAME");

        InputStream propertiesStream;
        if(isAndroid()){
            propertiesStream = new FileInputStream(AppDefaults.AUTOMATION_CONFIG_ANDROID_PROPERTIES_FILE);
        }
        else if(isIos()){
            propertiesStream = new FileInputStream(AppDefaults.AUTOMATION_CONFIG_IOS_PROPERTIES_FILE);
        }
        else {
            throw new Exception("No recognized operating system specified in " + AppDefaults.OPERATING_SYSTEM_PROPERTY_FILE);
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
     *
     */
    public abstract void loadConfigVariables();

    public abstract DesiredCapabilities setCapabilities();

}
