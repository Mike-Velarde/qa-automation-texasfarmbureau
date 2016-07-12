package operations.navops;


 import com.bottlerocket.utils.Logger;
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
public class NavOpsSchedule implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    public String chooseDateHeading(int dateIndex) {
        WebElement datePageIndicator = driverWrapper.getElementById(ResourceLocator.device.AWE_SCHEDULE_DATE_PAGE_INDICATOR);
        List<WebElement> dateIndicatorHeadings;

        //The general idea is something like this: we want the 30th item, but only 8 can fit on the screen at once
        //So we scroll past 24 items then we want to get the 6th item on screen. All of the size - 1 convert the size into indexes.
        //There is an extra -1 so that we don't get any partial headings, so we get the heading next to the end
        int currentHeadingIndex = 0;
        while(currentHeadingIndex < dateIndex){
            dateIndicatorHeadings = datePageIndicator.findElements(By.id(ResourceLocator.device.AWE_SCHEDULE_DATE_HEADINGS));
            int indexesToTheRight = (dateIndicatorHeadings.size() - 2) - getSelectedIndex(dateIndicatorHeadings);
            //take how many we've seen (currentHeadingIndex) and add the indexes to the right and see if we can see the dateIndex
            if(indexesToTheRight + currentHeadingIndex >= dateIndex) {
                //By subtracting the currentHeading index we account for what we have scrolled past so far.
                WebElement heading = dateIndicatorHeadings.get(dateIndex - currentHeadingIndex + getSelectedIndex(dateIndicatorHeadings));
                String headingText = heading.getText();
                heading.click();
                return headingText;
            }
            else{
                //scroll to the end
                MobileElement mobileElement = (MobileElement) dateIndicatorHeadings.get(dateIndicatorHeadings.size() - 2);
                //Not entirely sure why this works, but this will swipe to the given element
                mobileElement.swipe(SwipeElementDirection.DOWN, 1);
                //increment the index by the total headings on screen minus the index we are about to move to, this will get the net moved.
                currentHeadingIndex += indexesToTheRight;
                //Let settle after swipe, this seems to fix a few odd timing issues, not sure what's causing them but the exit if condition seems to trigger early in these situations
                driverWrapper.waitLogErr(3000);
            }

        }

        return null;
    }

    private int getSelectedIndex(List<WebElement> webElementList){
        for(int i=0; i<webElementList.size(); i++){
            WebElement element = webElementList.get(i);
            if(element.isSelected()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the current selected date heading, if none found will return null.
     *
     * @return current date heading or null if none found. It is not expected that no element will be found
     */
    public String getCurrentDateHeading() {
        WebElement datePageIndicator = driverWrapper.getElementById(ResourceLocator.device.AWE_SCHEDULE_DATE_PAGE_INDICATOR);
        List<WebElement> dateIndicatorHeadings = datePageIndicator.findElements(By.id(ResourceLocator.device.AWE_SCHEDULE_DATE_HEADINGS));

        for(WebElement element : dateIndicatorHeadings){
            if(element.isSelected()) {
                return element.getText();
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

        //Wait for the swipe to settle. There may be an object we can wait on but I have no idea what that would be.
        driverWrapper.waitLogErr(4000);
        //Heading may have changed based on the swipe direction.
        return getCurrentDateHeading();
    }
}
