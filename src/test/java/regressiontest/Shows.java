package regressiontest;

import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bottlerocket.config.AutomationConfigProperties;

import assertions.AssertionLogger;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;

/**
 * Created by Mahendranath.Nampally on 04/01/2016.
 */
public class Shows extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testShowDetails() {

        // Verify the shows displayed in the navigation drawer
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        // Verify exists shows in main drawer
        assertionLogger.setTestType("Test has shows availble in the main drawer: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.shows.toString()));
        AutomationOperations.instance().navOp.closeMainDrawer();

        // Select the shows
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Shows_03_verify_shows_feed_data_Charles_Proxy" + System.currentTimeMillis());

        // Verify shows arranged in alphabetical order
        assertionLogger.setTestType("Test has shows arranged in alphabetical order: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.hasShowsArrangedAlphabetically());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Shows_04_verify_shows_arranged_alphabetically_" + System.currentTimeMillis());

        // Verify the last row of images cut off
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_last_row_images_cut_off_" + System.currentTimeMillis());

        // Verify user can scroll up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Shows_06_verify_before_scroll_up_" + System.currentTimeMillis());
        AutomationOperations.instance().navOp.shows.scrollToBottom_Shows();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Shows_06_verify_after_scroll_up_" + System.currentTimeMillis());
        driverWrapper.back();
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);

        // Select the show
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        // Verify show details screen
        assertionLogger.setTestType("Test show detail screen : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasToolbarWatchlist());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Shows_08_verify_before_running_background" + System.currentTimeMillis());

        // TODO:The run app in back ground is not working - app issue
        // Run app in background
        /*
         * driverWrapper.runAppInBackground(10); 
         * assertionLogger.setTestType( "Test after running in background verify the screen position : "); 
         * assertionLogger .assertTrue(AutomationOperations.instance().navOp.hasToolbarWatchlist ()); 
         * driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_after_running_background" + System.currentTimeMillis());
         */
        driverWrapper.back();

        // Verify placeholder displays
        assertionLogger.setTestType("Test verify brand and place holder displaying : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.getScreenTitle().equals(ResourceLocator.DrawerNavigationItem.shows.toString()));
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Shows_11_verify_brand_placeholder_displaying_" + System.currentTimeMillis());

        // Tap on multiple shows, at a time only one show has to display
        AutomationOperations.instance().navOp.shows.selectMultipleShows();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Shows_12_verify_one_show_displays_" + System.currentTimeMillis());

        // TODO: Define the logic to test on the phone, 7" tablet and 10" tablet
        Dimension dimension = driverWrapper.manage().window().getSize();
        long width = dimension.getWidth();
        long height = dimension.getHeight();

        // Verify portrait screen
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Shows_13_verify_portrait_column_" + System.currentTimeMillis());
        assertionLogger.setTestType("Test the column count in portrait mode : ");
        if (width == 720 && height == 1280) {
            assertionLogger.assertEquals(AutomationOperations.instance().navOp.shows.getShowsColumnCount(), 1);
        }
        driverWrapper.rotate();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Shows_13_verify_landscape_column_" + System.currentTimeMillis());
        assertionLogger.setTestType("Test the column count in landscape mode: ");
        if (width == 720 && height == 1280) {
            assertionLogger.assertEquals(AutomationOperations.instance().navOp.shows.getShowsColumnCount(), 2);
        }
        driverWrapper.rotate();
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }

}