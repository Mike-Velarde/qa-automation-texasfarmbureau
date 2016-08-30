package operations;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import domod.UserBank;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Operations which are performed on a screen, often what a user themselves would do 
 * //TODO consider moving all the video operations to it's own video operations class
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class UserOperations implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    abstract public void signIn(UserBank.User userBank, boolean forced) throws WebDriverWrapperException;

    /**
     * Check to see if the button is in login or logout mode
     *
     * This may need to be expanded
     *
     * @return True if the button is in "login" mode, that is clicking it will start the login process
     */
    protected abstract boolean inLoginMode();

    public abstract void signOut() throws WebDriverWrapperException;

    /**
     * Check to see if the feed picker has been launched, and if it has select the given brand and feed
     *
     * @param aweBrandName
     * @param feedName
     * @param pickFeedServerURLID
     */
    public abstract void chooseFeedIfNeeded(String aweBrandName, String feedName, String pickFeedServerURLID) throws WebDriverWrapperException;

    public abstract String getShowDetailsShowTitle();

    public int getDrawerWatchlistCount() throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.openMainDrawerSafe();
        List<WebElement> watchlistCount = driverWrapper.elements(By.id(ResourceLocator.device.AWE_DRAWER_WATCHLIST_COUNT));
        // We have already checked if the drawer is open using openMainDrawerSafe, so if this doesn't exist count is 0
        if (watchlistCount.size() == 0) {
            AutomationOperations.instance().navOp.closeMainDrawer();
            return 0;
        }

        int watchCount = Integer.parseInt(watchlistCount.get(0).getText());
        AutomationOperations.instance().navOp.closeMainDrawer();
        return watchCount;
    }

    /**
     * This method makes the assumption that the "always show video player controls" option is on
     *
     * Takes the runtime and total time of the video playing and formats it into a calendar
     *
     * @param runOrTotal
     *            Return either the runtime or the total time
     * @return time
     */
    public Calendar getVideoCurrentRunTime(boolean runOrTotal) {
        String time = driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_TIMER).getText();
        Calendar cal = Calendar.getInstance();

        String runTime = time.split("/")[0];
        String totalTime = time.split("/")[1];

        time = runOrTotal ? runTime : totalTime;

        SimpleDateFormat sdf;

        // This is the length of "mm:ss" plus one space, because there is a space before and after the / in the runtime / total time
        int minuteSecondLength = 6;
        if (runTime.length() > minuteSecondLength) {
            sdf = new SimpleDateFormat("HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat("mm:ss");
        }
        java.util.Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException ex) {
            ErrorHandler.printErr("Error parsing video time", ex);
        }
        cal.setTime(date);

        return cal;
    }

    /**
     * This is NOT reliable and a new solution must be found
     *
     * The only way I have currently found out how to pause the video is to use one thread to long tap, while using another thread to check for the element. Then, I am waiting for the two threads to be done before moving forward
     */
    private void pauseVideoOld() {
        // bringUpVideoUI();
        // Wait a short amount of time for the black screen which shows up after hitting play to go away
        waitForVideoLoading(30);
        driverWrapper.waitLogErr(3000);
        Thread t = new Thread(new LongTapAsynch());
        t.run();
        Thread t2 = new Thread(new ClickVideoPlayPauseAsynch());
        t2.run();
        try {
            t.join();
            t2.join();
        } catch (Exception e) {
            ErrorHandler.printErr("Error when joining threads", e);
        }

    }

    private boolean isVideoFinished() {
        boolean finished;
        driverWrapper.setImplicitWait(5, TimeUnit.SECONDS);
        finished = !driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_VIDEO_PLAYER_ROOT));
        driverWrapper.restoreImplicitWait();

        return finished;
    }

    // Maybe use pollingevery for this method?
    private void waitForVideoLoading(int timeout) {
        driverWrapper.setImplicitWait(5, TimeUnit.SECONDS);
        // Wait for the loading bar to not be spinning
        long currentTime = System.currentTimeMillis();
        long timeElapsed;

        // While there is a loading spinner wait
        while (driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_VIDEO_LOADING_SPINNER))) {
            // divide by 1000 to get seconds
            timeElapsed = (System.currentTimeMillis() - currentTime) / 1000;
            if (timeout < timeElapsed) {
                throw new TimeoutException("The video took too long to load");
            }
        }

        currentTime = System.currentTimeMillis();
        // check for ads
        while (driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_VIDEO_PLAYER_AD_COUNTDOWN_BANNER))) {
            timeElapsed = (System.currentTimeMillis() - currentTime) / 1000;
            if (150 > timeElapsed) {
                throw new TimeoutException("The ads took too long to load");
            }
        }

        // restore to previous wait
        driverWrapper.restoreImplicitWait();
    }

    public abstract void pauseVideo();

    public abstract void playVideo();

    public abstract boolean isVideoPaused();

    public abstract boolean isVideoPlaying();

    public void playPauseVideo() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_PLAY_PAUSE).click();
    }

    /**
     * Bring up UI on the video player so we can access those elements. Be aware the UI will only be up for a short time
     */
    public void bringUpVideoUI() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_ROOT).click();
    }

    public void videoDetailsPlayVideo() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_DETAILS_PLAY_BUTTON).click();
    }

    public abstract boolean isUserLoggedIn();

    public void closedCaptionsToggle() {
        // open CC menu
        driverWrapper.getElementByName(ResourceLocator.device.AWE_VIDEO_PLAYER_CLOSED_CAPTION_DESC).click();
    }


    public abstract void scrubVideo(double percent);

    public abstract int search(String searchTerm) throws WebDriverWrapperException;

    public abstract void closedCaptionsOn();

    public abstract void closedCaptionsOff();

    public abstract void shareShowFacebook();

    public abstract void contentDrivenSignIn(UserBank.User user);


    public class LongTapAsynch implements Runnable {

        public void run() {
            WebElement webElement = driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_ROOT);
            driverWrapper.tap(1, webElement, 8000);
        }

    }

    public class ClickVideoPlayPauseAsynch implements Runnable {

        public void run() {
            driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_PLAY_PAUSE).click();
        }

    }

    public void enterSearchTitle(String title) {
        driverWrapper.getElementById(ResourceLocator.device.AWE_SEARCH_BAR_ENTER_TEXT).sendKeys(title);
    }

    public void clearSearchField() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_SEARCH_BAR_ENTER_TEXT).clear();
    }

    /**
     * It will return the search field text
     * 
     * @return search field text
     */
    public String getSearchFieldText() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_SEARCH_BAR_ENTER_TEXT).getText();
    }
}
