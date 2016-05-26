package regressiontest;

import com.bottlerocket.errorhandling.OperationsException;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
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
public class Movies extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testMoviesDetails() throws OperationsException, WebDriverWrapperException {
        // Device width
        int deviceWidth = 720;
        // Device height
        int deviceHeight = 1280;

        // Launch the navigation bar
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        // Verify exists movies in main drawer
        assertionLogger.setTestType("Test has Movies availble in the main drawer: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.movies.toString()));
        AutomationOperations.instance().navOp.closeMainDrawer();

        // Navigate to the movies
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.movies);
        // Verify page title as Movies
        assertionLogger.setTestType("Test page title as Movies: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.movies.toString());

        // Verify videos available or not
        assertionLogger.setTestType("Test Movies available for this brand: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.getShowsCount() > 0);

        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_02_verify_movie_listing_screen_" + System.currentTimeMillis());

        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_03_verify_movie_feed_data_with_charles_proxy_" + System.currentTimeMillis());

        // Verify movies arranged in alphabetical order
        assertionLogger.setTestType("Test has movies arranged in alphabetical order: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.shows.hasShowsArrangedAlphabetically());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_04_verify_movie_listing_screen_" + System.currentTimeMillis());

        // Verify the last row of images cut off
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_05_verify_last_row_images_cut_off_" + System.currentTimeMillis());

        // Verify user can scroll up
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_06_verify_before_scroll_up_" + System.currentTimeMillis());
        AutomationOperations.instance().navOp.shows.scrollToBottom_Shows();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_06_verify_after_scroll_up_" + System.currentTimeMillis());
        driverWrapper.back();
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.movies);

        // Select the video
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);

        // Verify the video details screen
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_07_verify_video_details_screen_" + System.currentTimeMillis());

        // Verify for the watchlist exists or not
        assertionLogger.setTestType("Test watchlist icon exists : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasToolbarWatchlist());

        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_08_verify_before_running_background_" + System.currentTimeMillis());

        // TODO:The run app in back ground is not working - app issue
        // Run app in background
        /*
         * driverWrapper.runAppInBackground(10);
         * assertionLogger.setTestType("Test after running in background verify the screen position : "); 
         * assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasToolbarWatchlist()); 
         * driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AWE_MOVIES_08_verify_after_running_background_" + System.currentTimeMillis());
         */

        driverWrapper.back();

        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_10_verify_last_row_images_cut_off_" + System.currentTimeMillis());

        // Verify the screen title has Movies
        assertionLogger.setTestType("Test verify the screen title has movies : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.getScreenTitle().equals(ResourceLocator.DrawerNavigationItem.movies.toString()));
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_11_verify_brand_placeholder_" + System.currentTimeMillis());

        // Tap on multiple videos, at a time only one video has to display
        AutomationOperations.instance().navOp.shows.tapOnMultipleShows();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_12_verify_one_show_displays_" + System.currentTimeMillis());

        // TODO: Define the logic to test on the phone, 7" tablet and 10" tablet
        Dimension dimension = driverWrapper.manage().window().getSize();
        long width = dimension.getWidth();
        long height = dimension.getHeight();
        int showsCount = AutomationOperations.instance().navOp.shows.getShowsCount();

        // Verify portrait screen
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_13_verify_portrait_column_" + System.currentTimeMillis());
        assertionLogger.setTestType("Test the column count in portrait mode : ");
        if (width == deviceWidth && height == deviceHeight) {
            assertionLogger.assertEquals(AutomationOperations.instance().navOp.shows.getShowsColumnCount(), 1);
        }
        driverWrapper.rotate();
        // Verify landscape screen
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName() + "/", "AWE_MOVIES_13_verify_landscape_column_" + System.currentTimeMillis());
        assertionLogger.setTestType("Test the column count in landscape mode: ");
        if (width == deviceWidth && height == deviceHeight && showsCount > 1) {
            assertionLogger.assertEquals(AutomationOperations.instance().navOp.shows.getShowsColumnCount(), 2);
        }
        driverWrapper.rotate();
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }

}