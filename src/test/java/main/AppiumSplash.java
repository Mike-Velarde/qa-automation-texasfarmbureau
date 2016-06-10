package main;

import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.utils.Logger;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * This class is really just documenting some code that was written.
 *
 * This is the very barebones to start up an appium server. This code was used to take screenshots of
 * the splash screen because the main code takes too long to initialize to take screenshots of the splash screen (at least that seems to be my observation).
 * This may need to be experimented more to see if we can take a screenshot earlier in the appium start up process. Even if this is figured out, it gets very messy
 * to maintain because now essentially you have test cases in your startup code, which is really nasty.
 *
 * Created by ford.arnett on 12/7/15.
 */
public class AppiumSplash {
    AndroidDriver driver;

    /** Run before each test **/
    @BeforeClass
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "/Users/ford.arnett/Library/Developer/CoreSimulator/Devices/9CC0F6C1-39A2-4B48-930C-3A1AC3CD27D7/data/Containers/Bundle/Application/012751D9-2415-42AC-A2FB-073518D23FD1/Reference.app");
        capabilities.setCapability("platformName", "ios");
        capabilities.setCapability("platformVersion", "9.2");
        capabilities.setCapability("deviceName", "iPad Air");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("udid", "9CC0F6C1-39A2-4B48-930C-3A1AC3CD27D7");

        URL serverAddress = new URL("http://127.0.0.1:4723/wd/hub");
        AndroidDriver driver = new AndroidDriver(serverAddress, capabilities);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        long time = System.currentTimeMillis();
        while(System.currentTimeMillis() < time + 10000){
            File srcFiler = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFiler, new File(AutomationConfigProperties.screenshotsDirectory + "/testing/splash_screen_abc" + System.currentTimeMillis()));
        }

    }

    @Test
    public void doSomething(){
        Logger.log("test");
    }


    /** Run after each test **/
    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null)
            driver.quit();
    }
}
