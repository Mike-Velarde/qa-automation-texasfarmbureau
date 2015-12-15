package operations;

import com.bottlerocket.domod.InputUtils;
import com.bottlerocket.utils.ErrorHandler;
import com.bottlerocket.webdriver.WebDriverWrapper;
import config.AppDefaults;
import config.ResourceLocator;
import domod.UserBank;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Operations which are preformed on a screen, often what a user themselves would do
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

    public void signIn(UserBank.User user){
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        WebElement loginLogoutButton = driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT);
        if(!inLoginMode())
          signOut();
        loginLogoutButton.click();
        driverWrapper.getElementById(ResourceLocator.device.AWE_LOGIN_CONTINUE).click();
        driverWrapper.swipeToElement(ResourceLocator.device.CABLE_PROVIDER_IMAGE_ID, ResourceLocator.device.OPTIMUM_CONTENT_DESC, WebDriverWrapper.AttributeCompareType.contentDesc, 20000).click();


        //This part is all in a WebView, we must use find in order to get the elements
        InputUtils utils = new InputUtils(driverWrapper);
        //make sure WebView is available
        WebElement webView = driverWrapper.driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(ResourceLocator.device.WEBKIT_WEBVIEW)));
        //The webView seems to need to be activated in order to get the elements from it

        List<WebElement> authElements = webView.findElements(By.className(ResourceLocator.device.EDIT_TEXT));
        long startTime = System.currentTimeMillis();

        //Keep trying to get web elements until timeout is reached. Sometimes even though the webview is visible it takes a little while to be able to grab the elements
        while(authElements.size() == 0 && (System.currentTimeMillis() - startTime)/(1000) < AppDefaults.globalWait){
            ((MobileElement) webView).tap(1,2000);
            authElements = webView.findElements(By.className(ResourceLocator.device.EDIT_TEXT));
        }

        //Sometimes we need to wait a little before the web view can be activated. This waits a few seconds and tries to activate the webview again. This probably isn't the best solution, but it should work
        if(authElements.size() == 0){
            driverWrapper.waitLogErr(3000);
            ((MobileElement) webView).tap(1, 2000);
            authElements = webView.findElements(By.className(ResourceLocator.device.EDIT_TEXT));
        }
        //Set username and password
        WebElement usernameElement = authElements.get(0); //driverWrapper.find(ResourceLocator.device.PROVIDER_LOGIN_USERNAME_ID);
        utils.sendKeysHideKeyboard(usernameElement, user.username);
        WebElement passwordElement = authElements.get(1); //driverWrapper.find(ResourceLocator.device.PROVIDER_LOGIN_PASSWORD_ID);
        utils.sendKeysHideKeyboard(passwordElement, user.password);

        //Scroll down so we can see the sign in button, this may not work in all scenarios, needs more testing. The first number is how far away from bottom, and I believe the second is as well
        ((AndroidElement) webView).swipe(SwipeElementDirection.UP,350, 150, 2000);
        webView.findElements(By.className("android.widget.Image")).get(2).click();
        //This doesn't work on all devices
        //driverWrapper.find(ResourceLocator.device.PROVIDER_SIGN_IN_BUTTON).click();

        //Wait for toolbar, then check to see that provider logo is now in toolbar
        driverWrapper.driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR)));
    }

    /**
     * Check to see if the button is in login or logout mode
     *
     * This may need to be expanded
     *
     * @return True if the button is in "login" mode, that is clicking it will start the login process
     */
    private boolean inLoginMode() {
        WebElement loginLogoutButton = driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT);
        //Older versions have Log In as two words, this is to account for that variance
        return loginLogoutButton.getText().equalsIgnoreCase("Log in to provider") || loginLogoutButton.getText().equalsIgnoreCase("Login to provider");
    }

    public void signOut() {
        String title = driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_TITLE_ID).getText();
        if(!title.equalsIgnoreCase(ResourceLocator.DrawerNavigationItem.settings.toString())) {
            AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        }
        WebElement loginLogoutButton = driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT);
        if(!inLoginMode()) {
            loginLogoutButton.click();
            AutomationOperations.instance().navOp.genericYesNoPopup(true);
        }
    }

    private void selectFeed(String pickFeedServerURLId) {
        driverWrapper.getElementById(pickFeedServerURLId).click();
    }

    /**
     * Check to see if the feed picker has been launched, and if it has select the given brand and feed
     *
     * @param aweBrandName
     * @param feedName
     * @param pickFeedServerURLID
     */
    public void chooseFeedIfNeeded(String aweBrandName, String feedName, String pickFeedServerURLID) {
        //Check if we are on feature screen
        if(AutomationOperations.instance().navOp.featured.isOnPage())
            return;

        AutomationOperations.instance().navOp.chooseFeeds.choseBrandToPickFeed(aweBrandName);
        AutomationOperations.instance().navOp.chooseFeeds.pickFeedToAWEHome(feedName);
        AutomationOperations.instance().userOp.selectFeed(pickFeedServerURLID);
    }

    public String getShowDetailsShowTitle() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SHOW_TITLE).getText();
    }

    public int getDrawerWatchlistCount(){
        AutomationOperations.instance().navOp.openMainDrawerSafe();
        List<WebElement> watchlistCount = driverWrapper.elements(By.id(ResourceLocator.device.AWE_DRAWER_WATCHLIST_COUNT));
        //We have already checked if the drawer is open using openMainDrawerSafe, so if this doesn't exist count is 0
        if(watchlistCount.size() == 0){
            AutomationOperations.instance().navOp.closeMainDrawer();
            return 0;
        }

        int watchCount = Integer.parseInt(watchlistCount.get(0).getText());
        AutomationOperations.instance().navOp.closeMainDrawer();
        return watchCount;
    }

    /**
     * Takes the runtime and total time of the video playing and formats it into a calendar
     *
     * @param runOrTotal Return either the runtime or the total time
     * @return time
     */
    public Calendar getVideoCurrentRunTime(boolean runOrTotal){
        String time = driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_TIMER).getText();
        Calendar cal = Calendar.getInstance();

        String runTime = time.split("/")[0];
        String totalTime = time.split("/")[1];

        time = runOrTotal ? runTime : totalTime;

        SimpleDateFormat sdf;

        //This is the length of "mm:ss" plus one space, because there is a space before and after the / in the runtime / total time
        int minuteSecondLength = 6;
        if(runTime.length() > minuteSecondLength){
            sdf = new SimpleDateFormat("HH:mm:ss");
        }
        else{
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
     * The only way I have currently found out how to pause the video is to use one thread to long tap, while using another thread to check for the element.
     * Then, I am waiting for the two threads to be done before moving forward
     */
    private void pauseVideo() {
        //bringUpVideoUI();
        //Wait a short amount of time for the black screen which shows up after hitting play to go away
        waitForVideoLoading(30);
        driverWrapper.waitLogErr(3000);
        Thread t = new Thread(new LongTapAsynch());
        t.run();
        Thread t2 = new Thread(new ClickVideoPlayPauseAsynch());
        t2.run();
        try {
            t.join();
            t2.join();
        }
        catch (Exception e){
            ErrorHandler.printErr("Error when joining threads", e);
        }

    }

    private boolean isVideoFinished(){
        boolean finished;

        driverWrapper.setImplicitWait(5, TimeUnit.SECONDS);
        finished = driverWrapper.elements(By.id(ResourceLocator.device.AWE_VIDEO_PLAYER_ROOT)).size() == 0;
        driverWrapper.restoreImplicitWait();

        return finished;
    }

    //Maybe use pollingevery for this method?
    private void waitForVideoLoading(int timeout) {
        driverWrapper.setImplicitWait(5, TimeUnit.SECONDS);
        //Wait for the loading bar to not be spinning
        long currentTime = System.currentTimeMillis();
        long timeElapsed;

        //While there is a loading spinner wait
        while(driverWrapper.elements(By.id(ResourceLocator.device.AWE_VIDEO_LOADING_SPINNER)).size()!=0){
            //divide by 1000 to get seconds
             timeElapsed = (System.currentTimeMillis() - currentTime) / 1000;
            if(timeout < timeElapsed){
                throw new TimeoutException("The video took too long to load");
            }
        }

        currentTime = System.currentTimeMillis();
        //check for ads
        while(driverWrapper.elements(By.id(ResourceLocator.device.AWE_VIDEO_PLAYER_AD_COUNTDOWN_BANNER)).size()!=0){
            timeElapsed = (System.currentTimeMillis() - currentTime) / 1000;
            if(150 > timeElapsed){
                throw new TimeoutException("The ads took too long to load");
            }
        }


        //restore to previous wait
        driverWrapper.restoreImplicitWait();
    }

    public void playPauseVideo(){
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

    /**
     * This will work if called from any screen which can see the banner. Since it seems to be most screens, I've opted not to make this more disruptive by forcing unnecessary navigation.
     * @return true if a user is logged in
     */
    public boolean isUserLoggedIn() {
        driverWrapper.setImplicitWait(5, TimeUnit.SECONDS);
        //If this element is there then a user is logged in
        boolean loggedIn = driverWrapper.elements(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR_PROVIDER_LOGO)).size() != 0;
        driverWrapper.restoreImplicitWait();
        return loggedIn;
    }

    public void closedCaptionsToggle() {
        //open CC menu
        driverWrapper.getElementByName(ResourceLocator.device.AWE_VIDEO_PLAYER_CLOSED_CAPTION_DESC).click();
    }

    public void scrubVideo(double percentFromLeft) {
        //Not sure what the highest index to the right we can go is yet, 90% seems like a good amount
        if(percentFromLeft > 0.9){
            percentFromLeft = 0.9;
        }

        MobileElement seekBar = (MobileElement) driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_SEEK_BAR);
        Point seekCoordCenter = seekBar.getCenter();
        //this is the amount of index we are going to move over from the left
        int scrubFromLeft = (int)(seekCoordCenter.getX() * 2 * percentFromLeft);
        seekBar.swipe(SwipeElementDirection.LEFT, 1, scrubFromLeft, 1);
    }

    public int search(String searchTerm) {
        AutomationOperations.instance().navOp.mainToolbarSearch();
        InputUtils utils = new InputUtils(driverWrapper);
        WebElement search = driverWrapper.getElementById(ResourceLocator.device.AWE_SEARCH_BAR_ENTER_TEXT);
        utils.sendKeysSafe(search, searchTerm);
        //Hit enter key, not sure why this is needed here but send keys doesn't trigger the search
        driverWrapper.pressKeyCode(ResourceLocator.device.KEYCODE_ENTER);

        //See how many search results there are on the page
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SEARCH_RESULTS)).size();
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
}

