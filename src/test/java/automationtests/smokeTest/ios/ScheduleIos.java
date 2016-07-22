package automationtests.smoketest.ios;

import automationtests.assertions.AssertionLogger;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import config.ResourceLocator;
import io.appium.java_client.SwipeElementDirection;
import appium.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by ford.arnett on 6/21/16.
 */
public class ScheduleIos extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testSchedule() throws WebDriverWrapperException {
        assertionLogger.setTestType("Screenshot: verify schedule body has been swiped");
        driverWrapper.takeScreenshotSuppressError("verify_schedule_body_swiped_before_" + System.currentTimeMillis());
        AutomationOperations.instance().navOp.schedule.swipeScheduleBody(SwipeElementDirection.LEFT);
        driverWrapper.takeScreenshotSuppressError("verify_schedule_body_swiped_after_" + System.currentTimeMillis());

        assertionLogger.setTestType("Screenshot: verify heading has changed");
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.schedule);
        driverWrapper.takeScreenshotSuppressError("verify_date_heading_changed_before_" + System.currentTimeMillis());
        AutomationOperations.instance().navOp.schedule.chooseDateHeading(1);
        driverWrapper.takeScreenshotSuppressError("verify_date_heading_changed_after_" + System.currentTimeMillis());
    }


    @AfterClass
    public void tearDown() throws Exception {
        assertionLogger.logMessages();
    }
}
