package config;

/**
 * Created by ford.arnett on 9/2/15.
 */
public class AppDefaults {
    public static final String CONFIG_FILE_NAME = "appconfig.properties";

    /**
     * Capabilities
     */
    public static String apkLocation = "/Users/ford.arnett/apks/AWE/watcher-usaGoogle-debug-b224.apk";
    //public static String apkLocation = "/Users/ford.arnett/apks/AWE/watcher-usaGoogle-debug-b28.apk";
    public static String appiumVersion = "1.0";
    public static String platformName = "Android";
    public static String platformVersion = "4.4";
    public static String deviceName = "Galaxy Note Edge";
    public static String name = "Automation Tests";
    public static boolean noReset = false;

    //used to initiate driver
    public static String appiumURL = "http://127.0.0.1:4723/wd/hub";
    //used to initiate driver wait
    public static int globalWait = 20;

    public static String screenshots = "/Users/ford.arnett/automation/awe_tests/mobile_screenshots";
    public static String testNGOutputDirectory = "../../automation/awe_tests/";
    public static String buildNumber = "284";
}
