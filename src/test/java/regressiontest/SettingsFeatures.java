package regressiontest;

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
public class SettingsFeatures extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testAboutBrand() {
        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);

        // Click on the About brand
        AutomationOperations.instance().navOp.settings.launchAboutBrand();
        // Navigates to the About Brand screen
        assertionLogger.setTestType("Test for about brand title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_ABOUT_BRAND_TITLE);

        assertionLogger.setTestType("Test for about brand web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());

        // Verify page content with server configuration
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-AboutBrand_02_check_admin_server_config_" + System.currentTimeMillis());
        // Verify the brand logo and about app brand name
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-AboutBrand_03_verify_brand_logo_name_" + System.currentTimeMillis());

        // Verify correct background displays
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-AboutBrand_06_verify_background_" + System.currentTimeMillis());

        // click on the back button on the title bar
        AutomationOperations.instance().navOp.mainToolbarBack();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Click on the About brand
        AutomationOperations.instance().navOp.settings.launchAboutBrand();
        // Navigate to the back button
        driverWrapper.back();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Click on the About brand
        AutomationOperations.instance().navOp.settings.launchAboutBrand();

        // Screenshot of before scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-AboutBrand_07_before_scroll_up_" + System.currentTimeMillis());

        // Verify scroll functionality
        AutomationOperations.instance().navOp.settings.webviewScrollUp();

        // Screenshot of after scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-AboutBrand_07_after_scroll_up_" + System.currentTimeMillis());

        driverWrapper.back();
    }

    @Test
    public void testFAQ() {
        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        // Launch the FAQ screen
        AutomationOperations.instance().navOp.settings.launchFAQ();

        // Navigates to the FAQ screen
        assertionLogger.setTestType("Test for FAQ title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_FAQ_TITLE);

        // Verify brand logo
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-FAQ_03_verify_brand_logo_" + System.currentTimeMillis());

        assertionLogger.setTestType("Test for FAQ web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());

        // Verify page content with server configuration
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-FAQ_02_check_content_with_admin_server_config_" + System.currentTimeMillis());

        // Verify correct background displays
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-FAQ_06_verify_background_" + System.currentTimeMillis());

        // click on the back button on the title bar
        AutomationOperations.instance().navOp.mainToolbarBack();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Click on the About brand
        AutomationOperations.instance().navOp.settings.launchFAQ();

        // Navigate to the back button
        driverWrapper.back();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Launch the FAQ screen
        AutomationOperations.instance().navOp.settings.launchFAQ();

        // Screenshot of before scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-FAQ_07_before_scroll_up_" + System.currentTimeMillis());

        // Verify scroll functionality
        AutomationOperations.instance().navOp.settings.webviewScrollUp();

        // Screenshot of after scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-FAQ_08_after_scroll_up_" + System.currentTimeMillis());

        driverWrapper.back();
    }

    @Test
    public void testPrivacyPolicy() {
        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);

        // Launch the PRIVACY POLICY screen
        AutomationOperations.instance().navOp.settings.launchPrivacyPolicy();

        // Navigates to the PRIVACY POLICY screen
        assertionLogger.setTestType("Test for Privacy Policy title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_PRIVACY_POLICY_TITLE);

        // Verify brand logo
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Privacy_Policy_03_verify_brand_logo_" + System.currentTimeMillis());

        assertionLogger.setTestType("Test for Privacy Policy web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());

        // Verify page content with server configuration
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Privacy_Policy_02_check_content_with_admin_server_config_" + System.currentTimeMillis());

        // Verify correct background displays
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Privacy_Policy_06_verify_background_" + System.currentTimeMillis());

        // click on the back button on the title bar
        AutomationOperations.instance().navOp.mainToolbarBack();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Click on the About brand
        AutomationOperations.instance().navOp.settings.launchPrivacyPolicy();
        // Navigate to the back button
        driverWrapper.back();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Launch the PRIVACY POLICY screen
        AutomationOperations.instance().navOp.settings.launchPrivacyPolicy();

        // Screenshot of before scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Privacy_Policy_07_before_scroll_up_" + System.currentTimeMillis());

        // Verify scroll functionality
        AutomationOperations.instance().navOp.settings.webviewScrollUp();

        // Screenshot of after scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Privacy_Policy_07_after_scroll_up_" + System.currentTimeMillis());

        driverWrapper.back();
    }

    @Test
    public void testTermsAndConditions() {
        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);

        // Launch the Terms & Conditions screen
        AutomationOperations.instance().navOp.settings.launchTermsConditions();

        // Navigates to the Terms and conditions screen
        assertionLogger.setTestType("Test for Terms & Conditions title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_TERMS_CONDITIONS_TITLE);

        // Verify brand logo
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Terms&Conditions_03_verify_brand_logo_" + System.currentTimeMillis());

        assertionLogger.setTestType("Test for Terms & Conditions web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());
        // Verify page content with server configuration
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Terms&Conditions_02_check_content_with_admin_server_config_" + System.currentTimeMillis());

        // Verify correct background displays
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Terms&Conditions_06_verify_background_" + System.currentTimeMillis());

        // click on the back button on the title bar
        AutomationOperations.instance().navOp.mainToolbarBack();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Launch the Terms & Conditions screen
        AutomationOperations.instance().navOp.settings.launchTermsConditions();
        // Navigate to the back button
        driverWrapper.back();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Launch the Terms & Conditions screen
        AutomationOperations.instance().navOp.settings.launchTermsConditions();

        // Screenshot of before scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Terms&Conditions_07_before_scroll_up_" + System.currentTimeMillis());

        // Verify scroll functionality
        AutomationOperations.instance().navOp.settings.webviewScrollUp();

        // Screenshot of after scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Terms&Conditions_07_after_scroll_up_" + System.currentTimeMillis());

        driverWrapper.back();
    }

    @Test
    public void testFeedback() {
        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);

        // click on the Feedback option
        AutomationOperations.instance().navOp.settings.launchFeedback();

        // Verify brand logo
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Feedback_01_verify_more_screen_" + System.currentTimeMillis());

        // Click on email option
        AutomationOperations.instance().navOp.settings.clickOnEmail();

        // Verify for the keyboard displayed before click the back button
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Feedback_05_verify_keyboard_displayed_before_click_back_button_" + System.currentTimeMillis());

        // Click on the back button
        driverWrapper.back();

        // Verify for the keyboard dismissed after clicking the back button
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Feedback_05_verify_keyboard_dismissed_after_click_back_button_" + System.currentTimeMillis());

        // Verify TO field as per server configuration file
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Feedback_02_verify_TO_field_config_file_" + System.currentTimeMillis());

        // Verify compose screen and app version, Device, OS version and Connection Type
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-Feedback_03_verify_pre_populated_instructions_" + System.currentTimeMillis());

        // Click on the back button
        driverWrapper.back();

        // Verify page title as Settings
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());
    }

    @Test
    public void testAboutBR() {
        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);

        // click on the About BottleRocket option
        AutomationOperations.instance().navOp.settings.launchAboutBR();

        // Navigates to the About BR screen
        assertionLogger.setTestType("Test for About BR title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_ABOUT_BOTTLE_ROCKET_TITLE);

        // Verify brand logo
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-About_BR_03_verify_brand_logo_" + System.currentTimeMillis());

        assertionLogger.setTestType("Test for About BR web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());
        // Verify page content with server configuration
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-About_BR_02_check_content_with_admin_server_config_" + System.currentTimeMillis());

        // Verify correct background displays
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-About_BR_06_verify_background_" + System.currentTimeMillis());

        // click on the back button on the title bar
        AutomationOperations.instance().navOp.mainToolbarBack();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // click on the About BottleRocket option
        AutomationOperations.instance().navOp.settings.launchAboutBR();
        // Navigate to the back button
        driverWrapper.back();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // click on the About BottleRocket option
        AutomationOperations.instance().navOp.settings.launchAboutBR();

        // Screenshot of before scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-About_BR_07_before_scroll_up_" + System.currentTimeMillis());

        // Verify scroll functionality
        AutomationOperations.instance().navOp.settings.webviewScrollUp();

        // Screenshot of after scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Settings-About_BR_07_after_scroll_up_" + System.currentTimeMillis());

        driverWrapper.back();
    }

    /**
     * It will verify all the links
     */
    @Test(dataProvider = "settings-verify-titles", dataProviderClass = Titles.class, groups = { "android" })
    public void testAllLinks(String testType, String buttonID, String title) {
        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        AutomationOperations.instance().navOp.settings.navigateToSettingsOption(buttonID);
        assertionLogger.setTestType("Verify the screen title is the title we expected");
        try {
            assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), title);
        }
        finally {
            AutomationOperations.instance().navOp.mainToolbarBack();
        }
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }

}
