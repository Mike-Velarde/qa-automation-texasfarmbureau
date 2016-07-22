package automationtests.smoketest.general;

import appium.AppiumMain;
import automationtests.assertions.AssertionLogger;
import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import domod.UserBank;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Common methods are used for both, while iOS has some separate code
 *
 * Created by ford.arnett on 11/2/15.
 */
public abstract class Featured extends AppiumMain{
    protected AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() throws WebDriverWrapperException {
        UserBank user = new UserBank();

        try {
            AutomationOperations.instance().userOp.signIn(user.defaultUser, false);
            AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.featured);
        }
        catch (Exception ex){
            driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "featured_setup_login_failed" + System.currentTimeMillis());
        }
    }

    protected void testWebsite() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.website);
        boolean visible = AutomationOperations.instance().navOp.featured.isWebsiteVisible();
        assertionLogger.setTestType("Verify that the website is visible");
        assertionLogger.assertTrue(visible);
        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    protected void testPlay() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.play);
        AutomationOperations.instance().userOp.videoDetailsPlayVideo();

        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "video_playing_before_" + System.currentTimeMillis());

        //Give video extra time to load
        driverWrapper.waitLogErr(ResourceLocator.AWE_INITIAL_ADS_WAIT_TIME);

        //check that video has played
        AutomationOperations.instance().assertions.assertVideoRuntimeChanged(assertionLogger, driverWrapper, 10000);

        //Leave video
        AutomationOperations.instance().navOp.mainToolbarBack();
        //go back from play
        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    @Test
    protected abstract void testWatchlist() throws WebDriverWrapperException;

    protected void testSearch() throws WebDriverWrapperException {
        int resultOnPage = AutomationOperations.instance().userOp.search("episode");
        assertionLogger.setTestType("Test that there are more than 0 results");
        assertionLogger.assertNotEquals(resultOnPage, 0);
        AutomationOperations.instance().navOp.mainToolbarBack();
    }

    protected void testShowDetails() throws WebDriverWrapperException {
        String showTitle = AutomationOperations.instance().navOp.featured.selectCallToAction(ResourceLocator.CallsToAction.details);
        assertionLogger.addMessage("Verify that the show title is what we expect, Expected: " + showTitle + " Actual: " + AutomationOperations.instance().userOp.getShowDetailsShowTitle());
        //assertionLogger.assertTrue(showTitle.equalsIgnoreCase(AutomationOperations.instance().userOp.getShowDetailsShowTitle()));
        AutomationOperations.instance().navOp.returnFromShowDetails();
    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }


}
