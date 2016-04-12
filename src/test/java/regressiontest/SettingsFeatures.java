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
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-AboutBrand_02_Check_content_with_Administration_Server Configuration_" + System.currentTimeMillis());
        // Verify the brand logo and about app brand name
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-AboutBrand_03_Verify_brand_logo_And_About_appBrnadName_" + System.currentTimeMillis());

        // Verify correct background displays
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-AboutBrand_06_Verify_background_" + System.currentTimeMillis());

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
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-AboutBrand_07_Before_Scroll_up_" + System.currentTimeMillis());

        // Verify scroll functionality
        AutomationOperations.instance().navOp.settings.scrollUp();

        // Screenshot of after scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-AboutBrand_07_After_Scroll_up_" + System.currentTimeMillis());

        driverWrapper.back();

        // Verify all links
        verifyAllLinks();
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
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-FAQ_03_Verify_brand_logo_" + System.currentTimeMillis());

        assertionLogger.setTestType("Test for FAQ web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());

        // Verify page content with server configuration
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-FAQ_02_Check_content_with_Administration_Server Configuration_" + System.currentTimeMillis());

        // Verify correct background displays
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-FAQ_06_Verify_background_" + System.currentTimeMillis());

        // click on the back button on the title bar
        AutomationOperations.instance().navOp.mainToolbarBack();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Click on the About brand
        AutomationOperations.instance().navOp.settings.launchFAQ();
        ;
        // Navigate to the back button
        driverWrapper.back();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Launch the FAQ screen
        AutomationOperations.instance().navOp.settings.launchFAQ();

        // Screenshot of before scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-FAQ_07_Before_Scroll_up_" + System.currentTimeMillis());

        // Verify scroll functionality
        AutomationOperations.instance().navOp.settings.scrollUp();

        // Screenshot of after scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-FAQ_08_After_Scroll_up_" + System.currentTimeMillis());

        driverWrapper.back();

        // Verify all links
        verifyAllLinks();

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
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-Privacy_Policy_03_Verify_brand_logo_" + System.currentTimeMillis());

        assertionLogger.setTestType("Test for Privacy Policy web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());

        // Verify page content with server configuration
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-Privacy_Policy_02_Check_content_with_Administration_Server Configuration_" + System.currentTimeMillis());

        // Verify correct background displays
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-Privacy_Policy_06_Verify_background_" + System.currentTimeMillis());

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
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-Privacy_Policy_07_Before_Scroll_up_" + System.currentTimeMillis());

        // Verify scroll functionality
        AutomationOperations.instance().navOp.settings.scrollUp();

        // Screenshot of after scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-Privacy_Policy_07_After_Scroll_up_" + System.currentTimeMillis());

        driverWrapper.back();

        // Verify all links
        verifyAllLinks();
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
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-Terms&Cionditions_03_Verify_brand_logo_" + System.currentTimeMillis());

        assertionLogger.setTestType("Test for Terms & Conditions web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());
        // Verify page content with server configuration
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-Terms&Cionditions_02_Check_content_with_Administration_Server Configuration_" + System.currentTimeMillis());

        // Verify correct background displays
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-Terms&Cionditions_06_Verify_background_" + System.currentTimeMillis());

        // click on the back button on the title bar
        AutomationOperations.instance().navOp.mainToolbarBack();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Click on the About brand
        AutomationOperations.instance().navOp.settings.launchTermsConditions();
        // Navigate to the back button
        driverWrapper.back();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Launch the Terms & Conditions screen
        AutomationOperations.instance().navOp.settings.launchTermsConditions();

        // Screenshot of before scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-Terms&Cionditions_07_Before_Scroll_up_" + System.currentTimeMillis());

        // Verify scroll functionality
        AutomationOperations.instance().navOp.settings.scrollUp();

        // Screenshot of after scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-Terms&Cionditions_07_After_Scroll_up_" + System.currentTimeMillis());

        driverWrapper.back();

        // Verify all links
        verifyAllLinks();

    }

    @Test
    public void testFeedback() {
        // Select the settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        
        //TODO: Have to work on this. After completion of Movies test steps  I will work on this.
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
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-About_Bottle_Rocket_03_Verify_brand_logo_" + System.currentTimeMillis());

        assertionLogger.setTestType("Test for About BR web view: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.settings.hasWebView());
        // Verify page content with server configuration
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-About_Bottle_Rocket_02_Check_content_with_Administration_Server Configuration_" + System.currentTimeMillis());

        // Verify correct background displays
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-About_Bottle_Rocket_06_Verify_background_" + System.currentTimeMillis());

        // click on the back button on the title bar
        AutomationOperations.instance().navOp.mainToolbarBack();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Click on the About brand
        AutomationOperations.instance().navOp.settings.launchAboutBR();
        // Navigate to the back button
        driverWrapper.back();
        // Verify user navigated to the Settings screen
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // click on the About BottleRocket option
        AutomationOperations.instance().navOp.settings.launchAboutBR();

        // Screenshot of before scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-About_Bottle_Rocket_07_Before_Scroll_up_" + System.currentTimeMillis());

        // Verify scroll functionality
        AutomationOperations.instance().navOp.settings.scrollUp();

        // Screenshot of after scrolls up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Settings-About_Bottle_Rocket_07_After_Scroll_up_" + System.currentTimeMillis());

        driverWrapper.back();

        // Verify all links
        verifyAllLinks();
    }

    /**
     * It will verify all the links
     */
    private void verifyAllLinks() {
        // Click on the About brand
        AutomationOperations.instance().navOp.settings.launchAboutBrand();
        // Navigates to the About Brand screen
        assertionLogger.setTestType("Test for about brand title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_ABOUT_BRAND_TITLE);
        driverWrapper.back();

        // Launch the FAQ screen
        AutomationOperations.instance().navOp.settings.launchFAQ();
        // Navigates to the FAQ screen
        assertionLogger.setTestType("Test for FAQ title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_FAQ_TITLE);
        driverWrapper.back();

        // Launch the PRIVACY POLICY screen
        AutomationOperations.instance().navOp.settings.launchPrivacyPolicy();
        // Navigates to the PRIVACY POLICY screen
        assertionLogger.setTestType("Test for Privacy Policy title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_PRIVACY_POLICY_TITLE);
        driverWrapper.back();

        // Launch the Terms & Conditions screen
        AutomationOperations.instance().navOp.settings.launchTermsConditions();
        // Navigates to the Terms and conditions screen
        assertionLogger.setTestType("Test for Terms & Conditions title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_TERMS_CONDITIONS_TITLE);
        driverWrapper.back();

        // click on the About BottleRocket option
        AutomationOperations.instance().navOp.settings.launchAboutBR();
        // Navigates to the About BR screen
        assertionLogger.setTestType("Test for About BR title: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_ABOUT_BOTTLE_ROCKET_TITLE);
        driverWrapper.back();

        // Check for the developer options
        if (AutomationOperations.instance().navOp.settings.hasDevOptions()) {
            // Launch the developer options
            AutomationOperations.instance().navOp.settings.launchDevOpts();
            // Verify the title as the developer options
            assertionLogger.setTestType("Test for Developer Options title: ");
            assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE);
            driverWrapper.back();
        }
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }

}