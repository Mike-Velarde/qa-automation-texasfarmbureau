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
public abstract class NavOpsSchedule implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    public abstract String chooseDateHeading(int dateIndex);

    protected int getSelectedIndex(List<WebElement> webElementList){
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

    public abstract String swipeScheduleBody(SwipeElementDirection direction);
}
