package smokeTest.generic;

/**
 * Created by ford.arnett on 11/23/15.
 */

import assertions.AssertionLibrary;
import assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.config.AutomationConfigProperties;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
    public void testWatchlistContinue(){
        playContinue();
    }

    /**
     * This script makes the assumption that there is a show in the queue watching part of the watchlist. More work must be done to fix this dependency.
     */
    @Test
    public void testWatchlistQueue(){
        playQueue();
    }

    private void playContinue(){
        AutomationOperations.instance().navOp.watchlist.playContinueWatchingShow(1);
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        AssertionLibrary.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 10000);
        AutomationOperations.instance().navOp.mainToolbarBack();
        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    private void playQueue(){
        AutomationOperations.instance().navOp.watchlist.playQueueShow(1, 0);
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        AssertionLibrary.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 10000);
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }
}