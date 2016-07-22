package operations.navops;

import config.ResourceLocatorIos;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.openqa.selenium.By;

/**
 * Created by ford.arnett on 11/17/15.
 */
public class NavOpsScheduleIos extends NavOpsSchedule{
    @Override
    public String chooseDateHeading(int dateIndex) {
        //without better labels, this is the only way I've found to get the elements
        MobileElement heading = (MobileElement) driverWrapper.elements(By.xpath(ResourceLocatorIos.AWE_SCHEDULE_DAYS_XPATH)).get(dateIndex);
        int scheduleYCoord = heading.getLocation().getY();

        //If a heading is out of view on the screen the Y coordinate is normally 0. If it is the most recently dismissed heading, the y will be 1. All others will have greater Y coordinates.
        if(scheduleYCoord == 0 || scheduleYCoord == 1){
            return "Date heading not visible";
        }

        heading.tap(2, 1000);

        return "Unable to get date heading title at this time";
    }

    /**
     * This method is currently limited. You can only swipe on the body on the first day. When moving to another day for whatever reason the xml does not update.
     * It still shows the CollectionView for the body swipe in the first cell. It will most likely be too expensive to check if we are on the
     * first heading or swipe up to that heading so we are assuming, at least for now, that we are on the first heading
     *
     * @param direction direction to swipe, up down left right
     * @return
     */
    @Override
    public String swipeScheduleBody(SwipeElementDirection direction){
        MobileElement scheduleBody = (MobileElement) driverWrapper.elements(By.xpath(ResourceLocatorIos.AWE_SCHEDULE_DAYS_XPATH)).get(0);
        driverWrapper.swipe(scheduleBody, direction, 2000);

        return "Unable to get date heading title for swipe verification at this time";
    }
}
