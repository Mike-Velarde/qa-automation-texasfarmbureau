package smokeTest.generic;

/**
 * Created by ford.arnett on 11/23/15.
 */

import assertions.AssertionLogger;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.config.AutomationConfigProperties;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Watchlist extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {
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
        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "video_playing_before_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("error taking screenshot", e);
        }

        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);

        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "video_playing_after_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("error taking screenshot",e);
        }
        driverWrapper.back();
    }

    private void playQueue(){
        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "video_playing_before_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("error taking screenshot",e);
        }
        AutomationOperations.instance().navOp.watchlist.playQueueShow(1, 0);

        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        try {
            driverWrapper.takeScreenshot(AutomationConfigProperties.screenshotsDirectory, "video_playing_after_" + System.currentTimeMillis());
        }
        catch(Exception e){
            ErrorHandler.printErr("error taking screenshot",e);
        }
        driverWrapper.back();
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }
}