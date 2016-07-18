package automationtests.assertions;

import com.bottlerocket.webdriverwrapper.WebDriverWrapper;

/**
 * Created by ford.arnett on 4/11/16.
 */
public abstract class AssertionLibrary {
    /**
     * Assert that the video runtime has changed
     *
     * @param waitInMillis time to wait for video during play, this should be the difference in between the two observed times
     */
    public abstract void assertVideoRuntimeChanged(AssertionLogger assertionLogger, WebDriverWrapper driverWrapper, int wait);

}
