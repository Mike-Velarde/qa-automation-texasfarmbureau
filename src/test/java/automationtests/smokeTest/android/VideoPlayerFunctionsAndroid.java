package automationtests.smoketest.android;

import automationtests.smoketest.general.VideoPlayerFunctions;
import operations.AutomationOperations;
import org.testng.annotations.Test;

/**
 * Created by ford.arnett on 7/13/16.
 */
public class VideoPlayerFunctionsAndroid extends VideoPlayerFunctions {
    @Test
    public void testScrub(){
        AutomationOperations.instance().userOp.pauseVideo();
        driverWrapper.takeScreenshotSuppressError("verify_video_is_scrubbed_before_" + System.currentTimeMillis());
        AutomationOperations.instance().userOp.scrubVideo(0.6);
        driverWrapper.takeScreenshotSuppressError("verify_video_is_scrubbed_after_" + System.currentTimeMillis());
    }

    @Test
    public void testPlay(){
        super.play();
    }

    @Test
    public void testPause(){
        super.pause();
    }

    @Test
    public void testClosedCaption(){
        super.closedCaption();
    }
}
