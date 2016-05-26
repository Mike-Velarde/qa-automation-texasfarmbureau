package smokeTest.generic;

/**
 * Created by ford.arnett on 11/17/15.
 */

import assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.config.AutomationConfigProperties;
import config.ResourceLocator;
import dataproviders.Endpoints;
import dataproviders.Titles;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import webinteractions.WebsiteConnection;

public class Settings extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
    }

    @Test(dataProvider = "settings-verify-titles", dataProviderClass = Titles.class, groups = {"android"})
    public void testSettingsTitles(String testType, String buttonID, String title){
        AutomationOperations.instance().navOp.settings.navigateToSettingsOption(buttonID);
        assertionLogger.setTestType("Verify the screen title is the title we expected");
        try {
            assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), title);
        }
        //Go back no matter what
        finally {
            AutomationOperations.instance().navOp.mainToolbarBack();
        }
    }

    /**
     * Test webviews by making sure the endpoints are valid, and taking a screenshot of the contents
     *
     * @param testType
     * @param title
     * @param URL
     */
    @Test(dataProvider = "settings-endpoints", dataProviderClass = Endpoints.class)
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
        AutomationOperations.instance().navOp.settings.backFromSettingsOption();
    }

    @Test
    public void testDeveloperOptions(){
        AutomationOperations.instance().navOp.settings.navigateToSettingsOption(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE_ID);
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_MAIN_LIST);
        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    @Test
    public void testFeedback(){
        AutomationOperations.instance().navOp.settings.navigateToSettingsOption(ResourceLocator.device.AWE_SETTINGS_FEEDBACK_TITLE_ID);
        //See if we are on the gmail app
        assertionLogger.assertEquals("Compose", AutomationOperations.instance().navOp.settings.getGmailComposeTitle());
        driverWrapper.hideKeyboard();
        //The awe back button will not return us to the app, must use device back
        driverWrapper.back();
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }


}