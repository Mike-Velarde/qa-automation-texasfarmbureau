package operations.navops;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import operations.AutomationOperationsListener;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ford.arnett on 10/20/15.
 */
public abstract class NavOpsFeatured implements AutomationOperationsListener{
    protected WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    /**
     * Returns true iff the app is on this page
     *
     * @return true iff on the featured page
     */
    public boolean isOnPage() {
        //See if the awe featured tag is visible
        return driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR_TITLE_ID));
    }

    public abstract String selectCallToAction(ResourceLocator.CallsToAction callsToAction) throws WebDriverWrapperException;

    public boolean isWebsiteVisible() {
        return driverWrapper.driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(ResourceLocator.device.WEBKIT_WEBVIEW))).size() != 0;
    }

    public void scrollToBottom() {
        MobileElement mobileElement = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_CONTAINER);
        mobileElement.swipe(SwipeElementDirection.DOWN, 10000);
    }
}
