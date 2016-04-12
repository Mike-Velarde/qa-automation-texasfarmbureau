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
 * Created by Mahendranath.Nampally on 03/29/2016.
 */
public class WatchList extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    /**
     * Verify the basic features of the watchlist screen
     */
    @Test
    public void test01_BasicFeatures() {
        // Navigate to the watchlist
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.watchlist);

        // Verify watch list screen displays in landscape mode also verify app brand logo
        driverWrapper.rotate();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Watchlist_03_verify_watchlist_landscape_mode_" + System.currentTimeMillis());

        // Verify Watchlist screen displays in portrait mode also verify app brand logo
        driverWrapper.rotate();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Watchlist_04_verify_watchlist_portrait_mode_" + System.currentTimeMillis());

        // Verify watch list after the brand logo
        assertionLogger.setTestType("Test watchlist is available after the brand logo");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.getScreenTitle().contains("Watchlist"));

        // Verify Continue and Queue tabs
        assertionLogger.setTestType("Test watchlist has the CONTINUE tab: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.watchlist.hasContinueTab());
        assertionLogger.setTestType("Test watchlist has the QUEUE tab: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.watchlist.hasQueueTab());
    }

    /**
     * Clear all the shows in the watch list
     */
    private void clearShows() {
        // Get the count in the watch list
        driverWrapper.back();
        int watchCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        if (watchCount > 0) {
            // Navigate to the watchlist
            AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.watchlist);

            // Select continue tab
            AutomationOperations.instance().navOp.watchlist.selectContinueTab();

            if (!AutomationOperations.instance().navOp.watchlist.isEmpty()) {
                AutomationOperations.instance().navOp.watchlist.removeAllShows();
            }

            // Select Queue tab
            AutomationOperations.instance().navOp.watchlist.selectQueueTab();

            if (!AutomationOperations.instance().navOp.watchlist.isEmpty()) {
                AutomationOperations.instance().navOp.watchlist.removeAllShows();
            }
        }
    }

    /**
     * Add the shows to the watchlist
     */
    private void addShowsToWatchList() {
        clearShows();
        int actualWatchCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        // Add the complete show
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);

        int showsCount = AutomationOperations.instance().navOp.shows.getShowsCount();

        // Verify the selected brand contains any shows
        assertionLogger.setTestType("Test does the brand has any shows :");
        assertionLogger.assertTrue(showsCount > 0);

        // Select the show
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);
        // Add the complete show to the watchlist
        AutomationOperations.instance().navOp.addShowToWatchlist();
        driverWrapper.back();
        int presentCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        assertionLogger.setTestType("Test watchlist count increased:");
        assertionLogger.assertEquals(actualWatchCount + 1, presentCount);

        driverWrapper.back();
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        if (showsCount > 1) {
            // Select the show
            AutomationOperations.instance().navOp.shows.selectShow(1, 0);
            // Scroll up
            AutomationOperations.instance().navOp.shows.scrollToBottom();

            // Get the videos count
            int videosCount = AutomationOperations.instance().navOp.shows.getVideosCount();

            for (int count = 0; count < videosCount; count++) {
                AutomationOperations.instance().navOp.shows.launchVideoDetail(count);
                // Add the video to the watchlist
                AutomationOperations.instance().navOp.addShowToWatchlist();
                driverWrapper.back();
            }
            driverWrapper.back();
        }
    }

    /**
     * Verifies the shows are available in alphabetical order or not
     */
    @Test
    public void test02_verifyQueueShowsSortOrder() {
        // Adding shows to the watchlist
        addShowsToWatchList();
        // Navigate to watchlist
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.watchlist);
        // Select the queue section
        AutomationOperations.instance().navOp.watchlist.selectQueueTab();
        // Verify QUEUE tab is selected or not
        assertionLogger.setTestType("Test is it QUEUE tab");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.watchlist.isQueueTab());

        // Select the show
        AutomationOperations.instance().navOp.watchlist.selectShow(0);

        // Verify shows in sort order
        assertionLogger.setTestType("Test the QUEUE has stored the clips in sorted order:");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.watchlist.hasShowsSortOrder());
        driverWrapper.back();
        // Verify vertical scroll functionality
        verifyVerticalScrollFunctionality();
    }

    /**
     * Verify the contextual bar remove functionality
     */
    @Test
    public void test03_RemoveFunctionality() {
        // Adding shows to the watchlist
        addShowsToWatchList();
        // Navigate to watchlst
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.watchlist);
        // Select the queue section
        AutomationOperations.instance().navOp.watchlist.selectQueueTab();
        // Get the shows count
        int showsCount = AutomationOperations.instance().navOp.watchlist.getShowsCount();

        // Tap the edit icon
        AutomationOperations.instance().navOp.watchlist.tapEdit();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Watchlist_09_verify_Contextual_Action_Bar_" + System.currentTimeMillis());

        // Verify the contextual action bar is displayed or not
        assertionLogger.setTestType("Test is contextual action bar displayed: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.watchlist.isContextualBarEnabled());

        // Select the video
        AutomationOperations.instance().navOp.watchlist.selectShow(0);

        // Click on the remove button
        AutomationOperations.instance().navOp.watchlist.removeShow();

        // Verify for the presence of confirmation dialog
        assertionLogger.setTestType("Test for the presence of the confirmation dialog: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.watchlist.isConfirmationPresent());

        // TODO: If we rotate the device the application is not removing the
        // selected show

        /*
         * //Rotate the device driverWrapper.rotate();
         * 
         * driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Watchlist_55_verify_is_any_error_present_" +System.currentTimeMillis()); 
         * //Verify is any error present after rotating 
         * assertionLogger.setTestType("Test is any error present after rotating the screen : "); 
         * assertionLogger.assertTrue(AutomationOperations.instance().navOp.watchlist.isConfirmationPresent());
         */
        // Accept the remove in the confirmation
        AutomationOperations.instance().navOp.watchlist.acceptRemove();

        // Get the shows count
        int presentShowsCount = AutomationOperations.instance().navOp.watchlist.getShowsCount();

        assertionLogger.setTestType("Test the video removed from watchlist:");
        assertionLogger.assertEquals(showsCount - 1, presentShowsCount);

    }

    // Verify the vertical scroll functionality
    private void verifyVerticalScrollFunctionality() {
        // Select the QUEUE tab
        AutomationOperations.instance().navOp.watchlist.selectQueueTab();
        // Select the show
        AutomationOperations.instance().navOp.watchlist.selectShow(0);
        String lastShowTitle_beforeScroll = AutomationOperations.instance().navOp.watchlist.getLastShowTitle();
        AutomationOperations.instance().navOp.watchlist.scrollUp();
        String lastShowTitle_afterScroll = AutomationOperations.instance().navOp.watchlist.getLastShowTitle();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Watchlist_22_verify_watchlist_scroll_up_" + System.currentTimeMillis());
        assertionLogger.setTestType("Test scroll up working successfully :");
        assertionLogger.assertEquals(lastShowTitle_beforeScroll, lastShowTitle_afterScroll);
        driverWrapper.back();
    }

    @Test
    public void test04_ActionBarEditFunctionalities() {
        // Adding shows to the watchlist
        addShowsToWatchList();

        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.watchlist);
        AutomationOperations.instance().navOp.watchlist.selectQueueTab();
        AutomationOperations.instance().navOp.watchlist.tapEdit();
        // Select the video
        AutomationOperations.instance().navOp.watchlist.selectShow(0);

        // get the text from the title bar
        String title = AutomationOperations.instance().navOp.watchlist.getHighlightedCount();
        assertionLogger.setTestType("Test is item selcted  :");
        assertionLogger.assertTrue(title.contains("1"));
        driverWrapper.back();

        // Long press on the watchlist show
        AutomationOperations.instance().navOp.watchlist.doLongPressonShow(0);
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Watchlist_46_verify_shaded_overlay_first_show_" + System.currentTimeMillis());
        title = AutomationOperations.instance().navOp.watchlist.getHighlightedCount();
        assertionLogger.setTestType("Test is item selcted  :");
        assertionLogger.assertTrue(title.contains("1"));
        AutomationOperations.instance().navOp.watchlist.doLongPressonShow(1);
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Watchlist_48_verify_shaded_overlay_second_show_" + System.currentTimeMillis());
        title = AutomationOperations.instance().navOp.watchlist.getHighlightedCount();
        assertionLogger.setTestType("Test is second item selcted  :");
        assertionLogger.assertTrue(title.contains("2"));
        driverWrapper.back();

        // Select the Edit in the action bar
        AutomationOperations.instance().navOp.watchlist.tapEdit();
        // Click on the Select all button
        AutomationOperations.instance().navOp.watchlist.selectAllWatchList();
        title = AutomationOperations.instance().navOp.watchlist.getHighlightedCount();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Watchlist_50_verify_all_shows_selected_" + System.currentTimeMillis());
        assertionLogger.setTestType("Test all items selcted  :");
        assertionLogger.assertFalse(title.contains("0"));
        driverWrapper.back();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Watchlist_53_verify_all_shows_deselected_" + System.currentTimeMillis());
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }

}