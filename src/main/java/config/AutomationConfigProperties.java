package config;

/**
 * Created by ford.arnett on 9/2/15.
 */
public class AutomationConfigProperties {

    /**
     * Capabilities
     */
    public static String appPath;
    public static String appiumVersion;
    public static String platformName;
    public static String platformVersion;
    public static String deviceName;
    public static String name;
    public static String uuid;

    /**
     * Properties
     */
    //used to initiate driver
    public static String appiumURL = "http://127.0.0.1:4723/wd/hub";
    public static boolean noReset = false;
    //used to initiate driver wait
    public static int globalWait = 20;

    public static String screenshotsDirectory = "../../automation/awe_tests/";
    public static String testNGOutputDirectory = "../../automation/awe_tests/";
    public static String buildNumber = "284";


    public static final String PROPERTIES_DIRECTORY = "src/main/resources/";
    public static final String OPERATING_SYSTEM_PROPERTY_FILE = PROPERTIES_DIRECTORY + "appconfig.properties";
    public static final String AUTOMATION_CONFIG_IOS_PROPERTIES_FILE = PROPERTIES_DIRECTORY + "appconfig_ios.properties";
    public static final String AUTOMATION_CONFIG_ANDROID_PROPERTIES_FILE = PROPERTIES_DIRECTORY + "appconfig_android.properties";
}
