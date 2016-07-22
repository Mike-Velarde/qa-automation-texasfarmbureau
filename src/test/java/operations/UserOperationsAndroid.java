package operations;

import com.bottlerocket.config.AutomationConfigProperties;
import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.utils.InputUtils;
import com.bottlerocket.webdriverwrapper.WebDriverWrapper;
import config.ResourceLocator;
import config.ResourceLocatorAndroid;
import domod.UserBank;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class UserOperationsAndroid extends UserOperations {

    /**
     * Sign in to allow the playing of restricted content
     *
     *
     * @param user The login credentials
     * @param forced This will force a logout if already signed in and then sign in again. If forced is false and user is already logged in the method will return.
     */
    public void signIn(UserBank.User user, boolean forced) throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        //Some devices have the bottom options off screen, this will scroll down for those devices
        driverWrapper.scroll_to(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE);
        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT);
        if(!inLoginMode()) {
            if(forced) {
                signOut();
            }
            else {
                return;
            }
        }

        driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT).click();
        MobileElement cableProvider = driverWrapper.swipeToElement(ResourceLocator.device.CABLE_PROVIDER_IMAGE_ID, ResourceLocator.device.OPTIMUM_CONTENT_DESC, WebDriverWrapper.AttributeCompareType.contentDesc, 90000, 2);

        cableProvider.click();


        //This part is all in a WebView, we must use find in order to get the elements
        InputUtils utils = AutomationOperations.instance().deviceAutomationComponents.createInputUtils(driverWrapper);
        //make sure WebView is available
        WebElement webView = driverWrapper.driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(ResourceLocator.device.WEBKIT_WEBVIEW)));
        //The webView seems to need to be activated in order to get the elements from it

        List<WebElement> authElements = webView.findElements(By.className(ResourceLocatorAndroid.EDIT_TEXT));
        long startTime = System.currentTimeMillis();

        //Keep trying to get web elements until timeout is reached. Sometimes even though the webview is visible it takes a little while to be able to grab the elements
        while(authElements.size() == 0 && (System.currentTimeMillis() - startTime)/(1000) < AutomationConfigProperties.globalWait){
            ((MobileElement) webView).tap(1,2000);
            authElements = webView.findElements(By.className(ResourceLocatorAndroid.EDIT_TEXT));
        }

        //Sometimes we need to wait a little before the web view can be activated. This waits a few seconds and tries to activate the webview again. This probably isn't the best solution, but it should work
        if(authElements.size() == 0){
            driverWrapper.waitLogErr(3000);
            ((MobileElement) webView).tap(1, 2000);
            authElements = webView.findElements(By.className(ResourceLocatorAndroid.EDIT_TEXT));
        }
        //Set username and password
        WebElement usernameElement = authElements.get(0); //driverWrapper.find(ResourceLocator.device.PROVIDER_LOGIN_USERNAME_ID);
        utils.sendKeysHideKeyboard(usernameElement, user.username);
        WebElement passwordElement = authElements.get(1); //driverWrapper.find(ResourceLocator.device.PROVIDER_LOGIN_PASSWORD_ID);
        utils.sendKeysSafe(passwordElement, user.password);

        utils.submitForm();

        //The two different approaches below have seemed unreliable across devices
        //Scroll down so we can see the sign in button, this may not work in all scenarios, needs more testing. The first number is how far away from bottom, and I believe the second is as well
        //((AndroidElement) webView).swipe(SwipeElementDirection.UP,350, 150, 2000);
        //webView.findElements(By.className(ResourceLocator.device.IMAGE_VIEW)).get(2).click();
        //This doesn't work on all devices
        //driverWrapper.find(ResourceLocator.device.PROVIDER_SIGN_IN_BUTTON).click();

        //Wait for toolbar, then check to see that provider logo is now in toolbar
        driverWrapper.driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR)));
    }

    public void signOut() throws WebDriverWrapperException {
        String title = driverWrapper.getElementById(ResourceLocator.device.AWE_MAIN_TOOLBAR_TITLE_ID).getText();
        if (!title.equalsIgnoreCase(ResourceLocator.DrawerNavigationItem.settings.toString())) {
            AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        }
        // Some devices have the bottom options off screen, this will scroll down for those devices
        driverWrapper.scroll_to(ResourceLocator.device.AWE_SETTINGS_DEV_OPTIONS_TITLE);
        WebElement loginLogoutButton = driverWrapper.getElementById(ResourceLocator.device.AWE_SETTINGS_LOGIN_LOGOUT_TEXT);

        loginLogoutButton.click();
        AutomationOperations.instance().navOp.genericYesNoPopup(true);
    }

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

    /**
     * This will work if called from any screen which can see the banner. Since it seems to be most screens, I've opted not to make this more disruptive by forcing unnecessary navigation.
     *
     * @return true if a user is logged in
     */
    public boolean isUserLoggedIn() {
        driverWrapper.setImplicitWait(10, TimeUnit.SECONDS);
        // If this element is there then a user is logged in
        boolean loggedIn = driverWrapper.elementExists(By.id(ResourceLocator.device.AWE_MAIN_TOOLBAR_PROVIDER_LOGO));
        driverWrapper.restoreImplicitWait();
        return loggedIn;
    }

    /**
     * Scrub a percentage of the video from the left of the seek bar. This method has no concept of where the video is currently
     *
     * @param percentFromLeft
     *            a percentage, number from 0 to 1, indicating where to scrub to. Currently seeking to the very end is not reliable. Instead this method will adjust any value higher than .9 to .9. This insures we are not seeking out of bounds.
     */
    @Override
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

    public void chooseFeedIfNeeded(String aweBrandName, String feedName, String pickFeedServerURLID) throws WebDriverWrapperException {
        //Check if we are on feature screen
        if(AutomationOperations.instance().navOp.featured.isOnPage())
            return;

        AutomationOperations.instance().navOp.chooseFeeds.chooseBrandToPickFeed(aweBrandName);
        AutomationOperations.instance().navOp.chooseFeeds.pickFeedToAWEHome(feedName);
        selectFeed(pickFeedServerURLID);
    }

    private void selectFeed(String pickFeedServerURLId) {
        driverWrapper.getElementById(pickFeedServerURLId).click();
    }

    public int search(String searchTerm) throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.mainToolbarSearch();
        InputUtils utils = AutomationOperations.instance().deviceAutomationComponents.createInputUtils(driverWrapper);
        WebElement search = driverWrapper.getElementById(ResourceLocator.device.AWE_SEARCH_BAR_ENTER_TEXT);
        utils.sendKeysSafe(search, searchTerm);
        //Hit enter key, not sure why this is needed here but send keys doesn't trigger the search
        driverWrapper.pressKeyCode(ResourceLocator.device.KEYCODE_ENTER);

        //See how many search results there are on the page
        return driverWrapper.elements(By.id(ResourceLocator.device.AWE_SEARCH_RESULTS)).size();
    }

    @Override
    public void closedCaptionsOff() {
        AutomationOperations.instance().userOp.bringUpVideoUI();
        driverWrapper.getElementById("Closed Captioning On").click();
    }

    @Override
    public void shareShowFacebook() {
        throw new NotImplementedException("This has not been implemented on Android yet.");
    }

    @Override
    public void closedCaptionsOn() {
        AutomationOperations.instance().userOp.bringUpVideoUI();
        driverWrapper.getElementById("Closed Captioning Off").click();
    }

    public String getShowDetailsShowTitle() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_SHOW_DETAILS_SHOW_TITLE).getText();
    }

    @Override
    public void pauseVideo() {
        throw new NotImplementedException("This needs to be implemented for Android");
    }

    @Override
    public void playVideo() {
        throw new NotImplementedException("This needs to be implemented for Android");
    }

    public boolean isVideoPaused() {
        return driverWrapper.getElementById(ResourceLocator.device.AWE_VIDEO_PLAYER_PLAY_PAUSE).getText().equals("OFF");
    }

    @Override
    public boolean isVideoPlaying() {
        throw new NotImplementedException("This needs to be implemented for Android");
    }
}
