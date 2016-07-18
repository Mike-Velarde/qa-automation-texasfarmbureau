package automationtests.smokeTest.generic;

/**
 * Created by ford.arnett on 11/23/15.
 */

import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import appium.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Works for iOS and Android
 */
public class Watchlist extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.watchlist);
    }

    /**
     * This script makes the assumption that there is a show in the continue watching part of the watchlist. More work must be done to fix this dependency.
     */
    @Test
    public void testWatchlistContinue() throws WebDriverWrapperException {
        playContinue();
    }

    /**
     * This script makes the assumption that there is a show in the queue watching part of the watchlist. More work must be done to fix this dependency.
     */
    @Test
    public void testWatchlistQueue() throws WebDriverWrapperException {
        playQueue();
    }

    private void playContinue() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.watchlist.playContinueWatchingShow(1);
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        AutomationOperations.instance().assertions.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 5000);
        AutomationOperations.instance().navOp.returnFromVideoPlayer();
    }

    private void playQueue() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.watchlist.playQueueShow(1, 0);
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        AutomationOperations.instance().assertions.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 5000);
        AutomationOperations.instance().navOp.returnFromVideoPlayer();
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }
}