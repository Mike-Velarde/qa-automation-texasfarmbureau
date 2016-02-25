package smokeTest;

/**
 * Created by ford.arnett on 11/17/15.
 */

import assertions.AssertionLogger;
import config.ResourceLocator;
import io.appium.java_client.SwipeElementDirection;
import main.AppiumMain;
import operations.AutomationOperations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Schedule extends AppiumMain {
    AssertionLogger assertionLogger = new AssertionLogger();

    @BeforeClass
    public void setup() {

    }

    @Test
    public void testSchedule(){
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.schedule);
        String currentDateHeading = AutomationOperations.instance().navOp.schedule.getCurrentDateHeading();
        assertionLogger.setTestType("Check to see if heading is shown");
        assertionLogger.assertNotNull(currentDateHeading, "This element is only null if the current date heading is not available");
        String newDateHeading = AutomationOperations.instance().navOp.schedule.chooseDateHeading(11);
        assertionLogger.setTestType("Check to see if heading has changed as a result of heading swipe");
        assertionLogger.assertNotEquals(currentDateHeading, newDateHeading, "These should not be the same since we swiped");

        currentDateHeading = newDateHeading;
        newDateHeading = AutomationOperations.instance().navOp.schedule.swipeScheduleBody(SwipeElementDirection.LEFT);
        assertionLogger.setTestType("Check to see if heading has changed as a result of body swipe");
        assertionLogger.assertNotEquals(currentDateHeading, newDateHeading, "These should not be the same since we swiped");

    }

    @AfterClass
    public void tearDown(){
        assertionLogger.logMessages();
    }
}