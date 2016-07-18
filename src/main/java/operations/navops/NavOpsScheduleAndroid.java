package operations.navops;

import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import operations.AutomationOperationsListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by ford.arnett on 11/17/15.
 */
public class NavOpsScheduleAndroid extends NavOpsSchedule{

    @Override
    public String chooseDateHeading(int dateIndex) {
        WebElement datePageIndicator = driverWrapper.getElementById(ResourceLocator.device.AWE_SCHEDULE_DATE_PAGE_INDICATOR);
        List<WebElement> dateIndicatorHeadings;

        //The general idea is something like this: we want the 30th item, but only 8 can fit on the screen at once
        //So we scroll past 24 items then we want to get the 6th item on screen. All of the size - 1 convert the size into indexes.
        //There is an extra -1 so that we don't get any partial headings, so we get the heading next to the end
        int currentHeadingIndex = 0;
        while (currentHeadingIndex < dateIndex) {
            dateIndicatorHeadings = datePageIndicator.findElements(By.id(ResourceLocator.device.AWE_SCHEDULE_DATE_HEADINGS));
            int indexesToTheRight = (dateIndicatorHeadings.size() - 2) - getSelectedIndex(dateIndicatorHeadings);
            //take how many we've seen (currentHeadingIndex) and add the indexes to the right and see if we can see the dateIndex
            if (indexesToTheRight + currentHeadingIndex >= dateIndex) {
                //By subtracting the currentHeading index we account for what we have scrolled past so far.
                WebElement heading = dateIndicatorHeadings.get(dateIndex - currentHeadingIndex + getSelectedIndex(dateIndicatorHeadings));
                String headingText = heading.getText();
                heading.click();
                return headingText;
            } else {
                //scroll to the end
                MobileElement mobileElement = (MobileElement) dateIndicatorHeadings.get(dateIndicatorHeadings.size() - 2);
                //Not entirely sure why this works, but this will swipe to the given element
                mobileElement.swipe(SwipeElementDirection.DOWN, 1);
                //increment the index by the total headings on screen minus the index we are about to move to, this will get the net moved.
                currentHeadingIndex += indexesToTheRight;
            }

        }

        return null;
    }

    /**
     * Swipe the schedule by swiping the body of the schedule. Can go up or down to see different times of day. Left/Right will change the day.
     *
     * @param direction direction to swipe, up down left right
     * @return The new current heading, which may be the same as it was before depending on the direction.
     */
    public String swipeScheduleBody(SwipeElementDirection direction) {
        MobileElement showLayout;
        //If we are scrolling down we need to an element on the bottom
        if(direction == SwipeElementDirection.DOWN){
            List<WebElement> scheduleShowsList = driverWrapper.elements(By.id(ResourceLocator.device.AWE_SCHEDULE_SHOW_LISTING));
            showLayout = (MobileElement) scheduleShowsList.get(scheduleShowsList.size()-1);
        }
        showLayout = (MobileElement)driverWrapper.elements(By.id(ResourceLocator.device.AWE_SCHEDULE_SHOW_LISTING)).get(0);
        showLayout.swipe(direction, 1000);

        //Heading may have changed based on the swipe direction.
        return getCurrentDateHeading();
    }
}
