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
 * Created by Mahendranath.Nampally on 04/06/2016.
 */
public class NavigationDrawer extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    /**
     * To verify the basic functionalities of the navigation drawer
     */
    @Test
    public void test01_verifyBasicFunctionalities() {
        // Verify the shows displayed in the navigation drawer
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        // Verify for the left arrow
        assertionLogger.setTestType("Test the hamburger replaced with left arrow : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.doesCloseMainDrawerExists());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Navigation_Drawer1_verify_hamburger_replaced_with_left_arrow_" + System.currentTimeMillis());

        // Verify icons for each option
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Navigation_Drawer10_verify_icons_for_each_option_" + System.currentTimeMillis());

        // Rotate the device and verify navigation drawer in exposed state
        driverWrapper.rotate();
        assertionLogger.setTestType("Test does the left arrow exists : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.doesCloseMainDrawerExists());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Navigation_Drawer12_verify_navigation_drawer_displaying_correctly_" + System.currentTimeMillis());

        // Rotate the device and verify navigation drawer in exposed state
        driverWrapper.rotate();
        assertionLogger.setTestType("Test does the left arrow exists : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.doesCloseMainDrawerExists());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "AAFR_Navigation_Drawer13_verify_navigation_drawer_displaying_correctly_" + System.currentTimeMillis());

    }

    /**
     * Verify the navigation drawer options.
     */
    @Test
    public void test02_verifyNavigationDrawerOptions() {
        // Verify Featured is available or not
        assertionLogger.setTestType("Test does Featured availble in the main drawer: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.featured.toString()));

        // Verify shows is available or not
        assertionLogger.setTestType("Test does Shows availble in the main drawer: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.shows.toString()));

        // Verify Movies is available or not
        assertionLogger.setTestType("Test does Movies availble in the main drawer: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.movies.toString()));

        // Verify watch list is available or not
        assertionLogger.setTestType("Test does watchlist availble in the main drawer: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.watchlist.toString()));

        // Verify schedule is available or not
        assertionLogger.setTestType("Test does schedule availble in the main drawer: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.schedule.toString()));

        // Verify settings is available or not
        assertionLogger.setTestType("Test does settings availble in the main drawer: ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.settings.toString()));

        // Close the main drawer
        AutomationOperations.instance().navOp.closeMainDrawer();
    }

    /**
     * Verify the navigation drawer page titles.
     */
    @Test
    public void test03_verifyNaigationPageTitles() {
        // Select featured
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.featured);

        // Verify page title as Featured
        assertionLogger.setTestType("Test page title as Fatured: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.featured.toString());

        // Select shows
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);

        // Verify page title as Shows
        assertionLogger.setTestType("Test page title as Shows: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.shows.toString());

        // Select Movies
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.movies);

        // Verify page title as movies
        assertionLogger.setTestType("Test page title as movies: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.movies.toString());

        // Select Watchlist
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.watchlist);

        // Verify page title as Watchlist
        assertionLogger.setTestType("Test page title as watchlist: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.watchlist.toString());

        // Verify the watch list display the count
        assertionLogger.setTestType("Test does watchlist displays count: ");
        assertionLogger.assertTrue(doesWatchlisthasCount());

        // Select Schedule
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.schedule);

        // Verify page title as Schedule
        assertionLogger.setTestType("Test page title as Schedule: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.schedule.toString());

        // Select Settings
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);

        // Verify page title as Settings
        assertionLogger.setTestType("Test page title as Settings: ");
        assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), ResourceLocator.DrawerNavigationItem.settings.toString());

        // Verify the shows displayed in the navigation drawer
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        if (AutomationOperations.instance().navOp.hasDrawerItem(ResourceLocator.DrawerNavigationItem.feeds.toString())) {
            // Closing the navigation drawer
            AutomationOperations.instance().navOp.closeMainDrawer();

            // Select Settings
            AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.feeds);

            // Verify page title as Settings
            assertionLogger.setTestType("Test for the feed picker screen : ");
            assertionLogger.assertTrue(AutomationOperations.instance().navOp.chooseFeeds.hasPickFeedList());
        }
        driverWrapper.back();
    }

    /**
     * verify does watchlist shows the display number.
     */
    private boolean doesWatchlisthasCount() {
        // Get the count in the watch list
        int watchCount = AutomationOperations.instance().userOp.getDrawerWatchlistCount();
        if (watchCount > 0) {
            return true;
        } else {
            // close the main drawer
            AutomationOperations.instance().navOp.closeMainDrawer();

            // Add the complete show
            AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);

            int showsCount = AutomationOperations.instance().navOp.shows.getShowsCount();

            // Verify QUEUE tab is selected or not
            assertionLogger.setTestType("Test has shows");
            assertionLogger.assertTrue(showsCount > 0);

            // Select the show
            AutomationOperations.instance().navOp.shows.selectShow(0, 0);
            // Add the complete show to the watchlist
            AutomationOperations.instance().navOp.addShowToWatchlist();
            driverWrapper.back();
            return AutomationOperations.instance().userOp.getDrawerWatchlistCount() > 0;
        }
    }

    @AfterClass
    public void tearDown() {
        assertionLogger.logMessages();
    }

}