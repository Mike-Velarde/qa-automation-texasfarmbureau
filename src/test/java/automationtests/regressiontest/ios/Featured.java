package automationtests.regressiontest.ios;
/**
 * Created by stephen.farmer on 7/21/16.
 */

import appium.AppiumMain;
import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
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
     * Tests if brand logo is present on screen
     */
    @Test
    public void testBrandLogo() {
        assertionLogger.setTestType("Testing that brand logo is visible");
        assertionLogger.assertTrue(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_FEATURED_BRAND_LOGO)));
    }

    /**
     * Verifies Chromecast icon is present and functions correctly on tap
     */
    @Test
    public void testChromecastIcon() {
        assertionLogger.setTestType("Check Chromecast icon");
        assertionLogger.assertTrue(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_CAST_MENU_ITEM)));

        driverWrapper.getElementById(ResourceLocator.device.AWE_CAST_MENU_ITEM).click();
        assertionLogger.setTestType("Testing Chromecast button");
        //Once the icon is clicked, the Chromecast overlay screen should appear along with the AWE_CAST_HEADER
        assertionLogger.assertTrue(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_CAST_HEADER)));
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
     * Verifies the labels of the CTAs to be correct
     */
    @Test
    public void testCTALabels(){
        WebElement ctaItem;
        for (ResourceLocator.CallsToAction cta : ResourceLocator.CallsToAction.values()) {
            ctaItem = driverWrapper.getElementByName(cta.toString());
            assertionLogger.addMessage("Verifying " + cta);
            assertionLogger.setTestType("Testing label");
            assertionLogger.assertEquals(driverWrapper.getElementValue(ctaItem), cta.toString());
        }
    }

    /**
     * Tests the functionality of the play CTA.
     * If the user is not logged in it will trigger content driven auth flow and sign the user in to continue.
     *
     * NOTE: This is not currently working. Something is wrong with the CTA IDs being recognized.
     *
     * @throws WebDriverWrapperException
     */
    @Test
    public void testPlayCTA() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.play);
        AutomationOperations.instance().userOp.playVideo();
        assertionLogger.setTestType("Testing play CTA to open video player");
        if(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MVPD_INFO_LABEL))){
            AutomationOperations.instance().userOp.contentDrivenSignIn(user.defaultUser);
        }
        assertionLogger.assertTrue(AutomationOperations.instance().userOp.isVideoPlaying());
    }

    @Test
    public void backgroundApp(){
        driverWrapper.runAppInBackground(5);
    }

    /**
     * Verifies that the progress spinner is present when tape is loading.
     * Best to test when device is throttled to simulate a bad network connection and spinner is displayed for longer.
     */
    @Test
    public void testSpinner() {
        WebElement spinner = driverWrapper.getElementByName(ResourceLocator.device.AWE_PROGRESS_SPINNER);
        assertionLogger.setTestType("Testing spinner is present if content has not loaded");
        assertionLogger.assertEquals(driverWrapper.getElementValue(spinner), "1");
    }

    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }
}