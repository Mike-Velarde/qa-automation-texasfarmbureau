package operations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;

import assertions.AssertionLogger;
import config.ResourceLocator;
import domod.UserBank;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import utils.VideoUtils;

/**
 * Operations which are performed on a screen, often what a user themselves would do //TODO consider moving all the video operations to it's own video operations class
 *
 * Created by ford.arnett on 9/3/15.
 */
public abstract class UserOperations implements AutomationOperationsListener {
    WebDriverWrapper driverWrapper;

    @Override
    public void init(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    abstract public void signIn(UserBank.User userBank);

    /**
     * Check to see if the button is in login or logout mode
     *
     * This may need to be expanded
     *
     * @return True if the button is in "login" mode, that is clicking it will start the login process
     */
    protected boolean inLoginMode() {
        WebElement loginLogoutButton = driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT);
        // Older versions have has different variations of the login text, as does iOS
        return loginLogoutButton.getText().equalsIgnoreCase("Log in to provider") || loginLogoutButton.getText().equalsIgnoreCase("Login to provider") || loginLogoutButton.getText().equalsIgnoreCase("Sign in to provider") || loginLogoutButton.getText().equalsIgnoreCase("Log In To Provider");
    }

    public void signOut() {
        String title = driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_TITLE_ID).getText();
        if (!title.equalsIgnoreCase(ResourceLocator.DrawerNavigationItem.settings.toString())) {
            AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        }
        // Some devices have the bottom options off screen, this will scroll down for those devices
        driverWrapper.scroll_to(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE);
        WebElement loginLogoutButton = driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT);
        // I'm not sure this check is necessary
        if (!inLoginMode()) {
            loginLogoutButton.click();
            AutomationOperations.instance().navOp.genericYesNoPopup(true);
        }
    }

    /**
     * Check to see if the feed picker has been launched, and if it has select the given brand and feed
     *
     * @param aweBrandName
     * @param feedName
     * @param pickFeedServerURLID
     */
    public abstract void chooseFeedIfNeeded(String aweBrandName, String feedName, String pickFeedServerURLID);

    public abstract String getShowDetailsShowTitle();

    public int getDrawerWatchlistCount() {
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
    private void pauseVideo() {
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
        finished = !driverWrapper.checkElementExists(By.id(ResourceLocator.device.AWE_VIDEO_PLAYER_ROOT));
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
        while (driverWrapper.checkElementExists(By.id(ResourceLocator.device.AWE_VIDEO_LOADING_SPINNER))) {
            // divide by 1000 to get seconds
            timeElapsed = (System.currentTimeMillis() - currentTime) / 1000;
            if (timeout < timeElapsed) {
                throw new TimeoutException("The video took too long to load");
            }
        }

        currentTime = System.currentTimeMillis();
        // check for ads
        while (driverWrapper.checkElementExists(By.id(ResourceLocator.device.AWE_VIDEO_PLAYER_AD_COUNTDOWN_BANNER))) {
            timeElapsed = (System.currentTimeMillis() - currentTime) / 1000;
            if (150 > timeElapsed) {
                throw new TimeoutException("The ads took too long to load");
            }
        }

        // restore to previous wait
        driverWrapper.restoreImplicitWait();
    }

    public void playPauseVideo() {
        driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_PLAY_PAUSE).click();
    }

    public boolean isVideoPaused() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_PLAY_PAUSE).getText().equals("OFF");
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
    
    /**
     * This will work if called from any screen which can see the banner. Since it seems to be most screens, I've opted not to make this more disruptive by forcing unnecessary navigation.
     * 
     * @return true if a user is logged in
     */
    public boolean isUserLoggedIn() {
        driverWrapper.setImplicitWait(5, TimeUnit.SECONDS);
        // If this element is there then a user is logged in
        boolean loggedIn = driverWrapper.checkElementExists(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR_PROVIDER_LOGO));
        driverWrapper.restoreImplicitWait();
        return loggedIn;
    }

    public void closedCaptionsToggle() {
        // open CC menu
        driverWrapper.getElementByName(ResourceLocator.device.AWE_VIDEO_PLAYER_CLOSED_CAPTION_DESC).click();
    }

    /**
     * Scrub a percentage of the video from the left of the seek bar. This method has no concept of where the video is currently
     *
     * @param percentFromLeft
     *            a percentage, number from 0 to 1, indicating where to scrub to. Currently seeking to the very end is not reliable. Instead this method will adjust any value higher than .9 to .9. This insures we are not seeking out of bounds.
     */
    public void scrubVideo(double percentFromLeft) {
        // Not sure what the highest index to the right we can go is yet, 90% seems like a good amount
        if (percentFromLeft > 0.9) {
            percentFromLeft = 0.9;
        }

        MobileElement seekBar = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_SEEK_BAR);
        Point seekCoordCenter = seekBar.getCenter();
        // this is the amount of index we are going to move over from the left
        int scrubFromLeft = (int) (seekCoordCenter.getX() * 2 * percentFromLeft);
        seekBar.swipe(SwipeElementDirection.LEFT, 1, scrubFromLeft, 1);
    }

    public abstract int search(String searchTerm);

    /**
     * Assert that the video runtime has changed
     *
     * This may not belong in this class
     *
     * @param wait
     */
    public void assertVideoRuntimeChanged(AssertionLogger assertionLogger, int wait) {
        Calendar startTime = AutomationOperations.instance().userOp.getVideoCurrentRunTime(true);
        driverWrapper.waitLogErr(wait);
        assertionLogger.setTestType("Verify that the video time has progressed, meaning the video has played.");
        Calendar timeAfterPlay = AutomationOperations.instance().userOp.getVideoCurrentRunTime(true);
        assertionLogger.assertNotEquals(VideoUtils.getVideoTimeFromCalendar(startTime), VideoUtils.getVideoTimeFromCalendar(timeAfterPlay));
    }

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
