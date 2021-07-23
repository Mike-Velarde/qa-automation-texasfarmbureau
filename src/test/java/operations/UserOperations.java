package operations;

import com.bottlerocket.driverwrapper.DriverWrapper;

/**
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class UserOperations implements AutomationOperationsListener {
    DriverWrapper driverWrapper;

    @Override
    public void init(DriverWrapper driverWrapper) {
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
