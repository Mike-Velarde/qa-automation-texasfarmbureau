package testingautomationtests;

import assertions.AssertionLogger;
import com.bottlerocket.config.AutomationConfigProperties;
import config.ResourceLocator;
import main.AppiumMain;
import operations.AutomationOperations;
import operations.OperationsException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by ford.arnett on 5/6/16.
 */
public class ImageCompare extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void compareTest() throws OperationsException {

        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(0, 0);
        //The output location to store the screenshots
        String outputLocation = AutomationConfigProperties.screenshotsDirectory + "" + this.getClass().getSimpleName();


        //Taking the full screen shots
        String fullImage1 = "/full_Image" + System.currentTimeMillis();
        driverWrapper.takeScreenshotSuppressError(outputLocation, fullImage1);
        String moreImage1 = driverWrapper.takeObjectScreenshot(driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_MORE_LINK), outputLocation, "/more_Icon" + System.currentTimeMillis());
        String watchlistImage1 = driverWrapper.takeObjectScreenshot(driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_WATCHLIST), outputLocation, "/watchlist_Icon" + System.currentTimeMillis());



        String moreImage2 = driverWrapper.takeObjectScreenshot(driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_MORE_LINK), outputLocation, "/more_Icon_2" + System.currentTimeMillis());
        String fullImage2 = "/full_Image2" + System.currentTimeMillis();
        driverWrapper.takeScreenshotSuppressError(outputLocation, fullImage2);

        String watchlistImage2 = driverWrapper.takeObjectScreenshot(driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_WATCHLIST), outputLocation, "/watchlist_Icon_2" + System.currentTimeMillis());
        // Verify more image
        assertionLogger.setTestType("Test the more images are identical or not: ");
        assertionLogger.assertFalse(driverWrapper.areImagesIdentical(moreImage1, moreImage2));

        // Verify full screenshots
        assertionLogger.setTestType("Test the full screen shots are identical or not: ");
        assertionLogger.assertFalse(driverWrapper.areImagesIdentical(fullImage1, fullImage2));

        // Verify watchlist image
        assertionLogger.setTestType("Test the watchlist images are identical or not: ");
        assertionLogger.assertTrue(driverWrapper.areImagesIdentical(watchlistImage1, watchlistImage2));

    }

    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }


}
