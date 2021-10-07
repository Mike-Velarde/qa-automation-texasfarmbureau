package operations;

import automationtestinstance.AutomationTestManager;

/**
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class UserOperations implements TestInitializerListener {
    AutomationTestManager am;

    @Override
    public void init(AutomationTestManager am) {
        this.am = am;
    }

    public void takeScreenshot(String fileName) {
        //A lot of these screenshots take really quickly, so allow some time
        am.driverWrapper.takeScreenshotSuppressError(fileName + "_" + System.currentTimeMillis());
    }

    public void waitLogErr(int millis) {
        am.driverWrapper.waitLogErr(millis);
    }
    
}
