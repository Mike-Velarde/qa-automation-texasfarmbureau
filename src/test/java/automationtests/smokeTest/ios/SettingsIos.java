package automationtests.smoketest.ios;

/**
 * Created by ford.arnett on 11/17/15.
 */

import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.webdriverwrapper.WebDriverWrapperIos;
import config.ResourceLocator;
import config.ResourceLocatorIos;
import automationtests.dataproviders.Endpoints;
import appium.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import webinteractions.WebsiteConnection;

@Test(groups = {"ios"})
/**
 * @deprecated I think we can delete this class
 */
public class SettingsIos extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() throws WebDriverWrapperException {
        //Wait for tool bar to show up, need to fix the is toolbar visible method
        driverWrapper.waitLogErr(7000);
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
    }

    /**
     * Test webviews by making sure the endpoints are valid, and taking a screenshot of the contents
     *
     * @param testType
     * @param title
     * @param URL
     */
    @Test(dataProvider = "endpoints", dataProviderClass = Endpoints.class)
    public void testWebViewOptions(String testType, String title, String URL){
        AutomationOperations.instance().navOp.settings.navigateToSettingsOption(title);
        int responseCode = AutomationOperations.instance().navOp.settings.testWebContentEndpoint(URL);
        //wait for page to load
        driverWrapper.waitLogErr(9000);
        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "webpage_load_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("Error taking screenshot ", e);
        }
        assertionLogger.setTestType("Check the response code from the end points");
        assertionLogger.assertTrue(WebsiteConnection.responseAcceptable(responseCode));
    }

    @Test
    public void testDeveloperOptions() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.settings.navigateToSettingsOption(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE_ID);
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_MAIN_LIST);
        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    @Test
    public void testFeedback(){
        AutomationOperations.instance().navOp.settings.navigateToSettingsOption(ResourceLocator.device.AWE_SETTINGS_FEEDBACK_TITLE_ID);

        assertionLogger.setTestType("Verify that the email feedback form is displayed");
        //Verify email form is displayed
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.verifyFeedbackScreen());
        ((WebDriverWrapperIos)driverWrapper).cancel();

        driverWrapper.getElementById(ResourceLocatorIos.AWE_SETTINGS_EMAIL_DELETE_DRAFT).click();
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }


}