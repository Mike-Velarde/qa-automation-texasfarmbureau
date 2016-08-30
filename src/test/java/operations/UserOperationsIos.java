package operations;

import com.bottlerocket.errorhandling.WebDriverWrapperException;
import com.bottlerocket.utils.InputUtils;
import com.bottlerocket.webdriverwrapper.WebDriverWrapperIos;
import config.ResourceLocator;
import config.ResourceLocatorIos;
import domod.UserBank;
import io.appium.java_client.ios.IOSElement;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class UserOperationsIos extends UserOperations {

    public void signIn(UserBank.User user, boolean forced) throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.navigateUsingDrawer(ResourceLocator.DrawerNavigationItem.settings);
        //do we need to scroll down for smaller screens?

        if(!inLoginMode()) {
            if (forced) {
                signOut();
            } else {
                return;
            }
        }
        driverWrapper.getElementById(ResourceLocatorIos.AWE_SETTINGS_LOGIN_BUTTON_ID).click();
        driverWrapper.getElementBy(By.id(ResourceLocator.device.OPTIMUM), 60000).click();

        InputUtils utils = AutomationOperations.instance().deviceAutomationComponents.createInputUtils(driverWrapper);

        WebElement userName = driverWrapper.getElementByClassName(ResourceLocatorIos.UIA_TEXT_FIELD);
        WebElement password = driverWrapper.getElementByClassName(ResourceLocatorIos.UIA_SECURE_TEXT_FIELD);

        //Set username and password
        utils.sendKeysHideKeyboard(userName, user.username);
        utils.sendKeysSafe(password, user.password);

        utils.submitForm();
    }

    /**
     * Used to log user in when the screen hits content driven sign in auth flow.
     * This can be revisited if necessary, it just disregards the need to navigate
     * to settings and check if user is signed in
     *
     * @param user
     * @throws WebDriverWrapperException
     */
    @Override
    public void contentDrivenSignIn(UserBank.User user) {

        driverWrapper.getElementBy(By.id(ResourceLocator.device.OPTIMUM), 60000).click();

        InputUtils utils = AutomationOperations.instance().deviceAutomationComponents.createInputUtils(driverWrapper);

        WebElement userName = driverWrapper.getElementByClassName(ResourceLocatorIos.UIA_TEXT_FIELD);
        WebElement password = driverWrapper.getElementByClassName(ResourceLocatorIos.UIA_SECURE_TEXT_FIELD);

        //Set username and password
        utils.sendKeysHideKeyboard(userName, user.username);
        utils.sendKeysSafe(password, user.password);

        utils.submitForm();
    }

    /**
     * This method is not as forgiving as it's Android counterpart, for example it does not check that it's on the settings page and then navigate if not.
     *
     * @throws WebDriverWrapperException
     */
    @Override
    public void signOut() throws WebDriverWrapperException {
        WebElement loginLogoutButton = driverWrapper.getElementById(ResourceLocatorIos.AWE_SETTINGS_LOGOUT_BUTTON_ID);
        loginLogoutButton.click();
        AutomationOperations.instance().navOp.genericYesNoPopup(true);
        AutomationOperations.instance().navOp.genericOkPopup();
    }

    /**
     * Check to see if the button is in login or logout mode
     *
     * This may need to be expanded
     *
     * @return True if the button is in "login" mode, that is clicking it will start the login process
     */
    protected boolean inLoginMode() {
        return driverWrapper.elementExists(By.id(ResourceLocatorIos.AWE_SETTINGS_LOGIN_BUTTON_ID));
    }

    /**
     * Unlike Android, this must be called from the settings screen
     *
     * @return true if a user is logged in
     */
    public boolean isUserLoggedIn() {
        driverWrapper.setImplicitWait(10, TimeUnit.SECONDS);
        // If this element is there then a user is logged in
        boolean loggedIn = driverWrapper.elementExists(By.id(ResourceLocatorIos.AWE_SETTINGS_LOGOUT_BUTTON_ID));
        driverWrapper.restoreImplicitWait();
        return loggedIn;
    }

    @Override
    public void scrubVideo(double absolutePercent) {
        IOSElement seekBar = (IOSElement)driverWrapper.getElementById("awe_slidervideotransportcontrols_tint");
        seekBar.setValue(String.valueOf(absolutePercent));
    }

    /**
     * Not needed for iOS
     *
     * @param aweBrandName
     * @param feedName
     * @param pickFeedServerURLID
     */
    @Override
    public void chooseFeedIfNeeded(String aweBrandName, String feedName, String pickFeedServerURLID) {}

    public int search(String searchTerm) throws WebDriverWrapperException {
        AutomationOperations.instance().navOp.mainToolbarSearch();
        InputUtils utils = AutomationOperations.instance().deviceAutomationComponents.createInputUtils(driverWrapper);
        utils.setTextField(searchTerm);
        //iOS is proving tricky to submit forms, and this one has the done keyboard customized to read Search
        ((WebDriverWrapperIos)driverWrapper).hideKeyboard("Search");

        //TODO labels needed
        //See how many search results there are on the page
        return -1;
    }

    @Override
    public void closedCaptionsOn() {
        throw new NotImplementedException("Not yet implemented on Android");
    }

    @Override
    public void closedCaptionsOff() {
        throw new NotImplementedException("Not yet implemented on Android");
    }

    @Override
    public void shareShowFacebook() {
        driverWrapper.getElementById(ResourceLocatorIos.AWE_SHOWS_SHARE_SHOW_BUTTON).click();
        driverWrapper.getElementById(ResourceLocatorIos.IOS_SHARE_APPS_FACEBOOK).click();
        driverWrapper.getElementById(ResourceLocatorIos.FACEBOOK_SHARE_SHOW_POST_BUTTON).click();
    }

    public String getShowDetailsShowTitle() {
        throw new NotImplementedException("this is not yet implemented for iOS");
    }

    @Override
    public void pauseVideo() {
        AutomationOperations.instance().userOp.bringUpVideoUI();
        driverWrapper.getElementById(ResourceLocatorIos.AWE_VIDEO_PLAYER_PAUSE).click();
    }

    @Override
    public void playVideo() {
        driverWrapper.getElementById(ResourceLocatorIos.AWE_VIDEO_PLAYER_PLAY).click();
    }

    @Override
    public boolean isVideoPaused() {
        return driverWrapper.elementExists(By.id(ResourceLocatorIos.AWE_VIDEO_PLAYER_PLAY));
    }

    @Override
    public boolean isVideoPlaying() {
        return driverWrapper.elementExists(By.id(ResourceLocatorIos.AWE_VIDEO_PLAYER_PAUSE));
    }

}
