package main; /**
 * Created by ford.arnett on 10/2/15.
 */

import com.bottlerocket.webdriver.WebDriverWrapperAndroid;
import config.AppDefaults;
import operations.AutomationOperations;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;



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
        capabilities.setCapability("app", AppDefaults.apkLocation);
        capabilities.setCapability("appium-version", AppDefaults.appiumVersion);
        capabilities.setCapability("platformName", AppDefaults.platformName);
        capabilities.setCapability("platformVersion", AppDefaults.platformVersion);
        capabilities.setCapability("deviceName", AppDefaults.deviceName);
        capabilities.setCapability("name", AppDefaults.name + date);

        URL serverAddress;

        serverAddress = new URL("http://127.0.0.1:4723/wd/hub");
        driverWrapper = new WebDriverWrapperAndroid(serverAddress, capabilities, AppDefaults.globalWait);


        sessionId = driverWrapper.getSessionId().toString();

        driverWrapper.setImplicitWait(AppDefaults.globalWait, TimeUnit.SECONDS);

        initAutomationOperations();

        System.out.println("Running test method: " );
    }

    private void initAutomationOperations() {
        AutomationOperations automationOperations = AutomationOperations.instance();
        automationOperations.userOp.init(driverWrapper);
        automationOperations.navOp.init(driverWrapper);
        automationOperations.config.initConfigVariables();
    }


    /** Run after each test **/
    @AfterClass
    public void tearDown() throws Exception {
        if (driverWrapper.notNull()) driverWrapper.quit();
    }

}
