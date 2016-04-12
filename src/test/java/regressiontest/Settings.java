package regressiontest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bottlerocket.config.AutomationConfigProperties;

import assertions.AssertionLogger;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;

/**
 * Created by Mahendranath.Nampally on 04/04/2016.
 */
public class Settings extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testSettingsDetails() {
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
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings_02_verify_Hamburger_Brand logo_" + System.currentTimeMillis());

        // Click on the About brand
        AutomationOperations.instance().navOp.settings.launchAboutBrand();
        // Navigates to the About Brand screen
        assertionLogger.setTestType("Test for about brand title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_ABOUT_BRAND_TITLE);

        assertionLogger.setTestType("Test for about brand web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings_03_verify_about_brand_web_view_" + System.currentTimeMillis());
        driverWrapper.back();

        // Launch the FAQ screen
        AutomationOperations.instance().navOp.settings.launchFAQ();

        // Navigates to the FAQ screen
        assertionLogger.setTestType("Test for FAQ title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_FAQ_TITLE);

        assertionLogger.setTestType("Test for FAQ web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings_04_verify_FAQ_web_view_" + System.currentTimeMillis());
        driverWrapper.back();

        // Launch the PRIVACY POLICY screen
        AutomationOperations.instance().navOp.settings.launchPrivacyPolicy();

        // Navigates to the PRIVACY POLICY screen
        assertionLogger.setTestType("Test for Privacy Policy title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_PRIVACY_POLICY_TITLE);

        assertionLogger.setTestType("Test for Privacy Policy web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings_05_verify_Privacy_Policy_web_view_" + System.currentTimeMillis());
        driverWrapper.back();

        // Launch the Terms & Conditions screen
        AutomationOperations.instance().navOp.settings.launchTermsConditions();

        // Navigates to the Terms and conditions screen
        assertionLogger.setTestType("Test for Terms & Conditions title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_TERMS_CONDITIONS_TITLE);

        assertionLogger.setTestType("Test for Terms & Conditions web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings_06_verify_Terms_Conditions_web_view_" + System.currentTimeMillis());
        driverWrapper.back();

        // Launch the Feedback option
        // AutomationOperations.instance().navOp.settings.launchFeedback();

        // click on the About BottleRocket option
        AutomationOperations.instance().navOp.settings.launchAboutBR();

        // Navigates to the About BR screen
        assertionLogger.setTestType("Test for About BR title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_ABOUT_BOTTLE_ROCKET_TITLE);

        assertionLogger.setTestType("Test for About BR web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings_08_verify_About_BR_web_view_" + System.currentTimeMillis());
        driverWrapper.back();

        // Verify Video over wifi set to on
        assertionLogger.setTestType("Test is video over wifi is active : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasVideoOverWifi());

        // Check for the developer options
        if (AutomationOperations.instance().navOp.settings.hasDevOptions()) {
            // Launch the developer options
            AutomationOperations.instance().navOp.settings.launchDevOpts();
            // Verify the title as the developer options
            assertionLogger.setTestType("Test for Developer Options title: ");
            assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE);
            driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings_13_verify_Dev_Options_title_" + System.currentTimeMillis());
            driverWrapper.back();
        }

        // Verify for the version info
        assertionLogger.setTestType("Test for version information: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasVersionInfo());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings_14_verify_version_info_" + System.currentTimeMillis());

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