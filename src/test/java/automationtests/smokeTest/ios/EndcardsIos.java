package automationtests.smokeTest.ios;
/**
 * Created by stephen.farmer on 8/18/16.
 */

import appium.AppiumMain;
import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.OperationsException;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import config.ResourceLocatorIos;
import domod.UserBank;
import io.appium.java_client.MobileElement;
import operations.AutomationOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.VideoItems;

public class EndcardsIos extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();
    UserBank user = new UserBank();

    @BeforeClass
    public void setup() throws WebDriverWrapperException, OperationsException {
        // Navigate to shows, select show and choose episode
        AutomationOperations.instance().navOp.openMainDrawerSafe();
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.shows);
        AutomationOperations.instance().navOp.shows.selectShow(1, -1);
        AutomationOperations.instance().navOp.shows.selectEpisode(1);
        AutomationOperations.instance().navOp.shows.clickShowDetailsActivePlayButton();

        if (driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MVPD_INFO_LABEL))) {
            AutomationOperations.instance().userOp.contentDrivenSignIn(user.defaultUser);
        }
    }

    @Test
    public void testEndcards() {
        VideoItems[] endcardItems = new VideoItems[]{
                new VideoItems("On Next", "awe_endcard_onnextlabel"),
                new VideoItems("Next Episode Timer", "awe_endcard_timerlabel"),
                new VideoItems("Play Button", "awe_endcard_playbutton"),
                new VideoItems("Show Title", "awe_endcard_titlelabel"),
                new VideoItems("Episode Title", "awe_endcard_episodelabel"),
                new VideoItems("Airing Info" , "awe_endcard_infolabel")
        };

        // If user is not signed in, activate content driven sign-in process
        if(driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MVPD_INFO_LABEL))){
            AutomationOperations.instance().userOp.contentDrivenSignIn(user.defaultUser);
        }

        // scrub to end of video...90%? This might need to change, not sure yet
        AutomationOperations.instance().userOp.scrubVideo(90);

        // Verify Endcard items
        for (VideoItems item : endcardItems) {
            assertionLogger.setTestType("Testing " + item.getName() + " is present:");
            assertionLogger.assertTrue(driverWrapper.elementExists(By.name(item.getValue())));
        }
    }


    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }
}