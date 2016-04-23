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
    @Test(enabled = true, priority = 1)
    public void testBasicFunctionalities() {
        // Open the navigation drawer
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        // Verify for the left arrow
        assertionLogger.setTestType("Test the hamburger replaced with left arrow : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.doesCloseMainDrawerExists());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Navigation_Drawer_01_verify_hamburger_replaced_with_left_arrow_" + System.currentTimeMillis());

        // Verify icons for each option
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Navigation_Drawer_10_verify_icons_for_each_option_" + System.currentTimeMillis());

        // Rotate the device and verify navigation drawer in exposed state
        driverWrapper.rotate();
        assertionLogger.setTestType("Test does the left arrow exists : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.doesCloseMainDrawerExists());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Navigation_Drawer_12_verify_navigation_drawer_displaying_correctly_" + System.currentTimeMillis());

        // Rotate the device and verify navigation drawer in exposed state
        driverWrapper.rotate();
        assertionLogger.setTestType("Test does the left arrow exists : ");
        assertionLogger.assertTrue(AutomationOperations.instance().navOp.doesCloseMainDrawerExists());
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory+""+this.getClass().getSimpleName()+"/", "AAFR_Navigation_Drawer_13_verify_navigation_drawer_displaying_correctly_" + System.currentTimeMillis());

        // Close the navigation drawer
        AutomationOperations.instance().navOp.closeMainDrawer();

    }

    /**
     * Verify the navigation drawer options.
     */
    @Test(enabled = true, priority = 2)
    public void testNavigationDrawerOptions() {

        // Open the navigation drawer
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        // Verify navigation drawer items
        for (ResourceLocator.DrawerNavigationItem item : ResourceLocator.DrawerNavigationItem.values()) {
            assertionLogger.setTestType("Test does " + item.toString() + " availble in the main drawer: ");
            assertionLogger.assertTrue(AutomationOperations.instance().navOp.hasDrawerItem(item.toString()));
        }

        // Close the main drawer
        AutomationOperations.instance().navOp.closeMainDrawer();
    }

    /**
     * Verify the navigation drawer page titles.
     */
    @Test(enabled = true, priority = 3)
    public void testNaigationPageTitles() {
        // Verify screen titles
        for (ResourceLocator.DrawerNavigationItem item : ResourceLocator.DrawerNavigationItem.values()) {
            // Navigating to the option
            AutomationOperations.instance().navOp.navigateUsingDrawer(item);
            if (!item.toString().equalsIgnoreCase(ResourceLocator.DrawerNavigationItem.feeds.toString())) {
                // Verify page titles
                assertionLogger.setTestType("Test page title as " + item.toString() + " : ");
                assertionLogger.assertEquals(AutomationOperations.instance().navOp.getScreenTitle(), item.toString());

                if (item.toString().equals(ResourceLocator.DrawerNavigationItem.watchlist.toString())) {
                    // Verify the watch list display the count
                    assertionLogger.setTestType("Test does watchlist displays count: ");
                    assertionLogger.assertTrue(doesWatchlistHaveCount());
                }

            } else {
                // Verify feed picker screen
                assertionLogger.setTestType("Test for the feed picker screen : ");
                assertionLogger.assertTrue(AutomationOperations.instance().navOp.chooseFeeds.hasPickFeedList());
            }
        }
        driverWrapper.back();
    }

    /**
     * verify does watchlist shows the display number.
     */
    private boolean doesWatchlistHaveCount() {
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
