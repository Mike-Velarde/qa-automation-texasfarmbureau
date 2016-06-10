package regressiontest;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.utils.ErrorHandler;

import assertions.AssertionLogger;
import config.ResourceLocator;
import dataproviders.Titles;
import main.AppiumMain;
import operations.AutomationOperations;

/**
 * Created by Mahendranath.Nampally on 04/14/2016.
 */
public class ToolBar extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    /**
     * To verify the Hamburger functionality.
     */
    @Test(enabled = true, priority = 0)
    public void testHamburger() throws WebDriverWrapperException {

        // Verify the Hamburger item
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_01_verify_hamburger_" + System.currentTimeMillis());

        // Open the navigation drawer
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        // Verify the UP Arrow
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_01_verify_up_arrow_" + System.currentTimeMillis());

        AutomationOperations.instance().navOp.closeMainDrawer();

        // Verify the Hamburger item
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_01_verify_hamburger_" + System.currentTimeMillis());

    }

    @Test(enabled = true, priority = 1)
    public void testMainTitles() {
        // Verify titles in the portrait mode
        verifyTitles();
        driverWrapper.rotate();
        // Verify titles in the landscape mode
        verifyTitles();
        driverWrapper.rotate();

    }

    @Test(dataProvider = "settings-verify-titles", dataProviderClass = Titles.class, groups = { "android" }, enabled = true, priority = 2)
    public void testSettingsTitles(String testType, String buttonID, String title) throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        verifySettingsOptions(testType, buttonID, title);
        driverWrapper.rotate();
        verifySettingsOptions(testType, buttonID, title);
        driverWrapper.rotate();
    }

    @Test(enabled = true, priority = 3)
    public void testIcons() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.featured);
        // Verify the search icon exists or not
        assertionLogger.setTestType("Test the search icon exists :");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasToolbarSearch());

        // Launch the main tool bar search
        AutomationOperations.instance().navOp.mainToolbarSearch();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_08_verify_search_mode_" + System.currentTimeMillis());

        // click on the search bar back button
        AutomationOperations.instance().navOp.searchBarBack();

        // Verify correct icons and height used in action bar
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_09_verify_correct_icons_hight_used_in_action_bar_" + System.currentTimeMillis());

        // Verify icons in portrait & landscape modes
        verifyIcons();
    }

    /**
     * To verify the icons work correctly in portrait and landscape mode
     */
    private void verifyIcons() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.openMainDrawerSafe();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_10_verify_items_portrait_" + System.currentTimeMillis());
        // Click on the search icon
        AutomationOperations.instance().navOp.mainToolbarSearch();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_11_verify_items_portrait_" + System.currentTimeMillis());
        AutomationOperations.instance().navOp.searchBarBack();
        // Rotate the device
        driverWrapper.rotate();
        AutomationOperations.instance().navOp.openMainDrawerSafe();
        // Verify items in landscape mode
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_10_verify_items_landscape_" + System.currentTimeMillis());
        // Click on the search icon
        AutomationOperations.instance().navOp.mainToolbarSearch();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_11_verify_items_landscape_" + System.currentTimeMillis());
        AutomationOperations.instance().navOp.searchBarBack();
        driverWrapper.rotate();
    }

    /**
     * It will verify the navigation drawer titles and their positions
     */
    private void verifyTitles() {
        try {
            // Verify icons are tintable
            for (ResourceLocator.DrawerNavigationItem item : ResourceLocator.DrawerNavigationItem.values()) {
                AutomationOperations.instance().navOp.openMainDrawerSafe();
                while (!AutomationOperations.instance().navOp.hasDrawerItem(item.toString())) {
                    AutomationOperations.instance().navOp.navigationDrawerScrollUp();
                }
                driverWrapper.getElementByName(item.toString()).click();
                if (!driverWrapper.elementExists(By.name(ResourceLocator.device.AWE_MAIN_TOOLBAR_BACK))) {
                    // Verify screen titles
                    assertionLogger.setTestType("Verify the screen title is the title we expected :");
                    assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), item.toString());

                    // Verify Hamburger displays and navigation drawer is not visible
                    driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_03_verify_hamburger_visible_and_navigation_not_" + item.toString() + "_" + System.currentTimeMillis());

                    // Verify title position
                    driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_16-35_verify_title_position_" + item.toString() + "_" + System.currentTimeMillis());

                    // Verify order of icons from left to right
                    driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_17-36_verify_icons_position_left_right_" + item.toString() + "_" + System.currentTimeMillis());

                    // Open the navigation drawer
                    AutomationOperations.instance().navOp.openMainDrawerSafe();
                    // Verify tintable icon
                    driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_02_verify_tintable_icon_" + item.toString() + "_" + System.currentTimeMillis());
                    // Close the navigation drawer
                    AutomationOperations.instance().navOp.closeMainDrawer();
                    if (item.toString().equals(ResourceLocator.DrawerNavigationItem.shows.toString()) || item.toString().equals(ResourceLocator.DrawerNavigationItem.movies.toString())) {
                        int showCount = AutomationOperations.instance().navOp.shows.getShowsCount();
                        if (showCount > 0) {
                            AutomationOperations.instance().navOp.shows.selectShow(0, 0);

                            // Verify up arrow icon
                            driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_03_verify_up_arrow_icon_" + item.toString() + "_" + System.currentTimeMillis());

                            // Verify order of icons
                            driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_19&24_verify_icons_order_" + item.toString() + "_" + System.currentTimeMillis());

                            // Add the show to the watchlist
                            AutomationOperations.instance().navOp.addShowToWatchlist();

                            if (AutomationOperations.instance().navOp.shows.hasMore() && (AutomationOperations.instance().navOp.shows.getVideosCount() > 0)) {
                                // Launching the show
                                AutomationOperations.instance().navOp.shows.launchVideoDetail(0);
                                driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_03_verify_up_arrow_icon_show_details_" + System.currentTimeMillis());

                                // Verify icons order
                                driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_20&21_verify_icons_order_" + item.toString() + "_" + System.currentTimeMillis());
                                driverWrapper.back();
                            }
                            driverWrapper.back();
                        }
                    } else if (item.toString().equals(ResourceLocator.DrawerNavigationItem.watchlist.toString())) {
                        // Select the queue section
                        AutomationOperations.instance().navOp.watchlist.selectQueueTab();

                        // Tap on edit icon
                        AutomationOperations.instance().navOp.watchlist.tapEdit();

                        // Verify icons position
                        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_27_verify_icons_order_" + item.toString() + "_" + System.currentTimeMillis());

                        // Select the show
                        AutomationOperations.instance().navOp.watchlist.selectShow(0);

                        // Verify selected items displays
                        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_29_verify_selected_items_" + item.toString() + "_" + System.currentTimeMillis());

                        driverWrapper.back();

                        // Tap on edit icon
                        AutomationOperations.instance().navOp.watchlist.tapEdit();

                        // Click on the Select all button
                        AutomationOperations.instance().navOp.watchlist.selectAllWatchList();

                        // Verify all items selected
                        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_31_verify_selected_all_" + item.toString() + "_" + System.currentTimeMillis());

                        // Click on the remove button
                        AutomationOperations.instance().navOp.watchlist.removeShow();

                        // Verify for the presence of confirmation dialog
                        assertionLogger.setTestType("Test for the presence of the confirmation dialog: ");
                        assertionLogger.assertTrue(AutomationOperations.instance().navOp.watchlist.isConfirmationPresent());
                        // Verify all items selected
                        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_32_verify_confirmation_" + item.toString() + "_" + System.currentTimeMillis());

                        driverWrapper.back();
                        driverWrapper.back();
                        driverWrapper.back();
                    }

                } else {
                    AutomationOperations.instance().navOp.mainToolbarBack();
                }
            }
        } catch (Exception e) {
            ErrorHandler.printErr(e);
        }
    }

    /**
     * To verify the settings tab options
     * 
     * @param testType
     *            - Type of test
     * @param buttonID
     *            - Settings option
     * @param title
     *            - Settings option title
     */
    private void verifySettingsOptions(String testType, String buttonID, String title) throws WebDriverWrapperException {
        while (!AutomationOperations.instance().navOp.settings.hasSettingsOption(buttonID)) {
            AutomationOperations.instance().navOp.settings.settingsOptionsScrollUp();
        }
        AutomationOperations.instance().navOp.settings.navigateToSettingsOption(buttonID);
        assertionLogger.setTestType("Verify the screen title is the title we expected :");
        try {
            assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), title);
            driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_03_verify_up_arrow_icon_and_brand_logo_" + System.currentTimeMillis());
            // Verify icons position
            driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AAFR_Tool_Bar_37-46_verify_icons_position_" + System.currentTimeMillis());
        } catch (Exception e) {
            ErrorHandler.printErr(e);
        } finally {
            AutomationOperations.instance().navOp.mainToolbarBack();
        }
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }

}