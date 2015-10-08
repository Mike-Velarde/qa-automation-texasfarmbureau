/**
 * Created by ford.arnett on 10/2/15.
 */

import domod.CreditCard;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import config.AppDefaults;
import operations.AutomationOperations;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import webDriver.WebDriverWrapperAndroid;

public class AppiumMain{
    protected WebDriverWrapperAndroid driverWrapper;


/*    @Rule
    public TestRule printTests = new TestWatcher() {
        protected void starting(Description description) {
            System.out.print("  test: " + description.getMethodName());
        }

        protected void finished(Description description) {
            final String session = sessionId;

            if (session != null) {
                System.out.println(" " + session);
            } else {
                System.out.println();
            }
        }
    };*/

    private String sessionId;

    /** Keep the same date prefix to identify job sets. **/
    private static Date date = new Date();



    /** Run before each test **/
    @BeforeClass
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium-version", "1.0");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "4.4");
        capabilities.setCapability("deviceName", "Galaxy Note Edge ");
        capabilities.setCapability("name", "Automation tests " + date);
        //String userDir = System.getProperty("user.dir");

        URL serverAddress;
        //String apkPath = System.getProperty("apkPath");

        capabilities.setCapability("app", AppDefaults.APK_LOCATION);
        serverAddress = new URL("http://127.0.0.1:4723/wd/hub");
        driverWrapper = new WebDriverWrapperAndroid(serverAddress, capabilities);


        sessionId = driverWrapper.getSessionId().toString();

        driverWrapper.setImplicitWait(AppDefaults.GLOBAL_WAIT, TimeUnit.SECONDS);

        initAutomationOperations();

        System.out.println("Running test method: " );
    }

    private void initAutomationOperations() {
        AutomationOperations automationOperations = AutomationOperations.instance();
        automationOperations.userOp.init(driverWrapper);
        automationOperations.navOp.init(driverWrapper);
    }

    /** Run after each test **/
    @AfterClass
    public void tearDown() throws Exception {
        if (driverWrapper.notNull()) driverWrapper.quit();
    }

}
