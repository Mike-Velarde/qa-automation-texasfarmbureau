package automationtests.smoketest.ios;

import appium.AppiumMain;
import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.OperationsException;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.config.AutomationConfigProperties;
import config.ResourceLocator;
import config.ResourceLocatorIos;
import operations.AutomationOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.VideoDetailItems;

import java.util.List;

/**
 * Created by ford.arnett on 2/17/16.
 */
public class ShowsIos extends AppiumMain {

    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testShows() throws WebDriverWrapperException, OperationsException {

        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(1, -1);

        //For now I am making the assumption that if we can get the show title then we have made it to the show details screen
        assertionLogger.setTestType("Test if the show title is non empty");
        assertionLogger.assertNotEquals(AutomationOperations.instance().userOp.getShowDetailsShowTitle(), "");

        AutomationOperations.instance().navOp.addShowToWatchlist();


        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_season_change_before_" + System.currentTimeMillis());
        AutomationOperations.instance().navOp.shows.showDetailSelectSeason(1);
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_season_change_after_" + System.currentTimeMillis());

        AutomationOperations.instance().navOp.shows.playFromActiveSeason(2);

        //Wait for ads
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);
        driverWrapper.takeScreenshotSuppressError("run_time_before");
        driverWrapper.waitLogErr(10000);
        driverWrapper.takeScreenshotSuppressError("run_time_after");

        AutomationOperations.instance().navOp.closeVideoPlayer();
        AutomationOperations.instance().navOp.mainToolbarBack();

        AutomationOperations.instance().navOp.shows.scrollToBottom();
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_season_change_after_" + System.currentTimeMillis());
        driverWrapper.waitLogErr(3000);

        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    @Test
    public void testShareFacebook() throws OperationsException, WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(1, -1);
        AutomationOperations.instance().userOp.shareShowFacebook();
        //The timing may be off on this, it's not an easy thing to capture
        driverWrapper.takeScreenshotSuppressError("verify_facebook_post_successful_" + System.currentTimeMillis());

    }

    @Test
    public void testShowsContainer() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.openMainDrawerSafe();
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        List<WebElement> assetContainer = driverWrapper.getElementById(ResourceLocatorIos.AWE_SHOWS_LISTING_CONTAINER).findElements(By.className(ResourceLocatorIos.UIA_COLLECTION_CELL));
        assertionLogger.setTestType("Verifying the shows listing is not empty");
        assertionLogger.addMessage("Shows size: " + assetContainer.size());
        assertionLogger.assertTrue(assetContainer.size() >= 1);

        if (driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MOVIES_SEGMENTED_CONTROL))) {
            //TODO get rid of these coordinates
            driverWrapper.swipe(800, 400, 200, 400, 500);
            assetContainer = driverWrapper.getElementById(ResourceLocatorIos.AWE_SHOWS_LISTING_CONTAINER).findElements(By.className(ResourceLocatorIos.UIA_COLLECTION_CELL));
            assertionLogger.setTestType("Verifying the movies listing is not empty");
            assertionLogger.addMessage("Movies size: " + assetContainer.size());
            assertionLogger.assertTrue(assetContainer.size() >= 1);
        }
    }

    /**
     * Iterating through the video details items to verify they exist.
     *
     * @throws WebDriverWrapperException
     * @throws OperationsException
     */
    @Test
    public void testVideoDetails() throws WebDriverWrapperException, OperationsException {
        VideoDetailItems[] videoDetailItems = new VideoDetailItems[] {
            new VideoDetailItems("Show Title", "awe_assetdetail_showtitlelabel"),
            new VideoDetailItems("Episode Title", "awe_assetdetail_titlelabel"),
            new VideoDetailItems("Play Button", "awe_assetdetail_playbutton"),
            new VideoDetailItems("Season Info", "awe_assetdetail_seasoninfolabel"),
            new VideoDetailItems("Airing Info", "awe_assetdetail_airinginfolabel"),
            new VideoDetailItems("Description", "awe_assetdetail_descriptionlabel"),
            new VideoDetailItems("Expiration Date", "awe_assetdetail_availableuntillabel"),
            new VideoDetailItems("Video Thumbnail", "Video thumbnail"),
        };

        AutomationOperations.instance().navOp.openMainDrawerSafe();
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(1, -1);
        AutomationOperations.instance().navOp.shows.selectEpisode(0);

        for (VideoDetailItems item : videoDetailItems) {
            assertionLogger.setTestType("Testing " + item.getName() + " is present:");
            assertionLogger.assertTrue(driverWrapper.elementExists(By.name(item.getValue())));
        }
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }

}
