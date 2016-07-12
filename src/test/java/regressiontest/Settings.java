package regressiontest;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bottlerocket.config.AutomationConfigProperties;

import assertions.AssertionLogger;
import config.ResourceLocator;
import dataproviders.Titles;
import main.AppiumMain;
import operations.AutomationOperations;

/**
 * Created by Mahendranath.Nampally on 04/04/2016.
 */
public class Settings extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();
    // The test step serial number used in the for loop.
    int count = 2;

    @BeforeClass
    public void setup() throws WebDriverWrapperException {
        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
    }

    @Test(enabled = true, priority = 1)
    public void testSettingsDetails() throws WebDriverWrapperException {
        // Launch the navigation bar
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        // Verify the Settings screen
        assertionLogger.setTestType("Test Settings availble in the main drawer: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.settings.toString()));
        AutomationOperations.instance().navOp.closeMainDrawer();

        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        // Verify page title as Settings
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Settings_02_verify_hamburger_brand_logo_" + System.currentTimeMillis());

    }

    @Test(dataProvider = "settings-verify-titles", dataProviderClass = Titles.class, groups = { "android" }, enabled = true, priority = 2)
    public void testSettingsOptions(String testType, String buttonID, String title) {
        // Launch the settings option
        AutomationOperations.instance().navOp.settings.navigateToSettingsOption(buttonID);
        assertionLogger.setTestType("Verify the screen title is the title we expected");
        try {
            assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), title);
            if (!title.equalsIgnoreCase(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE)) {
                assertionLogger.setTestType("Test for web view: ");
                assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());
                driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Settings_" + (++count) + "_verify_" + title + "_web_view_" + System.currentTimeMillis());
            }
        } finally {
            driverWrapper.back();
        }
    }

    @Test(enabled = true, priority = 3)
    public void testFeedbackAndVersion() throws WebDriverWrapperException {
        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);

        // Launch the Feedback option
        AutomationOperations.instance().navOp.settings.navigateToSettingsOption(ResourceLocator.device.AWE_SETTINGS_FEEDBACK_TITLE_ID);

        // Click on email option
        AutomationOperations.instance().navOp.settings.clickOnEmail();

        // Click on the back button
        driverWrapper.back();

        // Verify compose screen and app version, Device, OS version and Connection Type
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Settings_07_verify_pre_populated_instructions_" + System.currentTimeMillis());

        // Click on the back button
        driverWrapper.back();

        // Verify Video over wifi set to on
        assertionLogger.setTestType("Test is video over wifi is active : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasVideoOverWifi());

        // Verify for the version info
        assertionLogger.setTestType("Test for version information: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasVersionInfo());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Settings_14_verify_version_info_" + System.currentTimeMillis());

        // Long press on the version info
        AutomationOperations.instance().navOp.settings.doLongPressOnVersion();

        // Verify for the more build information
        assertionLogger.setTestType("Test for more build information : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasBuildInfo());

        // Verify for the build information
        assertionLogger.setTestType("Test for build information : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.getBuildInfo().contains("Build Properties"));

        driverWrapper.back();
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }

}
