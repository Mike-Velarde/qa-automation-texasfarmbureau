package operations;

import com.bottlerocket.webdriverwrapper.WebDriverWrapper;

/**
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class UserOperations implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    public void takeScreenshot(String fileName) {
        //A lot of these screenshots take really quickly, so allow some time
        driverWrapper.takeScreenshotSuppressError(fileName + "_" + System.currentTimeMillis());
    }

    public void waitLogErr(int millis) {
        driverWrapper.waitLogErr(millis);
    }
    
}
