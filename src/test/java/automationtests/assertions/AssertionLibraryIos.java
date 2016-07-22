package automationtests.assertions;

import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;

/**
 * Created by ford.arnett on 6/7/16.
 */
public class AssertionLibraryIos extends AssertionLibrary {

    @Override
    public void assertVideoRuntimeChanged(AssertionLogger assertionLogger, WebDriverWrapper driverWrapper, int wait) {
        //TODO this isn't an assertion, need to figure out how to assert on ios
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_video_before_" + System.currentTimeMillis());
        driverWrapper.waitLogErr(wait);
        driverWrapper.takeScreenshotSuppressError(AutomationConfigProperties.screenshotsDirectory, "verify_video_after_" + System.currentTimeMillis());

    }
}
