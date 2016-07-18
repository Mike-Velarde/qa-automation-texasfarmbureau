package automationtests.smokeTest.ios;

import automationtests.smokeTest.generic.VideoPlayerFunctions;
import org.testng.annotations.Test;

/**
 *
 * Ios scrubbing doesn't work, however, all other methods do. Therefore this subclass only uses the tests of the superclass that make sense for iOS.
 *
 * Created by ford.arnett on 6/27/16.
 */
public class VideoPlayerFunctionsIos extends VideoPlayerFunctions {
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
