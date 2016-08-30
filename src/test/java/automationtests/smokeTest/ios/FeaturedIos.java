package automationtests.smoketest.ios;

import automationtests.assertions.AssertionLogger;
import automationtests.smoketest.general.Featured;
import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import domod.UserBank;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

import operations.AutomationOperations;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static config.ResourceLocator.DrawerNavigationItem.live;

/**
 * Created by ford.arnett on 2/10/16.
 */
public class FeaturedIos extends Featured {
    protected AssertionLogger assertionLogger = new AssertionLogger();
    UserBank user = new UserBank();

    @BeforeClass
    public void setup(){

    }

    @Override
    protected void testWatchlist() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.watchlist);
        //TODO reevaluate how to verify on iOS
        //assertionLogger.setTestType("Verify that the watchlist count is the expected count");
        //assertionLogger.assertNotEquals(watchCount, AutomationOperations.instance().userOp.getDrawerWatchlistCount());
    }

    @Test
    protected void testPlay() throws WebDriverWrapperException {
        super.testPlay();
    }

    @Test
    protected void testSearch() throws WebDriverWrapperException {
        super.testSearch();
    }

    @Test
    protected void testShowDetails() throws WebDriverWrapperException {
        super.testShowDetails();
    }

    @Test
    protected void testWebsite() throws WebDriverWrapperException {
        super.testWebsite();
    }

    /**
     * Tests the nav menu items are present and the functionality of navigating through the menu items.
     *
     * @throws WebDriverWrapperException
     */
    @Test
    public void testNavMenuItems() throws WebDriverWrapperException {
        WebElement navItem;
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        // Test all screens inside nav menu except live
        for (ResourceLocator.DrawerNavigationItem item : ResourceLocator.DrawerNavigationItem.values()) {
            // This is a bit of a janky fix, the logic being if it's "Not iOS" or "Live" then ignore it.
            // In the future it might be better to just remove the unnecessary ones from iOS completely
            if ((!item.toString().equals("NOT ON IOS")) && (!item.toString().equals("Live Menu Item"))) {
                AutomationOperations.instance().navOp.navigateUsingDrawer(item);

                // Check that icons are present
                assertionLogger.setTestType("Verify " + item + " is present");
                assertionLogger.assertTrue(driverWrapper.elementExists(By.name(item.toString())));

                // Verify functionality of nav items
                navItem = driverWrapper.getElementByName(item.toString());
                assertionLogger.addMessage("Verifying " + item);
                assertionLogger.setTestType("Testing value");
                // This checks the element value of the nav menu icon (if it's 1 then the icon is selected)
                assertionLogger.assertEquals(driverWrapper.getElementValue(navItem), "1");
            }
        }

        // Test live
        assertionLogger.setTestType("Verify " + live + " icon is present");
        assertionLogger.assertTrue(driverWrapper.elementExists(By.name(live.toString())));
        AutomationOperations.instance().navOp.navigateUsingDrawer(live);
        assertionLogger.setTestType("Testing Live player nav button");
        if(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MVPD_INFO_LABEL))){
            AutomationOperations.instance().userOp.contentDrivenSignIn(user.defaultUser);
        }
        assertionLogger.assertTrue(AutomationOperations.instance().userOp.isVideoPlaying());
    }

    /**
     * Takes screenshots of before/after backgrounding app then compares.
     *
     * NOTE: This doesn't work very well on slow network connection as the tape takes longer to load
     */
    @Test
    public void testTapeAfterBackground() {
        driverWrapper.waitLogErr(1000);
        // take screenshot
        String image1 = "Verify_featured_tape_for_comparison_" + System.currentTimeMillis();

        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, image1);

        for (int i=0; i<5; i++) {
            driverWrapper.runAppInBackground(1);
        }
        //take another screenshot
        String image2 = "Verify_featured_tape_for_comparison_" + System.currentTimeMillis();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, image2);
        //compare screenshots
        assertionLogger.setTestType("Verify tape remains after backgrounding app multiple times");
        assertionLogger.assertTrue(driverWrapper.areImagesIdentical(AutomationConfigProperties.screenshotsDirectory + image1, AutomationConfigProperties.screenshotsDirectory + image2));
    }

    @Test
    public void testTapeItems() {
        assertionLogger.setTestType("Verify sponsor label is present");
        assertionLogger.assertTrue(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_FEATURED_SPONSOR_TEXT)));

        assertionLogger.setTestType("Verify sponsor text says SPONSORED BY");
        assertionLogger.assertEquals(driverWrapper.getElementValue(driverWrapper.getElementById(ResourceLocator.device.AWE_FEATURED_SPONSOR_TEXT)), "SPONSORED BY");
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }


}
