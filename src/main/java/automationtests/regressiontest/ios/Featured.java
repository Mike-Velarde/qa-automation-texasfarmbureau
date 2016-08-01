package automationtests.regressiontest.ios;
/**
 * Created by stephen.farmer on 7/21/16.
 */

import appium.AppiumMain;
import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import config.ResourceLocatorIos;
import domod.UserBank;
import operations.AutomationOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static config.ResourceLocator.DrawerNavigationItem.*;

public class Featured extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();
    UserBank user = new UserBank();

    @BeforeClass
    public void setup() {

    }

    /**
     * Tests if the user is logged in, then the MVPD logo should appear next to nav bar
     */
    @Test
    public void testMVPDLogo() {
        assertionLogger.setTestType("Testing if MVPD is visible next to nav bar");
        if (AutomationOperations.instance().userOp.isUserLoggedIn()){
            assertionLogger.assertTrue(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MVPD_MENU_LOGO)));
        }
    }

    /**
     * Tests if brand logo is present on screen
     * Need to investigate further if this is actually working as expected
     */
    @Test
    public void testBrandLogo() {
        assertionLogger.setTestType("Testing that brand logo is visible");
        assertionLogger.assertTrue(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_FEATURED_BRAND_LOGO)));
    }

    /**
     * Opens the nav drawer and verifies all icons are present
     *
     * @throws WebDriverWrapperException
     */
    @Test
    public void testNavBarIcons() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        String[] navMenuIcons = {"Settings Menu Item", "Schedule Menu Item", "Search Menu Item", "Watchlist Menu Item",
                "Shows Menu Item", "Live Menu Item"};

        for (String item : navMenuIcons) {
            assertionLogger.setTestType("Verify " + item);
            assertionLogger.assertTrue(driverWrapper.elementExists(By.name(item)));
        }
    }

    @Test
    public void testChromecastIcon() {
        assertionLogger.setTestType("Check Chromecast icon");
        assertionLogger.assertTrue(driverWrapper.elementExists(By.name("Cast Menu Item")));

        driverWrapper.getElementByName("Cast Menu Item").click();
        assertionLogger.setTestType("Testing Chromecast button");
        assertionLogger.assertTrue(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_CAST_HEADER)));
    }

    /**
     * Tests the functionality of navigating through the menu items.
     * It checks the element value of the nav menu icon (if it's 1 then the icon is selected)
     *
     * @throws WebDriverWrapperException
     */
    @Test
    public void testNavMenuItems() throws WebDriverWrapperException {
        WebElement navItem;
        AutomationOperations.instance().navOp.openMainDrawerSafe();

        // Test all screens inside nav menu except live
        for (ResourceLocator.DrawerNavigationItem item : ResourceLocator.DrawerNavigationItem.values()) {
            if (!item.toString().equals("NOT ON IOS")) {
                if (!item.toString().equals("Live Menu Item")) {
                    AutomationOperations.instance().navOp.navigateUsingDrawer(item);
                    navItem = driverWrapper.getElementById(String.valueOf(item));
                    assertionLogger.addMessage("Verifying " + item);
                    assertionLogger.setTestType("Testing value");
                    assertionLogger.assertEquals(driverWrapper.getElementValue(navItem), "1");
                }
            }
        }

        // Test live
        AutomationOperations.instance().navOp.navigateUsingDrawer(live);
        assertionLogger.setTestType("Testing Live player nav button");
        if(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MVPD_INFO_LABEL))){
            AutomationOperations.instance().userOp.contentDrivenSignIn(user.defaultUser);
        }
        assertionLogger.assertTrue(AutomationOperations.instance().userOp.isVideoPlaying());
    }

    /**
     * Tests the functionality of the play CTA.
     * If the user is not logged in it will trigger content driven auth flow and sign the user in to continue.
     *
     * @throws WebDriverWrapperException
     */
    @Test
    public void testPlayCTA() throws WebDriverWrapperException {

        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocatorIos.CallsToAction.play);
        AutomationOperations.instance().userOp.playVideo();
        assertionLogger.setTestType("Testing play CTA to open video player");
        if(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MVPD_INFO_LABEL))){
            AutomationOperations.instance().userOp.contentDrivenSignIn(user.defaultUser);
        }
        assertionLogger.assertTrue(AutomationOperations.instance().userOp.isVideoPlaying());
    }

    /**
     * Verifies that the progress spinner is present when tape is loading.
     * Best to test when device is throttled to simulate a bad network connection and spinner is displayed for longer.
     */
    @Test
    public void testSpinner() {
        WebElement spinner = driverWrapper.getElementByName("In progress");
        assertionLogger.setTestType("Testing spinner is present if content has not loaded");
        assertionLogger.assertEquals(driverWrapper.getElementValue(spinner), "1");
    }

    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }
}